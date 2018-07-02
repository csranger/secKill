package com.csranger.dao;

import com.csranger.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)                      //junit启动时加载spring IOC 容器
@ContextConfiguration({"classpath:spring/spring-dao.xml"})   // 告诉Junit spring 的配置文件从而启动Spring的IOC容器
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() {   // 测试插入一行确实写入了数据库
        long id = 1000L;
        long phone = 901234567L;
        int insertCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insert count: " + insertCount);
    }

    @Test
    public void queryByIdWithSeckill() {
        long id = 1000L;
        long phone = 901234567L;
        SuccessKilled sk = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println();
        System.out.println(sk.getSeckill());
    }
}