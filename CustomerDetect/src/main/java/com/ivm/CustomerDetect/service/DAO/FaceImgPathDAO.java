package com.ivm.CustomerDetect.service.DAO;

import java.sql.SQLException;
import java.util.List;

import com.ivm.CustomerDetect.model.FaceImagePathModel;
import com.ivm.CustomerDetect.service.ReadonlyObjectBaseDAO;
import com.ivm.CustomerDetect.service.WritableObjectBaseDAO;

import org.springframework.transaction.annotation.Transactional;

public class FaceImgPathDAO
        implements WritableObjectBaseDAO<FaceImagePathModel>, ReadonlyObjectBaseDAO<FaceImagePathModel> {

    @Override
    @Transactional
    public List<FaceImagePathModel> retrieveByCondition(List<String> whereClause) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public List<FaceImagePathModel> retrieveAll() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public FaceImagePathModel retrieveById(Integer id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public boolean createModel(FaceImagePathModel user) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    @Transactional
    public boolean updateModel(FaceImagePathModel user) throws SQLException {
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