package com.zyd.petfamily.domain.pojo;

public class FamilyInfo {
    private Integer familyInfoId;

    private String familyAddress;

    private String familyName;

    private Double familyLat;

    private Double familyLng;

    private String familyDetail;

    private Double familyCommentStar;

    private Integer userId;

    private String familyPhone;

    private Integer familyCommentCount;

    public FamilyInfo(Integer familyInfoId, String familyAddress, String familyName, Double familyLat, Double familyLng, String familyDetail, Double familyCommentStar, Integer userId,
                      String familyPhone, Integer familyCommentCount) {
        this.familyInfoId = familyInfoId;
        this.familyAddress = familyAddress;
        this.familyName = familyName;
        this.familyLat = familyLat;
        this.familyLng = familyLng;
        this.familyDetail = familyDetail;
        this.familyCommentStar = familyCommentStar;
        this.userId = userId;
        this.familyPhone = familyPhone;
        this.familyCommentCount = familyCommentCount;
    }

    public FamilyInfo() {
        super();
    }

    public Integer getFamilyInfoId() {
        return familyInfoId;
    }

    public void setFamilyInfoId(Integer familyInfoId) {
        this.familyInfoId = familyInfoId;
    }

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress == null ? null : familyAddress.trim();
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName == null ? null : familyName.trim();
    }

    public Double getFamilyLat() {
        return familyLat;
    }

    public void setFamilyLat(Double familyLat) {
        this.familyLat = familyLat;
    }

    public Double getFamilyLng() {
        return familyLng;
    }

    public void setFamilyLng(Double familyLng) {
        this.familyLng = familyLng;
    }

    public String getFamilyDetail() {
        return familyDetail;
    }

    public void setFamilyDetail(String familyDetail) {
        this.familyDetail = familyDetail == null ? null : familyDetail.trim();
    }

    public Double getFamilyCommentStar() {
        return familyCommentStar;
    }

    public void setFamilyCommentStar(Double familyCommentStar) {
        this.familyCommentStar = familyCommentStar;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }

    public Integer getFamilyCommentCount() {
        return familyCommentCount;
    }

    public void setFamilyCommentCount(Integer familyCommentCount) {
        this.familyCommentCount = familyCommentCount;
    }
}