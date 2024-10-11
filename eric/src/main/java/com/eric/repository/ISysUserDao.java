package com.eric.repository;

import com.eric.core.domain.entity.UserEntity;
import com.eric.repository.entity.SysUser;
import com.eric.repository.entity.TransfeeFree;
import com.eric.repository.entity.Transport;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统用户
 */
@Repository
public interface ISysUserDao {

    @Select("SELECT * FROM tz_sys_user ")
    List<SysUser> selectAll();
    @Select("SELECT * FROM tz_sys_user WHERE  user_id=#{userId} ")
    SysUser selectItem(Long userId);

    @Select("SELECT shop_id FROM tz_sys_user WHERE  user_id=#{userId} ")
    Long selectShopId(Long userId);



}
