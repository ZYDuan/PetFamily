package com.zyd.petfamily.quartz;

import com.zyd.petfamily.dao.FosterOrderInfoMapper;
import com.zyd.petfamily.domain.pojo.FosterOrder;
import com.zyd.petfamily.domain.pojo.FosterOrderInfo;
import com.zyd.petfamily.utils.CodeUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @program: petfamily
 * @author: zyd
 * @description: 定时完成订单任务
 * @create: 2019-05-02 00:41
 */
@DisallowConcurrentExecution
public class CompleteOrderTask extends QuartzJobBean {

    //订单id
    private Integer orderId;

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Autowired
    private FosterOrderInfoMapper orderInfoMapper;

    private static Logger log = LoggerFactory.getLogger(CompleteOrderTask.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("start order job");
        //完成订单更新
        FosterOrderInfo order = orderInfoMapper.selectByOrder(orderId);
        order.setOrderStatus(CodeUtil.ORDER_COMPLETE);
        orderInfoMapper.updateByPrimaryKeySelective(order);
    }
}
