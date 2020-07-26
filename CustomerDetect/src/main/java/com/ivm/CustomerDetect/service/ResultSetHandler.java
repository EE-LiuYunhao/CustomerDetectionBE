package com.ivm.CustomerDetect.service;

import java.sql.ResultSet;
import java.util.List;

/**
 * Converting the result set from SQL into java bean objects
 */
public interface ResultSetHandler
{
    /**
     * Converting the result set from SQL into java bean objects
     * @param resultSet The query results
     * @return List of objects drawn from the results
     * @throws Exception
     */
    List<Object> handler(ResultSet resultSet) throws Exception;
}