package com.zyd.petfamily.domain.pojo;

public class FamilyServe {
    private Integer familyServeId;

    private Integer servePetKind;

    private Float servePetMoney;

    private Integer familyInfoId;

    public FamilyServe(Integer familyServeId, Integer servePetKind, Float servePetMoney, Integer familyInfoId) {
        this.familyServeId = familyServeId;
        this.servePetKind = servePetKind;
        this.servePetMoney = servePetMoney;
        this.familyInfoId = familyInfoId;
    }

    public FamilyServe() {
        super();
    }

    public Integer getFamilyServeId() {
        return familyServeId;
    }

    public void setFamilyServeId(Integer familyServeId) {
        this.familyServeId = familyServeId;
    }

    public Integer getServePetKind() {
        return servePetKind;
    }

    public void setServePetKind(Integer servePetKind) {
        this.servePetKind = servePetKind;
    }

    public Float getServePetMoney() {
        return servePetMoney;
    }

    public void setServePetMoney(Float servePetMoney) {
        this.servePetMoney = servePetMoney;
    }

    public Integer getFamilyInfoId() {
        return familyInfoId;
    }

    public void setFamilyInfoId(Integer familyInfoId) {
        this.familyInfoId = familyInfoId;
    }
}