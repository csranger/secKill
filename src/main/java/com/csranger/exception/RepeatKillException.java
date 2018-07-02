package com.csranger.exception;

/**
 * 重复秒杀异常 {运行期异常}  spring声明式事务只对运行期异常会进行回滚
 * 如果成功秒杀了就不能让他在成功进行秒杀了
 */
public class RepeatKillException extends SeckillException {

    // 构造方法
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
