package com.zyd.petfamily.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * @program: petfamily
 * @author: zyd
 * @description: send active email to register user
 * @create: 2019-04-24 21:28
 */
public class MailUtil {

    public static void sendActiveMail(String userMail, String userName, Integer userId) throws Exception {
        //导入邮件配置信息
        InputStream in = new ClassPathResource("properties/mail.properties").getInputStream();
        Properties props = new Properties();
        props.load(in);

        //配置邮件
        Properties mailProps = new Properties();
        mailProps.setProperty("mail.transport.protocol", props.getProperty("mail.transport.protocol"));
        mailProps.setProperty("mail.smtp.host", props.getProperty("mail.smtp.host"));
        mailProps.setProperty("mail.smtp.auth", props.getProperty("mail.smtp.auth"));
        //邮箱发送服务器端口,这里设置为465端口
        mailProps.setProperty("mail.smtp.port", props.getProperty("mail.smtp.port"));
        mailProps.setProperty("mail.smtp.socketFactory.port", props.getProperty("mail.smtp.socketFactory.port"));
        mailProps.setProperty("mail.smtp.socketFactory.fallback", props.getProperty("mail.smtp.socketFactory.fallback"));
        mailProps.put("mail.smtp.socketFactory.class", props.getProperty("mail.smtp.socketFactory.class"));

        String account = props.getProperty("mail.account");
        String password = props.getProperty("mail.password");
        //根据参数创建会话对象
        Session session = Session.getInstance(mailProps);
        session.setDebug(true);     //设置为debug模式, 可以查看详细的发送log

        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // From: 发件人
        //其中 InternetAddress 的三个参数分别为: 邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
        message.setFrom(new InternetAddress(account, "宠物联盟", "UTF-8"));
        //To: 收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(userMail, userName, "UTF-8"));
        //Subject: 邮件主题
        message.setSubject("宠物寄养家庭平台注册", "UTF-8");

        //Content: 邮件正文（可以使用html标签）
        message.setContent("<a href= " + CodeUtil.getLocalUrl() + ":8080/user/active?userId=" + userId + "> 账号激活链接 </a>", "text/html;charset=UTF-8");

        //设置显示的发件时间
        message.setSentDate(new Date());

        //保存设置
        message.saveChanges();

        Transport transport = session.getTransport();
        transport.connect(account, password);

        //发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        transport.close();
    }
}
