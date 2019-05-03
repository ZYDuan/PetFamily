package com.zyd.petfamily.dao;

import com.zyd.petfamily.domain.Response.CommentResponse;
import com.zyd.petfamily.domain.pojo.FamilyComment;

import java.util.List;

public interface FamilyCommentMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(FamilyComment record);

    int insertSelective(FamilyComment record);

    FamilyComment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(FamilyComment record);

    int updateByPrimaryKey(FamilyComment record);

    List<CommentResponse> selectByFamily(Integer familyId);
}