package com.csranger.dao;

import com.csranger.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


/**
 * 配置spring和junit整合，junit启动时加载spring IOC 容器
 * spring-test, junit
 */
@RunWith(SpringJUnit4ClassRunner.class)                      //junit启动时加载spring IOC 容器
@ContextConfiguration({"classpath:spring/spring-dao.xml"})   // 告诉Junit spring 的配置文件从而启动Spring的IOC容器
public class SeckillDaoTest {

    // 注入dao实现类依赖
    @Resource
    private SeckillDao seckillDao;


    @Test
    public void queryById() {
        long seckillId = 1000;
        Seckill seckill = seckillDao.queryById(seckillId);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() {
        /**
         * Caused by: org.apache.ibatis.binding.BindingException: Parameter 'offset' not found.
         * List<Seckill> queryAll(int offset, int limit);
         * 当传入多个参数是会报错，因为java没有保存形参的记录
         * 通过Mybatis提供的@Param注解来确定参数名，件Seckill接口
         */
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        System.out.println(seckills.size());
    }

    @Test
    public void reduceNumber() {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("update count: " + updateCount);
    }
}