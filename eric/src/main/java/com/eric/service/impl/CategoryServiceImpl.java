package com.eric.service.impl;

import com.eric.repository.ICategoryDao;
import com.eric.repository.IProductDao;
import com.eric.repository.entity.CategoryEntity;
import com.eric.repository.entity.Product;
import com.eric.service.CategoryService;
import com.eric.service.ProductService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Primary // 解决 expected single matching bean but found 2: accountServiceImpl,accountService
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private ICategoryDao mCategoryDao;
    @Override
    public CategoryEntity getItem(Long prodId) {
        List<CategoryEntity> list = mCategoryDao.selectItem(prodId);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<CategoryEntity> SelectAll() {
        List<CategoryEntity> list = mCategoryDao.selectAll();
        return list;
    }

    /**
     * 新品推荐
     */
    @Override
    public List<CategoryEntity> selectRange(int start, int end) {
        List<CategoryEntity> list = mCategoryDao.selectRange(start, end);

        return list;
    }
}
