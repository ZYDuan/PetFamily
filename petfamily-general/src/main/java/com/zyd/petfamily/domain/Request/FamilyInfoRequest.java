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

    public void setFamilyServeDetail(List<FamilyServe> familyServeDetail) {
        this.familyServeDetail = familyServeDetail;
    }
}
