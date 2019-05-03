package com.zyd.petfamily.domain.Response;

import com.zyd.petfamily.domain.pojo.FamilyServe;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-04-28 20:16
 */
public class PetServeResponse extends FamilyServe {
    private String kindName;

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }
}
