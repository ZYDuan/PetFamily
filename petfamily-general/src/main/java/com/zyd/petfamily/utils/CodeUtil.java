package com.zyd.petfamily.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CodeUtil {

    //响应状态码
    //成功响应状态码
    public static int SUCCESS_CODE = 200;
    //响应失败状态码
    public static int FAILE_CODE = 400;

    //响应返回信息
    public static String SUCCESS_MSG = "响应成功";
    public static String FAILE_MSG = "响应失败";

    //订单状态
    public static int ORDER_CANCEL = 0;
    public static int ORDER_UNPAY = 1;
    public static int ORDER_PAY = 2;
    public static int ORDER_COMPLETE = 3;

    //图片存放的目录
    public static String PIC_URL = "/usr/local/petfamily/static/img/pet/";

    /**
     * 获取服务器ip地址
     *
     * @return ip地址
     */
    public static String getLocalUrl() {
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "http://" + localHost.getHostAddress();
    }
}
