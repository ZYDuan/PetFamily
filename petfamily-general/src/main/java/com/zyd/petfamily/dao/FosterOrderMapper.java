package com.zyd.petfamily.dao;

import com.zyd.petfamily.domain.Response.OrderResponse;
import com.zyd.petfamily.domain.pojo.FosterOrder;

import java.util.List;

public interface FosterOrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(FosterOrder record);

    int insertSelective(FosterOrder record);

    FosterOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(FosterOrder record);

    int updateByPrimaryKey(FosterOrder record);

    List<OrderResponse> selectByUserId(Integer userId);

    OrderResponse selectSignleByOrder(Integer orderId);

    List<OrderResponse> selectByFamilyId(Integer familyId);
}