package com.eric.controller;


import cn.hutool.core.map.MapUtil;
import com.eric.BaseResponse;
import com.eric.constant.Constant;
import com.eric.jwt.JwtUtils;
import com.eric.repository.dto.ShopCartDto;
import com.eric.repository.dto.ShopCartItemDto;
import com.eric.repository.entity.Basket;
import com.eric.repository.entity.Product;
import com.eric.repository.entity.Sku;
import com.eric.repository.param.ChangeShopCartParam;
import com.eric.repository.param.ShopCartParam;
import com.eric.service.BasketService;
import com.eric.service.ProductService;
import com.eric.service.SkuService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController // 相当于@ResponseBody和@Controller
@RequestMapping(value = "/shopCart")// 配置url映射,一级
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class ShopCartController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ShopCartController.class);

    @Resource
    private BasketService mBasketService;
    @Resource
    private ProductService mProductService;
    @Resource
    private SkuService mSkuService;
    /**
     * 获取用户购物车信息
     *
     * @param basketIdShopCartParamMap 购物车参数对象列表
     * @return
     */
    @PostMapping("/info")
    @Operation(summary = "获取用户购物车信息" , description = "获取用户购物车信息，参数为用户选中的活动项数组,以购物车id为key")
    public BaseResponse<List<ShopCartDto>> info(HttpServletRequest request, @RequestBody(required = false) Map<Long, ShopCartParam> basketIdShopCartParamMap) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));

        // 更新购物车信息，
        if (MapUtil.isNotEmpty(basketIdShopCartParamMap)) {
            mBasketService.updateBasketByShopCartParam(userId, basketIdShopCartParamMap);
        }

        // 拿到购物车的所有item
        List<ShopCartItemDto> shopCartItems = mBasketService.getShopCartItems(userId);
        List<ShopCartDto> res = mBasketService.getShopCarts(shopCartItems);
        return BaseResponse.success(res);
    }

    @DeleteMapping("/deleteItem")
//    @RequestMapping(value = {"/deleteItem"}, method = {RequestMethod.POST})
    @Operation(summary = "删除用户购物车物品" , description = "通过购物车id删除用户购物车物品")
    public BaseResponse<Void> deleteItem(HttpServletRequest request, @RequestBody List<Long> basketIds) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        mBasketService.deleteShopCartItemsByBasketIds(userId, basketIds);
        return BaseResponse.success();
    }

    @DeleteMapping("/deleteAll")
    @Operation(summary = "清空用户购物车所有物品" , description = "清空用户购物车所有物品")
    public BaseResponse<String> deleteAll(HttpServletRequest request) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        mBasketService.deleteAllShopCartItems(userId);
        return BaseResponse.success("删除成功");
    }

    @RequestMapping(value = {"/changeItem"}, method = {RequestMethod.POST})
    @Operation(summary = "添加、修改用户购物车物品", description = "通过商品id(prodId)、skuId、店铺Id(shopId),添加/修改用户购物车商品，并传入改变的商品个数(count)，" +
            "当count为正值时，增加商品数量，当count为负值时，将减去商品的数量，当最终count值小于0时，会将商品从购物车里面删除")
    public BaseResponse<String> changeItem(HttpServletRequest request, @Valid @RequestBody ChangeShopCartParam param) {
        BaseResponse<String> responseEntity;
        try {
            logger.info("changeItem" + ",param:" + param);
            String token = request.getHeader(Constant.ACCESS_TOKEN);
            String userId = String.valueOf(JwtUtils.getUserId(token));
            logger.info("changeItem" + ",userId:" + userId);

            if (param.getCount() == 0) {
                return BaseResponse.fail("输入更改数量");
            }
            List<ShopCartItemDto> shopCartItems = mBasketService.getShopCartItems(userId);
            Product prodParam = mProductService.getItem(param.getProdId());
            Sku skuParam = mSkuService.getSkuBySkuId(param.getSkuId());
            // 当商品状态不正常时，不能添加到购物车
            if (prodParam.getStatus() != 1 || skuParam.getStatus() != 1) {
                return BaseResponse.fail("当前商品已下架");
            }
            // 1、更新现有购物车item
            for (ShopCartItemDto shopCartItemDto : shopCartItems) {
                if (Objects.equals(param.getSkuId(), shopCartItemDto.getSkuId())) {
                    Basket basket = new Basket();
                    basket.setUserId(userId);
                    basket.setBasketCount(param.getCount() + shopCartItemDto.getProdCount());
                    basket.setBasketId(shopCartItemDto.getBasketId());

                    // 防止购物车变成负数
                    if (basket.getBasketCount() <= 0) {
                        mBasketService.deleteShopCartItemsByBasketIds(userId, Collections.singletonList(basket.getBasketId()));
                        return BaseResponse.success();
                        }

                    // 当sku实际库存不足时，不能添加到购物车
                    if (skuParam.getStocks() < basket.getBasketCount() && shopCartItemDto.getProdCount() > 0) {
                        return BaseResponse.fail("库存不足");
                    }
                    mBasketService.updateShopCartItemCount(basket);
                    return BaseResponse.success();
                }
            }
            // 2、新增的购物车item
            // 防止购物车已被删除的情况下,添加了负数的商品
            if (param.getCount() < 0) {
                return BaseResponse.fail("商品已从购物车移除");
            }
            // 当sku实际库存不足时，不能添加到购物车
            if (skuParam.getStocks() < param.getCount()) {
                return BaseResponse.fail("库存不足");
            }
            // 所有都正常时
            mBasketService.addShopCartItem(param,userId);
            responseEntity = BaseResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.fail(e.getMessage());
        }
        return responseEntity;
    }
}
