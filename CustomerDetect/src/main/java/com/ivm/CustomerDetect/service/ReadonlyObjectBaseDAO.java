package com.ivm.CustomerDetect.service;

import java.util.List;

public interface ReadonlyObjectBaseDAO<T>
{
    public List<T> retrieveByCondition(List<String> whereClause);
    public List<T> retrieveAll();
    public T retrieveById(Integer id);
}