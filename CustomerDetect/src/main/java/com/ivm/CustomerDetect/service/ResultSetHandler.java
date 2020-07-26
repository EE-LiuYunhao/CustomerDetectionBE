package com.ivm.CustomerDetect.service;

import java.sql.ResultSet;
import java.util.List;

public interface ResultSetHandler
{
    List<Object> handler(ResultSet resultSet) throws Exception;
}