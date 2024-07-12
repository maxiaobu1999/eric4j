package com.eric.service;

import com.eric.repository.entity.CategoryEntity;
import com.eric.repository.entity.Product;

import java.util.List;

/**
 * 商品类别
 */
public interface CategoryService {
    /**
     * 根据商品id获取商品信息
     *
     * @param prodId
     * @return
     */
    CategoryEntity getItem(Long prodId);

    /**
     * 根据商品id获取商品信息
     *
     * @return
     */
    List<CategoryEntity> SelectAll();

    List<CategoryEntity> selectRange(int start, int end);



}
