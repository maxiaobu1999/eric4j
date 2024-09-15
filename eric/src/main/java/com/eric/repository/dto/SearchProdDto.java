package com.eric.repository.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 */
@Data
@Schema(description = "搜索商品数据")
public class SearchProdDto {

    @Schema(description = "商品id" )
    private Long prodId;

    @Schema(description = "商品照片" )
    private String pic;

    @Schema(description = "商品名字" )
    private String prodName;

    @Schema(description = "商品价格" )
    private Double price;

    @Schema(description = "商品评论数量" )
    private Integer prodCommNumber;

    @Schema(description = "好评率" )
    private Double positiveRating;

    @Schema(description = "好评数量" )
    private Integer praiseNumber;

}
