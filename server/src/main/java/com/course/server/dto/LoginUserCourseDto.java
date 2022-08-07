package com.course.server.dto;


public class LoginUserCourseDto {

    /**
     * id
     */
    private String id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 昵称
     */
    private String name;


    /**
     * 登录凭证
     */
    private String token;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginUserCourseDto{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}