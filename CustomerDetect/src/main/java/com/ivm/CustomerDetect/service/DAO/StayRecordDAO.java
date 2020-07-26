package com.ivm.CustomerDetect.service.DAO;

import java.sql.SQLException;
import java.util.List;

import com.ivm.CustomerDetect.model.StayRecordModel;
import com.ivm.CustomerDetect.service.ReadonlyObjectBaseDAO;
import com.ivm.CustomerDetect.service.WritableObjectBaseDAO;

import org.springframework.transaction.annotation.Transactional;

public class StayRecordDAO implements ReadonlyObjectBaseDAO<StayRecordModel>, WritableObjectBaseDAO<StayRecordModel> {

    @Override
    @Transactional
    public List<StayRecordModel> retrieveByCondition(List<String> whereClause) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public List<StayRecordModel> retrieveAll() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public StayRecordModel retrieveById(Integer id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public boolean createModel(StayRecordModel user) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    @Transactional
    public boolean updateModel(StayRecordModel user) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    @Transactional
    public boolean deleteModel(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }
    
}