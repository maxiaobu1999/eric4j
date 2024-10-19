package com.eric.repository;

import com.eric.core.domain.entity.UserEntity;
import com.eric.repository.entity.CategoryEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品类型
 */
@Repository
public interface ICategoryDao {
    /**
     * 根据userId获取查询用户信息
     */
    @Select("SELECT * FROM tz_category WHERE parent_id=#{parentId} AND status = 1  ORDER BY seq")
//    @Results({
//            @Result(id=true,property="id",column="category_id"),
//            @Result(property="name",column="category_name"),
//            @Result(property="pic",column="pic")
//    })
    List<CategoryEntity> listByParentId(Long parentId);

    /**
     * 根据userId获取查询用户信息
     */
    @Select("SELECT * FROM tz_category where category_id=#{id} ")
    @Results({
            @Result(id=true,property="id",column="category_id"),
            @Result(property="name",column="category_name"),
            @Result(property="pic",column="pic")
    })
    List<CategoryEntity> selectItem(Long id);


    @Select("SELECT * FROM tz_category ")
    ArrayList<CategoryEntity> selectAll();



    @Select("SELECT * FROM tz_category WHERE  category_id   BETWEEN #{param1}  AND #{param2}")
//    @Results({
//            @Result(id=true,property="id",column="category_id"),
//            @Result(property="name",column="category_name"),
//            @Result(property="pic",column="pic")
//    })
    ArrayList<CategoryEntity> selectRange(int start, int end);








    /**
     * 增加一条
     *
     * @param item 用户信息
     */
//    @Insert("INSERT INTO user(userId,phoneNum,userName,password," + "avatar," + "nickname)" +
    @Insert("INSERT INTO user(userId,phoneNum,userName,password,avatar,nickName,token,salt)" +
            "values(#{userId},#{phoneNum},#{userName},#{password}" + ",#{avatar}" + ",#{nickName},#{token},#{salt})")
    int insertItem(UserEntity item);

    @Select("SELECT * FROM user ")
    ArrayList<UserEntity> queryAllUser();



    /**
     * 根据userId获取删除用户信息
     */
    @Delete("DELETE FROM user WHERE userId=#{userId} ")
    void deleteByUserId(Long userId);
    /**
     * 根据username获取查询用户信息
     */
    @Select("SELECT * FROM user where userName=#{userName} ")
    List<UserEntity> queryByUsername(String userName);


    /**
     * 根据userId更新
     */
    @Update("update user set phoneNum=#{phoneNum}," + "userName=#{userName}," + "password=#{password}," +
            "avatar=#{avatar}," + "nickName=#{nickName}," + "token=#{token} where userId=#{userId}")
    void updateByUserId(UserEntity user);


    /**
     * 根据 phoneNum 获取查询用户信息
     */
    @Select("SELECT * FROM user where phoneNum=#{phoneNum} ")
    List<UserEntity> findByphoneNum(String phoneNum);


}
