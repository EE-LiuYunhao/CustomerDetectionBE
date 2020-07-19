package com.ivm.CustomerDetect.model;

import java.beans.JavaBean;

@JavaBean
public class UserInfoModel
{
    private Integer uid;
    private String name;
    private Character gender;
    private String [] imgPath;
    private String [] encodedFacePath;
    private Long avgStay; // in minutes

    public void setUid(Integer value)
    {
        uid = value;
    }
    public Integer getUid()
    {
        return uid;
    }

    public void setName(String value)
    {
        name = value;
    }
    public String getName()
    {
        return name;
    }

    public void setGender(Character value)
    {
        gender = value;
    }
    public Character getGender()
    {
        return gender;
    }

    public void setImgPath(String [] value)
    {
        imgPath = value;
    }
    public String [] getImgPath()
    {
        return imgPath;
    }
    
    public void setEncodedFacePath(String [] value)
    {
        encodedFacePath = value;
    }
    public String [] getEncodedFacePath()
    {
        return encodedFacePath;
    }

    public void setAvgStay(Long value)
    {
        avgStay = value;
    }
    public Long getAvgStay()
    {
        return avgStay;
    }

    public void testSetter(
        Integer uid,
        String name,
        Character gender,
        String [] imgPath,
        String [] encodedFacePath,
        Long avgStay // in minutes
    )
    {
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.imgPath = imgPath;
        this.encodedFacePath = encodedFacePath;
        this.avgStay = avgStay;
    }
}