package com.ivm.CustomerDetect.service.DAO;

import java.util.List;

import com.ivm.CustomerDetect.model.AverageStayModel;
import com.ivm.CustomerDetect.service.ReadonlyObjectBaseDAO;

import org.springframework.transaction.annotation.Transactional;

public class ReadonlyAverageStay implements ReadonlyObjectBaseDAO<AverageStayModel> {

    @Override
    @Transactional
    public List<AverageStayModel> retrieveByCondition(List<String> whereClause) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public List<AverageStayModel> retrieveAll() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public AverageStayModel retrieveById(Integer id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
    
}