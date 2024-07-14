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
import java.util.List;

/**
 * 商品分组标签
 *
 * @author hzm
 * @date 2019-04-18 10:48:44
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
    public ProdTag getItem(Long id) {
        List<ProdTag> list = mProductTagDao.selectItem(id);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    @Override
    @CacheEvict(cacheNames = "prodTag", key = "'prodTag'")
    public void removeProdTag() {
    }
}
