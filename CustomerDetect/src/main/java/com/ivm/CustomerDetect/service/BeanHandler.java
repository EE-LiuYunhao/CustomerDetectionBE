package com.ivm.CustomerDetect.service;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * Converting the result set from SQL into java bean objects
 */
public class BeanHandler implements ResultSetHandler
{
    private Class<?> classRef;

    /**
     * Constructor
     * @param ref The target java bean classtype
     */
    public BeanHandler(Class<?> ref)
    {
        this.classRef = ref;
    }

    @Override
    public List<Object> handler(ResultSet resultSet) throws Exception
    {
        List<Object> results = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        while(resultSet.next())
        {
            Object bean = classRef.getDeclaredConstructor().newInstance();
            for (int i=0; i< resultSetMetaData.getColumnCount() ; i++)
            {
                String columnName = resultSetMetaData.getColumnName(i+1);
                String value      = resultSet.getString(i+1);
                Field field       = classRef.getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(bean, value);
            }
            results.add(bean);
        }
        return results;
    }
}