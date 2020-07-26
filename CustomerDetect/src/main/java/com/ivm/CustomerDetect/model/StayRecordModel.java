package com.ivm.CustomerDetect.model;

import java.beans.JavaBean;
import java.security.Timestamp;

@JavaBean
public class StayRecordModel
{
    private Integer recordId;
    private Timestamp datetimeIn;
    private Timestamp datetimeOut;
    private Integer uid;

    public Integer getRecordId()
    {
        return recordId;
    }
    public void setRecordId(Integer value)
    {
        recordId = value;
    }

    public Timestamp getDatetimeIn()
    {
        return datetimeIn;
    }
    public void setDatetimeIn(Timestamp value)
    {
        datetimeIn = value;
    }

    public Timestamp getDatetimeOut()
    {
        return datetimeOut;
    }
    public void setDatetimeOut(Timestamp value)
    {
        datetimeOut = value;
    }

    public Integer getUid()
    {
        return uid;
    }
    public void setUid(Integer value)
    {
        uid = value;
    }

    @Override
    public String toString()
    {
        return String.format(
            "{recordId: %d, datetimeIn: %s, datetimeOUt: %s, uid: %d}",
            recordId, datetimeIn.toString(),
            datetimeOut.toString(),
            uid
        );
    }
}