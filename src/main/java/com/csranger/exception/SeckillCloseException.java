package com.csranger.exception;

/**
 * 秒杀关闭异常（时间到了或商品卖完了）
 */
public class SeckillCloseException extends SeckillException {

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
