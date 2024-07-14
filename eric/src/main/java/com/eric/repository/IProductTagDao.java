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
}
