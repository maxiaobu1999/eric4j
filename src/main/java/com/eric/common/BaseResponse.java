package com.eric.common;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

//@ApiModel
public class BaseResponse<T> implements Serializable {
//    @ApiModelProperty(value = "状态码，0:成功, -1:token过期, -2:参数错误, -3:其他已知错误, -4:其他未知错误。 其他状态码见方法说明", example = "-2", required = true)
    private int code;
//    @ApiModelProperty(value = "具体数据")
    private T   data;
//    @ApiModelProperty(value = "处理结果描述", example = "参数错误")
    private String msg;
//    @ApiModelProperty(value = "token", example = "671688988")
    private String token;

    public BaseResponse() {
    }
    /** 构造函数，初始化code和msg */
    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    /** 构造函数，初始化code和msg&token */
    public BaseResponse(int code, String msg, String token) {
        this.code = code;
        this.msg = msg;
        this.token = token;
    }

    // 判断结果是否成功
    public boolean isSuccess() {
        return (0 == code || 1 == code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
