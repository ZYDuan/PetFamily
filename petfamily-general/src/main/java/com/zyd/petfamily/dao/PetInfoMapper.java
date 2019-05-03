package com.zyd.petfamily.dao;

import com.zyd.petfamily.domain.pojo.PetInfo;

import java.util.List;

public interface PetInfoMapper {
    int deleteByPrimaryKey(Integer petId);

    int insert(PetInfo record);

    int insertSelective(PetInfo record);

    PetInfo selectByPrimaryKey(Integer petId);

    int updateByPrimaryKeySelective(PetInfo record);

    int updateByPrimaryKey(PetInfo record);

    /**
     * 根据用户id查找所有的宠物信息
     * @param userId
     * @return
     */
    List<PetInfo> selectByUser(Integer userId);
}