package com.eric.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.eric.core.page.PageParam;
import com.eric.repository.IProdTagReferenceDao;
import com.eric.repository.IProductDao;
import com.eric.repository.ISkuDao;
import com.eric.repository.dto.ProductDto;
import com.eric.repository.dto.SearchProdDto;
import com.eric.repository.entity.Product;
import com.eric.service.ProductService;
import com.eric.utils.Arith;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private IProductDao mProductDao;
    @Resource
    private ISkuDao mSkuDao;
    @Resource
    private IProdTagReferenceDao mProdTagReferenceDao;
    @Override
    public Product getItem(Long prodId) {
        List<Product> list = mProductDao.selectItem(prodId);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Product> selectAll() {
        List<Product> list = mProductDao.selectAll();
        return list;
    }

    /**
     * 新品推荐
     */
    @Override
    public List<Product> selectRange(int start, int end) {
        List<Product> list = mProductDao.selectRange(start, end);

        return list;
    }


    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "product", key = "#prodId"),
            @CacheEvict(cacheNames = "skuList", key = "#prodId")
    })
    public void removeProductCacheByProdId(Long prodId) {


    }

    @Override
    public List<SearchProdDto> getSearchProdDtoPageByProdName(int current,int size, String prodName, int sort, int orderBy) {
//        List<Product> searchProdDtoPage = null;
        List<SearchProdDto> searchProdDtoPage = mProductDao.getSearchProdDtoPageByProdName(current,size, prodName, sort, orderBy);
        for (SearchProdDto searchProdDto : searchProdDtoPage) {
            //计算出好评率
            if (searchProdDto.getPraiseNumber() == 0 || searchProdDto.getProdCommNumber() == 0) {
                searchProdDto.setPositiveRating(0.0);
            } else {
                searchProdDto.setPositiveRating(Arith.mul(Arith.div(searchProdDto.getPraiseNumber(), searchProdDto.getProdCommNumber()), 100));
            }
        }
        return searchProdDtoPage;
    }

    @Override
    public void saveProduct(Product product) {
       int prodId= mProductDao.insert(product);
        if (CollectionUtil.isNotEmpty(product.getSkuList())) {
            mSkuDao.insertBatch((long) prodId, product.getSkuList());
        }
        mProdTagReferenceDao.insertBatch(product.getShopId(), product.getProdId(), product.getTagList());
    }

    @Override
    public void removeProductByProdId(Long prodId) {
        Product dbProduct = mProductDao.selectItem(prodId).get(0);

        mProductDao.deleteById(prodId);

        mSkuDao.deleteByProdId(prodId);

        //删除商品关联的分组标签
        mProdTagReferenceDao.delete(prodId);

    }

    @Override
    public List<ProductDto> pageByTagId(Long tagId) {
        return mProductDao.pageByTagId( tagId);
    }

    @Override
    public List<ProductDto> pageByCategoryId(Long categoryId) {
        return mProductDao.pageByCategoryId( categoryId);

    }


}
