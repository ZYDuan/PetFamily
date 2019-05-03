package com.zyd.petChat.handler;

import com.zyd.petChat.domain.ChatMsg;
import com.zyd.petChat.redis.Constant;
import com.zyd.petfamily.utils.JsonUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-05-03 15:39
 */
public class WsChatReceiveHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private static Logger logger = LoggerFactory.getLogger(WsChatReceiveHandler.class);

    /**
     * 描述：读取完连接的消息后，对消息进行处理。
     * 这里主要是处理WebSocket请求
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        handlerWebSocketFrame(ctx, msg);
    }

    /**
     * 描述：处理WebSocketFrame
     *
     * @param ctx
     * @param frame
     * @throws Exception
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // 关闭请求
        if (frame instanceof CloseWebSocketFrame) {
            WebSocketServerHandshaker handshaker =
                    Constant.webSocketHandshakerMap.get(ctx.channel().id().asLongText());
            if (handshaker == null) {
                return;
            } else {
                handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            }
            return;
        }
        // ping请求
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 只支持文本格式，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            return;
        }

        // 客服端发送过来的消息
        String msg = ((TextWebSocketFrame) frame).text();
        logger.info("服务端收到新信息：" + msg);
        handlerMsg(msg, ctx);
    }


    /**
     * 处理受到的聊天信息
     *
     * @param msg
     * @param ctx
     * @throws Exception
     */
    private void handlerMsg(String msg, ChannelHandlerContext ctx) throws Exception {
        //解析传递的json数据格式的信息
        ChatMsg chatMsg = JsonUtil.fromJson(msg, ChatMsg.class);
        //判断发送信息的各种状态
        if (chatMsg.getType().equals(Constant.ONLINE_TYPE))
            Constant.onlineUserMap.put(chatMsg.getUserName(), ctx);
        else if (chatMsg.getType().equals(Constant.LEAVE_TYPE))
            remove(ctx);
        else {
            //获取客户端的ip地址
            InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
            //向在线聊天列表插入数据
            //RedisHandler.insertOnline(chatMsg.getUserName(), chatMsg.getTargetName(), address.getHostName() + ":" + address.getPort());
            //向聊天记录插入数据
            RedisHandler.insertMsg(chatMsg);
        }
    }

    /**
     * 描述：客户端断开连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        remove(ctx);
    }

    private void remove(ChannelHandlerContext ctx) {
        Iterator<Map.Entry<String, ChannelHandlerContext>> iterator =
                Constant.onlineUserMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ChannelHandlerContext> entry = iterator.next();
            if (entry.getValue() == ctx) {
                logger.info("正在移除握手实例...");
                Constant.webSocketHandshakerMap.remove(ctx.channel().id().asLongText());
                logger.info(MessageFormat.format("已移除握手实例，当前握手实例总数为：{0}"
                        , Constant.webSocketHandshakerMap.size()));
                iterator.remove();
                logger.info(MessageFormat.format("userName为 {0} 的用户已退出聊天，当前在线人数为：{1}"
                        , entry.getKey(), Constant.onlineUserMap.size()));
                break;
            }
        }
    }

    /**
     * 异常处理：关闭channel
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
