package com.zyd.petChat.handler;

import com.zyd.petChat.domain.ChatResponse;
import com.zyd.petfamily.utils.JsonUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @program: petfamily
 * @author: zyd
 * @description: 向目标地址发送信息
 * @create: 2019-05-03 02:18
 */
public class ChatSendHandler {

    /**
     * 发送聊天数据到远程对象
     *
     * @param msg
     * @param ctx
     * @throws Exception
     */
    public static void sendMsg(ChatResponse msg, ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(new TextWebSocketFrame(JsonUtil.toJson(msg)));
    }
}
