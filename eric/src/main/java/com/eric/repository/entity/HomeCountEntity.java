package com.eric.repository.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/** sys首页用户运营信息 */
@Data
public class HomeCountEntity {
    @ApiModelProperty(value = "用户注册数量")
    private Integer registerNum;

    @ApiModelProperty(value = "在用预付卡数量")
    private Integer usingPreCardsNum;

    @ApiModelProperty(value = "预付卡在线申请数量")
    private Integer preCardsApplyNum;

    @ApiModelProperty(value = "当天在线客户")
    private Integer onLineNum;
}
