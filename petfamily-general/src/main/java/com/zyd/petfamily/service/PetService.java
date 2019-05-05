package com.zyd.petfamily.service;

import com.zyd.petfamily.domain.Response.PetInfoResponse;
import com.zyd.petfamily.domain.pojo.PetInfo;
import com.zyd.petfamily.domain.pojo.PetKind;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PetService {
    /**
     * 获取宠物信息
     *
     * @param petId
     * @return
     */
    PetInfo selectPet(Integer petId);

    /**
     * 存储新的宠物信息
     *
     * @param petInfo
     * @return
     */
    boolean insertPet(PetInfo petInfo) throws IOException;

    /**
     * 更新宠物信息
     *
     * @param petInfo
     * @return
     */
    boolean updatePet(PetInfo petInfo);

    /**
     * 删除宠物信息
     *
     * @param petId
     * @return
     */
    boolean deletePet(Integer petId);

    /**
     * 获取宠物种类
     *
     * @return
     */
    List<PetKind> selectPetKind();

    /**
     * 获取某个用户的所有宠物信息
     *
     * @param userId
     * @return
     */
    List<PetInfoResponse> selectPetList(Integer userId);

    /**
     * 更新宠物信息和图片
     *
     * @param petInfo
     */
    void updatePetWithPic(PetInfo petInfo) throws IOException;
}
