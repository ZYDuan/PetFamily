package com.zyd.petfamily.serviceImpl;

import com.zyd.petfamily.dao.UserMapper;
import com.zyd.petfamily.domain.pojo.User;
import com.zyd.petfamily.service.UserServer;
import com.zyd.petfamily.utils.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-04-24 19:25
 */

@Service
public class UserServerImpl implements UserServer {


    @Autowired
    private UserMapper userMapper;

    private static Logger log = LoggerFactory.getLogger(UserServerImpl.class);

    @Override
    public boolean saveUser(User user) {
        //查询用户名是否重复
        if (userMapper.selectByName(user.getUserName()) != null)
            return false;
        //插入user数据
        userMapper.insert(user);
        //发送账户激活邮件
        try {
            log.debug("发送用户{}的激活邮件", user.getUserId() );
            MailUtil.sendActiveMail(user.getUserEmail(), user.getUserName(), user.getUserId());
            log.debug("发送成功！");
        } catch (Exception e) {
            log.error("发送失败！");
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean isUser(User user) {
        User selectUser = userMapper.selectByName(user.getUserName());
        //判断是否相等
        if (selectUser != null && selectUser.getUserPwd().equals(user.getUserPwd())) {
            user.setUserValid(selectUser.getUserValid());
            return true;
        }
        return false;
    }

    @Override
    public boolean active(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user.getUserValid() == 0) {
            user.setUserValid(1);
            userMapper.updateByPrimaryKey(user);
        }
        return true;
    }

    @Override
    public boolean sendEmail(Integer userId) {
        boolean res = false;
        //根据userid获取具体数据
        User user = userMapper.selectByPrimaryKey(userId);

        if (user == null)
            return res;
        try {
            //向目标邮箱发送激活邮件
            MailUtil.sendActiveMail(user.getUserEmail(), user.getUserName(), userId);
            res = true;
        } catch (Exception e) {
            log.error("发送邮件{}失败", user.getUserEmail());
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean updateUser(User user) {
        User oldUser = userMapper.selectByPrimaryKey(user.getUserId());
        if (oldUser != null){
            user.setUserValid(oldUser.getUserValid());
            userMapper.updateByPrimaryKey(user);
            return true;
        }
        return false;
    }
}
