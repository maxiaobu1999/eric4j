package com.eric.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/** 账户信息 */
@Data
public class UserEntity {
    /**
     * ID
     */
    private String userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */

    private String realName;

    /**
     * 用户邮箱
     */

    private String userMail;

    /**
     * 登录密码
     */

    private String loginPassword;

    /**
     * 支付密码
     */

    private String payPassword;

    /**
     * 手机号码
     */

    private String userMobile;

    /**
     * 修改时间
     */

    private Date modifyTime;

    /**
     * 注册时间
     */

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date userRegtime;

    /**
     * 注册IP
     */

    private String userRegip;

    /**
     * 最后登录时间
     */

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date userLasttime;

    /**
     * 最后登录IP
     */

    private String userLastip;

    /**
     * 备注
     */

    private String userMemo;

    /**
     * M(男) or F(女)
     */
    private String sex;

    /**
     * 例如：2009-11-27
     */

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private String birthDate;

    /**
     * 头像图片路径
     */
    private String pic;

    /**
     * 状态 1 正常 0 无效
     */
    private Integer status;

    /**
     * 积分
     */
    private Integer score;
    /**
     * 密码盐值
     */
    private String salt;
}
