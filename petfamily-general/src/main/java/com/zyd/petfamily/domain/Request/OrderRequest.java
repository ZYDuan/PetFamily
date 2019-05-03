package com.zyd.petfamily.domain.Request;

import com.zyd.petfamily.domain.Response.OrderPetResponse;
import com.zyd.petfamily.domain.pojo.FosterOrderInfo;

import java.util.List;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-04-28 21:51
 */
public class OrderRequest extends FosterOrderInfo {
    private Integer userId;

    private Integer familyId;

    private List<OrderPetResponse> petList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public List<OrderPetResponse> getPetList() {
        return petList;
    }

    public void setPetList(List<OrderPetResponse> petList) {
        this.petList = petList;
    }
}
