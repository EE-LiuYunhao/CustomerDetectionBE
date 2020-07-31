package com.ivm.CustomerDetect.model;

import java.beans.JavaBean;

@JavaBean
public class FaceImagePathModel
{
    private Integer imgId;
    private Integer uid;
    private Integer faceId;
    private String imgPath;

    public Integer getImgId()
    {
        return imgId;
    }
    public void setImgId(String value)
    {
        imgId = Integer.getInteger(value);
    }

    public String getImgPath()
    {
        return imgPath;
    }
    public void setImgPath(String value)
    {
        imgPath = value;
    }

    public Integer getUid()
    {
        return uid;
    }
    public void setUid(String value)
    {
        uid = Integer.getInteger(value);
    }

    public Integer getFaceId()
    {
        return faceId;
    }
    public void setFaceId(String value)
    {
        faceId = Integer.getInteger(value);
    }

    @Override
    public String toString()
    {
        return String.format(
            "{imgId: %d, uid: %d, faceId: %d, imgPath: %s}", 
            imgId,
            uid,
            faceId,
            imgPath
        );
    }
}