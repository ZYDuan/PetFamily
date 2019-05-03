package com.zyd.petfamily.domain.Response;

import com.zyd.petfamily.domain.pojo.PetInfo;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-04-26 21:05
 */
public class PetInfoResponse extends PetInfo {
    private String kindName;

    public PetInfoResponse(){}

    public PetInfoResponse(PetInfo info, String kindName){
        this.setKindName(kindName);
        this.setPetBirthday(info.getPetBirthday());
        this.setPetId(info.getPetId());
        this.setPetKind(info.getPetKind());
        this.setPetName(info.getPetName());
        this.setPetPic(info.getPetPic());
        this.setPetSex(info.getPetSex());
        this.setPetWeight(info.getPetWeight());
        this.setUserId(info.getUserId());
        this.setPetSpay(info.getPetSpay());
    }
    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

}
