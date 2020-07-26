package com.ivm.CustomerDetect.service;

/**
 * DAO module for the model where CUD is supported
 * @param <T> The type of the model
 */
public interface WritableObjectBaseDAO<T> extends ReadonlyObjectBaseDAO<T>
{
    public boolean createModel(T user);
    public boolean updateModel(T user);
    public boolean deleteModel(Integer id);
}