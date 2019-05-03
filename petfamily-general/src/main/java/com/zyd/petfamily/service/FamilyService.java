package com.zyd.petfamily.service;

import com.zyd.petfamily.domain.Request.FamilyInfoRequest;
import com.zyd.petfamily.domain.Response.CommentResponse;
import com.zyd.petfamily.domain.Response.PetServeResponse;
import com.zyd.petfamily.domain.pojo.FamilyInfo;
import com.zyd.petfamily.domain.pojo.FamilyServe;

import java.util.List;

public interface FamilyService {

    /**
     * 存储新的宠物家庭信息
     *
     * @param familyInfoRequest
     */
    boolean insertFamily(FamilyInfoRequest familyInfoRequest);

    /**
     * 更新宠物家庭信息
     *
     * @param familyInfoRequest
     * @return
     */
    boolean updateFamily(FamilyInfoRequest familyInfoRequest);

    /**
     * 删除宠物家庭信息
     *
     * @param familyId
     * @return
     */
    boolean deleteFamily(Integer familyId);

    /**
     * 查看某个用户的宠物家庭信息
     *
     * @param userId
     * @return
     */
    FamilyInfo selectByUserId(Integer userId);


    /**
     * 获取所有家庭宠物信息
     *
     * @return
     */
    List<FamilyInfoRequest> familyInfoList();

    /**
     * 查看某个宠物家庭信息
     *
     * @param familyId
     * @return
     */
    FamilyInfo selectByInfoId(Integer familyId);


    /**
     * 查看某个宠物家庭信息
     *
     * @param familyId
     * @return
     */
    List<CommentResponse> selectComment(Integer familyId);


    /**
     * 查看宠物家庭的宠物寄养
     *
     * @param familyId
     * @return
     */
    List<PetServeResponse> selectFamilyServe(Integer familyId);


    /**
     * 插入新的宠物寄养价格
     *
     * @param familyServes
     */
    void insertFamilyServe(List<FamilyServe> familyServes);

    /**
     * 更新新的宠物寄养价格
     *
     * @param familyServes
     */
    void updateFamilyServe(List<FamilyServe> familyServes);
}
