package com.csranger.dto;

/**
 * 暴露秒杀地址方法的输出类
 * DTO：数据传输层
 * 这些成员变量大部分业务不相关的，它只是方便Service返回的一个封装
 */
public class Exposer {

    // 是否可以暴露地址 开启了秒杀后才可以暴露地址
    private boolean exposed;

    /**
     * 秒杀地址，一种机密措施
     */
    private String md5;


    // id
    private long seckillId;


    //  系统当前时间（毫秒），比如用户调用SeckillService接口的exportSeckillUrl方法秒杀还未开始，那么就不能告诉秒杀地址
    private long now;


    // 秒杀的开始时间和结束时间
    private long start;

    private long end;

    // 构造器
    // 可以暴露地址 输出 加密的地址 商品id
    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    // 构造器
    // 不可以暴露地址，因为秒杀时间未到或已经结束，输出 商品id，当前时间，开始与结束时间
    public Exposer(boolean exposed, long seckillId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    // 构造器
    // 不可以暴露地址 输出商品id
    public  Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    // getter setter
    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    // toString

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
