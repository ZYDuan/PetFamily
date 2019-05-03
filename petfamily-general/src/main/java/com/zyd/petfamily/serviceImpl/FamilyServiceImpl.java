package com.zyd.petfamily.serviceImpl;

import com.zyd.petfamily.dao.FamilyCommentMapper;
import com.zyd.petfamily.dao.FamilyInfoMapper;
import com.zyd.petfamily.dao.FamilyServeMapper;
import com.zyd.petfamily.domain.Request.FamilyInfoRequest;
import com.zyd.petfamily.domain.Response.CommentResponse;
import com.zyd.petfamily.domain.Response.PetServeResponse;
import com.zyd.petfamily.domain.pojo.FamilyComment;
import com.zyd.petfamily.domain.pojo.FamilyInfo;
import com.zyd.petfamily.domain.pojo.FamilyServe;
import com.zyd.petfamily.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-04-28 17:32
 */
@Service
public class FamilyServiceImpl implements FamilyService {
    @Autowired
    private FamilyInfoMapper familyInfoMapper;

    @Autowired
    private FamilyServeMapper familyServeMapper;

    @Autowired
    private FamilyCommentMapper familyCommentMapper;

    @Override
    public boolean insertFamily(FamilyInfoRequest familyInfoRequest) {
        FamilyInfo familyInfo = setFamilyInfo(familyInfoRequest);

        //判断是否已经注册过
        if (familyInfoMapper.selectByUser(familyInfoRequest.getUserId()) != null)
            return false;

        //向数据库中存储新的家庭信息数据
        familyInfoMapper.insert(familyInfo);

        //向数据库中插入寄养家庭的寄养宠物价格
        List<FamilyServe> serves = familyInfoRequest.getFamilyServeDetail();
        for (FamilyServe serve : serves) {
            serve.setFamilyInfoId(familyInfo.getFamilyInfoId());
            familyServeMapper.insert(serve);
        }

        return true;
    }

    @Override
    public boolean updateFamily(FamilyInfoRequest familyInfoRequest) {
        FamilyInfo familyInfo = setFamilyInfo(familyInfoRequest);

        //判断数据是否还存在
        if (familyInfoMapper.selectByPrimaryKey(familyInfoRequest.getFamilyInfoId()) != null) {
            //设置家庭数据的标志id
            familyInfo.setFamilyInfoId(familyInfoRequest.getFamilyInfoId());
            //更新家庭数据
            familyInfoMapper.updateByPrimaryKeySelective(familyInfo);
            //更新家庭寄养宠物信息
            List<FamilyServe> familyServes = familyInfoRequest.getFamilyServeDetail();
            for (FamilyServe serve : familyServes) {
                serve.setFamilyInfoId(familyInfoRequest.getFamilyInfoId());
            }
            updateFamilyServe(familyInfoRequest.getFamilyServeDetail());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFamily(Integer familyId) {
        familyInfoMapper.deleteByPrimaryKey(familyId);
        return true;
    }

    @Override
    public FamilyInfo selectByUserId(Integer userId) {
        return familyInfoMapper.selectByUser(userId);
    }

    @Override
    public List<FamilyInfoRequest> familyInfoList() {
        return null;
    }

    @Override
    public FamilyInfo selectByInfoId(Integer familyId) {
        return familyInfoMapper.selectByPrimaryKey(familyId);
    }

    @Override
    public List<CommentResponse> selectComment(Integer familyId) {
        return familyCommentMapper.selectByFamily(familyId);
    }

    @Override
    public List<PetServeResponse> selectFamilyServe(Integer familyId) {
        return familyServeMapper.selectByFamilyId(familyId);
    }

    @Override
    public void insertFamilyServe(List<FamilyServe> familyServes) {
        if (familyServes != null || familyServes.size() > 0) {
            for (FamilyServe serve : familyServes)
                familyServeMapper.insert(serve);
        }

    }

    @Override
    public void updateFamilyServe(List<FamilyServe> familyServes) {
        if (familyServes != null || familyServes.size() > 0) {
            Integer familyId = familyServes.get(0).getFamilyInfoId();
            familyServeMapper.deleteByFamily(familyId);
            insertFamilyServe(familyServes);
        }
    }

    /**
     * 将FamilyInfoRequest对象的数据设置到FamilyInfo对象中
     *
     * @param familyInfoRequest
     * @return
     */
    private FamilyInfo setFamilyInfo(FamilyInfoRequest familyInfoRequest) {
        FamilyInfo familyInfo = new FamilyInfo();
        familyInfo.setFamilyAddress(familyInfoRequest.getFamilyAddress());
        familyInfo.setFamilyDetail(familyInfoRequest.getFamilyDetail());
        familyInfo.setFamilyLat(familyInfoRequest.getFamilyLat());
        familyInfo.setFamilyLng(familyInfoRequest.getFamilyLng());
        familyInfo.setFamilyName(familyInfoRequest.getFamilyName());
        familyInfo.setUserId(familyInfoRequest.getUserId());
        familyInfo.setFamilyPhone(familyInfo.getFamilyPhone());
        familyInfo.setFamilyCommentCount(familyInfoRequest.getFamilyCommentCount() == null ? 0 : familyInfoRequest.getFamilyCommentCount());
        familyInfo.setFamilyCommentStar(familyInfoRequest.getFamilyCommentStar() == null ? new Double(0) : familyInfoRequest.getFamilyCommentStar());
        return familyInfo;
    }


}
