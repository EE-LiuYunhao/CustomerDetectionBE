package com.ivm.CustomerDetect.model;

import java.beans.JavaBean;
import java.sql.Timestamp;


/**
 * For the query regarding the EncodedFace table
 */
@JavaBean
public class EncodedFaceModel
{
    private Integer faceId;
    private Integer uid;
    private Timestamp timestamp;
    private String encodedFacePath;

    public Integer getFaceId()
    {
        return faceId;
    }
    public void setFaceId(String value)
    {
        faceId = Integer.valueOf(value);
    }

    public Integer getUid()
    {
        return uid;
    }
    public void setUid(String value)
    {
        uid = Integer.valueOf(value);
    }

    public Timestamp getTimeStamp()
    {
        return timestamp;
    }
    public void setTimeStamp(String value)
    {
        timestamp = Timestamp.valueOf(value);
    }

    public String getEncodedFacePath()
    {
        return encodedFacePath;
    }
    public void setEncodedFacePath(String value)
    {
        encodedFacePath = value;
    }

    @Override
    public String toString()
    {
        return String.format
        ("{faceId: %d, uid: %d, timeStamp: %s, encodedFacePath: %s}", 
            faceId,
            uid,
            timestamp.toString(),
            encodedFacePath
        );
    }
}