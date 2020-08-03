package com.ivm.CustomerDetect.service.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.ivm.CustomerDetect.model.AverageStayModel;
import com.ivm.CustomerDetect.service.BeanHandler;
import com.ivm.CustomerDetect.service.CURDTool;
import com.ivm.CustomerDetect.service.ReadonlyObjectBaseDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReadonlyAverageStay implements ReadonlyObjectBaseDAO<AverageStayModel>
{
    @Autowired
    private CURDTool curdTool;
    @Value("${sql.table.averagestay}")
    private String tableName;

    @Override
    @Transactional
    public List<AverageStayModel> retrieveByCondition(List<String> whereClause) throws Exception
    {
        Connection connection = curdTool.getConnection();
        StringBuilder clauseBuilder = new StringBuilder();
        clauseBuilder.append("SELECT * FROM ");
        clauseBuilder.append(tableName);
        if(whereClause!=null)
        {
            clauseBuilder.append(" WHERE ");
            for(int i=0; i<whereClause.size()-1; i++)
            {
                clauseBuilder.append(" "+whereClause.get(i)+" and");
            }
            clauseBuilder.append(" "+whereClause.get(whereClause.size()-1));
        }

        PreparedStatement statement = curdTool.getPreparedStatment(connection, clauseBuilder.toString());

        ArrayList<AverageStayModel> retVal = new ArrayList<>();
        for(Object each : curdTool.query(statement, new Object[]{}, new BeanHandler(AverageStayModel.class)))
        {
            retVal.add((AverageStayModel) each);
        }
        return retVal;
    }

    @Override
    @Transactional
    public List<AverageStayModel> retrieveAll() throws Exception
    {
        return this.retrieveByCondition(null);
    }

    @Override
    @Transactional
    public AverageStayModel retrieveById(Integer id) throws Exception
    {
        Connection connection = curdTool.getConnection();
        StringBuilder clauseBuilder = new StringBuilder();
        clauseBuilder.append("SELECT * FROM ");
        clauseBuilder.append(tableName);
        clauseBuilder.append(" WHERE uid = ?");

        PreparedStatement statement = curdTool.getPreparedStatment(connection, clauseBuilder.toString());

        ArrayList<AverageStayModel> retVal = new ArrayList<>();
        for(Object each : curdTool.query(statement, new Object[]{id}, new BeanHandler(AverageStayModel.class)))
        {
            retVal.add((AverageStayModel) each);
        }
        return retVal.size()==0 ? null : retVal.get(0);
    }
    
}