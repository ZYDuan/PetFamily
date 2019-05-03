package com.zyd.petfamily.dao;

import com.zyd.petfamily.domain.Response.OrderPetResponse;
import com.zyd.petfamily.domain.Response.PetServeResponse;
import com.zyd.petfamily.domain.pojo.FamilyServe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FamilyServeMapper {
    int deleteByPrimaryKey(Integer familyServeId);

    int insert(FamilyServe record);

    int insertSelective(FamilyServe record);

    FamilyServe selectByPrimaryKey(Integer familyServeId);

    int updateByPrimaryKeySelective(FamilyServe record);

    int updateByPrimaryKey(FamilyServe record);

    List<PetServeResponse> selectByFamilyId(Integer familyId);

    int deleteByFamily(Integer familyId);

    /**
     * 获取用户能够寄养的宠物信息
     * @param userId
     * @param familyId
     * @return
     */
    List<OrderPetResponse> selectServePet(@Param("userId") Integer userId, @Param("familyId")Integer familyId);
}