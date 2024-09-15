package com.eric.controller;

import cn.hutool.core.bean.BeanUtil;
import com.eric.BaseResponse;
import com.eric.constant.Constant;
import com.eric.jwt.JwtUtils;
import com.eric.repository.dto.*;
import com.eric.repository.entity.Order;
import com.eric.repository.entity.OrderItem;
import com.eric.repository.entity.ShopDetail;
import com.eric.repository.entity.UserAddrOrder;
import com.eric.repository.enums.OrderStatus;
import com.eric.repository.param.PayParam;
import com.eric.service.*;
import com.eric.utils.Arith;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController // 相当于@ResponseBody和@Controller
@RequestMapping(value = "/myOrder")// 配置url映射,一级
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@Api(value = "activity", description = "我的订单接口")
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class MyOrderController  extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(MyOrderController.class);
    @Resource
    OrderService mOrderService;

    @Resource
    private  UserAddrOrderService mUserAddrOrderService;

    @Resource
    private ProductService mProductService;

    @Resource
    private SkuService mSkuService;

    @Resource
    private  MyOrderService mMyOrderService;

    @Resource
    private  ShopDetailService mShopDetailService;

    @Resource
    private  OrderItemService mOrderItemService;


    @ApiOperation("订单详情信息")
    @Operation(summary = "订单详情信息", description = "根据订单号获取订单详情信息")
    @Parameter(name = "orderNumber", description = "订单号", required = true)
    @RequestMapping(value = {"/orderDetail"}, method = {RequestMethod.POST})
    public BaseResponse<OrderShopDto> orderDetail(HttpServletRequest request, @RequestParam(value = "orderNumber") String orderNumber) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("normalPay" + ",userId:" + userId);
        OrderShopDto orderShopDto = new OrderShopDto();

        Order order = mOrderService.getOrderByOrderNumber(orderNumber);
        if (order == null) {
            throw new RuntimeException("该订单不存在");
        }
        if (!Objects.equals(order.getUserId(), userId)) {
            throw new RuntimeException("你没有权限获取该订单信息");
        }
        ShopDetail shopDetail = mShopDetailService.getShopDetailByShopId(order.getShopId());
        UserAddrOrder userAddrOrder = mUserAddrOrderService.getById(order.getAddrOrderId());
        UserAddrDto userAddrDto = BeanUtil.copyProperties(userAddrOrder, UserAddrDto.class);
        List<OrderItem> orderItems = mOrderItemService.getOrderItemsByOrderNumber(orderNumber);
        List<OrderItemDto> orderItemList = BeanUtil.copyToList(orderItems, OrderItemDto.class);

        orderShopDto.setShopId(shopDetail.getShopId());
        orderShopDto.setShopName(shopDetail.getShopName());
        orderShopDto.setActualTotal(order.getActualTotal());
        orderShopDto.setUserAddrDto(userAddrDto);
        orderShopDto.setOrderItemDtos(orderItemList);
        orderShopDto.setTransfee(order.getFreightAmount());
        orderShopDto.setReduceAmount(order.getReduceAmount());
        orderShopDto.setCreateTime(order.getCreateTime());
        orderShopDto.setRemarks(order.getRemarks());
        orderShopDto.setStatus(order.getStatus());

        double total = 0.0;
        Integer totalNum = 0;
        for (OrderItemDto orderItem : orderShopDto.getOrderItemDtos()) {
            total = Arith.add(total, orderItem.getProductTotalAmount());
            totalNum += orderItem.getProdCount();
        }
        orderShopDto.setTotal(total);
        orderShopDto.setTotalNum(totalNum);
        return BaseResponse.success(orderShopDto);
    }

    @ApiOperation("订单列表信息")
    @Operation(summary = "订单列表信息", description = "根据订单状态获取订单列表信息，状态为0时获取所有订单")
    @Parameters({
            @Parameter(name = "status", description = "订单状态 1:待付款 2:待发货 3:待收货 4:待评价 5:成功 6:失败")
    })
    @RequestMapping(value = {"/myOrder"}, method = {RequestMethod.POST})
    public BaseResponse<List<MyOrderDto>> myOrder(HttpServletRequest request, @RequestParam(value = "status") Integer status,  int pageNum, int pageSize) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("myOrder" + ",userId:" + userId);
        PageHelper.startPage(pageNum,pageSize);
        List<MyOrderDto> myOrderDtos = mMyOrderService.pageMyOrderByUserIdAndStatus(userId, status);
        return BaseResponse.success(myOrderDtos);
    }

    @ApiOperation("根据订单号取消订单")
    @Operation(summary = "根据订单号取消订单", description = "根据订单号取消订单")
    @Parameter(name = "orderNumber", description = "订单号", required = true)
    @RequestMapping(value = {"/cancel/{orderNumber}"}, method = {RequestMethod.POST})
    public BaseResponse<String> cancel(HttpServletRequest request, @PathVariable("orderNumber") String orderNumber) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("myOrder" + ",userId:" + userId);
        Order order = mOrderService.getOrderByOrderNumber(orderNumber);
        if (!Objects.equals(order.getUserId(), userId)) {
            throw new RuntimeException("你没有权限获取该订单信息");
        }
        if (!Objects.equals(order.getStatus(), OrderStatus.UNPAY.value())) {
            throw new RuntimeException("订单已支付，无法取消订单");
        }
        List<OrderItem> orderItems = mOrderItemService.getOrderItemsByOrderNumber(orderNumber);
        order.setOrderItems(orderItems);
        // 取消订单
        mOrderService.cancelOrders(Collections.singletonList(order));

        // 清除缓存
        for (OrderItem orderItem : orderItems) {
            mProductService.removeProductCacheByProdId(orderItem.getProdId());
            mSkuService.removeSkuCacheBySkuId(orderItem.getSkuId(), orderItem.getProdId());
        }
        return BaseResponse.success();
    }

    @ApiOperation("根据订单号确认收货")
    @Operation(summary = "根据订单号确认收货", description = "根据订单号确认收货")
    @RequestMapping(value = {"/receipt/{orderNumber}"}, method = {RequestMethod.POST})
    public BaseResponse<String> receipt(HttpServletRequest request, @PathVariable("orderNumber") String orderNumber) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("myOrder" + ",userId:" + userId);
        Order order = mOrderService.getOrderByOrderNumber(orderNumber);
        if (!Objects.equals(order.getUserId(), userId)) {
            throw new RuntimeException("你没有权限获取该订单信息");
        }
        if (!Objects.equals(order.getStatus(), OrderStatus.CONSIGNMENT.value())) {
            throw new RuntimeException("订单未发货，无法确认收货");
        }
        List<OrderItem> orderItems = mOrderItemService.getOrderItemsByOrderNumber(orderNumber);
        order.setOrderItems(orderItems);
        // 确认收货
        mOrderService.confirmOrder(Collections.singletonList(order));

        for (OrderItem orderItem : orderItems) {
            mProductService.removeProductCacheByProdId(orderItem.getProdId());
            mSkuService.removeSkuCacheBySkuId(orderItem.getSkuId(), orderItem.getProdId());
        }
        return BaseResponse.success();
    }

    @ApiOperation("根据订单号删除订单")
    @Operation(summary = "根据订单号删除订单", description = "根据订单号删除订单")
    @Parameter(name = "orderNumber", description = "订单号", required = true)
    @RequestMapping(value = {"/{orderNumber}"}, method = {RequestMethod.POST})
    public BaseResponse<String> delete(HttpServletRequest request, @PathVariable("orderNumber") String orderNumber) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("delete" + ",userId:" + userId);
        Order order = mOrderService.getOrderByOrderNumber(orderNumber);
        if (order == null) {
            throw new RuntimeException("该订单不存在");
        }
        if (!Objects.equals(order.getUserId(), userId)) {
            throw new RuntimeException("你没有权限获取该订单信息");
        }
        if (!Objects.equals(order.getStatus(), OrderStatus.SUCCESS.value()) && !Objects.equals(order.getStatus(), OrderStatus.CLOSE.value())) {
            throw new RuntimeException("订单未完成或未关闭，无法删除订单");
        }

        // 删除订单
        mOrderService.deleteOrders(Collections.singletonList(order));
        return BaseResponse.success("删除成功");
    }

    @ApiOperation("获取我的订单订单数量")
    @Operation(summary = "获取我的订单订单数量", description = "获取我的订单订单数量")
    @RequestMapping(value = {"/orderCount"}, method = {RequestMethod.POST})
    public BaseResponse<OrderCountData> getOrderCount(HttpServletRequest request) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = String.valueOf(JwtUtils.getUserId(token));
        logger.info("getOrderCount" + ",userId:" + userId);
        OrderCountData orderCountMap = mOrderService.getOrderCount(userId);
        return BaseResponse.success(orderCountMap);
    }
}
