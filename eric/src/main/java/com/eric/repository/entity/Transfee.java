/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.eric.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

/**
 * 运费 费用
 */
//@TableName("tz_transfee")
public class Transfee implements Serializable {
    private static final long serialVersionUID = 8039640964056626028L;

    /**
     * 运费项id
     */
    @Schema(description = "运费项id" ,required=true)
    private Long transfeeId;

    /**
     * 运费模板id
     */

    @Schema(description = "运费模板id" ,required=true)
    private Long transportId;

    /**
     * 续件数量
     */

    @Schema(description = "续件数量" ,required=true)
    private Double continuousPiece;

    /**
     * 首件数量
     */

    @Schema(description = "首件数量" ,required=true)
    private Double firstPiece;

    /**
     * 续件费用
     */

    @Schema(description = "续件费用" ,required=true)
    private Double continuousFee;

    /**
     * 首件费用
     */

    @Schema(description = "首件费用" ,required=true)
    private Double firstFee;

    @Schema(description = "指定条件运费城市项" ,required=true)
    private List<Area> cityList;

    public Long getTransfeeId() {
        return transfeeId;
    }

    public void setTransfeeId(Long transfeeId) {
        this.transfeeId = transfeeId;
    }

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
    }

    public Double getContinuousPiece() {
        return continuousPiece;
    }

    public void setContinuousPiece(Double continuousPiece) {
        this.continuousPiece = continuousPiece;
    }

    public Double getFirstPiece() {
        return firstPiece;
    }

    public void setFirstPiece(Double firstPiece) {
        this.firstPiece = firstPiece;
    }

    public Double getContinuousFee() {
        return continuousFee;
    }

    public void setContinuousFee(Double continuousFee) {
        this.continuousFee = continuousFee;
    }

    public Double getFirstFee() {
        return firstFee;
    }

    public void setFirstFee(Double firstFee) {
        this.firstFee = firstFee;
    }

    public List<Area> getCityList() {
        return cityList;
    }

    public void setCityList(List<Area> cityList) {
        this.cityList = cityList;
    }

}
