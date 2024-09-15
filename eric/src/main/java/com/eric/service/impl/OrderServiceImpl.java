package com.eric.service.impl;

import cn.hutool.core.date.DateTime;
import com.eric.event.SubmitOrderEvent;
import com.eric.repository.IBasketDao;
import com.eric.repository.IOrderDao;
import com.eric.repository.IOrderItemDao;
import com.eric.repository.dto.OrderCountData;
import com.eric.repository.dto.ShopCartOrderMergerDto;
import com.eric.repository.entity.Order;
import com.eric.repository.entity.OrderItem;
import com.eric.service.OrderService;
import com.eric.service.ProductService;
import com.eric.service.ShopDetailService;
import com.eric.service.SkuService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private IOrderDao mOrderDao;
    @Resource
    private IOrderItemDao orderItemMapper;
    @Resource
    private IBasketDao mBasketDao;
    @Resource
    private SkuService mSkuService;
    @Resource
    private ProductService mProductService;
    @Resource
    private ShopDetailService mShopDetailService;
    @Resource
    private  ApplicationEventPublisher eventPublisher;

    @Override
    public Order getOrderByOrderNumber(String orderNumber) {
        return mOrderDao.getOrderByOrderNumber(orderNumber);
    }

    @Override
    @CachePut(cacheNames = "ConfirmOrderCache", key = "#userId")
    public ShopCartOrderMergerDto putConfirmOrderCache(String userId, ShopCartOrderMergerDto shopCartOrderMergerDto) {
        return shopCartOrderMergerDto;

    }

    @Override
    @Cacheable(cacheNames = "ConfirmOrderCache", key = "#userId")
    public ShopCartOrderMergerDto getConfirmOrderCache(String userId) {
        return null;
    }

    @Override
    @CacheEvict(cacheNames = "ConfirmOrderCache", key = "#userId")
    public void removeConfirmOrderCache(String userId) {

    }

    @Override
    public List<Order> submit(String userId, ShopCartOrderMergerDto mergerOrder) {
        List<Order> orderList = new ArrayList<>();
        // 通过事务提交订单
        eventPublisher.publishEvent(new SubmitOrderEvent(mergerOrder, orderList));
//         插入订单
        for (Order order : orderList) {
            mOrderDao.insert(order);
        }

        List<OrderItem> orderItems = orderList.stream().flatMap(order -> order.getOrderItems().stream()).collect(Collectors.toList());
//        // 插入订单项，返回主键
        for (OrderItem orderItem : orderItems) {
            orderItemMapper.insert(orderItem);

        }
        return orderList;
    }

    @Override
    public void delivery(Order order) {

    }

    @Override
    public List<Order> listOrderAndOrderItems(Integer orderStatus, DateTime lessThanUpdateTime) {
        return Collections.emptyList();
    }

    @Override
    public void cancelOrders(List<Order> orders) {

    }

    @Override
    public void confirmOrder(List<Order> orders) {

    }

    @Override
    public List<Order> listOrdersDetailByOrder(Order order, Date startTime, Date endTime) {
        return Collections.emptyList();
    }

    @Override
    public void deleteOrders(List<Order> orders) {

    }

    @Override
    public OrderCountData getOrderCount(String userId) {
        return null;
    }
}
