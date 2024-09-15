package com.eric.repository;

import com.eric.repository.dto.ShopCartItemDto;
import com.eric.repository.entity.Basket;
import com.eric.repository.entity.Order;
import com.eric.repository.entity.OrderItem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单拆分
 */
@Repository
public interface IOrderItemDao {


    /**
     * 根据订单编号获取订单项
     * @param orderNumber
     * @return
     */
    @Select("SELECT * FROM tz_order_item where order_number=#{orderNumber} ")
    List<OrderItem> listByOrderNumber(@Param("orderNumber") String orderNumber);

    /**
     * 插入订单项
     * @param orderItems
     */
    @Insert("INSERT INTO tz_order_item(order_item_id,shop_id,order_number,prod_id,sku_id,prod_count,prod_name,sku_name," +
            "pic,price,user_id,product_total_amount,rec_time,comm_sts,distribution_card_no,basket_date)" +
            "values(#{orderItemId},#{shopId},#{orderNumber},#{prodId},#{skuId},#{prodCount},#{prodName},#{skuName}," +
            "#{pic},#{price},#{userId},#{productTotalAmount},#{recTime},#{commSts},#{distributionCardNo},#{basketDate})")
    void insert(OrderItem orderItems);

}
