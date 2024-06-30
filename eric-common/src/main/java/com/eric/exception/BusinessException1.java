package com.eric.exception;


public class BusinessException1 extends RuntimeException {

    public BusinessException1(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
