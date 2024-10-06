package com.eric.service.impl;

import com.eric.repository.IProductTagDao;
import com.eric.repository.entity.ProdTag;
import com.eric.repository.entity.Product;
import com.eric.service.ProdTagService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 商品分组标签
 *
 */
@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class ProdTagServiceImpl implements ProdTagService {

    @Resource
    private IProductTagDao mProductTagDao;

    @Override
    @Cacheable(cacheNames = "prodTag", key = "'prodTag'")
    public List<ProdTag> listProdTag() {
        return mProductTagDao.selectAll();
    }

    @Override
    public List<ProdTag> list(ProdTag prodTag) {
        return mProductTagDao.list(prodTag);
    }

    @Override
    public ProdTag getItem(Long id) {
        List<ProdTag> list = mProductTagDao.selectItem(id);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int removeById(Long id) {
       return mProductTagDao.removeById(id);
    }

    @Override
    public boolean save(ProdTag prodTag) {
        return mProductTagDao.save(prodTag);
    }

    @Override
    public boolean updateById(ProdTag prodTag) {
        return mProductTagDao.updateById(prodTag);
    }

    @Override
    @CacheEvict(cacheNames = "prodTag", key = "'prodTag'")
    public void removeProdTag() {
    }
}
