-- 数据库初始化脚本


-- 创建数据库
DROP DATABASE IF EXISTS seckill;
CREATE DATABASE seckill;
use seckill;

-- 创建秒杀库存表
CREATE TABLE seckill (
  `seckill_id`  bigint       NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name`        varchar(120) NOT NULL COMMENT '商品名称',
  `number`      int          NOT NULL COMMENT '库存数量',
  `start_time`  timestamp    NOT NULL COMMENT '秒杀开启时间',
  `end_time`    timestamp    NOT NULL COMMENT '秒杀结束时间',
  `create_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id), /*设计索引工作:主键*/
  key idx_start_time(start_time), /*设计索引工作:其他索引，加速查询用的*/
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)ENGINE = InnoDB AUTO_INCREMENT = 1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';
/*使用InnoDB引擎创建表格支持事务;自增从1000开始*/

-- 初始化数据
insert into
  seckill (name, number, start_time, end_time)
values
  ('1000元秒杀iphone8', 500, '2018-4-1 00:00:00', '2020-6-3 00:00:00'),
  ('1000元秒杀ipad4', 300, '2018-4-1 00:00:00', '2020-6-3 00:00:00'),
  ('1000元秒杀小米8', 200, '2018-4-1 00:00:00', '2020-6-3 00:00:00'),
  ('1000元秒杀华为P20', 400, '2018-4-1 00:00:00', '2018-6-3 00:00:00');

-- 秒杀成功明细表：记录谁秒杀成功的以及秒杀时间
-- 用户登陆认证相关的信息
create table success_killed (
  `seckill_id`  bigint    NOT NULL COMMENT '秒杀商品id',
  `user_phone`  bigint    NOT NULL COMMENT '用户手机号',
  `state`       tinyint   NOT NULL DEFAULT -1 COMMENT '状态标示：-1：无效 0：成功 1:已付款 2：已付款', /*标注状态*/
  `create_time` timestamp NOT NULL COMMENT '创建时间',
  PRIMARY KEY (seckill_id, user_phone), /*联合主键*/
  key idx_create_time(create_time)
)ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT ='秒杀成功明细表';


