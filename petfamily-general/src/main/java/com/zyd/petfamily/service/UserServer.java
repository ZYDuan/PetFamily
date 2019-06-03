package com.zyd.petfamily.service;

import com.zyd.petfamily.domain.pojo.User;

public interface UserServer {

    /**
     * 存储user信息
     * 若不存在相同用户名则存储，存在则返回失败
     * @param user
     */
    boolean saveUser(User user);

    /**
     * 判断账号是否匹配
     * @param user
     * @return
     */
    boolean isUser(User user);

    /**
     * 激活用户
     * @param userId
     */
    boolean active(Integer userId);

    /**
     * 向发送激活邮件
     * @param userId
     */
    boolean sendEmail(Integer userId);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 根据用户id获取用户名称
     * @param userId
     * @return
     */
    String getUserName(Integer userId);
}
