package com.ivm.CustomerDetect.model;

import java.beans.JavaBean;

/**
 * Model for the returned result via RESTFul API
 * Including all the relevant info that is connected 
 * by the UID field. 
 */
@JavaBean
public class UserInfoModel
{
    private Integer uid;
    private String name;
    private Character gender;
    private String [] imgPath;
    private String [] encodedFacePath;
    private Float avgStay; // in minutes

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

    public void setAvgStay(Float value)
    {
        avgStay = value;
    }
    public Float getAvgStay()
    {
        return avgStay;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("{\n\tuid:%d;\n\tname:%s\n\tgender:%c\n\timgPath:\n\t{", uid, name, gender));
        for(String path : imgPath)
        {
            builder.append(String.format("\n\t\t%s", path));
        }
        builder.append("\n\t}\n\tencodedFacePath:\n\t{");
        for(String path : encodedFacePath)
        {
            builder.append(String.format("\n\t\t%s", path));
        }
        builder.append("\n\t}\n}");
        return builder.toString();
    }
}