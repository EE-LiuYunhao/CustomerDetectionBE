package com.ivm.CustomerDetect.service.DAO;

import java.util.List;

import com.ivm.CustomerDetect.model.AggregatedUserInfoModel;
import com.ivm.CustomerDetect.service.ReadonlyObjectBaseDAO;

import org.springframework.transaction.annotation.Transactional;

public class StoredProcedureSelectByName implements ReadonlyObjectBaseDAO<AggregatedUserInfoModel> {

    @Override
    @Transactional
    public List<AggregatedUserInfoModel> retrieveByCondition(List<String> whereClause) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public List<AggregatedUserInfoModel> retrieveAll() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public AggregatedUserInfoModel retrieveById(Integer id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}