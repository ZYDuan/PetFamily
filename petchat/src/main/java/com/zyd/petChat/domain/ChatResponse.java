package com.zyd.petChat.domain;

import java.util.Date;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-05-03 16:06
 */
public class ChatResponse {
    private String targetName;
    private Date time;
    private String msg;

    public ChatResponse() {
    }

    public ChatResponse(String targetName, Date time, String msg) {
        this.targetName = targetName;
        this.time = time;
        this.msg = msg;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
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
