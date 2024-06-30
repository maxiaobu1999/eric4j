package com.eric.exception;

import com.eric.BaseResponse1;

import java.text.MessageFormat;

public class BusinessException extends RuntimeException{
    public int getCode() {
        return code;
    }

    /**
     *  异常 code
     */
    private final int code;

    public String getDefaultMessage() {
        return defaultMessage;
    }

    /**
     *  异常提示
     */
    public final String defaultMessage;

    /**
     * 构造函数
     * @param response ResponseInterface 异常码
     */
    public BusinessException(ResponseInterface response) {
        this(response.messageCode(), response.message());
    }

    /**
     *  构造函数
     * @param baseResponse  传入的枚举类，异常码
     */
    public BusinessException(BaseResponse1 baseResponse, Object ... params){
        this(baseResponse.messageCode(), MessageFormat.format(baseResponse.message(), params));
    }

    /**
     * 正常的 构造函数
     * @param code 异常码
     * @param defaultMessage 异常信息
     */
    public BusinessException(int code, String defaultMessage) {
        super(defaultMessage);
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
