package com.csranger.service;

import com.csranger.dto.Exposer;
import com.csranger.dto.SeckillExecution;
import com.csranger.entity.Seckill;
import com.csranger.exception.RepeatKillException;
import com.csranger.exception.SeckillCloseException;
import com.csranger.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    // 日志输出，使用 slf4j 管理 logger
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);      // {}是占位符
    }

    @Test
    public void getById() {
        long id = 1000L;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);

    }

    @Test
    public void exportSeckillUrl() {
        long id = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
        // 返回的值是
        // Exposer{exposed=true, md5='50c68cedb6d930583139cf9e95af084b', seckillId=1000, now=0, start=0, end=0}
        // 说明id为1000L的商品秒杀已经开启，所以返回了Exposer类的对象，这里主要关注的是md5，如果某个商品开始了秒杀，则可以获取商品对应的md5，其由id产生

    }

    @Test
    public void executeSeckill() {
        long id = 1000L;
        long phone = 123123983493874L;
        String md5 = "50c68cedb6d930583139cf9e95af084b";
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
            logger.info("result={}", seckillExecution);
        }  catch (SeckillCloseException e1) {
            logger.error(e1.getMessage());
        } catch (RepeatKillException e2) {
            logger.error(e2.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        // 1.理解：执行秒杀需要商品的秒杀地址md5，这个md5是由商品id在开始秒杀后才生成的，这就使得只有在开启秒杀后才可以执行秒杀
        // 2.此处测试就是测试执行秒杀方法是否正确，如果出现异常，使用try catch来捕获，这个方法会产生那些异常呢？见具体实现类
        // 3.exportSeckillUrl和executeSeckill方法测试应该放在一起，这里没有
    }
}