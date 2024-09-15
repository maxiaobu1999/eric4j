package com.eric.repository;

import com.eric.repository.dto.MyOrderDto;
import com.eric.repository.dto.MyOrderItemDto;
import com.eric.repository.dto.ShopCartItemDto;
import com.eric.repository.entity.Basket;
import com.eric.repository.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 产品类型
 */
@Repository
public interface IOrderDao {

    /**
     * 根据订单编号获取订单
     *
     * @param orderNumber
     * @return
     */
    @Select("SELECT * FROM tz_order where order_number=#{orderNumber} ")
    Order getOrderByOrderNumber(@Param("orderNumber") String orderNumber);

    /**
     * 根据用户id和订单状态获取订单列表
     *
     * @param userId
     * @param status
     * @return
     */
    @Results({
            @Result(property = "orderNumber", column = "order_number"),
            @Result(property = "orderItemDtos", javaType = List.class, column = "order_number", many = @Many(select = "listMyOrderItemByOrderNum"))
    })
    @Select({
            "<script>",
            "SELECT  * FROM tz_order WHERE user_id=#{userId} ",
            "<if test='status !=null and status !=0'> ",
            "AND status=#{status} ",
            "</if> ",
            "ORDER BY create_time DESC",
            "</script>"
    })
    List<MyOrderDto> listMyOrderByUserIdAndStatus(@Param("userId") String userId, @Param("status") int status);

    @Select({"select oi.* from tz_order_item oi ",
            "inner join tz_order o on o.order_number = oi.order_number ",
            "where oi.order_number=#{orderNum} "
    })
    List<MyOrderItemDto> listMyOrderItemByOrderNum(@Param("orderNum") String orderNum);


    @Insert("INSERT INTO tz_order(order_id,shop_id,prod_name,user_id,order_number,total,actual_total,pay_type," +
            "remarks,status,dvy_type,dvy_id,dvy_flow_id,freight_amount,addr_order_id,product_nums,create_time," +
            "update_time,pay_time,dvy_time,finally_time,cancel_time,is_payed,delete_status,refund_sts,reduce_amount)" +
            "values(#{orderId},#{shopId},#{prodName},#{userId},#{orderNumber},#{total},#{actualTotal},#{payType}," +
            "#{remarks},#{status},#{dvyType},#{dvyId},#{dvyFlowId},#{freightAmount},#{addrOrderId},#{productNums},NOW()," +
            "NOW(),#{payTime},#{dvyTime},#{finallyTime},#{cancelTime},#{isPayed},#{deleteStatus},#{refundSts},#{reduceAmount})")
    int insert(Order entity);

    /**
     * 获取购物项
     *
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
     *
     * @param userId    用户id
     * @param basketIds 购物车id列表
     */
    @Delete({
            "<script>",
            "DELETE  FROM tz_basket",
            "WHERE user_id=#{userId} ",
            " AND basket_id IN ",
            "<foreach collection='basketIds' item='id' OPEN='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    int deleteShopCartItemsByBasketIds(@Param("userId") String userId, @Param("basketIds") List<Long> basketIds);


    /**
     * 更新订单为支付成功
     *
     * @param orderNumbers 订单编号列表
     * @param payType      支付类型
     */
    @Update({
            "<script>",
            "UPDATE tz_order SET status=2, is_payed=1, update_time=NOW(), pay_time=NOW(), pay_type =#{payType} WHERE order_number IN ( ",
            "<foreach collection='orderNumbers' OPEN='(' item='value' separator=',' close=')'>",
            "#{value} ",
            "</foreach>",
            "</script>"
    })
    void updateByToPaySuccess(@Param("orderNumbers") List<String> orderNumbers, @Param("payType") Integer payType);


    /**
     * 根据userId获取删除用户信息
     */
    @Delete("delete from tz_order where user_id=#{userId} ")
    void deleteAll(String userId);


}
