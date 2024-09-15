package com.eric.repository;

import com.eric.repository.entity.UserAddrOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单地址
 */
@Repository
public interface IUserAddrOrderDao {
    @Insert("INSERT INTO tz_user_addr_order(addr_order_id,addr_id,user_id,receiver,province,city,area,addr,post_code,province_id,city_id,area_id,mobile,create_time,version)" +
            "values(#{addrOrderId},#{addrId},#{userId},#{receiver},#{province},#{city},#{area},#{addr},#{postCode},#{provinceId},#{cityId},#{areaId},#{mobile},#{createTime},#{version})")
    int save(UserAddrOrder userAddrOrder);

    @Select("SELECT * FROM tz_user_addr_order where order_number=#{orderNumber} ")
    UserAddrOrder getById(Long orderNumber);

}
