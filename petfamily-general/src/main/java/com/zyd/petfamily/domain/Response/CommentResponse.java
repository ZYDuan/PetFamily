package com.zyd.petfamily.domain.Response;

import com.zyd.petfamily.domain.pojo.FamilyComment;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-04-30 15:07
 */
public class CommentResponse extends FamilyComment {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
