package com.ivm.CustomerDetect.model;

import java.beans.JavaBean;

/**
 * Modelling the returned result of stored procedure: SelectByName
 */
@JavaBean
public class AggregatedUserInfoModel
{
    private Integer faceId;
    private String facePath;
    private Integer imageId;
    private String imagePath;

    public Integer getFaceId()
    {
        return faceId;
    }
    public void setFaceId(String value)
    {
        faceId = Integer.valueOf(value);
    }

    public String getEncodedFacePath()
    {
        return facePath;
    }
    public void setEncodedFacePath(String value)
    {
        facePath = value;
    }

    public Integer getImgId()
    {
        return imageId;
    }
    public void setImgId(String value)
    {
        imageId = Integer.valueOf(value);
    }

    public String getImgPath()
    {
        return imagePath;
    }
    public void setImgPath(String value)
    {
        imagePath = value;
    }

    @Override
    public String toString()
    {
        return String.format("{FaceId: %d, FacePath: %s, ImageId: %d, ImagePath: %s}", faceId, facePath, imageId, imagePath);
    }
}