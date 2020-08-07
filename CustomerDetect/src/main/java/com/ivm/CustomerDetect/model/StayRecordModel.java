package com.ivm.CustomerDetect.model;

import java.beans.JavaBean;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.format.annotation.DateTimeFormat;

@JavaBean
@JsonInclude(Include.NON_NULL)
public class StayRecordModel
{
    private Integer recordId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp datetimeIn;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp datetimeOut;
    private Integer uid;

    public Integer getRecordId()
    {
        return recordId;
    }
    public void setRecordId(String value)
    {
        recordId = Integer.valueOf(value);
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
        uid = Integer.valueOf(value);
    }

    @Override
    public String toString()
    {
        return String.format(
            "{recordId: %d, datetimeIn: %s, datetimeOUt: %s, uid: %d}",
            recordId,
            datetimeIn == null ?  "" : datetimeIn.toString(),
            datetimeOut == null ? "" : datetimeOut.toString(),
            uid
        );
    }
}