package com.zyd.petfamily.domain.Response;

import com.zyd.petfamily.domain.pojo.FamilyInfo;


/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-05-04 12:19
 */
public class FamilyResponse extends FamilyInfo {
    private Double distance;

    public FamilyResponse() {
    }

    public FamilyResponse(Double distance) {
        this.distance = distance;
    }

//    public FamilyResponse(FamilyInfoRequest familyInfo, Double distance) {
//        super(familyInfo.getFamilyInfoId(), familyInfo.getFamilyAddress(), familyInfo.getFamilyName(),
//                familyInfo.getFamilyLat(), familyInfo.getFamilyLng(), familyInfo.getFamilyDetail(),
//                familyInfo.getFamilyCommentStar(), familyInfo.getUserId(), familyInfo.getFamilyPhone(),
//                familyInfo.getFamilyCommentCount(), familyInfo.getFamilyServeDetail());
//        this.distance = distance;
//    }

    public FamilyResponse(FamilyInfo familyInfo, Double distance) {
        super(familyInfo.getFamilyInfoId(), familyInfo.getFamilyAddress(), familyInfo.getFamilyName(),
                familyInfo.getFamilyLat(), familyInfo.getFamilyLng(), familyInfo.getFamilyDetail(),
                familyInfo.getFamilyCommentStar(), familyInfo.getUserId(), familyInfo.getFamilyPhone(),
                familyInfo.getFamilyCommentCount());
        this.distance = distance;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
