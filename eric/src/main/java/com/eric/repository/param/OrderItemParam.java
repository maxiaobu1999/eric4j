package com.eric.repository.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 */
@Data
@Schema(description = "购物车物品参数")
public class OrderItemParam {

	@NotNull(message = "产品ID不能为空")
	@Schema(description = "产品ID" ,required=true)
	private Long prodId;

	@NotNull(message = "skuId不能为空")
	@Schema(description = "skuId" ,required=true)
	private Long skuId;

	@NotNull(message = "产品数量不能为空")
	@Min(value = 1,message = "产品数量不能为空")
	@Schema(description = "产品数量" ,required=true)
	private Integer prodCount;

	@NotNull(message = "店铺id不能为空")
	@Schema(description = "店铺id" ,required=true)
	private Long shopId;

	@Schema(description = "推广员使用的推销卡号" )
	private String distributionCardNo;
}
