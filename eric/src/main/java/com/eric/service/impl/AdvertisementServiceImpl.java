package com.eric.service.impl;

import com.eric.repository.IAdvertisementDao;
import com.eric.repository.IProductDao;
import com.eric.repository.entity.AdvertisementEntity;
import com.eric.service.AdvertisementService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    @Resource
    private IAdvertisementDao mAdvertisementDao;

    @Override
    public List<AdvertisementEntity> selectAll() {
        return mAdvertisementDao.selectAll();
    }
}
