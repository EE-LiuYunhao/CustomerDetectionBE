package com.ivm.CustomerDetect.model;

import java.beans.JavaBean;

/**
 * For the query regarding the User table
 */
@JavaBean
public class UserModel
{
    private Integer uid;
    private String name;
    private Character gender;

    public void setUid(String value)
    {
        uid = Integer.getInteger(value);
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

    public void setGender(String value)
    {
        gender = value.charAt(0);
    }
    public Character getGender()
    {
        return gender;
    }
}