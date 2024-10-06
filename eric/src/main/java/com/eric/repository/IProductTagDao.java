package com.eric.repository;

import com.eric.core.domain.entity.UserEntity;
import com.eric.repository.entity.ProdTag;
import com.eric.repository.entity.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 账户信息
 */
@Repository
public interface IProductTagDao {
    /**
     * 根据userId获取查询用户信息
     */
    @Select("SELECT * FROM tz_prod_tag where id=#{id} ")
    List<ProdTag> selectItem(Long id);


    @Select("SELECT * FROM tz_prod_tag ")
    @Results({
            @Result(id=true,property="prodId",column="prod_id"),
            @Result(property="name",column="prod_name"),
            @Result(property="oriPrice",column="ori_price")
    })
    ArrayList<ProdTag> selectAll();

    @Select("SELECT * FROM tz_prod_tag WHERE title=#{title}")
    @Results({
            @Result(id=true,property="prodId",column="prod_id"),
            @Result(property="name",column="prod_name"),
            @Result(property="oriPrice",column="ori_price")
    })
    ArrayList<ProdTag> list(ProdTag prodTag);

    @Insert("INSERT INTO  tz_prod_tag(title,shop_Id,status,is_Default,prod_Count,seq,style,create_Time," +
            "update_Time,delete_Time)" +
            "values(#{title},#{shopId},#{status},#{isDefault},#{prodCount},#{seq},#{style},#{createTime}," +
            "#{updateTime},#{deleteTime})")

    boolean save(ProdTag prodTag);

    @Update("UPDATE tz_prod_tag  SET title=#{title}," + "shop_Id=#{shopId}," + "status=#{status}," +
            "is_Default=#{isDefault}," + "prod_Count=#{prodCount}," + "seq=#{seq}," +
            "style=#{style}," + "update_Time=Date()" +
            " WHERE id=#{id}")

    boolean updateById(ProdTag prodTag);

    /**
     * 根据userId获取删除用户信息
     */
    @Delete("delete from tz_prod_tag where id=#{id} ")
    int removeById(Long id);
}
