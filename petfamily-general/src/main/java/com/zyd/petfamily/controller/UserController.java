package com.zyd.petfamily.controller;

import com.zyd.petfamily.domain.common.CommonResponse;
import com.zyd.petfamily.domain.pojo.User;
import com.zyd.petfamily.service.UserServer;
import com.zyd.petfamily.utils.CodeUtil;
import com.zyd.petfamily.utils.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: petfamily
 * @author: zyd
 * @description: Controller Of User
 * @create: 2019-04-24 19:15
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServer userServerImpl;

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @RequestMapping("/register")
    public CommonResponse register(@RequestBody User user) {
        log.debug("注册用户：" + user.getUserName());
        CommonResponse response = new CommonResponse();

        //未激活邮箱
        user.setUserValid(0);

        //用户信息存储
        if (!userServerImpl.saveUser(user)) {
            return new CommonResponse(CodeUtil.FAILE_CODE, "已存在相同用户名");
        }

        //返回用户id
        response.setData(user.getUserId());
        response.setStatus(CodeUtil.SUCCESS_CODE);
        response.setMsg("注册成功!");

        log.debug("注册用户 " + user.getUserId() + "成功！");

        return response;
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public CommonResponse login(@RequestBody User user) {
        log.debug("用户{}请求登录", user.getUserName());

        //判断账号密码是否正确
        if (userServerImpl.isUser(user) && user.getUserValid() != null && user.getUserValid() == 1) {
            log.debug("登录成功！");
            return new CommonResponse(CodeUtil.SUCCESS_CODE, user.getUserId(), CodeUtil.SUCCESS_MSG);
        }

        log.debug("登录失败");
        String msg = "账号不存在或密码错误！";
        //判断是否账号未激活
        if (user.getUserValid() == null || user.getUserValid() == 0) {
            msg = "账号未激活！";
            log.debug(msg);
        }
        return new CommonResponse(CodeUtil.FAILE_CODE, msg);
    }

    /**
     * 激活用户
     *
     * @param userId
     * @return
     */
    @RequestMapping("/active")
    public String active(Integer userId) {
        log.info("激活用户" + userId);
        userServerImpl.active(userId);
        return "The account has been actived successfully!";
    }

    /**
     * 向用户发送激活邮件
     * @param userId
     * @return
     */
    @RequestMapping("/sendEmail")
    public CommonResponse sendEmail(Integer userId) {
        log.info("向用户{} 发送激活邮件" + userId);
        if (userServerImpl.sendEmail(userId)) {
            log.info("发送激活邮件成功！");
            return new CommonResponse(CodeUtil.SUCCESS_CODE, userId, "发送激活邮件成功！");
        }
        log.info("发送激活邮件失败！");
        return new CommonResponse(CodeUtil.FAILE_CODE, CodeUtil.FAILE_MSG);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @RequestMapping("/update")
    public CommonResponse update(@RequestBody User user){
        log.info("修改用户{} 的信息", user.getUserId());
        if(userServerImpl.updateUser(user)){
            return new CommonResponse(CodeUtil.SUCCESS_CODE, user, "修改成功！");
        }
        return new CommonResponse(CodeUtil.FAILE_CODE, CodeUtil.FAILE_MSG);
    }

    /**
     * 获取用户名称
     * @param userId
     * @return
     */
    @RequestMapping("/userName")
    public CommonResponse userName(Integer userId){
        log.info("获取用户{} 的名称", userId);
        String userName = userServerImpl.getUserName(userId);
        if(userName != null && !userName.equals("")){
            return new CommonResponse(CodeUtil.SUCCESS_CODE, userName, CodeUtil.SUCCESS_MSG);
        }
        return new CommonResponse(CodeUtil.FAILE_CODE, CodeUtil.FAILE_MSG);
    }
}
