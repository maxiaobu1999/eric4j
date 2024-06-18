package com.eric.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/** 账户信息 */
//@ApiModel(value="上传账户信息")
public class UserInfo {
    /** 用户ID */
    @ApiModelProperty(value = "用户ID", required = true, example = "10")
    public Integer userId;
    /** 手机号码 */
    @ApiModelProperty(value = "手机号", required = true, example = "15652814311")
    @NotBlank(message = "手机号不能为空")// 只能作用在String上，不能为null，而且调用trim()后，长度必须大于0
    @Size(min = 11, max = 11, message = "无效的手机号")
    public Long phoneNum;
    /** 用户名 */
    @ApiModelProperty(value = "用户名", required = true, example = "马小布")
    public String userName;
    /** 密码 */
    @ApiModelProperty(value = "密码", required = true, example = "123456")
    public String password;
    /** 邮箱 */
    @ApiModelProperty(value = "邮箱", required = true, example = "maxiaobu1999@163.com")
    public String email;
    /** 头像 */
    @ApiModelProperty(value = "头像", required = true, example = "https://upload.jianshu.io/users/upload_avatars/3030148/355c2962-8a22-4578-a391-cd3c88218ea8.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240")
    public String avatar;
    /** 性别 */
    @ApiModelProperty(value = "性别", required = true, example = "男")
    public String gender;
    /** 设备标示id */
    @ApiModelProperty(value = "设备标示id", required = true, example = "123")
    public String identifier;
    /** 昵称 */
    @ApiModelProperty(value = "昵称", required = true, example = "马小布")
    public String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
