package com.ivm.CustomerDetect.service.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ivm.CustomerDetect.model.StayRecordModel;
import com.ivm.CustomerDetect.service.BeanHandler;
import com.ivm.CustomerDetect.service.CURDTool;
import com.ivm.CustomerDetect.service.ReadonlyObjectBaseDAO;
import com.ivm.CustomerDetect.service.WritableObjectBaseDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StayRecordDAO implements ReadonlyObjectBaseDAO<StayRecordModel>, WritableObjectBaseDAO<StayRecordModel>
{
    @Autowired
    private CURDTool curdTool;
    @Value("${sql.table.stayrecord}")
    private String tableName;

    
    @Override
    @Transactional
    public List<StayRecordModel> retrieveByCondition(List<String> whereClause) throws Exception
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

        ArrayList<StayRecordModel> retVal = new ArrayList<>();
        for(Object each : curdTool.query(statement, new Object[]{}, new BeanHandler(StayRecordModel.class)))
        {
            retVal.add((StayRecordModel) each);
        }
        return retVal;
    }

    @Override
    @Transactional
    public List<StayRecordModel> retrieveAll() throws Exception
    {
        return this.retrieveByCondition(null);
    }

    @Override
    @Transactional
    public StayRecordModel retrieveById(Integer id) throws Exception
    {
        Connection connection = curdTool.getConnection();
        StringBuilder clauseBuilder = new StringBuilder();
        clauseBuilder.append("SELECT * FROM ");
        clauseBuilder.append(tableName);
        clauseBuilder.append(" WHERE recordId = ?");

        PreparedStatement statement = curdTool.getPreparedStatment(connection, clauseBuilder.toString());

        ArrayList<StayRecordModel> retVal = new ArrayList<>();
        for(Object each : curdTool.query(statement, new Object[]{id}, new BeanHandler(StayRecordModel.class)))
        {
            retVal.add((StayRecordModel) each);
        }
        return retVal.size()==0 ? null : retVal.get(0);
    }

    @Override
    @Transactional
    public boolean createModel(StayRecordModel record) throws SQLException
    {
        Connection connection = curdTool.getConnection();
        String sql = "INSERT INTO "+tableName+" (recordId, dateTimeIn, dateTimeOut, uid) VALUES ( ?, ?, ?, ? );";
        PreparedStatement statement = curdTool.getPreparedStatment(connection, sql);
        curdTool.update
        (
            statement,
            new Object[]
            {
                null,
                record.getDatetimeIn(),
                record.getDatetimeOut(),
                record.getUid()
            }
        );
        return true;
    }

    @Override
    @Transactional
    public boolean updateModel(StayRecordModel record) throws SQLException
    {

        Connection connection = curdTool.getConnection();
        String sql = "UPDATE "+tableName+" SET uid = ?, dateTimeIn = ?, dateTimeOut = ? WHERE recordId=?;";
        PreparedStatement statement = curdTool.getPreparedStatment(connection, sql);
        curdTool.update
        (
            statement,
            new Object[]
            {
                record.getUid(),
                record.getDatetimeIn(),
                record.getDatetimeOut(),
                record.getRecordId()
            }
        );
        return true;
    }

    @Override
    @Transactional
    public boolean deleteModel(Integer id) throws SQLException
    {
        Connection connection = curdTool.getConnection();
        String sql = "DELETE FROM "+tableName+" WHERE recordId=?;";
        PreparedStatement statement = curdTool.getPreparedStatment(connection, sql);
        curdTool.update
        (
            statement,
            new Object[]
            {
                (Object)id
            }
        );
        return true;
    }
    
}