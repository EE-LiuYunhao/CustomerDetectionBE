package com.ivm.CustomerDetect.model;

import java.beans.JavaBean;

@JavaBean
public class UserModel
{
    private Integer uid;
    private String name;
    private Character gender;

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
}