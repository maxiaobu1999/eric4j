package com.eric.repository;

import com.eric.repository.entity.Basket;
import com.eric.repository.entity.ShopDetail;
import com.eric.repository.entity.UserAddr;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IUserAddrDao {
    @Select("SELECT * FROM tz_user_addr where user_id=#{userId} AND common_addr = 1")
    UserAddr getDefaultUserAddr(String userId);

    @Update("update tz_user_addr set " +
            "common_addr=0 " +
            "where user_id=#{userId}")
    void removeDefaultUserAddr(@Param("userId") String userId);

    @Update("update tz_user_addr set " +
            "common_addr=1 " +
            "where user_id=#{userId} AND addr_id=#{addrId}")
    int setDefaultUserAddr(@Param("addrId") Long addrId, @Param("userId") String userId);


    @Select("SELECT * FROM tz_user_addr where user_id=#{userId} AND addr_id =#{addrId}")
    UserAddr getUserAddrByUserIdAndAddrId(@Param("userId") String userId,@Param("addrId")  Long addrId);


    @Select("SELECT * FROM tz_user_addr where user_id=#{userId}  ORDER BY common_addr DESC, update_time DESC")
    List<UserAddr> list(String userId);



    @Insert("INSERT INTO tz_user_addr(addr_id,user_id,receiver,province_id,province,city,city_id,area,area_id,post_code,addr,mobile,status,common_addr,create_time,version,update_time)" +
            "values(#{addrId},#{userId},#{receiver},#{provinceId},#{province},#{city},#{cityId},#{area},#{areaId},#{postCode},#{addr},#{mobile},#{status},#{commonAddr},#{createTime},#{version},#{updateTime})")
    int insert(UserAddr userAddr);


    @Update("UPDATE tz_user_addr SET " +
            "user_id=#{userId}," +
            "receiver=#{receiver}," +
            "province_id=#{provinceId}," +
            "province=#{province}," +
            "city=#{city}," +
            "city_id=#{cityId}," +
            "area=#{area}," +
            "area_id=#{areaId}," +
            "post_code=#{postCode}," +
            "addr=#{addr}," +
            "mobile=#{mobile}," +
            "status=#{status}," +
            "common_addr=#{commonAddr}," +
            "version=#{version}," +
            "update_time=Now() " +
            "WHERE addr_id=#{addrId}")
    int updateById( UserAddr userAddr);

    @Delete("delete from tz_user_addr where addr_id=#{addrId} ")
    void removeById(@Param("addrId") Long addrId);
}
