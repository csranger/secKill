package com.csranger.service;

import com.csranger.dto.Exposer;
import com.csranger.dto.SeckillExecution;
import com.csranger.entity.Seckill;
import com.csranger.exception.RepeatKillException;
import com.csranger.exception.SeckillCloseException;
import com.csranger.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在"使用者"的角度设计接口
 * 先设计接口，然后实现
 */
public interface SeckillService {

    /**
     * 查询所有秒杀商品
     * @return
     */
    List<Seckill> getSeckillList();


    /**
     * 查询单个秒杀商品
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀地址
     * 否者输出系统时间和秒杀时间
     * 通过商品id查看当前商品能否输出秒杀地址（只有在开启秒杀时才会暴露地址）将 exportSeckillUrl 对象里的 md5 看成秒杀地址
     * 这些返回类型不是 Entity 里的数据，是一些业务不相关的数据 使用 DTO 里的 Exposer
     * DTO里的实体类代表着 Web 层想要从 service 层获取的数据，这些数据和 Entity 不同
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);


    /**
     * 执行秒杀操作，核心操作
     * 1. 返回的类型想要知道的数据有哪些呢？将这些数据全部封装在dto数据传输层里的SeckillExecution类里
     * 2. 执行异常时需要通知接口使用方它可能输出什么样的具体的异常，这些异常放入到exception包里
     * 3. 执行秒杀方法可能产生的异常是秒杀相关异常SeckillException，具体两个重复秒杀RepeatKillException 秒杀关闭SeckillCloseException
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;
}
