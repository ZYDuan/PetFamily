package com.zyd.petfamily.domain.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class FosterOrderInfo {
    private Integer orderInfoId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date orderStart;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date orderEnd;

    private Float orderMoney;

    private String orderRemark;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date payTime;

    private Integer orderId;

    private Integer orderStatus;

    public FosterOrderInfo(Integer orderInfoId, Date orderStart, Date orderEnd, Float orderMoney, String orderRemark,
                           Date createTime, Date payTime, Integer orderId, Integer orderStatus) {
        this.orderInfoId = orderInfoId;
        this.orderStart = orderStart;
        this.orderEnd = orderEnd;
        this.orderMoney = orderMoney;
        this.orderRemark = orderRemark;
        this.createTime = createTime;
        this.payTime = payTime;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public FosterOrderInfo() {
        super();
    }

    public Integer getOrderInfoId() {
        return orderInfoId;
    }

    public void setOrderInfoId(Integer orderInfoId) {
        this.orderInfoId = orderInfoId;
    }

    public Date getOrderStart() {
        return orderStart;
    }

    public void setOrderStart(Date orderStart) {
        this.orderStart = orderStart;
    }

    public Date getOrderEnd() {
        return orderEnd;
    }

    public void setOrderEnd(Date orderEnd) {
        this.orderEnd = orderEnd;
    }

    public Float getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Float orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark == null ? null : orderRemark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}