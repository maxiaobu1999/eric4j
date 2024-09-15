package com.eric.service.impl;

import com.eric.repository.IOrderDao;
import com.eric.repository.dto.MyOrderDto;
import com.eric.service.MyOrderService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class MyOrderServiceImpl implements MyOrderService {
    @Resource
    private IOrderDao mOrderDao;

    @Override
    public List<MyOrderDto> pageMyOrderByUserIdAndStatus( String userId, Integer status) {
        List<MyOrderDto> myOrderDtos = mOrderDao.listMyOrderByUserIdAndStatus(userId, status);
        return myOrderDtos;
    }
}
