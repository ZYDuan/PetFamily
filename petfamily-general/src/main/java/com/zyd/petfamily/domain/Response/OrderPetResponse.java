package com.zyd.petfamily.domain.Response;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-04-30 15:46
 */
public class OrderPetResponse {
    private Integer petId;

    private String petName;

    private Float petMoney;

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Float getPetMoney() {
        return petMoney;
    }

    public void setPetMoney(Float petMoney) {
        this.petMoney = petMoney;
    }
}
