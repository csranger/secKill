package com.csranger.exception;

/**
 * 所有秒杀业务相关的异常都可以算作 SeckillException
 * 通用的异常，所以秒杀关闭异常和重复秒杀异常都集成它
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
