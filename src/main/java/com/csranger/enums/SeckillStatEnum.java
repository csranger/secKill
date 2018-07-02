package com.csranger.enums;

/**
 * 枚举包
 * 使用枚举表述常量数据
 * 替换 state, stateInfo 常量
 */
public enum SeckillStatEnum {

    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATA_REWRITE(-3, "数据篡改");

    // 两个成员变量
    private int state;

    private String stateInfo;

    // 构造方法
    SeckillStatEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    // getter 方法
    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    // 静态方法，根据state变量返回
    public static SeckillStatEnum stateOf(int index) {
        for (SeckillStatEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
