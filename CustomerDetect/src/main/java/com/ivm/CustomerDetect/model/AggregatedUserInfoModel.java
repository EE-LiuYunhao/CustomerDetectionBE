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
    public void setFaceId(Integer value)
    {
        faceId = value;
    }

    public String getFacePath()
    {
        return facePath;
    }
    public void setFacePath(String value)
    {
        facePath = value;
    }

    public Integer getImageId()
    {
        return imageId;
    }
    public void setImageId(Integer value)
    {
        imageId = value;
    }

    public String getImagePath()
    {
        return imagePath;
    }
    public void setImagePath(String value)
    {
        imagePath = value;
    }

    @Override
    public String toString()
    {
        return String.format("{FaceId: %d, FacePath: %s, ImageId: %d, ImagePath: %s}", faceId, facePath, imageId, imagePath);
    }
}