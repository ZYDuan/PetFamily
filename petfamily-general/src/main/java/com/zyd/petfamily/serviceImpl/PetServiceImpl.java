package com.zyd.petfamily.serviceImpl;

import com.zyd.petfamily.dao.PetInfoMapper;
import com.zyd.petfamily.dao.PetKindMapper;
import com.zyd.petfamily.domain.Response.PetInfoResponse;
import com.zyd.petfamily.domain.pojo.PetInfo;
import com.zyd.petfamily.domain.pojo.PetKind;
import com.zyd.petfamily.service.PetService;
import com.zyd.petfamily.utils.CodeUtil;
import com.zyd.petfamily.utils.PicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: petfamily
 * @author: zyd
 * @description: The service of pet
 * @create: 2019-04-26 20:14
 */
@Service
public class PetServiceImpl implements PetService {

    private static Logger log = LoggerFactory.getLogger(PetServiceImpl.class);

    @Autowired
    private PetInfoMapper petInfoMapper;

    @Autowired
    private PetKindMapper petKindMapper;

    @Override
    public PetInfo selectPet(Integer petId) {
        PetInfo petInfo = petInfoMapper.selectByPrimaryKey(petId);
        PetKind kind = petKindMapper.selectByPrimaryKey(petInfo.getPetKind());
        return new PetInfoResponse(petInfo, kind.getKindName());
    }

    @Override
    public boolean insertPet(PetInfo petInfo, MultipartFile petPic) {
        //将图片存储url设置到宠物信息中
        PicUtil picUtil = new PicUtil(petPic);
        //将宠物数据存储到数据库中
        petInfoMapper.insert(petInfo);

        petInfo.setPetPic(picUtil.storePic("pet", petInfo.getUserId(), petInfo.getPetId()));

        petInfoMapper.updateByPrimaryKey(petInfo);
        return true;
    }

    @Override
    public boolean updatePet(PetInfo petInfo) {
        //根据请求的petid查询是否存在该数据
        PetInfo oldPetInfo = petInfoMapper.selectByPrimaryKey(petInfo.getPetId());
        //旧数据不存在
        if (oldPetInfo == null)
            return false;
        //更新旧数据
        petInfoMapper.updateByPrimaryKey(petInfo);
        return true;
    }

    @Override
    public boolean deletePet(Integer petId) {
        petInfoMapper.deleteByPrimaryKey(petId);
        return true;
    }

    @Override
    public List<PetKind> selectPetKind() {
        return petKindMapper.selectAll();
    }

    @Override
    public List<PetInfoResponse> selectPetList(Integer userId) {
        List<PetInfo> petList = petInfoMapper.selectByUser(userId);
        List<PetInfoResponse> resList = new ArrayList<>();
        for (PetInfo petInfo : petList) {
            resList.add(new PetInfoResponse(petInfo,
                    petKindMapper.selectByPrimaryKey(petInfo.getPetKind()).getKindName()));
        }
        return resList;
    }

    @Override
    public void updatePetWithPic(PetInfo petInfo, MultipartFile petPic) {
        //获取旧数据
        PetInfo oldPetInfo = petInfoMapper.selectByPrimaryKey(petInfo.getPetId());
        //获得旧图片存放路径
        String oldUrl = oldPetInfo.getPetPic();
        String url = CodeUtil.PIC_URL + petInfo.getUserId() + "/pet" + oldUrl.substring(oldUrl.lastIndexOf("/"));
        //删除旧照片并存放新照片
        PicUtil picUtil = new PicUtil(petPic);
        picUtil.deletePic(url);
        petInfo.setPetPic(picUtil.storePic("pet", petInfo.getUserId(), petInfo.getPetId()));
        petInfoMapper.updateByPrimaryKey(petInfo);
    }
}
