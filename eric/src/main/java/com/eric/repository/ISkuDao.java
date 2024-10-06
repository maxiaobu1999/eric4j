package com.eric.repository;

import com.eric.repository.entity.ShopDetail;
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
    @Select("SELECT * FROM tz_sku WHERE sku_id=#{skuId} ")
    Sku selectById(Long skuId);


    @Update("UPDATE tz_sku SET " +
            "stocks=stocks - #{stocks}," + "version=version + 1," + "update_time=NOW()" +
            "WHERE sku_id=#{skuId} AND #{stocks} <= stocks")
    int updateStocks(Sku sku);

    /**
     * 批量插入sku
     *
     * @param prodId  商品id
     * @param skuList sku列表
     */
    @Insert({
            "<script>",
            "INSERT INTO tz_sku(prod_id,properties,ori_price,price,stocks,actual_stocks,update_time,rec_time,party_code,model_id,pic,sku_name,prod_name,version,weight,volume,status,is_delete) ",
            "VALUES ",
            "<foreach collection='skuList'  item='sku' separator=','>",
            "(",
            "#{prodId},#{sku.properties},#{sku.oriPrice},#{sku.price},#{sku.stocks},#{sku.actualStocks},NOW(),NOW(),#{sku.partyCode},#{sku.modelId},#{sku.pic},#{sku.skuName},#{sku.prodName},0,#{sku.weight},#{sku.volume},#{sku.status},0",
            ")",
            "</foreach>",
            "</script>"
    })
    void insertBatch(@Param("prodId") Long prodId, @Param("skuList") List<Sku> skuList);

    /**
     * 根据商品id删除sku
     *
     * @param prodId
     */

    void deleteByProdId(@Param("prodId") Long prodId);
}
