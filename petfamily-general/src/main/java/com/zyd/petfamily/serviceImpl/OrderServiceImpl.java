package com.zyd.petfamily.serviceImpl;

import com.zyd.petfamily.dao.*;
import com.zyd.petfamily.domain.Request.CommentRequest;
import com.zyd.petfamily.domain.Request.OrderRequest;
import com.zyd.petfamily.domain.Response.OrderPetResponse;
import com.zyd.petfamily.domain.Response.OrderResponse;
import com.zyd.petfamily.domain.pojo.*;
import com.zyd.petfamily.quartz.CompleteOrderTask;
import com.zyd.petfamily.service.OrderService;
import com.zyd.petfamily.utils.CodeUtil;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @program: petfamily
 * @author: zyd
 * @description: 处理订单相关的逻辑问题
 * @create: 2019-04-28 21:45
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FamilyInfoMapper familyInfoMapper;

    @Autowired
    private FamilyServeMapper familyServeMapper;

    @Autowired
    private FosterOrderMapper orderMapper;

    @Autowired
    private FosterOrderInfoMapper orderInfoMapper;

    @Autowired
    private FosterOrderPetMapper orderPetMapper;

    @Autowired
    private FamilyCommentMapper commentMapper;


    @Override
    public List<OrderPetResponse> getFosterPet(Integer userId, Integer familyId) {
        return familyServeMapper.selectServePet(userId, familyId);
    }

    @Override
    public boolean createOrder(OrderRequest orderRequest) {

        FosterOrder order = new FosterOrder(null, orderRequest.getFamilyId(), orderRequest.getUserId());
        //向数据库中插入订单数据
        orderMapper.insert(order);

        //向数据库中插入订单详细数据
        FosterOrderInfo orderInfo = new FosterOrderInfo(null, orderRequest.getOrderStart(),
                orderRequest.getOrderEnd(), orderRequest.getOrderMoney(),
                orderRequest.getOrderRemark(), new Date(), null, order.getOrderId(), CodeUtil.ORDER_UNPAY);

        orderInfoMapper.insert(orderInfo);

        //插入订单中选中的宠物
        List<OrderPetResponse> orderPets = orderRequest.getPetList();
        for (OrderPetResponse orderPet : orderPets) {
            FosterOrderPet pet = new FosterOrderPet(null,
                    orderPet.getPetId(), order.getOrderId(), orderPet.getPetMoney());
            orderPetMapper.insert(pet);
        }
        return true;
    }

    @Override
    public List<OrderResponse> getOrderList(Integer userId) {
        //获取用户的订单信息
        List<OrderResponse> orders = orderMapper.selectByUserId(userId);

        //判断用户是否下过订单
        if (orders == null || orders.size() <= 0)
            return null;

        //根据下订单的用户id获取用户信息
        User user = userMapper.selectByPrimaryKey(userId);

        for (OrderResponse order : orders) {
            //根据家庭id获取家庭信息
            FamilyInfo familyInfo = familyInfoMapper.selectByPrimaryKey(order.getFamilyId());

            //获取每个订单中的宠物信息
            List<OrderPetResponse> petList = orderPetMapper.selectByOrder(order.getOrderId());
            order.setPetList(petList);

            order.setUserName(user.getUserName());
            order.setFamilyName(familyInfo.getFamilyName());
        }

        return orders;
    }

    @Override
    public List<OrderResponse> getOrderByFamily(Integer familyId) {

        //获取用户的订单信息
        List<OrderResponse> orders = orderMapper.selectByFamilyId(familyId);

        //判断用户是否下过订单
        if (orders == null || orders.size() <= 0)
            return null;

        //根据家庭id获取家庭信息
        FamilyInfo familyInfo = familyInfoMapper.selectByPrimaryKey(familyId);

        for (OrderResponse order : orders) {
            //获取每个订单中的宠物信息
            List<OrderPetResponse> petList = orderPetMapper.selectByOrder(order.getOrderId());
            order.setPetList(petList);

            //根据下订单的用户id获取用户信息
            User user = userMapper.selectByPrimaryKey(order.getUserId());

            order.setUserName(user.getUserName());
            order.setFamilyName(familyInfo.getFamilyName());
        }

        return orders;
    }

    @Override
    public OrderResponse getOrderById(Integer orderId) {
        List<OrderResponse> orders = orderMapper.selectSingleByOrder(orderId);
        //判断订单信息是否存在
        if (orders == null || orders.size() != 1)
            return null;

        OrderResponse order = orders.get(0);

        //根据下订单的用户id获取用户信息
        User user = userMapper.selectByPrimaryKey(order.getUserId());
        order.setUserName(user.getUserName());
        //根据家庭id获取家庭信息
        FamilyInfo familyInfo = familyInfoMapper.selectByPrimaryKey(order.getFamilyId());
        order.setFamilyName(familyInfo.getFamilyName());
        //获取订单中的宠物信息
        List<OrderPetResponse> petList = orderPetMapper.selectByOrder(order.getOrderId());
        order.setPetList(petList);

        return order;
    }

    @Override
    public boolean payOrder(Integer orderId) throws SchedulerException {
        //根据id从数据库获取订单id
        FosterOrderInfo orderInfo = orderInfoMapper.selectByOrder(orderId);

        if (orderInfo == null)
            return false;

        //判断订单是否为未支付状态
        if (orderInfo.getOrderStatus() == CodeUtil.ORDER_UNPAY) {
            orderInfo.setOrderStatus(CodeUtil.ORDER_PAY);
            Date payDate = new Date();

            //判断支付时间是否在订单开始之前
            if (payDate.after(orderInfo.getOrderStart()))
                return false;
            orderInfo.setPayTime(payDate);
            orderInfoMapper.updateByPrimaryKey(orderInfo);

            //将日期转化为cron形式并设定定时完成订单的任务
            SimpleDateFormat dateFormat = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
            scheduleOrderTask(orderInfo.getOrderId(), dateFormat.format(orderInfo.getOrderEnd()));

            return true;
        }

        return false;
    }

    @Override
    public boolean cancelOrder(Integer orderId) {
        //根据id从数据库获取订单id
        FosterOrderInfo orderInfo = orderInfoMapper.selectByOrder(orderId);

        //判断订单是否为未支付状态
        if (orderInfo.getOrderStatus() == CodeUtil.ORDER_UNPAY) {
            orderInfo.setOrderStatus(CodeUtil.ORDER_CANCEL);
            orderInfoMapper.updateByPrimaryKey(orderInfo);
            return true;
        }

        return false;
    }

    @Override
    public boolean offerComment(CommentRequest commentRequest) {
        //根据评论所属的订单id获取订单信息
        List<OrderResponse> orders = orderMapper.selectSingleByOrder(commentRequest.getOrderId());
        //判断订单信息是否存在
        if (orders == null || orders.size() != 1)
            return false;

        OrderResponse order = orders.get(0);
        //构建订单评论
        FamilyComment comment = new FamilyComment(null, commentRequest.getCommentContent(),
                order.getFamilyId(), order.getUserId(), commentRequest.getCommentStar());
        commentMapper.insert(comment);

        //更新家庭信息
        FamilyInfo family = familyInfoMapper.selectByPrimaryKey(order.getFamilyId());
        //更新家庭的评论统计信息
        int count = family.getFamilyCommentCount() + 1;
        family.setFamilyCommentCount(count);
        family.setFamilyCommentStar((commentRequest.getCommentStar() + family.getFamilyCommentStar()) / (double) count);
        familyInfoMapper.updateByPrimaryKeySelective(family);

        return true;
    }

    private void scheduleOrderTask(Integer orderId, String cron) throws SchedulerException {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(CompleteOrderTask.class)
                .withIdentity(String.valueOf(orderId), "orders").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "orders")
                .usingJobData("orderId", orderId).withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }
}
