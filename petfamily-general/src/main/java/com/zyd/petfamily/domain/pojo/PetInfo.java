package com.zyd.petfamily.domain.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PetInfo {
    private Integer petId;

    private String petName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date petBirthday;

    private String petPic;

    private Integer petKind;

    private Integer petSex;

    private Float petWeight;

    private Integer petSpay;

    private Integer userId;

    private String petIntro;

    public PetInfo(Integer petId, String petName, Date petBirthday, String petPic,
                   Integer petKind, Integer petSex, Float petWeight, Integer petSpay, Integer userId, String petIntro) {
        this.petId = petId;
        this.petName = petName;
        this.petBirthday = petBirthday;
        this.petPic = petPic;
        this.petKind = petKind;
        this.petSex = petSex;
        this.petWeight = petWeight;
        this.petSpay = petSpay;
        this.userId = userId;
        this.petIntro = petIntro;
    }

    public PetInfo() {
        super();
    }

    public String getPetIntro() {
        return petIntro;
    }

    public void setPetIntro(String petIntro) {
        this.petIntro = petIntro;
    }

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
        this.petName = petName == null ? null : petName.trim();
    }

    public Date getPetBirthday() {
        return petBirthday;
    }

    public void setPetBirthday(Date petBirthday) {
        this.petBirthday = petBirthday;
    }

    public String getPetPic() {
        return petPic;
    }

    public void setPetPic(String petPic) {
        this.petPic = petPic == null ? null : petPic.trim();
    }

    public Integer getPetKind() {
        return petKind;
    }

    public void setPetKind(Integer petKind) {
        this.petKind = petKind;
    }

    public Integer getPetSex() {
        return petSex;
    }

    public void setPetSex(Integer petSex) {
        this.petSex = petSex;
    }

    public Float getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(Float petWeight) {
        this.petWeight = petWeight;
    }

    public Integer getPetSpay() {
        return petSpay;
    }

    public void setPetSpay(Integer petSpay) {
        this.petSpay = petSpay;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}