package com.eric.repository;

import com.eric.repository.dto.ShopCartItemDto;
import com.eric.repository.entity.Basket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 产品类型
 */
@Repository
public interface IBasketDao {

    @Insert("INSERT INTO tz_basket(shop_id,prod_id,sku_id,user_id,basket_count,basket_date,discount_id,distribution_card_no)" +
            "values(#{shopId},#{prodId},#{skuId},#{userId},#{basketCount},#{basketDate},#{discountId},#{distributionCardNo})")
    int insert(Basket entity);
    /**
     * 获取购物项
     * @param userId 用户id
     * @return 购物项列表
     */
    @Select("SELECT  tb.*,tb.basket_count as prod_count,tsd.shop_name,IFNULL(ts.pic,tp.pic) AS pic,ts.price,ts.ori_price,tp.brief,ts.properties,ts.prod_name,ts.sku_name " +
            "FROM tz_basket tb " +
            "LEFT JOIN tz_shop_detail tsd ON tb.shop_id = tsd.shop_id " +
            "LEFT JOIN tz_prod tp ON tb.prod_id = tp.prod_id " +
            "LEFT JOIN  tz_sku ts ON tb.sku_id = ts.sku_id " +
            "WHERE tb.user_id = #{userId}  order by tb.basket_id DESC")
        List<ShopCartItemDto> getShopCartItems(@Param("userId") String userId);

    /**
     * 根据Id更新
     */
    @Update("UPDATE tz_basket SET shop_id=#{shopId}," + "prod_id=#{prodId}," + "sku_id=#{skuId}," +
            "user_id=#{userId}," + "basket_count=#{basketCount}," + "basket_date=#{basketDate}," +
            "discount_id=#{discountId}," + "distribution_card_no=#{distributionCardNo}" +
            " WHERE basket_id=#{basketId}")
    void updateById(Basket basket);

    /**
     * 根据Id更新购物车item数量
     */
    @Update("UPDATE tz_basket SET basket_count=#{basketCount}" +
            " WHERE basket_id=#{basketId}")
    void updateBasketCount(Basket basket);
    /**
     * 根据购物车id列表和用户id删除购物车
     * @param userId 用户id
     * @param basketIds 购物车id列表
     */
    @Delete({
            "<script>",
            "DELETE  FROM tz_basket",
            "WHERE user_id=#{userId} ",
            " AND basket_id IN ",
            "<foreach collection='basketIds' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    int deleteShopCartItemsByBasketIds(@Param("userId") String userId, @Param("basketIds") List<Long> basketIds);

    /**
     * 根据userId获取删除用户信息
     */
    @Delete("delete from tz_basket where user_id=#{userId} ")
    void deleteAll(String userId);

}
