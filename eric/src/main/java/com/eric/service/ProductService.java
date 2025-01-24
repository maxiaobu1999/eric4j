package com.eric.service;

import com.eric.repository.dto.ProductDto;
import com.eric.repository.dto.SearchProdDto;
import com.eric.repository.entity.Product;

import java.util.List;

/**
 * 商品
 */
public interface ProductService {
    /**
     * 根据商品id获取商品信息
     *
     * @param prodId
     * @return
     */
    Product getItem(Long prodId);

    /**
     * 根据商品id获取商品信息
     *
     * @return
     */
    List<Product> selectAll();

    List<Product> selectRange(int start, int end);

    /**
     * 根据商品id删除缓存
     * @param prodId
     */
    void removeProductCacheByProdId(Long prodId);

    /**
     * 根据商品名称模糊查询 搜索

     * @param prodName
     * @param sort
     * @param orderBy
     * @return
     */
    List<SearchProdDto> getSearchProdDtoPageByProdName(int current,int size, String prodName, int sort, int orderBy);

    /**
     * 保存商品
     *
     * @param product 商品信息
     */
    void saveProduct(Product product);

    /**
     * 根据商品id删除商品
     * @param prodId
     */
    void removeProductByProdId(Long prodId);

    /**
     * 根据标签分页获取商品
     * @param tagId
     * @return
     */
    List<ProductDto> pageByTagId(Long tagId);

    /**
     * 根据分类id分页获取商品列表
     * @param categoryId
     * @return
     */
    List<ProductDto> pageByCategoryId( Long categoryId);
}
