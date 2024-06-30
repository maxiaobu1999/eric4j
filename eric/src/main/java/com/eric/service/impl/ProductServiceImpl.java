package com.eric.service.impl;

import com.eric.repository.IProductDao;
import com.eric.repository.entity.Product;
import com.eric.service.ProductService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private IProductDao mProductDao;
    @Override
    public Product getItem(Long prodId) {
        List<Product> list = mProductDao.selectItem(prodId);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Product> SelectAll() {
        List<Product> list = mProductDao.selectAll();
        return list;
    }
}
