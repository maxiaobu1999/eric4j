package com.eric.repository;

import com.eric.repository.entity.ProdTag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组标签引用
 */
@Repository
public interface IProdTagReferenceDao {

    /**
     * 插入标签
     * @param shopId
     * @param prodId
     * @param tagList
     */
    @Insert({
            "<script>",
            "INSERT INTO tz_prod_tag_reference(shop_id,tag_id,prod_id,status,create_time) ",
            "VALUES ",
            "<foreach collection='tagList' item='item' separator=',' >",
            "(",
            "#{shopId},#{item},#{prodId},1,NOW()",
            ")",
            "</foreach>",
            "</script>"
    })
    void insertBatch(@Param("shopId") Long shopId, @Param("prodId") Long prodId, @Param("tagList") List<Long> tagList);
    /**
     * 根据id获取标签id列表
     * @param prodId 商品ID
     * @return 分组id集合
     */
    @Select("SELECT tag_id FROM tz_prod_tag_reference where prod_id=#{prodId} ")
    List<Long> listTagIdByProdId(Long prodId);

    @Delete("DELETE FROM tz_prod_tag_reference WHERE prod_id=#{prodId} ")
    void delete(Long prodId);

}
