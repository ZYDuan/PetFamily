package com.zyd.petfamily.domain.Request;

import com.zyd.petfamily.domain.pojo.FamilyInfo;
import com.zyd.petfamily.domain.pojo.FamilyServe;

import java.util.List;

/**
 * @program: petfamily
 * @author: zyd
 * @description:宠物寄养家庭请求相关的具体信息类
 * @create: 2019-04-28 18:26
 */
public class FamilyInfoRequest extends FamilyInfo {

    //宠物寄养的定价
    private List<FamilyServe> familyServeDetail;

    public List<FamilyServe> getFamilyServeDetail() {
        return familyServeDetail;
    }

    public FamilyInfoRequest(){

    }
    public FamilyInfoRequest(Integer familyInfoId, String familyAddress, String familyName, Double familyLat, Double familyLng, String familyDetail, Double familyCommentStar, Integer userId, String familyPhone, Integer familyCommentCount, List<FamilyServe> familyServeDetail) {
        super(familyInfoId, familyAddress, familyName, familyLat, familyLng, familyDetail, familyCommentStar, userId, familyPhone, familyCommentCount);
        this.familyServeDetail = familyServeDetail;
    }

    public FamilyInfoRequest(List<FamilyServe> familyServeDetail) {
        this.familyServeDetail = familyServeDetail;
    }

    public void setFamilyServeDetail(List<FamilyServe> familyServeDetail) {
        this.familyServeDetail = familyServeDetail;
    }
}
