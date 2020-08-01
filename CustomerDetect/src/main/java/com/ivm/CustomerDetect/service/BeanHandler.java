package com.ivm.CustomerDetect.service;

import java.lang.reflect.Method;
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
                if(value == null)
                    break;
                Method setter = bean.getClass().getMethod("set"+ Character.toUpperCase(columnName.charAt(0)) + columnName.substring(1), String.class);
                setter.setAccessible(true);
                setter.invoke(bean, value);
            }
            results.add(bean);
        }
        return results;
    }
}