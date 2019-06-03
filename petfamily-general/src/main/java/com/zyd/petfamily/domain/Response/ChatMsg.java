package com.zyd.petfamily.domain.Response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-05-03 00:37
 */
public class ChatMsg {
    public ChatMsg() {

    }

    public ChatMsg(String name, Date time, String msg) {
        this.name = name;
        this.time = time;
        this.msg = msg;
    }

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    private String msg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
