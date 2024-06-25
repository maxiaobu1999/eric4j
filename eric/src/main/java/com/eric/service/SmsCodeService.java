package com.eric.service;

/** 验证码 */
public interface SmsCodeService {

    /** 获取验证码 */
    int getSmsCode(String phoneNum);

    /** 校验验证码 */
    boolean verifySmsCode(String phoneNum, int code);

}
