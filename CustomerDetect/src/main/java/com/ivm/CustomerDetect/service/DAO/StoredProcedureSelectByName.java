package com.ivm.CustomerDetect.service.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.ivm.CustomerDetect.model.AggregatedUserInfoModel;
import com.ivm.CustomerDetect.service.BeanHandler;
import com.ivm.CustomerDetect.service.CURDTool;
import com.ivm.CustomerDetect.service.ReadonlyObjectBaseDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StoredProcedureSelectByName implements ReadonlyObjectBaseDAO<AggregatedUserInfoModel>
{
    @Autowired
    private CURDTool curdTool;
    @Value("${sql.procedure}")
    private String procedureName;

    @Override
    @Transactional
    public List<AggregatedUserInfoModel> retrieveByCondition(List<String> whereClause) throws Exception
    {
        if(whereClause.size()!=1)
        {
            throw new IllegalArgumentException("The procedure call accept only ONE argument but "+whereClause.size()+" are given");
        }
        Connection connection = curdTool.getConnection();
        PreparedStatement statement = connection.prepareCall("{call " + procedureName +"(?)}" );
        List<AggregatedUserInfoModel> retVal = new ArrayList<>();
        for(Object each : curdTool.query(statement, whereClause.toArray(), new BeanHandler(AggregatedUserInfoModel.class)))
        {
            retVal.add((AggregatedUserInfoModel) each);
        }
        return retVal;
    }

    @Override
    @Transactional
    @Deprecated
    public List<AggregatedUserInfoModel> retrieveAll() throws Exception { return null; }

    @Override
    @Transactional
    @Deprecated
    public AggregatedUserInfoModel retrieveById(Integer id) throws Exception { return null; }

}