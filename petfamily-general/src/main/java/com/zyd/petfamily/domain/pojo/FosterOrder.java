package com.zyd.petfamily.domain.pojo;

public class FosterOrder {
    private Integer orderId;

    private Integer familyId;

    private Integer userId;

    public FosterOrder(Integer orderId, Integer familyId, Integer userId) {
        this.orderId = orderId;
        this.familyId = familyId;
        this.userId = userId;
    }

    public FosterOrder() {
        super();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}