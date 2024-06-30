package com.eric.repository;

import com.eric.core.domain.entity.SysUser;
import com.eric.repository.entity.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 账户信息
 */
@Repository
public interface IProductDao {
    /**
     * 根据userId获取查询用户信息
     */
    @Select("SELECT * FROM mall_prod where prod_id=#{prodId} ")
    List<Product> selectItem(Long prodId);


    @Select("SELECT * FROM mall_prod ")
    @Results({
            @Result(id=true,property="prodId",column="prod_id"),
            @Result(property="prodName",column="prod_name"),
            @Result(property="oriPrice",column="ori_price")
    })
    ArrayList<Product> selectAll();












    /**
     * 增加一条
     *
     * @param item 用户信息
     */
//    @Insert("INSERT INTO user(userId,phoneNum,userName,password," + "avatar," + "nickname)" +
    @Insert("INSERT INTO user(userId,phoneNum,userName,password,avatar,nickName,token,salt)" +
            "values(#{userId},#{phoneNum},#{userName},#{password}" + ",#{avatar}" + ",#{nickName},#{token},#{salt})")
    int insertItem(SysUser item);

    @Select("SELECT * FROM user ")
    ArrayList<SysUser> queryAllUser();



    /**
     * 根据userId获取删除用户信息
     */
    @Delete("delete from user where userId=#{userId} ")
    void deleteByUserId(Long userId);
    /**
     * 根据username获取查询用户信息
     */
    @Select("SELECT * FROM user where userName=#{userName} ")
    List<SysUser> queryByUsername(String userName);


    /**
     * 根据userId更新
     */
    @Update("update user set phoneNum=#{phoneNum}," + "userName=#{userName}," + "password=#{password}," +
            "avatar=#{avatar}," + "nickName=#{nickName}," + "token=#{token} where userId=#{userId}")
    void updateByUserId(SysUser user);


    /**
     * 根据 phoneNum 获取查询用户信息
     */
    @Select("SELECT * FROM user where phoneNum=#{phoneNum} ")
    List<SysUser> findByphoneNum(String phoneNum);


}
