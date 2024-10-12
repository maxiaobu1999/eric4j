package com.eric.repository.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PigChartResVo {
    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "用户数")
    private Integer customerNum;

    @ApiModelProperty(value = "用户占比")
    private String customerRate;

}
