package com.zyd.petfamily.dao;

import com.zyd.petfamily.domain.pojo.PetKind;

import java.util.List;

public interface PetKindMapper {
    int deleteByPrimaryKey(Integer petKindId);

    int insert(PetKind record);

    int insertSelective(PetKind record);

    PetKind selectByPrimaryKey(Integer petKindId);

    int updateByPrimaryKeySelective(PetKind record);

    int updateByPrimaryKey(PetKind record);

    /**
     * 获取所有类型
     * @return
     */
    List<PetKind> selectAll();
}