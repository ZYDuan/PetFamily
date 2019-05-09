package com.zyd.petfamily.serviceImpl;

import com.zyd.petfamily.dao.FamilyCommentMapper;
import com.zyd.petfamily.dao.FamilyInfoMapper;
import com.zyd.petfamily.dao.FamilyServeMapper;
import com.zyd.petfamily.domain.Request.FamilyInfoRequest;
import com.zyd.petfamily.domain.Response.CommentResponse;
import com.zyd.petfamily.domain.Response.FamilyResponse;
import com.zyd.petfamily.domain.Response.PetServeResponse;
import com.zyd.petfamily.domain.pojo.FamilyInfo;
import com.zyd.petfamily.domain.pojo.FamilyServe;
import com.zyd.petfamily.service.FamilyService;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public boolean deleteFamily(Integer userId) {
        familyInfoMapper.deleteByUserId(userId);
        return true;
    }

    @Override
    public FamilyInfo selectByUserId(Integer userId) {
        return familyInfoMapper.selectByUser(userId);
    }

    @Override
    public Queue<FamilyResponse> familyInfoList(Double lng, Double lat) {
        //获取所有家庭信息
        List<FamilyInfo> infoList = familyInfoMapper.selectAll();
        if (infoList == null || infoList.size() == 0)
            return null;

        //构造目标坐标
        GlobalCoordinates target = new GlobalCoordinates(lat, lng);
        //根据家庭与目标坐标距离大小进行排序
        PriorityQueue<FamilyResponse> queue = new PriorityQueue<>(new Comparator<FamilyResponse>() {
            @Override
            public int compare(FamilyResponse o1, FamilyResponse o2) {
                if (o1.getDistance() > o2.getDistance())
                    return 1;
                return -1;
            }
        });

        for (FamilyInfo info : infoList){
            //构造家庭的坐标
            GlobalCoordinates source = new GlobalCoordinates(info.getFamilyLat(), info.getFamilyLng());
            //根据WGS84坐标系计算两点之间的距离
            Double distance = getDistanceMeter(target, source, Ellipsoid.WGS84);
            FamilyResponse familyResponse = new FamilyResponse(info, distance);
            queue.add(familyResponse);
        }
        return queue;
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

    /**
     * 根据坐标系计算两个坐标之间的距离
     *
     * @param from
     * @param to
     * @param ellipsoid
     * @return
     */
    private double getDistanceMeter(GlobalCoordinates from, GlobalCoordinates to, Ellipsoid ellipsoid) {
        //调用GeodeticCalculator,调用计算方法，传入坐标系，经纬度进行计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, from, to);
        return geoCurve.getEllipsoidalDistance();
    }

}
