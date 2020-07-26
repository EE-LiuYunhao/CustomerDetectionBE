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
    public void setImgId(Integer value)
    {
        imgId = value;
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
    public void setUid(Integer value)
    {
        uid = value;
    }

    public Integer getFaceId()
    {
        return faceId;
    }
    public void setFaceId(Integer value)
    {
        faceId = value;
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