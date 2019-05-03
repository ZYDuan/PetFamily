package com.zyd.petfamily.controller;

import com.zyd.petfamily.domain.Response.PetInfoResponse;
import com.zyd.petfamily.domain.common.CommonResponse;
import com.zyd.petfamily.domain.pojo.PetInfo;
import com.zyd.petfamily.domain.pojo.PetKind;
import com.zyd.petfamily.service.PetService;
import com.zyd.petfamily.utils.CodeUtil;
import com.zyd.petfamily.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: petfamily
 * @author: zyd
 * @description: Receive Request of Pet
 * @create: 2019-04-26 20:13
 */

@RestController
@RequestMapping("/pet")
public class PetController {

    private Logger log = LoggerFactory.getLogger(PetController.class);

    @Autowired
    private PetService petServiceImpl;

    /**
     * 获取所有宠物类型
     *
     * @return
     */
    @RequestMapping("/getPetKinds")
    public CommonResponse getPetKinds() {
        log.info("获取所有宠物类型");

        List<PetKind> petKinds = petServiceImpl.selectPetKind();
        return new CommonResponse(CodeUtil.SUCCESS_CODE, petKinds, CodeUtil.SUCCESS_MSG);
    }

    /**
     * 获取某个用户所有宠物
     *
     * @param userId
     * @return
     */
    @RequestMapping("/petList")
    public CommonResponse getPetList(Integer userId) {
        log.info("获取某个用户所有宠物");

        List<PetInfoResponse> petInfos = petServiceImpl.selectPetList(userId);
        return new CommonResponse(CodeUtil.SUCCESS_CODE, petInfos, CodeUtil.SUCCESS_MSG);
    }

    /**
     * 注册宠物信息
     *
     * @param petData
     * @param petPic
     * @return
     */
    @RequestMapping("/register")
    public CommonResponse register(@RequestParam("petData") String petData,
                                   @RequestParam("petPic") MultipartFile petPic) {
        log.info("注册新的宠物信息");

        //将json字符串转化为petinfo对象
        PetInfo petInfo = JsonUtil.fromJson(petData, PetInfo.class);
        if (!petPic.isEmpty()) {
            petServiceImpl.insertPet(petInfo, petPic);
        }
        return new CommonResponse(CodeUtil.SUCCESS_CODE, CodeUtil.SUCCESS_MSG);

    }

    /**
     * 更新宠物信息(不包括图片)
     *
     * @return
     */
    @RequestMapping("/update")
    public CommonResponse update(@RequestBody PetInfo petInfo) {
        log.info("更新宠物信息");

        petServiceImpl.updatePet(petInfo);
        return new CommonResponse(CodeUtil.SUCCESS_CODE, CodeUtil.SUCCESS_MSG);

    }

    /**
     * 更新宠物信息（包括图片）
     *
     * @return
     */
    @RequestMapping("/updateWithPic")
    public CommonResponse updateWithPic(@RequestParam("petData") String petData,
                                        @RequestParam("petPic") MultipartFile petPic) {
        log.info("更新宠物信息和相关图片");

        //将json字符串转化为petinfo对象
        PetInfo petInfo = JsonUtil.fromJson(petData, PetInfo.class);
        petServiceImpl.updatePetWithPic(petInfo, petPic);
        return new CommonResponse(CodeUtil.SUCCESS_CODE, CodeUtil.SUCCESS_MSG);

    }

    /**
     * 根据宠物id获取某只宠物信息
     *
     * @param petId
     * @return
     */
    @RequestMapping("/petInfo")
    public CommonResponse petInfo(Integer petId) {
        log.info("根据宠物id获取某只宠物信息");

        PetInfo petInfo = petServiceImpl.selectPet(petId);
        if (petInfo != null)
            return new CommonResponse(CodeUtil.SUCCESS_CODE, petInfo, CodeUtil.SUCCESS_MSG);
        return new CommonResponse(CodeUtil.FAILE_CODE, CodeUtil.FAILE_MSG);
    }

    /**
     * 删除宠物信息
     *
     * @param petId
     * @return
     */
    @RequestMapping("/delete")
    public CommonResponse delete(Integer petId) {
        log.info("删除宠物信息");

        petServiceImpl.deletePet(petId);
        return new CommonResponse(CodeUtil.SUCCESS_CODE, CodeUtil.SUCCESS_MSG);
    }
}
