
package com.eric.repository.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 */
@Schema(description = "我的订单-订单项")
@Data
public class MyOrderItemDto {

    @Schema(description = "商品图片" , required = true)
    private String pic;

    @Schema(description = "商品名称" , required = true)
    private String prodName;

    @Schema(description = "商品数量" , required = true)
    private Integer prodCount;

    @Schema(description = "商品价格" , required = true)
    private Double price;

    @Schema(description = "skuName" , required = true)
    private String skuName;

}
