package com.eric.repository.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DiscountChartReqVo {
    @ApiModelProperty(value = "下拉框选择的日期时间段")
    private String date;

    @ApiModelProperty(value = "类型：day,month")
    private String type;

    @JsonIgnore
    private String startDate;

    @JsonIgnore
    private String endDate;
}
