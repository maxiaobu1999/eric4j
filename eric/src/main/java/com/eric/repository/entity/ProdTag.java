package com.eric.repository.entity;


import java.io.Serializable;
import java.util.Date;

/**
 * 商品分组标签
 *
 */
public class ProdTag implements Serializable {

    private static final long serialVersionUID = 1991508792679311621L;
    /**
     * 分组标签id
     */
    private Long id;
    /**
     * 分组标题
     */
    private String title;
    /**
     * 店铺Id
     */
    private Long shopId;
    /**
     * 状态(1为正常,0为删除)
     */
    private Integer status;
    /**
     * 默认类型(0:商家自定义,1:系统默认类型)
     */
    private Integer isDefault;
    /**
     * 商品数量
     */
    private Long prodCount;
    /**
     * 排序
     */
    private Integer seq;
    /**
     * 列表样式(0:一列一个,1:一列两个,2:一列三个)
     */
    private Integer style;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 删除时间
     */
    private Date deleteTime;

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Long getProdCount() {
        return prodCount;
    }

    public void setProdCount(Long prodCount) {
        this.prodCount = prodCount;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
