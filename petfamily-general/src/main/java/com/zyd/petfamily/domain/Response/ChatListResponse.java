package com.zyd.petfamily.domain.Response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-05-03 00:31
 */
public class ChatListResponse {
    private Integer targetId;

    private String targetName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    private String lastMsg;

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
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

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }
}
