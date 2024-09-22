package com.eric.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.eric.BaseResponse;
import com.eric.constant.Constant;
import com.eric.event.ConfirmOrderEvent;
import com.eric.exception.base.BaseException;
import com.eric.jwt.JwtUtils;
import com.eric.repository.dto.*;
import com.eric.repository.entity.Order;
import com.eric.repository.entity.Product;
import com.eric.repository.entity.Sku;
import com.eric.repository.entity.UserAddr;
import com.eric.repository.param.OrderParam;
import com.eric.repository.param.OrderShopParam;
import com.eric.repository.param.SubmitOrderParam;
import com.eric.service.*;
import com.eric.utils.Arith;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController // 相当于@ResponseBody和@Controller
@RequestMapping(value = "/order")// 配置url映射,一级
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@Api(value = "activity", description = "订单接口")
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class OrderController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private ProductService mProductService;
    @Resource
    private SkuService mSkuService;
    @Resource
    private UserAddrService mUserAddrService;
    @Resource
    private BasketService mBasketService;
    @Resource
    private OrderService mOrderService;
    @Resource
    private ApplicationContext applicationContext;
    /**
     * 生成订单
     */
    @ApiOperation("结算，生成订单信息")
    @ApiImplicitParam(name = "OrderParam", value = "下单所需要的参数", paramType = "body", required = true, dataType = "OrderParam")
    @RequestMapping(value = {"/confirm"}, method = {RequestMethod.POST})
    public BaseResponse<ShopCartOrderMergerDto> confirm(HttpServletRequest request, @Valid @RequestBody OrderParam orderParam) {
        logger.info("changeItem" + ",orderParam:" + orderParam);
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("changeItem" + ",userId:" + userId);
        // 订单的地址信息
        UserAddr userAddr = mUserAddrService.getUserAddrByUserId(orderParam.getAddrId(), userId);
        UserAddrDto userAddrDto = BeanUtil.copyProperties(userAddr, UserAddrDto.class);


        // 组装获取用户提交的购物车商品项
        List<ShopCartItemDto> shopCartItems = mBasketService.getShopCartItemsByOrderItems(orderParam.getBasketIds(), orderParam.getOrderItem(), userId);

        if (CollectionUtil.isEmpty(shopCartItems)) {
            return BaseResponse.fail("请选择您需要的商品加入购物车");
        }

        // 根据店铺组装购车中的商品信息，返回每个店铺中的购物车商品信息
        List<ShopCartDto> shopCarts = mBasketService.getShopCarts(shopCartItems);

        // 将要返回给前端的完整的订单信息
        ShopCartOrderMergerDto shopCartOrderMergerDto = new ShopCartOrderMergerDto();

        shopCartOrderMergerDto.setUserAddr(userAddrDto);

        // 所有店铺的订单信息
        List<ShopCartOrderDto> shopCartOrders = new ArrayList<>();

        double actualTotal = 0.0;
        double total = 0.0;
        int totalCount = 0;
        double orderReduce = 0.0;
        for (ShopCartDto shopCart : shopCarts) {

            // 每个店铺的订单信息
            ShopCartOrderDto shopCartOrder = new ShopCartOrderDto();
            shopCartOrder.setShopId(shopCart.getShopId());
            shopCartOrder.setShopName(shopCart.getShopName());


            List<ShopCartItemDiscountDto> shopCartItemDiscounts = shopCart.getShopCartItemDiscounts();

            // 店铺中的所有商品项信息
            List<ShopCartItemDto> shopAllShopCartItems = new ArrayList<>();
            for (ShopCartItemDiscountDto shopCartItemDiscount : shopCartItemDiscounts) {
                List<ShopCartItemDto> discountShopCartItems = shopCartItemDiscount.getShopCartItems();
                shopAllShopCartItems.addAll(discountShopCartItems);
            }

            shopCartOrder.setShopCartItemDiscounts(shopCartItemDiscounts);

            applicationContext.publishEvent(new ConfirmOrderEvent(shopCartOrder,orderParam,shopAllShopCartItems));
            // 计算订单金额
            calActual(shopCartOrder, orderParam, shopAllShopCartItems, userId);

            actualTotal = Arith.add(actualTotal, shopCartOrder.getActualTotal());
            total = Arith.add(total, shopCartOrder.getTotal());
            totalCount = totalCount + shopCartOrder.getTotalCount();
            orderReduce = Arith.add(orderReduce, shopCartOrder.getShopReduce());
            shopCartOrders.add(shopCartOrder);
        }
        shopCartOrderMergerDto.setActualTotal(actualTotal);
        shopCartOrderMergerDto.setTotal(total);
        shopCartOrderMergerDto.setTotalCount(totalCount);
        shopCartOrderMergerDto.setShopCartOrders(shopCartOrders);
        shopCartOrderMergerDto.setOrderReduce(orderReduce);

        shopCartOrderMergerDto = mOrderService.putConfirmOrderCache(userId, shopCartOrderMergerDto);

        return BaseResponse.success(shopCartOrderMergerDto);
    }

    private void calActual(ShopCartOrderDto shopCartOrderDto, OrderParam orderParam, List<ShopCartItemDto> shopAllShopCartItems, String userId) throws BaseException {

        UserAddr userAddr = mUserAddrService.getUserAddrByUserId(orderParam.getAddrId(), userId);

        double total = 0.0;

        int totalCount = 0;

        double transfee = 0.0;

        for (ShopCartItemDto shopCartItem : shopAllShopCartItems) {
            // 获取商品信息
            Product product = mProductService.getItem(shopCartItem.getProdId());
            // 获取sku信息
            Sku sku = mSkuService.getSkuBySkuId(shopCartItem.getSkuId());
            if (product == null || sku == null) {
                throw new BaseException("购物车包含无法识别的商品");
            }
            if (product.getStatus() != 1 || sku.getStatus() != 1) {
                throw new BaseException("商品[" + sku.getProdName() + "]已下架");
            }

            totalCount = shopCartItem.getProdCount() + totalCount;
            total = Arith.add(shopCartItem.getProductTotalAmount(), total);
//            todo
            // 用户地址如果为空，则表示该用户从未设置过任何地址相关信息
//            if (userAddr != null) {
//                // 每个产品的运费相加
//                transfee = Arith.add(transfee, transportManagerService.calculateTransfee(shopCartItem, userAddr));
//            }

            shopCartItem.setActualTotal(shopCartItem.getProductTotalAmount());
            shopCartOrderDto.setActualTotal(Arith.add(total, transfee));
            shopCartOrderDto.setTotal(total);
            shopCartOrderDto.setTotalCount(totalCount);
            shopCartOrderDto.setTransfee(transfee);
        }
    }


    /**
     * 购物车/立即购买  提交订单,根据店铺拆单
     */
    @PostMapping("/submit")
    @Operation(summary = "提交订单，返回支付流水号", description = "根据传入的参数判断是否为购物车提交订单，同时对购物车进行删除，用户开始进行支付")
    public BaseResponse<OrderNumbersDto> submitOrders(HttpServletRequest request, @Valid @RequestBody SubmitOrderParam submitOrderParam) {
        logger.info("submitOrders" + ",submitOrderParam:" + submitOrderParam);
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("submitOrders" + ",userId:" + userId);
        ShopCartOrderMergerDto mergerOrder = mOrderService.getConfirmOrderCache(userId);
        logger.info("submitOrders" + ",mergerOrder:" + mergerOrder);
        if (mergerOrder == null) {
            return BaseResponse.fail("订单已过期，请重新下单");
        }

        List<OrderShopParam> orderShopParams = submitOrderParam.getOrderShopParam();

        List<ShopCartOrderDto> shopCartOrders = mergerOrder.getShopCartOrders();
        // 设置备注
        if (CollectionUtil.isNotEmpty(orderShopParams)) {
            for (ShopCartOrderDto shopCartOrder : shopCartOrders) {

                for (OrderShopParam orderShopParam : orderShopParams) {
                    if (Objects.equals(shopCartOrder.getShopId(), orderShopParam.getShopId())) {
                        shopCartOrder.setRemarks(orderShopParam.getRemarks());
                    }
                }
            }
        }

        List<Order> orders = mOrderService.submit(userId, mergerOrder);


        StringBuilder orderNumbers = new StringBuilder();
        for (Order order : orders) {
            orderNumbers.append(order.getOrderNumber()).append(",");
        }
        orderNumbers.deleteCharAt(orderNumbers.length() - 1);

        boolean isShopCartOrder = false;
        // 移除缓存
        for (ShopCartOrderDto shopCartOrder : shopCartOrders) {
            for (ShopCartItemDiscountDto shopCartItemDiscount : shopCartOrder.getShopCartItemDiscounts()) {
                for (ShopCartItemDto shopCartItem : shopCartItemDiscount.getShopCartItems()) {
                    Long basketId = shopCartItem.getBasketId();
                    if (basketId != null && basketId != 0) {
                        isShopCartOrder = true;
                    }
                    mSkuService.removeSkuCacheBySkuId(shopCartItem.getSkuId(), shopCartItem.getProdId());
                    mProductService.removeProductCacheByProdId(shopCartItem.getProdId());
                }
            }
        }
        // 购物车提交订单时(即有购物车ID时)
        if (isShopCartOrder) {
            mBasketService.removeShopCartItemsCacheByUserId(userId);
        }
        mOrderService.removeConfirmOrderCache(userId);
        return BaseResponse.success(new OrderNumbersDto(orderNumbers.toString()));
    }

}
