package com.zyd.petfamily.serviceImpl;

import com.zyd.petfamily.dao.UserMapper;
import com.zyd.petfamily.domain.Response.ChatListResponse;
import com.zyd.petfamily.domain.Response.ChatMsg;
import com.zyd.petfamily.domain.pojo.User;
import com.zyd.petfamily.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-05-02 23:54
 */

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<ChatListResponse> getChatList(Integer userId) {
        ZSetOperations zset = redisTemplate.opsForZSet();

        //根据用户Id获取用户名
        User user = userMapper.selectByPrimaryKey(userId);
        String userName = user.getUserName();

        //根据用户名获取聊天对象
        Set<String> targetNames = zset.range(userName, 0, zset.size(userName) - 1);

        //判断是否有聊天对象
        if (targetNames == null || targetNames.size() == 0)
            return null;

        //获取聊天对象的信息
        List<ChatListResponse> result = new ArrayList<>();
        for (String targetName : targetNames) {
            ChatListResponse chatResponse = new ChatListResponse();
            //获取聊天对象的名字
            chatResponse.setTargetName(targetName);
            chatResponse.setTargetId(userMapper.selectByName(targetName).getUserId());
            //获取聊天对象最新的聊天时间跟讯息
            chatResponse.setTime(new Date(zset.score(userName, targetName).longValue()));
            //构造聊天记录的键
            String msgKey = userName + ":" + targetName;

            //获取聊天内容
            String val = (String) zset.range(msgKey, -1, -1).toArray()[0];
            chatResponse.setLastMsg(getMsg(val));

            result.add(chatResponse);
        }
        return result;
    }

    @Override
    public List<ChatMsg> getChatRecord(Integer userId, Integer targetId, String url) {
        ZSetOperations zset = redisTemplate.opsForZSet();
        //根据userId和targetid获取名字
        String userName = userMapper.selectByPrimaryKey(userId).getUserName();
        String targetName = userMapper.selectByPrimaryKey(targetId).getUserName();

        //构造聊天记录的键
        String msgKey = userName + ":" + targetName;

        //向在线聊天记录插入
//        redisTemplate.opsForHash().put("online", msgKey, url);

        //按时间从小到大获取聊天记录
        Set<String> chatMsgs = zset.range(msgKey, 0, zset.size(msgKey) - 1);

        //判断聊天记录是否为空
        if (chatMsgs == null || chatMsgs.size() == 0)
            return null;

        //构造聊天记录的数据信息
        List<ChatMsg> res = new ArrayList<>();
        for (String msg : chatMsgs) {
            int timeIndex = msg.indexOf(":");
            int nameIndex = msg.indexOf(":", timeIndex + 1);
            ChatMsg chatMsg = new ChatMsg(msg.substring(timeIndex + 1, nameIndex), new Date(zset.rank(msgKey, msg).longValue()),
                    msg.substring(nameIndex + 1, msg.length()));
            res.add(chatMsg);
        }
        return res;
    }

    /**
     * 存储在redis中的聊天记录的格式为： time:name:msg
     *
     * @param oldMsg
     * @return
     */
    private String getMsg(String oldMsg) {
        String[] valPattern = oldMsg.split(":");
        return oldMsg.substring(valPattern[0].length() + valPattern[1].length() + 2);
    }
}
