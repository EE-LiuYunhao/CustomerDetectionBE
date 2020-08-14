package com.ivm.CustomerDetect;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class ControllerExceptionHandler
{
    @ExceptionHandler(IllegalURLParameter.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String alertUrlError(IllegalURLParameter ex)
    {
        return ex.getMessage();
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String mysqlFailure(SQLException ex)
    {
        return "Database error. Try later";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String javaBeanParsingFailed(Exception ex)
    {
        ex.printStackTrace();
        return "Internal server exception, needing inspection";
    }
}