package com.zyd.petfamily.controller;

import com.zyd.petfamily.domain.Request.FamilyInfoRequest;
import com.zyd.petfamily.domain.Response.CommentResponse;
import com.zyd.petfamily.domain.Response.FamilyResponse;
import com.zyd.petfamily.domain.Response.PetServeResponse;
import com.zyd.petfamily.domain.common.CommonResponse;
import com.zyd.petfamily.domain.pojo.FamilyComment;
import com.zyd.petfamily.domain.pojo.FamilyInfo;
import com.zyd.petfamily.domain.pojo.FamilyServe;
import com.zyd.petfamily.service.FamilyService;
import com.zyd.petfamily.utils.CodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Queue;

/**
 * @program: petfamily
 * @author: zyd
 * @description: Receive the request about FamilyService
 * @create: 2019-04-28 17:30
 */
@RestController
@RequestMapping("/petFamily")
public class PetFamilyController {

    @Autowired
    private FamilyService familyServiceImpl;

    private static Logger log = LoggerFactory.getLogger(PetFamilyController.class);

    /**
     * 请求进行宠物家庭注册
     *
     * @param familyInfoRequest
     * @return
     */
    @RequestMapping("/register")
    public CommonResponse register(@RequestBody FamilyInfoRequest familyInfoRequest) {
        log.info("请求进行宠物家庭注册");

        if (familyServiceImpl.insertFamily(familyInfoRequest))
            return new CommonResponse(CodeUtil.SUCCESS_CODE, CodeUtil.SUCCESS_MSG);

        return new CommonResponse(CodeUtil.FAILE_CODE, "该用户已经注册过宠物家庭");
    }

    /**
     * 更新宠物家庭信息
     *
     * @param familyInfoRequest
     * @return
     */
    @RequestMapping("/update")
    public CommonResponse update(@RequestBody FamilyInfoRequest familyInfoRequest) {
        log.info("进行宠物家庭更新");

        if (!familyServiceImpl.updateFamily(familyInfoRequest))
            return new CommonResponse(CodeUtil.FAILE_CODE, "家庭信息不存在");

        return new CommonResponse(CodeUtil.SUCCESS_CODE, CodeUtil.SUCCESS_MSG);
    }

    /**
     * 删除宠物家庭信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/layout")
    public CommonResponse delete(Integer userId) {
        log.info("删除宠物家庭信息");

        familyServiceImpl.deleteFamily(userId);

        return new CommonResponse(CodeUtil.SUCCESS_CODE, CodeUtil.SUCCESS_MSG);
    }


    /**
     * 根据用户id获取信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("")
    public CommonResponse familyInfo(Integer userId) {
        log.info("根据用户id获取信息");

        FamilyInfo familyInfo = familyServiceImpl.selectByUserId(userId);

        if (familyInfo != null)
            return new CommonResponse(CodeUtil.SUCCESS_CODE, familyInfo, CodeUtil.SUCCESS_MSG);
        return new CommonResponse(CodeUtil.FAILE_CODE, "该用户还未注册宠物家庭");
    }

    /**
     * 根据家庭id获取信息
     *
     * @param familyId
     * @return
     */
    @RequestMapping("/selectByfamilyId")
    public CommonResponse selectByfamilyId(Integer familyId) {
        log.info("根据家庭id获取信息");

        FamilyInfo familyInfo = familyServiceImpl.selectByInfoId(familyId);

        if (familyInfo != null)
            return new CommonResponse(CodeUtil.SUCCESS_CODE, familyInfo, CodeUtil.SUCCESS_MSG);
        return new CommonResponse(CodeUtil.FAILE_CODE, "该宠物家庭暂无数据");
    }

    /**
     * 查看宠物家庭的宠物寄养信息
     *
     * @param familyId
     * @return
     */
    @RequestMapping("/serve")
    public CommonResponse selectServe(Integer familyId) {
        log.info("查看宠物家庭的宠物寄养信息");

        //从数据库中获取宠物家庭的宠物价格数据
        List<PetServeResponse> familyServes = familyServiceImpl.selectFamilyServe(familyId);

        //判断是否有数据
        if (familyServes == null || familyServes.size() <= 0)
            return new CommonResponse(CodeUtil.FAILE_CODE, "无寄养信息");

        return new CommonResponse(CodeUtil.SUCCESS_CODE, familyServes, CodeUtil.SUCCESS_MSG);
    }


    /**
     * 更新宠物家庭的宠物寄养信息
     *
     * @param familyServes
     * @return
     */
    @RequestMapping("/serve/update")
    public CommonResponse updateServe(@RequestBody List<FamilyServe> familyServes) {
        log.info("进行宠物家庭更新");

        familyServiceImpl.updateFamilyServe(familyServes);

        return new CommonResponse(CodeUtil.SUCCESS_CODE, CodeUtil.SUCCESS_MSG);
    }

//    /**
//     * 插入宠物家庭的宠物寄养信息
//     *
//     * @param familyServes
//     * @return
//     */
//    @RequestMapping("/serve/register")
//    public CommonResponse insertServe(@RequestBody List<FamilyServe> familyServes) {
//        log.info("插入宠物家庭的宠物寄养信息");
//
//        familyServiceImpl.insertFamilyServe(familyServes);
//
//        return new CommonResponse(CodeUtil.SUCCESS_CODE, CodeUtil.SUCCESS_MSG);
//    }

    /**
     * 根据家庭id获取该用户评论
     *
     * @param familyId
     * @return
     */
    @RequestMapping("/{familyId}/comment")
    public CommonResponse getComments(@PathVariable(value = "familyId") Integer familyId) {
        log.info("插入宠物家庭的宠物寄养信息");

        List<CommentResponse> comments = familyServiceImpl.selectComment(familyId);

        if (comments != null)
            return new CommonResponse(CodeUtil.SUCCESS_CODE, comments, CodeUtil.SUCCESS_MSG);
        return new CommonResponse(CodeUtil.FAILE_CODE, "该家庭暂无评论");
    }

    /**
     * 查询定点附近的宠物家庭信息
     *
     * @param lat
     * @param lng
     * @return
     */
    @RequestMapping("/searchFamily")
    public CommonResponse searchFamily(Double lng, Double lat){
        log.info("查询经度为{}，纬度为{} 附近的家庭信息", lng, lat);

        Queue<FamilyResponse> familyList = familyServiceImpl.familyInfoList(lng, lat);

        if (familyList == null || familyList.size() == 0) {
            log.error("暂无寄养家庭");
            return new CommonResponse(CodeUtil.FAILE_CODE, "附近暂无寄养家庭");
        }

        return new CommonResponse(CodeUtil.SUCCESS_CODE, familyList, CodeUtil.SUCCESS_MSG);

    }
}
