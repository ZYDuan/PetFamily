package com.zyd.petfamily.domain.pojo;

public class FosterOrderPet {
    private Integer orderPetId;

    private Integer petId;

    private Integer orderId;

    private Float orderPetMoney;

    public FosterOrderPet(Integer orderPetId, Integer petId, Integer orderId, Float orderPetMoney) {
        this.orderPetId = orderPetId;
        this.petId = petId;
        this.orderId = orderId;
        this.orderPetMoney = orderPetMoney;
    }

    public FosterOrderPet() {
        super();
    }

    public Integer getOrderPetId() {
        return orderPetId;
    }

    public void setOrderPetId(Integer orderPetId) {
        this.orderPetId = orderPetId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Float getOrderPetMoney() {
        return orderPetMoney;
    }

    public void setOrderPetMoney(Float orderPetMoney) {
        this.orderPetMoney = orderPetMoney;
    }
}