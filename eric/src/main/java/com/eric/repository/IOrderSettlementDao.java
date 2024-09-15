package com.eric.repository;

import com.eric.repository.entity.Basket;
import com.eric.repository.entity.OrderSettlement;
import com.eric.repository.entity.ShopDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 订单支付
 */
public interface IOrderSettlementDao {


    @Insert("INSERT INTO tz_order_settlement(settlement_id,pay_no,biz_pay_no,order_number,pay_type,pay_amount,user_id,is_clearing,create_time,clearing_time,pay_status,version)" +
            "values(#{settlementId},#{payNo},#{bizPayNo},#{orderNumber},#{payType},#{payAmount},#{userId},#{isClearing},#{createTime},#{clearingTime},#{payStatus},#{version})")
    int insert(OrderSettlement orderSettlement);


    /**
     * 更新订单结算
     *
     * @param orderSettlement
     */
    @Update({
            "<script>",
            "UPDATE  tz_order_settlement SET ",
            "<if test='#{param.payNo} != null '> ",
            "pay_no=#{param.payNo}, ",
            "</if> ",
            "<if test='#{param.payType} != null'> ",
            "pay_type=#{param.payType}, ",
            "</if> ",
            "<if test='#{param.isClearing} != null'> ",
            "is_clearing=#{param.isClearing}, ",
            "</if> ",
            "<if test='#{param.clearingTime} != null'> ",
            "clearing_time=#{param.clearingTime}, ",
            "</if> ",
            "order_number=#{param.orderNumber}  ",
            "WHERE order_number=#{param.orderNumber} AND user_id=#{param.userId} ",
            "</script>"
    })

    void updateByOrderNumberAndUserId(@Param("param") OrderSettlement orderSettlement);

//    @Update("update tz_order_settlement set " +
//            "IF #{payNo} != NULL THEN pay_no=#{payNo},  END " +
//            "IF #{payType} != NULL THEN pay_type=#{payType},  END  " +
//            "IF #{isClearing} != NULL THEN is_clearing=#{isClearing},  END  " +
//            "IF #{clearingTime} != NULL THEN clearing_time=#{clearingTime},  END  " +
//            "order_number=#{orderNumber} " +
//            "where order_number=#{orderNumber} AND user_id=#{userId}")


    /**
     * 根据支付单号更新结算
     * @param outTradeNo
     * @param transactionId
     */
    @Update("update tz_order_settlement " +
            "IF #{outTradeNo}!=NULL AND #{transactionId}!=NULL THEN "+
            "SET " +
            "biz_pay_no=#{transactionId}, is_clearing = 1, pay_status = 1" +
            "where pay_no=#{outTradeNo}")
    void updateSettlementsByPayNo(String outTradeNo, String transactionId);

    /**
     * 更新结算信息为已支付
     * @param payNo
     * @param version
     * @return
     */
    @Update("UPDATE tz_order_settlement SET " +
            "pay_status = 1," +
            "version = version +1 " +
            "WHERE pay_no=#{payNo} AND version=#{version}")
    int updateToPay(@Param("payNo") String payNo, @Param("version")Integer version);

    /**
     * 获取结算信息
     *
     * @param payNo 支付单号
     * @return 结算信息
     */
    @Select("SELECT * FROM tz_order_settlement where pay_no=#{payNo} ")
    List<OrderSettlement> selectList(String payNo);

}
