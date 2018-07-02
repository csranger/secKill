package com.csranger.dao;

import com.csranger.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SeckillDao {

    /**
     * 更新操作 减库存，number-1 根据商品 id 和 create time
     * 数据库更新：seckill 表中更新行
     * 从接口实现(mapper文件)来看，要求id一致，时间在秒杀时间内，而且库存大于0
     * @param seckillId
     * @param killTime    秒杀／购买的时间，要求该商品秒杀／购买已经开始并且还买结束
     * @return
     * 更新操作，返回的是更新记录的行数
     * 注意：多个参数需要使用 @Param 注解确定参数名，是的mapper文件中 #{seckillId} 不会找不到变量值
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);


    /**
     * 查询操作，单表查询，根据秒杀商品的 seckillId 查询秒杀商品的对象 Seckill
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);


    /**
     * 查询操作，先按照秒杀商品的开始时间倒序，offset是起始行，limit是查询后列出的行数：询秒杀商品的列表 List<Seckill>
     * 数据库操作：seckill 表 单表查询
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

}

/**
 * update seckill set number = number-1
 *         where seckill_id = #{seckillId} and start_time <![CDATA[ <= ]] #{killTime} and end_time >= #{killTime} and number > 0
 */
