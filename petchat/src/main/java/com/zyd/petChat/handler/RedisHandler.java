package com.zyd.petChat.handler;

import com.zyd.petChat.domain.ChatMsg;
import com.zyd.petChat.domain.ChatResponse;
import com.zyd.petChat.redis.Constant;
import com.zyd.petChat.redis.RedisPool;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-05-03 02:01
 */
public class RedisHandler {
    private static String ONLINE_REDIS ="online";
    private static Jedis jedis = RedisPool.getJedis();

    private static Logger logger = LoggerFactory.getLogger(RedisHandler.class);

    /**
     * 向redis 插入在线聊天记录
     *
     * @param userName
     * @param targetName
     * @param ip
     */
    public static void insertOnline(String userName, String targetName, String ip) {
        //插入user跟对象处于聊天状态是的ip地址
        jedis.hset(ONLINE_REDIS, userName + ":" + targetName, ip);
    }

    /**
     * 向redis插入传递讯息数据
     *
     * @param chatMsg
     */
    public static void insertMsg(ChatMsg chatMsg) throws Exception{
        String targetName = chatMsg.getTargetName();
        String userName = chatMsg.getUserName();
        String msg = chatMsg.getMsg();
        Date date = chatMsg.getTime();

        String targetKey = targetName + ":" + userName;
        String userKey = userName + ":" + targetName;

        long time = date.getTime();
        //插入聊天记录
        //1. 分别插入两者的聊天对象列表
        logger.info("插入{} 和 {} 的聊天记录", userName, targetName);
        jedis.zadd(userName, time, targetName);
        jedis.zadd(targetName, time, userName);
        //2. 将聊天记录插入到两者到聊天记录列表中
        jedis.zadd(userKey, time, time + ":" + userName + ":" + msg);
        jedis.zadd(targetKey, time, time + ":" + userName + ":" + msg);

        //判断发送的对象是否也处于在线聊天状态
        String targetIp = jedis.hget(ONLINE_REDIS, targetKey);
        ChannelHandlerContext targetCtx = Constant.onlineUserMap.get(targetName);
        if (targetCtx == null)
            return;
        ChatSendHandler.sendMsg(new ChatResponse(userName, date, msg), targetCtx);
    }
}
