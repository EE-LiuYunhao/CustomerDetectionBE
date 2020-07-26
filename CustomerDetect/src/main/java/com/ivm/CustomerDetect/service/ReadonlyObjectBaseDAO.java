package com.ivm.CustomerDetect.service;

import java.util.List;

public interface ReadonlyObjectBaseDAO<T>
{
    /**
     * Retrieve using where clause
     * @param whereClause Condictional statements, such as "id=36578"
     * @return The query results
     * @throws Exception
     */
    public List<T> retrieveByCondition(List<String> whereClause) throws Exception;

    /**
     * Retrieve all items in the table
     * @return The quesry results
     * @throws Exception
     */
    public List<T> retrieveAll() throws Exception;

    /**
     * Retrieve one entry by its id
     * @param id The id (primary key)
     * @return The first item in the query result, or NULL
     * @throws Exception
     */
    public T retrieveById(Integer id) throws Exception;
}