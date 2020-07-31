package com.ivm.CustomerDetect.model;

import java.beans.JavaBean;
import java.sql.Timestamp;

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
    public void setRecordId(String value)
    {
        recordId = Integer.getInteger(value);
    }

    public Timestamp getDatetimeIn()
    {
        return datetimeIn;
    }
    public void setDatetimeIn(String value)
    {
        datetimeIn = Timestamp.valueOf(value);
    }

    public Timestamp getDatetimeOut()
    {
        return datetimeOut;
    }
    public void setDatetimeOut(String value)
    {
        datetimeOut = Timestamp.valueOf(value);
    }

    public Integer getUid()
    {
        return uid;
    }
    public void setUid(String value)
    {
        uid = Integer.getInteger(value);
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