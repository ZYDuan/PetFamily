package com.zyd.petfamily.domain.pojo;

public class OrderStatus {
    private Integer orderStatusId;

    private String statusName;

    public OrderStatus(Integer orderStatusId, String statusName) {
        this.orderStatusId = orderStatusId;
        this.statusName = statusName;
    }

    public OrderStatus() {
        super();
    }

    public Integer getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Integer orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }
}