package com.ivm.CustomerDetect.service;

import java.sql.SQLException;

/**
 * DAO module for the model where CUD is supported
 * @param <T> The type of the model
 */
public interface WritableObjectBaseDAO<T>
{
    /**
     * Insert one entry into the table
     * @param user: The item to be inserted
     * @return True if succeed
     * @throws SQLException
     */
    public boolean createModel(T user) throws SQLException;

    /**
     * Update one entry into the table
     * @param user: The item to be updated
     * @return True if succeed
     * @throws SQLException
     */
    public boolean updateModel(T user) throws SQLException;

    /**
     * Delete one entry into the table
     * @param user: The item to be deleted
     * @return True if succeed
     * @throws SQLException
     */
    public boolean deleteModel(Integer id) throws SQLException;
}