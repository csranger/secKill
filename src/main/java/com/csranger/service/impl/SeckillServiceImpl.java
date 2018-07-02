package com.csranger.service.impl;

import com.csranger.dao.SeckillDao;
import com.csranger.dao.SuccessKilledDao;
import com.csranger.dto.Exposer;
import com.csranger.dto.SeckillExecution;
import com.csranger.entity.Seckill;
import com.csranger.entity.SuccessKilled;
import com.csranger.enums.SeckillStatEnum;
import com.csranger.exception.RepeatKillException;
import com.csranger.exception.SeckillCloseException;
import com.csranger.exception.SeckillException;
import com.csranger.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {

    // 日志对象
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    // md5盐值字符串，用于混淆MD5，随意取复杂的字符串
    private final String slat = "23kjsd9$QDSD#$@Ccq909";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    // 执行秒杀前需要输出秒杀地址
    // 通过商品id查看当前商品能否输出秒杀地址（只有在开启秒杀时才会暴露地址）
    public Exposer exportSeckillUrl(long seckillId) {
        // 1.根据id查询是什么商品
        Seckill secKill = seckillDao.queryById(seckillId);
        // 2.如果商品不存在，输出秒杀地址失败，使用Exposer生成对象包装这些数据 false：不可以暴露地址 seckillId 商品id
        if (secKill == null) return new Exposer(false, seckillId);
        // 3. 商品不为空，找到此商品秒杀的起始与结束时间以及系统当前时间
        Date startTime = secKill.getStartTime();
        Date endTime = secKill.getEndTime();
        Date nowTime = new Date();
        // 4. 秒杀还没有开始或者已经结束的情况，输出秒杀地址失败，使用Exposer生成对象包装这些数据   false：不可以暴露地址 seckillId 商品id 后面三个数据是当前时间商品的开始与结束时间
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        // 5. 秒杀已经开启，可以输出秒杀地址md5
        // 转化特定字符串的过程，不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;    // 通过盐值和规则(盐值和seckillId的如何组合)生成md5
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    // service 核心方法，执行秒杀
    @Transactional
    /**
     * 使用注解控制事务方法的优点
     * 1.开发团队达成一致约定，明确标注事务方法de编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他的网络请求PRC，HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要实务操作，比如只有一条修改操作，只读不需要事务操作
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        // 执行秒杀逻辑： 减库存 ＋ 记录秒杀／购买 行为

        if (md5 == null || !md5.equals(getMD5(seckillId))) {          // md5 和 seckillId 时匹配的，则说明参数传递没有问题
            throw new SeckillException("seckill data rewrite");  // 秒杀数据被重写了
        }
        // 1. 减库存
        Date nowTime = new Date();
        try {
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            // 2. 减库存失败：从接口实现(mapper文件)来看，要求id一致，时间在秒杀时间内，而且库存大于0
            // 秒杀关闭异常是运行时异常，会使得数据库事务回滚
            if (updateCount <= 0) {
                throw new SeckillCloseException("seckill is closed");
                // 3. 减库存成功
            } else {
                // 4. 记录购买行为：向success_killed表中插入数据
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                // 5. 记录购买行为失败：插入数据失败原因,insert ignore 表明如果主键冲突，不报错，返回 0，即重复秒杀
                // 重复秒杀异常是运行时异常，会使得数据库事务回滚
                if (insertCount <= 0) {
                    throw new RepeatKillException("seckill repeat");
                } else {
                    // 6. 记录购买行为成功；插入数据成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {    // 比如数据库运行超时，数据库连接断了等等问题造成的异常
            logger.error(e.getMessage(), e);
            // 所有编译期异常(checked)转换成 秒杀相关异常 ［运行期(Runtime)异常］意味着一旦发生异常都会回滚
            throw new SeckillException("seckill inner error: " + e.getMessage());
        }

    }
}
