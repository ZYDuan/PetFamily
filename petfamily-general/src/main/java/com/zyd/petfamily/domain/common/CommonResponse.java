package com.zyd.petfamily.domain.common;

/**
 * @program: petfamily
 * @author: zyd
 * @description: 统一的数据返回结构
 * @create: 2019-04-24 19:34
 */
public class CommonResponse {

    public CommonResponse() {
    }

    public CommonResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public CommonResponse(int status, Object data, String msg) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    //响应状态码
    private int status;

    //响应数据
    private Object data;

    //响应信息
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
