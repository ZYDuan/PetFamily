package com.zyd.petfamily.service;

import com.zyd.petfamily.domain.Request.CommentRequest;
import com.zyd.petfamily.domain.Request.OrderRequest;
import com.zyd.petfamily.domain.Response.OrderPetResponse;
import com.zyd.petfamily.domain.Response.OrderResponse;
import org.quartz.SchedulerException;

import java.util.List;

public interface OrderService {

    /**
     * 获取用户能够寄养的宠物
     *
     * @param userId
     * @param familyId
     * @return
     */
    List<OrderPetResponse> getFosterPet(Integer userId, Integer familyId);

    /**
     * 下订单
     *
     * @param orderRequest
     * @return
     */
    boolean createOrder(OrderRequest orderRequest);

    /**
     * 根据用户id查看订单列表
     *
     * @param userId
     * @return
     */
    List<OrderResponse> getOrderList(Integer userId);

    /**
     * 根据家庭id查看
     *
     * @param familyId
     * @return
     */
    List<OrderResponse> getOrderByFamily(Integer familyId);

    /**
     * 根据订单id查看
     *
     * @param orderId
     * @return
     */
    OrderResponse getOrderById(Integer orderId);

    /**
     * 为订单付款
     *
     * @param orderId
     * @return
     */
    boolean payOrder(Integer orderId) throws SchedulerException;

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    boolean cancelOrder(Integer orderId);

    /**
     * 提交评论
     *
     * @param comment
     * @return
     */
    boolean offerComment(CommentRequest comment);
}
