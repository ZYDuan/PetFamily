package com.zyd.petfamily.domain.pojo;

public class User {
    private Integer userId;

    private String userName;

    private String userPwd;

    private String userEmail;

    private Integer userValid;

    public User(Integer userId, String userName, String userPwd, String userEmail, Integer userValid) {
        this.userId = userId;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userEmail = userEmail;
        this.userValid = userValid;
    }

    public User() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public Integer getUserValid() {
        return userValid;
    }

    public void setUserValid(Integer userValid) {
        this.userValid = userValid;
    }
}