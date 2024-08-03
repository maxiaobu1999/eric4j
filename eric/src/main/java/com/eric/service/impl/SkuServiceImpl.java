package com.eric.service.impl;

import com.eric.repository.ISkuDao;
import com.eric.repository.entity.Sku;
import com.eric.service.SkuService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class SkuServiceImpl implements SkuService {
    @Resource
    private ISkuDao mDao;

    @Override
    public List<Sku> listByProdId(Long prodId) {
        List<Sku> list = mDao.listByProdId(prodId);
        return list;
    }

}
