package com.zyd.petfamily.dao;

import com.zyd.petfamily.domain.pojo.OrderStatus;

public interface OrderStatusMapper {
    int deleteByPrimaryKey(Integer orderStatusId);

    int insert(OrderStatus record);

    int insertSelective(OrderStatus record);

    OrderStatus selectByPrimaryKey(Integer orderStatusId);

    int updateByPrimaryKeySelective(OrderStatus record);

    int updateByPrimaryKey(OrderStatus record);
}