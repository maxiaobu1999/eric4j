package com.eric.repository.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class CategoryEntity {

    /**
     * 类目ID
     *
     */
    private Long categoryId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 父节点
     */
    private Long parentId = 0L;

    /**
     * 产品类目名称
     */
    private String categoryName;

    /**
     * 类目图标
     */
    private String icon;

    /**
     * 类目的显示图片
     */
    private String pic;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 默认是1，表示正常状态,0为下线状态
     */
    private Integer status;

    /**
     * 记录时间
     */
    private Date recTime;

    /**
     * 分类层级
     */
    private Integer grade;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 品牌id
     */
    private List<Long> brandIds;

    /**
     * 参数id
     */
    private List<Long> attributeIds;

    /**
     * 品牌列表
     */
    private List<Brand> brands;

    /**
     * 参数列表
     */
    private List<ProdProp> prodProps;

    /**
     * 商品列表
     */
    private List<Product> products;

    private List<CategoryEntity> categories;

}
