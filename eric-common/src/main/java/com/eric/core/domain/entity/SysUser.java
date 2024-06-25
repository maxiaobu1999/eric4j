package com.eric.core.domain.entity;

/** 账户信息 */
public class SysUser {
    /** 用户ID */
    public Long userId;
    /** 手机号码 */
    public Long phoneNum;
    /** 用户名 */
    public String userName;
    /** 密码 */
    public String password;

    /** 头像 */
    public String avatar;
    /** 昵称 */
    public String nickName;

    /** token */
    public String token;



    public boolean isAdmin()
    {
        return isAdmin(userId);
    }
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }







    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
