package com.ivm.CustomerDetect.model;

import java.beans.JavaBean;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JavaBean
@JsonInclude(Include.NON_NULL)
public class StayRecordModel
{
    private Integer recordId;
    private Integer uid;
    private String datetimeIn;
    private String datetimeOut;

    @JsonIgnore
    private Timestamp datetimeInOrigin;
    @JsonIgnore
    private Timestamp datetimeOutOrigin;

    public Integer getRecordId()
    {
        return recordId;
    }
    public void setRecordId(String value)
    {
        recordId = Integer.valueOf(value);
    }

    public String getDatetimeIn()
    {
        return datetimeIn;
    }
    public Timestamp getDatetimeInOrigin()
    {
        return datetimeInOrigin;
    }
    public void setDatetimeIn(String value)
    {
        datetimeInOrigin = Timestamp.valueOf(value);
        datetimeIn = value;
    }

    public String getDatetimeOut()
    {
        return datetimeOut;
    }
    public Timestamp getDatetimeOutOrigin()
    {
        return datetimeOutOrigin;
    }
    public void setDatetimeOut(String value)
    {
        datetimeOutOrigin = Timestamp.valueOf(value);
        datetimeOut = value;
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