package com.eric.repository.dto;

import com.eric.repository.entity.Sku;
import com.eric.repository.entity.Transport;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 */
@Data
public class ProductDto {


    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID" , required = true)
    private Long shopId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称" , required = true)
    private String shopName;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID" , required = true)
    private Long prodId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称" )
    private String prodName;

    /**
     * 商品价格
     */
    @Schema(description = "商品价格" , required = true)
    private Double price;

    /**
     * 商品详情
     */
    @Schema(description = "详细描述" )
    private String content;

    /**
     * 商品原价
     */
    @Schema(description = "商品原价" , required = true)
    private Double oriPrice;

    /**
     * 库存量
     */
    @Schema(description = "库存量" , required = true)
    private Integer totalStocks;

    /**
     * 简要描述,卖点等
     */
    @Schema(description = "简要描述,卖点等" , required = true)
    private String brief;

    /**
     * 商品主图
     */
    @Schema(description = "商品主图" , required = true)
    private String pic;

    @Schema(description = "商品图片列表，以逗号分割" , required = true)
    private String imgs;

    /**
     * 商品分类
     */
    @Schema(description = "商品分类id" , required = true)
    private Long categoryId;

    @Schema(description = "sku列表" )
    private List<Sku> skuList;

    @Schema(description = "运费信息" , required = true)
    private Transport transport;


}
