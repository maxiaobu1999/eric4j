package com.eric.service;

import com.eric.repository.entity.Product;

import java.util.List;

/**
 * 商品
 */
public interface ProductService {
    /**
     * 根据商品id获取商品信息
     *
     * @param prodId
     * @return
     */
    Product getItem(Long prodId);

    /**
     * 根据商品id获取商品信息
     *
     * @return
     */
    List<Product> SelectAll();
}
