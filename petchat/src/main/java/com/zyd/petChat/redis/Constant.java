package com.zyd.petChat.redis;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-05-03 15:41
 */
public class Constant {
    public static final String LEAVE_TYPE = "leave";
    public static String ONLINE_TYPE = "online";
    public static String SEND_TYPE = "send";

    public static Map<String, WebSocketServerHandshaker> webSocketHandshakerMap =
            new ConcurrentHashMap<String, WebSocketServerHandshaker>();

    public static Map<String, ChannelHandlerContext> onlineUserMap =
            new ConcurrentHashMap<String, ChannelHandlerContext>();

}
