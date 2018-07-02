package com.csranger.dto;

// 封装json结果，泛型
// 所有ajax请求返回类型全部是它，因为使用了泛型，data可以是任何类型的数据
public class SeckillResult<T> {

    // 判断请求是否成功
    private boolean success;

    // 泛型类型的数据
    private T data;

    // 错误信息
    private String error;

    // 构造器
    // 请求失败，则有输出错误信息
    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    // 构造器
    // 请求成功，则有数据
    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    // getter setter

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
