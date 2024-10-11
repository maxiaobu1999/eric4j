package com.eric.repository;

import com.eric.core.domain.entity.UserEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 账户信息
 */
@Repository
public interface IUserDao {
    /**
     * 增加一条
     *
     * @param item 用户信息
     */
    @Insert("INSERT INTO tz_user(user_id,nick_name,real_name,user_mail,login_password,pay_password,user_mobile,modify_time,user_regtime,sex,birth_date,pic,score,salt)" +
            "values(#{userId},#{nickName},#{realName},#{userMail},#{loginPassword},#{payPassword},#{userMobile},#{modifyTime},#{userRegtime},#{sex},#{birthDate},#{pic},#{score},#{salt})")
    int insertItem(UserEntity item);

    @Select("SELECT * FROM tz_user ")
    ArrayList<UserEntity> queryAllUser();

    /**
     * 根据userId获取查询用户信息
     */
    @Select("SELECT * FROM tz_user where user_id=#{userId} ")
    List<UserEntity> queryByUserId(Long userId);

    /**
     * 根据userId获取删除用户信息
     */
    @Delete("DELETE FROM tz_user WHERE userId=#{userId} ")
    void deleteByUserId(Long userId);
    /**
     * 根据username获取查询用户信息
     */
    @Select("SELECT * FROM tz_user where real_name=#{realName} ")
    List<UserEntity> queryByName(String realName);


    /**
     * 根据userId更新
     */
    @Update("update tz_user set phoneNum=#{phoneNum}," + "userName=#{userName}," + "password=#{password}," +
            "avatar=#{avatar}," + "nickName=#{nickName}," + "token=#{token} where userId=#{userId}")
    void updateByUserId(UserEntity user);


    /**
     * 根据 phoneNum 获取查询用户信息
     */
    @Select("SELECT * FROM tz_user where phoneNum=#{phoneNum} ")
    List<UserEntity> findByphoneNum(String phoneNum);


}
