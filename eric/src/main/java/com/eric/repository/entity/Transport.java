package com.eric.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 配送方式
 */
//@TableName("tz_transport")
public class Transport implements Serializable {
    private static final long serialVersionUID = 1876655654053364580L;


    /**
     * 运费模板id
     */
    @Schema(description = "运费模板id" ,required=true)
    private Long transportId;

    /**
     * 运费模板名称
     */

    @Schema(description = "运费模板名称" ,required=true)
    private String transName;

    /**
     * 创建时间
     */

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间" ,required=true)
    private Date createTime;

    /**
     * 店铺id
     */

    @Schema(description = "店铺id" ,required=true)
    private Long shopId;

    /**
     * 参考 TransportChargeType
     * 收费方式（0 按件数,1 按重量 2 按体积）
     */
    @Schema(description = "收费方式（0 按件数,1 按重量 2 按体积）" ,required=true)
    private Integer chargeType;


   /**
    * 是否包邮 0:不包邮 1:包邮
    */
    @Schema(description = "是否包邮 0:不包邮 1:包邮" ,required=true)
    private Integer isFreeFee;

    /**
     * 是否含有包邮条件
     */
    @Schema(description = "是否含有包邮条件" ,required=true)
    private Integer hasFreeCondition;

    /**
     * 指定条件包邮项
     */
    @Schema(description = "指定条件包邮项" ,required=true)
    private List<TransfeeFree> transfeeFrees;

    /**
     * 运费项
     */
    @Schema(description = "运费项" ,required=true)
    private List<Transfee> transfees;


    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public Integer getIsFreeFee() {
        return isFreeFee;
    }

    public void setIsFreeFee(Integer isFreeFee) {
        this.isFreeFee = isFreeFee;
    }

    public Integer getHasFreeCondition() {
        return hasFreeCondition;
    }

    public void setHasFreeCondition(Integer hasFreeCondition) {
        this.hasFreeCondition = hasFreeCondition;
    }

    public List<TransfeeFree> getTransfeeFrees() {
        return transfeeFrees;
    }

    public void setTransfeeFrees(List<TransfeeFree> transfeeFrees) {
        this.transfeeFrees = transfeeFrees;
    }

    public List<Transfee> getTransfees() {
        return transfees;
    }

    public void setTransfees(List<Transfee> transfees) {
        this.transfees = transfees;
    }
}
