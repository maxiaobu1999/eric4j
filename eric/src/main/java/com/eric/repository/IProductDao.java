package com.eric.repository;

import com.eric.core.domain.entity.UserEntity;
import com.eric.core.page.PageParam;
import com.eric.repository.dto.SearchProdDto;
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

    @Update("update tz_prod set " +
            "total_stocks=total_stocks - #{totalStocks}," + "version=version + 1 " +
            "where prod_id=#{prodId} AND #{totalStocks} <= total_stocks")
    int updateStocks(Product product);

    /**
     * 根据商品名称和排序分页获取商品
     *
     * @param prodName
     * @param sort
     * @param orderBy 排序 orderBy sql语句冲突 改order
     * @return
     */
    @Select({
            "<script>",
            "SELECT  p.prod_id, p.pic,p.prod_name,p.price ,count(pc.prod_comm_id) as prod_comm_number FROM tz_prod p ",
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
    List<SearchProdDto> getSearchProdDtoPageByProdName(int current, int size, @Param("prodName") String prodName, @Param("sort") int sort,@Param("order") int orderBy);
//    @Select("SELECT p.prod_id, p.pic,p.prod_name,p.price,count(pc.prod_comm_id) as prod_comm_number FROM tz_prod p " +
//            "LEFT JOIN tz_prod_comm pc ON  p.prod_id=pc.prod_id AND  pc.`status`=1 " +
//            "where prod_name like concat('%',#{prodName} ,'%') GROUP BY p.prod_id ")


}
