package com.csranger.dao;

import com.csranger.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

public interface SuccessKilledDao {


    /**
     * 插入操作，进行一次秒杀，插入秒杀／购买记录，可过滤重复
     * 数据库插入：success_killed 表中插入行
     * @param seckillId
     * @param userPhone
     * @return
     * 插入操作，插入的行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);


    /**
     * 查询操作，根据秒杀/购买记录的商品id seckillId 查询秒杀/购买记录，此携带秒杀商品的对象
     * 需要注意的是success_killed的每行记录的主键是seckillId＋userPhone，它是联合主键，所以需要两个参数
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
