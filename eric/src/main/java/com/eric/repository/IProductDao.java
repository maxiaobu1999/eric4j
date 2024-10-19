package com.eric.repository;

import com.eric.core.domain.entity.UserEntity;
import com.eric.core.page.PageParam;
import com.eric.repository.dto.SearchProdDto;
import com.eric.repository.entity.Order;
import com.eric.repository.entity.Product;
import com.eric.repository.entity.Sku;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品
 */
@Repository
public interface IProductDao {
    /**
     * 根据userId获取查询用户信息
     */
    @Select("SELECT * FROM tz_prod where prod_id=#{prodId} ")
    List<Product> selectItem(Long prodId);


    @Select("SELECT * FROM tz_prod ")
    ArrayList<Product> selectAll();


    @Select("SELECT * FROM tz_prod WHERE  prod_id   BETWEEN #{param1}  AND #{param2}")
    ArrayList<Product> selectRange(int start, int end);

    @Update("UPDATE tz_prod SET " +
            "total_stocks=total_stocks - #{totalStocks}," + "version=version + 1 " +
            "WHERE prod_id=#{prodId} AND #{totalStocks} <= total_stocks")
    int updateStocks(Product product);

    /**
     * 根据商品名称和排序分页获取商品
     *
     * @param prodName
     * @param sort
     * @param orderBy  排序 orderBy sql语句冲突 改order
     * @return
     */
    @Select({
            "<script>",
            "SELECT  p.prod_id, p.pic,p.prod_name,p.price ,count(pc.prod_comm_id) as prod_comm_number, ",
            "count( CASE WHEN evaluate = 0 THEN prod_comm_id ELSE null END ) AS praise_number ",
            "FROM tz_prod p ",
            "LEFT JOIN tz_prod_comm pc ON  p.prod_id=pc.prod_id AND  pc.status=1 ",
            "WHERE prod_name LIKE CONCAT('%',#{prodName} ,'%')GROUP BY p.prod_id ",
            "<if test='sort == 0'> ",
            "ORDER BY p.update_time ",
            "</if> ",

            "<if test='sort == 1'> ",
            "ORDER BY p.sold_num ",
            "</if> ",

            "<if test='sort == 2'> ",
            "ORDER BY p.price ",
            "</if> ",

            "<if test='order == 0'> ",
            " ASC ",
            "</if> ",

            "<if test='order == 1'> ",
            " DESC ",
            "</if> ",
            "</script>"
    })
    List<SearchProdDto> getSearchProdDtoPageByProdName(int current, int size, @Param("prodName") String prodName, @Param("sort") int sort, @Param("order") int orderBy);

    @Insert("INSERT INTO tz_prod(shop_id,prod_name,ori_price,price,brief,pic,imgs,status," +
            "category_id,sold_num,total_stocks,delivery_mode,delivery_template_id,create_time,update_time,content,putaway_time," +
            "version)" +
            "values(#{shopId},#{prodName},#{oriPrice},#{price},#{brief},#{pic},#{imgs},#{status}," +
            "#{categoryId},#{soldNum},#{totalStocks},#{deliveryMode},#{deliveryTemplateId},NOW(),NOW(),#{content},#{putawayTime}," +
            "#{version})")
    @Options(useGeneratedKeys = true, keyProperty = "prodId", keyColumn = "prod_id")
//单条插入返回主键
    int insert(Product product);


}
