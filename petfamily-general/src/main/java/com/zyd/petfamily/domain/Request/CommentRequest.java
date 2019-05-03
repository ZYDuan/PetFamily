package com.zyd.petfamily.domain.Request;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-04-30 17:12
 */
public class CommentRequest {
    private Integer orderId;
    private String commentContent;
    private Integer commentStar;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getCommentStar() {
        return commentStar;
    }

    public void setCommentStar(Integer commentStar) {
        this.commentStar = commentStar;
    }
}
