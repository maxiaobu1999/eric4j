package com.eric.service.impl;

import com.eric.repository.IOrderItemDao;
import com.eric.repository.entity.OrderItem;
import com.eric.service.OrderItemService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;


@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Resource
    private  IOrderItemDao orderItemMapper;
    @Override
    @Cacheable(cacheNames = "OrderItems", key = "#orderNumber")
    public List<OrderItem> getOrderItemsByOrderNumber(String orderNumber) {
        return orderItemMapper.listByOrderNumber(orderNumber);
    }
}
