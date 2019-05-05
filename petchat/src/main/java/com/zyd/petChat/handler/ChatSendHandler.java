package com.zyd.petChat.handler;

import com.zyd.petChat.domain.ChatResponse;
import com.zyd.petfamily.utils.JsonUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: petfamily
 * @author: zyd
 * @description: 向目标地址发送信息
 * @create: 2019-05-03 02:18
 */
public class ChatSendHandler {

    private static Logger logger = LoggerFactory.getLogger(ChatSendHandler.class);
    /**
     * 发送聊天数据到远程对象
     *
     * @param msg
     * @param ctx
     * @throws Exception
     */
    public static void sendMsg(ChatResponse msg, ChannelHandlerContext ctx) throws Exception {
        logger.info("发送讯息：" + msg.getMsg());
        ctx.channel().writeAndFlush(new TextWebSocketFrame(JsonUtil.toJson(msg)));
    }
}
