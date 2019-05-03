package com.zyd.petfamily.domain.Response;


import com.zyd.petfamily.domain.Request.OrderRequest;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-04-28 21:53
 */
public class OrderResponse extends OrderRequest {
    private String userName;
    private String familyName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
}
