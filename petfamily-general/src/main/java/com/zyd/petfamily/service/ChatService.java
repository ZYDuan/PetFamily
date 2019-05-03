package com.zyd.petfamily.service;

import com.zyd.petfamily.domain.Response.ChatListResponse;
import com.zyd.petfamily.domain.Response.ChatMsg;

import java.util.List;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-05-02 23:53
 */
public interface ChatService {

    /**
     * 根据userid获取聊天列表
     *
     * @param userId
     * @return
     */
    List<ChatListResponse> getChatList(Integer userId);

    /**
     * 获取userId跟targetName聊天记录
     *
     * @param userId
     * @param targetId
     * @param url
     * @return
     */
    List<ChatMsg> getChatRecord(Integer userId, Integer targetId, String url);
}
