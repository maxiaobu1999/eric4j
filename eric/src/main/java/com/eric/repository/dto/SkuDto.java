/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.eric.repository.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * @author lanhai
 */
public class SkuDto implements Serializable {

    private static final long serialVersionUID = 6457261945829470666L;

    @Schema(description = "skuId" , required = true)
    private Long skuId;
    @Schema(description = "价格" , required = true)
    private Double price;
    @Schema(description = "原价" )
    private Double oriPrice;

    @Schema(description = "库存(-1表示无穷)" , required = true)
    private Integer stocks;

    @Schema(description = "sku名称" , required = true)
    private String skuName;

    @Schema(description = "图片" )
    private String pic;

    @Schema(description = "销售属性组合字符串,格式是p1:v1;p2:v2" , required = true)
    private String properties;
}
