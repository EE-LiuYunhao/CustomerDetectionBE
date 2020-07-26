package com.ivm.CustomerDetect.model;

import java.beans.JavaBean;

/**
 * For the entires from view AverageStay
 */
@JavaBean
public class AverageStayModel
{
    private Integer uid;
    private float averageStay;

    public Integer getUid()
    {
        return uid;
    }
    public void setUid(Integer value)
    {
        uid = value;
    }

    public float getAverageStay()
    {
        return averageStay;
    }
    public void setAverageStay(float value)
    {
        averageStay = value;
    }

    @Override
    public String toString()
    {
        return String.format("{uid: %d, averageStay: %f}", uid, averageStay);
    }
}