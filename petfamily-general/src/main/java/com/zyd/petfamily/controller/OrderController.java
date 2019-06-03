package com.zyd.petfamily.controller;

import com.zyd.petfamily.domain.Request.CommentRequest;
import com.zyd.petfamily.domain.Request.OrderRequest;
import com.zyd.petfamily.domain.Response.OrderPetResponse;
import com.zyd.petfamily.domain.Response.OrderResponse;
import com.zyd.petfamily.domain.common.CommonResponse;
import com.zyd.petfamily.service.OrderService;
import com.zyd.petfamily.utils.CodeUtil;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @program: petfamily
 * @author: zyd
 * @description: receive the requests about order
 * @create: 2019-04-28 21:44
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderServiceImpl;

    private static Logger log = LoggerFactory.getLogger(OrderController.class);

    /**
     * 创建订单
     *
     * @param orderRequest
     * @return
     */
    @RequestMapping("/create")
    public CommonResponse createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("创建订单");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date today = calendar.getTime();

        if (orderRequest.getOrderStart().before(today) || orderRequest.getOrderStart().after(orderRequest.getOrderEnd()))
            return new CommonResponse(CodeUtil.FAILE_CODE, "订单时间输入不符合逻辑");
        if (orderServiceImpl.createOrder(orderRequest))
            return new CommonResponse(CodeUtil.SUCCESS_CODE, CodeUtil.SUCCESS_MSG);

        return new CommonResponse(CodeUtil.FAILE_CODE, CodeUtil.FAILE_MSG);

    }

    /**
     * 获取可寄养的宠物列表
     *
     * @param userId
     * @param familyId
     * @return
     */
    @RequestMapping("/petList")
    public CommonResponse fosterPet(@RequestParam("userId") Integer userId,
                                    @RequestParam("familyId") Integer familyId) {
        log.info("获取可寄养的宠物列表");

        List<OrderPetResponse> petList = orderServiceImpl.getFosterPet(userId, familyId);

        if (petList == null || petList.size() == 0)
            return new CommonResponse(CodeUtil.FAILE_CODE, "暂无符合寄养的宠物");

        return new CommonResponse(CodeUtil.SUCCESS_CODE, petList, CodeUtil.SUCCESS_MSG);
    }

    /**
     * 根据用户id获取订单列表
     *
     * @param userId
     * @return
     */
    @RequestMapping("/orderList")
    public CommonResponse orderList(Integer userId) {
        log.info("根据用户id获取订单列表");

        List<OrderResponse> orders = orderServiceImpl.getOrderList(userId);
        //判断是否用户拥有订单
        if (orders == null || orders.size() == 0)
            return new CommonResponse(CodeUtil.FAILE_CODE, "暂无订单");

        return new CommonResponse(CodeUtil.SUCCESS_CODE, orders, CodeUtil.SUCCESS_MSG);
    }

    /**
     * 根据家庭id获取订单列表
     *
     * @param familyId
     * @return
     */
    @RequestMapping("/family")
    public CommonResponse orderListByFamily(Integer familyId) {
        log.info("根据家庭id获取订单列表");

        List<OrderResponse> orders = orderServiceImpl.getOrderByFamily(familyId);
        //判断是否家庭拥有订单
        if (orders == null || orders.size() == 0)
            return new CommonResponse(CodeUtil.FAILE_CODE, "暂无订单");

        return new CommonResponse(CodeUtil.SUCCESS_CODE, orders, CodeUtil.SUCCESS_MSG);
    }

    /**
     * 根据订单id获取订单详细信息
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/orderInfo")
    public CommonResponse orderInfo(Integer orderId) {
        log.info("根据订单id获取订单详细信息");

        OrderResponse order = orderServiceImpl.getOrderById(orderId);
        if (order != null)
            return new CommonResponse(CodeUtil.SUCCESS_CODE,order, CodeUtil.SUCCESS_MSG);

        return new CommonResponse(CodeUtil.FAILE_CODE, CodeUtil.FAILE_MSG);
    }

    /**
     * 支付订单
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/pay")
    public CommonResponse payOrder(Integer orderId) throws SchedulerException{
        log.info("支付订单");

        //判断是否支付成功
        if (orderServiceImpl.payOrder(orderId))
            return new CommonResponse(CodeUtil.SUCCESS_CODE, CodeUtil.SUCCESS_MSG);

        return new CommonResponse(CodeUtil.FAILE_CODE, "支付失败！");
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/cancel")
    public CommonResponse cancelOrder(Integer orderId) {
        log.info("取消订单");

        //判断是否取消成功
        if (orderServiceImpl.cancelOrder(orderId))
            return new CommonResponse(CodeUtil.SUCCESS_CODE, CodeUtil.SUCCESS_MSG);

        return new CommonResponse(CodeUtil.FAILE_CODE, "无法取消！");
    }

    /**
     * 提交评论
     *
     * @param orderComment
     * @return
     */
    @RequestMapping("/comment")
    public CommonResponse offerComment(@RequestBody CommentRequest orderComment) {
        log.info("提交评论");

        //判断是否评论成功
        if (orderServiceImpl.offerComment(orderComment))
            return new CommonResponse(CodeUtil.SUCCESS_CODE, CodeUtil.SUCCESS_MSG);

        return new CommonResponse(CodeUtil.FAILE_CODE, CodeUtil.FAILE_MSG);
    }
}
