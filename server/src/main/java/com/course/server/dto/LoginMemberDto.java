package com.course.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class LoginMemberDto {

    /**
     * id
     */
    private String id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 昵称
     */
    private String name;

    private String token;

    private String photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "LoginMemberDto{" +
                "id='" + id + '\'' +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}