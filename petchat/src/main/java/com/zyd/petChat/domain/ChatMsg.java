package com.zyd.petChat.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-05-03 14:52
 */
public class ChatMsg {
    private String type;
    private String userName;
    private String targetName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private String msg;

    public ChatMsg() {
    }

    public ChatMsg(String type, String userName, String targetName, Date time, String msg) {
        this.type = type;
        this.userName = userName;
        this.targetName = targetName;
        this.time = time;
        this.msg = msg;
    }

    public ChatMsg(String userName, String targetName, Date time, String msg) {
        this.userName = userName;
        this.targetName = targetName;
        this.time = time;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
