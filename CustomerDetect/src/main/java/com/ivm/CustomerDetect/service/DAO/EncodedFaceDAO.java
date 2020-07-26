package com.ivm.CustomerDetect.service.DAO;

import java.sql.SQLException;
import java.util.List;

import com.ivm.CustomerDetect.model.EncodedFaceModel;
import com.ivm.CustomerDetect.service.ReadonlyObjectBaseDAO;
import com.ivm.CustomerDetect.service.WritableObjectBaseDAO;

import org.springframework.transaction.annotation.Transactional;

public class EncodedFaceDAO
        implements WritableObjectBaseDAO<EncodedFaceModel>, ReadonlyObjectBaseDAO<EncodedFaceModel>
{

    @Override
    @Transactional
    public List<EncodedFaceModel> retrieveByCondition(List<String> whereClause) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public List<EncodedFaceModel> retrieveAll() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public EncodedFaceModel retrieveById(Integer id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public boolean createModel(EncodedFaceModel user) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    @Transactional
    public boolean updateModel(EncodedFaceModel user) throws SQLException {
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