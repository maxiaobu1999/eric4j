package com.eric.service;

import com.eric.repository.entity.Product;
import com.eric.repository.entity.Sku;

import java.util.List;

/**
 * 商品
 */
public interface SkuService {
    /**
     * 根据商品id获取商品中的sku列表（将会被缓存起来）
     *
     * @param prodId 商品id
     * @return sku列表
     */
    List<Sku> listByProdId(Long prodId);

    /**
     * 根据skuId获取sku信息（将会被缓存起来）
     * @param skuId
     * @return
     */
    Sku getSkuBySkuId(Long skuId);

    /**
     * 根据商品id和skuId删除缓存
     * @param skuId
     * @param prodId
     */
    void removeSkuCacheBySkuId(Long skuId,Long prodId);
}
