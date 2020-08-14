package com.ivm.CustomerDetect;

public class IllegalURLParameter extends Exception
{

    private static final long serialVersionUID = -3345106165767373757L;
    private String msg = "Illegal URL parameter(s)";
    private String url = "";

    public IllegalURLParameter(){};
    public IllegalURLParameter(String msg)
    {
        this.msg = msg;
    }
    public IllegalURLParameter(String url, String explanation)
    {
        this.msg += " @"+url+" because:\n\t"+explanation;
    }

    @Override
    public String getMessage()
    {
        return msg;
    }

    @Override
    public String getLocalizedMessage()
    {
        return url;
    }
}