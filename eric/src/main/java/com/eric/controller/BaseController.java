package com.eric.controller;

import com.eric.BaseResponse;
import com.eric.exception.BusinessException1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 当一个Controller中有方法加了@ExceptionHandler之后，
     * 这个Controller其他方法中没有捕获的异常就会以参数的形式传入加了@ExceptionHandler
     * 注解的那个方法中。
     *
     * @param e 捕获的异常
     * @return Controller层的返回值
     */
    @ExceptionHandler
    public BaseResponse exceptionHandler(Exception e) {
        logger.info("Exception", e);
        BaseResponse response = new BaseResponse();
        if(e instanceof BusinessException1) {
            response.setCode(-3);
            response.setMsg(e.getMessage());
        } else {
            response.setCode(-4);
            response.setMsg(e.getMessage());
        }
        return response;
    }

}
