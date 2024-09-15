package com.eric.service.impl;

import com.eric.repository.IBasketDao;
import com.eric.repository.IUserAddrOrderDao;
import com.eric.repository.entity.UserAddrOrder;
import com.eric.service.UserAddrOrderService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class UserAddrOrderServiceImpl implements UserAddrOrderService {
    @Resource
    private IUserAddrOrderDao mUserAddrOrderDao;

    @Override
    public void save(UserAddrOrder userAddrOrder) {
        mUserAddrOrderDao.save(userAddrOrder);
    }

    @Override
    public UserAddrOrder getById(Long orderNumber) {
        return mUserAddrOrderDao.getById(orderNumber);
    }
}
