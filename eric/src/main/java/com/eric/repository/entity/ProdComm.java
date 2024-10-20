
package com.eric.repository.entity;

import com.eric.repository.vo.UserVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品评论 tz_prod_comm
 *
 */
@Data
public class ProdComm implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long prodCommId;
    /**
     * 商品ID
     */
    private Long prodId;
    /**
     * 订单项ID
     */
    private Long orderItemId;
    /**
     * 评论用户ID
     */
    private String userId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 掌柜回复
     */
    private String replyContent;
    /**
     * 记录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recTime;
    /**
     * 回复时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date replyTime;
    /**
     * 是否回复 0:未回复  1:已回复
     */
    private Integer replySts;
    /**
     * IP来源
     */
    private String postip;
    /**
     * 得分，0-5分
     */
    private Integer score;
    /**
     * 有用的计数
     */
    private Integer usefulCounts;
    /**
     * 晒图的字符串 以逗号分隔
     */
    private String pics;
    /**
     * 是否匿名(1:是  0:否)
     */
    private Integer isAnonymous;
    /**
     * 是否显示，1:为显示，0:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是0,，否则1
     */
    private Integer status;

    /**
     * 评价(0好评 1中评 2差评)
     */
    private Integer evaluate;

    /**
     * 关联用户
     */
    private UserVO user;

    /**
     * 商品名称
     */
    private String prodName;
}
