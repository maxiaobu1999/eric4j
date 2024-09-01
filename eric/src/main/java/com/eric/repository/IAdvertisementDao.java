package com.eric.repository;

import com.eric.repository.entity.AdvertisementEntity;
import com.eric.repository.entity.Product;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 轮播图
 */
@Repository
public interface IAdvertisementDao {

    /**
     * 根据userId获取查询用户信息
     */
    @Select("SELECT * FROM tpl_advertisement ")
    @Results({
            @Result(id=true,property="name",column="advert_name"),
            @Result(id=true,property="description",column="advert_desc"),
            @Result(id=true,property="imageUrl",column="advert_images"),
            @Result(property="url",column="url")
    })
    List<AdvertisementEntity> selectAll();
}
