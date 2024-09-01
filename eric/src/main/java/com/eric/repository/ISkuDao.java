package com.eric.repository;

import com.eric.repository.entity.Sku;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账户信息
 */
@Repository
public interface ISkuDao {

    /**
     * 商品的SKU列表
     * prodId 商品id
     */
    @Select("SELECT * FROM tz_sku where prod_id=#{prodId} AND is_delete=0 AND status=1")
    List<Sku> listByProdId(Long prodId);

    /**
     * 商品的SKU列表
     * prodId 商品id
     */
    @Select("SELECT * FROM tz_sku where sku_id=#{skuId} ")
    Sku selectById(Long skuId);
}
