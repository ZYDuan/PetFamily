package com.zyd.petfamily.domain.pojo;

public class FamilyComment {
    private Integer commentId;

    private String commentContent;

    private Integer familyInfoId;

    private Integer userId;

    private Integer commentStar;

    public FamilyComment(Integer commentId, String commentContent, Integer familyInfoId, Integer userId, Integer commentStar) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.familyInfoId = familyInfoId;
        this.userId = userId;
        this.commentStar = commentStar;
    }

    public FamilyComment() {
        super();
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public Integer getFamilyInfoId() {
        return familyInfoId;
    }

    public void setFamilyInfoId(Integer familyInfoId) {
        this.familyInfoId = familyInfoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCommentStar() {
        return commentStar;
    }

    public void setCommentStar(Integer commentStar) {
        this.commentStar = commentStar;
    }
}