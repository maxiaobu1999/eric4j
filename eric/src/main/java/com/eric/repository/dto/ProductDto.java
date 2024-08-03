package com.eric.repository.dto;

import com.eric.repository.entity.Sku;
import com.eric.repository.entity.Transport;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author lanhai
 */
public class ProductDto {


    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID" , required = true)
    private Long shopId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称" , required = true)
    private String shopName;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID" , required = true)
    private Long prodId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称" )
    private String prodName;

    /**
     * 商品价格
     */
    @Schema(description = "商品价格" , required = true)
    private Double price;

    /**
     * 商品详情
     */
    @Schema(description = "详细描述" )
    private String content;

    /**
     * 商品原价
     */
    @Schema(description = "商品原价" , required = true)
    private Double oriPrice;

    /**
     * 库存量
     */
    @Schema(description = "库存量" , required = true)
    private Integer totalStocks;

    /**
     * 简要描述,卖点等
     */
    @Schema(description = "简要描述,卖点等" , required = true)
    private String brief;

    /**
     * 商品主图
     */
    @Schema(description = "商品主图" , required = true)
    private String pic;

    @Schema(description = "商品图片列表，以逗号分割" , required = true)
    private String imgs;

    /**
     * 商品分类
     */
    @Schema(description = "商品分类id" , required = true)
    private Long categoryId;

    @Schema(description = "sku列表" )
    private List<Sku> skuList;

    @Schema(description = "运费信息" , required = true)
    private Transport transport;

    public static interface WithNoContent{}

    public static interface WithContent extends WithNoContent{}
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(Double oriPrice) {
        this.oriPrice = oriPrice;
    }

    public Integer getTotalStocks() {
        return totalStocks;
    }

    public void setTotalStocks(Integer totalStocks) {
        this.totalStocks = totalStocks;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

}
