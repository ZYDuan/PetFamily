package com.zyd.petfamily.controller;

import com.zyd.petfamily.domain.Response.ChatListResponse;
import com.zyd.petfamily.domain.Response.ChatMsg;
import com.zyd.petfamily.domain.common.CommonResponse;
import com.zyd.petfamily.service.ChatService;
import com.zyd.petfamily.utils.CodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-05-02 23:46
 */
@RestController
@RequestMapping("/chatList")
public class ChatController {

    @Autowired
    private ChatService chatServiceImpl;

    private static Logger log = LoggerFactory.getLogger(ChatController.class);

    /**
     * 获取聊天列表
     *
     * @param userId
     * @return
     */
    @RequestMapping("")
    public CommonResponse chatList(Integer userId){
        log.info("用户 {} 获取聊天列表", userId);
        List<ChatListResponse> chatList = chatServiceImpl.getChatList(userId);

        if (chatList == null || chatList.size() == 0)
            return new CommonResponse(CodeUtil.FAILE_CODE, "该用户暂无聊天记录");
        return new CommonResponse(CodeUtil.SUCCESS_CODE, chatList, CodeUtil.SUCCESS_MSG);
    }

    /**
     * 获取聊天记录
     *
     * @param userId
     * @param targetId
     * @return
     */
    @RequestMapping("/chat")
    public CommonResponse chatRecord(Integer userId, Integer targetId, HttpServletRequest request){
        log.info("用户 {} 获取与用户 {} 的聊天记录", userId, targetId);
        List<ChatMsg> chatMsgs = chatServiceImpl.getChatRecord(userId, targetId, request.getRemoteHost() + ":" + request.getRemotePort());

        if (chatMsgs == null || chatMsgs.size() == 0)
            return new CommonResponse(CodeUtil.FAILE_CODE, "该用户暂无聊天记录");
        return new CommonResponse(CodeUtil.SUCCESS_CODE, chatMsgs, CodeUtil.SUCCESS_MSG);
    }
}
