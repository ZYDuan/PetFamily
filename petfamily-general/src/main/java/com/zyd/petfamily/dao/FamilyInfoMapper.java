package com.zyd.petfamily.dao;

import com.zyd.petfamily.domain.Request.FamilyInfoRequest;
import com.zyd.petfamily.domain.pojo.FamilyInfo;

import java.util.List;

public interface FamilyInfoMapper {
    int deleteByPrimaryKey(Integer familyInfoId);

    int insert(FamilyInfo record);

    int insertSelective(FamilyInfo record);

    FamilyInfo selectByPrimaryKey(Integer familyInfoId);

    int updateByPrimaryKeySelective(FamilyInfo record);

    int updateByPrimaryKey(FamilyInfo record);

    FamilyInfo selectByUser(Integer userId);

    List<FamilyInfo> selectAll();
}