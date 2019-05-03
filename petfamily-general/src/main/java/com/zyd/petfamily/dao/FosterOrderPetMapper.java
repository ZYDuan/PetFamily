package com.zyd.petfamily.dao;

import com.zyd.petfamily.domain.Response.OrderPetResponse;
import com.zyd.petfamily.domain.pojo.FosterOrderPet;

import java.util.List;

public interface FosterOrderPetMapper {
    int deleteByPrimaryKey(Integer orderPetId);

    int insert(FosterOrderPet record);

    int insertSelective(FosterOrderPet record);

    FosterOrderPet selectByPrimaryKey(Integer orderPetId);

    int updateByPrimaryKeySelective(FosterOrderPet record);

    int updateByPrimaryKey(FosterOrderPet record);

    List<OrderPetResponse> selectByOrder(Integer orderId);
}