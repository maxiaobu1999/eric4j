package com.eric.repository;

import com.eric.repository.entity.Product;
import com.eric.repository.entity.SysMenu;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * 导航菜单
 */
@Repository
public interface SysMenuDao {
    /**
     * 查询用户的所有菜单
     */
    @Select("SELECT DISTINCT m.menu_id, m.parent_id, m.name, m.url, m.type, m.icon, m.order_num FROM tz_sys_user_role ur " +
            "LEFT JOIN tz_sys_role_menu rm ON ur.role_id = rm.role_id LEFT JOIN tz_sys_menu m ON m.`menu_id` = rm.`menu_id` " +
            "WHERE ur.user_id = #{userId} and m.type != 2 order by order_num")
    @Results({
            @Result(id=true,property="menuId",column="menu_id"),
            @Result(property="parentId",column="parent_id"),
            @Result(property="orderNum",column="order_num")
    })
    ArrayList<SysMenu> listMenuByUserId(String userId);

    @Select("SELECT * FROM tz_sys_menu ")
    @Results({
            @Result(id=true,property="menuId",column="menu_id"),
            @Result(property="parentId",column="parent_name"),
            @Result(property="name",column="name")
    })
    ArrayList<SysMenu> selectAll();
}
