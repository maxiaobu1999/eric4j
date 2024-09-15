
package com.eric.repository.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 7307405761190788407L;
    /**
     * 订单项ID
     */
    private Long orderItemId;


    private Long shopId;

    /**
     * 订单sub_number
     */

    private String orderNumber;

    /**
     * 产品ID
     */

    private Long prodId;

    /**
     * 产品SkuID
     */

    private Long skuId;

    /**
     * 购物车产品个数
     */

    private Integer prodCount;

    /**
     * 产品名称
     */

    private String prodName;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * 产品主图片路径
     */
    private String pic;

    /**
     * 产品价格
     */
    private Double price;

    /**
     * 用户Id
     */

    private String userId;

    /**
     * 商品总金额
     */
    private Double productTotalAmount;

    /**
     * 购物时间
     */

    private Date recTime;

    /**
     * 评论状态： 0 未评价  1 已评价
     */

    private Integer commSts;

    /**
     * 推广员使用的推销卡号
     */
    private String distributionCardNo;

    /**
     * 加入购物车的时间
     */
    private Date basketDate;
}
