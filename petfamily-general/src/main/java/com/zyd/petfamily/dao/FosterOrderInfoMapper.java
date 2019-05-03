package com.zyd.petfamily.dao;

import com.zyd.petfamily.domain.Response.OrderResponse;
import com.zyd.petfamily.domain.pojo.FosterOrderInfo;

public interface FosterOrderInfoMapper {
    int deleteByPrimaryKey(Integer orderInfoId);

    int insert(FosterOrderInfo record);

    int insertSelective(FosterOrderInfo record);

    FosterOrderInfo selectByPrimaryKey(Integer orderInfoId);

    int updateByPrimaryKeySelective(FosterOrderInfo record);

    int updateByPrimaryKey(FosterOrderInfo record);

    FosterOrderInfo selectByOrder(Integer orderId);
}