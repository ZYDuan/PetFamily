package com.zyd.petfamily.domain.pojo;

public class PetKind {
    private Integer petKindId;

    private String kindName;

    public PetKind(Integer petKindId, String kindName) {
        this.petKindId = petKindId;
        this.kindName = kindName;
    }

    public PetKind() {
        super();
    }

    public Integer getPetKindId() {
        return petKindId;
    }

    public void setPetKindId(Integer petKindId) {
        this.petKindId = petKindId;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName == null ? null : kindName.trim();
    }
}