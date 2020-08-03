package com.ivm.CustomerDetect.service.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ivm.CustomerDetect.model.EncodedFaceModel;
import com.ivm.CustomerDetect.service.BeanHandler;
import com.ivm.CustomerDetect.service.CURDTool;
import com.ivm.CustomerDetect.service.ReadonlyObjectBaseDAO;
import com.ivm.CustomerDetect.service.WritableObjectBaseDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EncodedFaceDAO
        implements WritableObjectBaseDAO<EncodedFaceModel>, ReadonlyObjectBaseDAO<EncodedFaceModel>
{
    @Autowired
    private CURDTool curdTool;
    @Value("${sql.table.encodedface}")
    private String tableName;
    
    @Override
    @Transactional
    public List<EncodedFaceModel> retrieveByCondition(List<String> whereClause) throws Exception
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

        ArrayList<EncodedFaceModel> retVal = new ArrayList<>();
        for(Object each : curdTool.query(statement, new Object[]{}, new BeanHandler(EncodedFaceModel.class)))
        {
            retVal.add((EncodedFaceModel) each);
        }
        return retVal;
    }

    @Override
    @Transactional
    public List<EncodedFaceModel> retrieveAll() throws Exception
    {
        return this.retrieveByCondition(null);
    }

    @Override
    @Transactional
    public EncodedFaceModel retrieveById(Integer id) throws Exception
    {
        Connection connection = curdTool.getConnection();
        StringBuilder clauseBuilder = new StringBuilder();
        clauseBuilder.append("SELECT * FROM ");
        clauseBuilder.append(tableName);
        clauseBuilder.append(" WHERE faceId = ?");

        PreparedStatement statement = curdTool.getPreparedStatment(connection, clauseBuilder.toString());

        ArrayList<EncodedFaceModel> retVal = new ArrayList<>();
        for(Object each : curdTool.query(statement, new Object[]{id}, new BeanHandler(EncodedFaceModel.class)))
        {
            retVal.add((EncodedFaceModel) each);
        }
        return retVal.size()==0 ? null : retVal.get(0);
    }

    @Override
    @Transactional
    public boolean createModel(EncodedFaceModel face) throws SQLException
    {
        Connection connection = curdTool.getConnection();
        String sql = "INSERT INTO "+tableName+" (faceId, uid, timeStamp, encodedFacePath) VALUES ( ?, ?, ?, ? );";
        PreparedStatement statement = curdTool.getPreparedStatment(connection, sql);
        curdTool.update
        (
            statement,
            new Object[]
            {
                null,
                face.getUid(),
                face.getTimeStamp().toString(),
                face.getEncodedFacePath()
            }
        );
        return true;
    }

    @Override
    @Transactional
    public boolean updateModel(EncodedFaceModel face) throws SQLException
    {
        Connection connection = curdTool.getConnection();
        String sql = "UPDATE "+tableName+" SET uid = ?, timeStamp = ?, encodedFacePath = ? WHERE faceId=?;";
        PreparedStatement statement = curdTool.getPreparedStatment(connection, sql);
        curdTool.update
        (
            statement,
            new Object[]
            {
                face.getUid(),
                face.getTimeStamp(),
                face.getEncodedFacePath(),
                face.getFaceId(),
            }
        );
        return true;
    }

    @Override
    @Transactional
    public boolean deleteModel(Integer id) throws SQLException
    {
        Connection connection = curdTool.getConnection();
        String sql = "DELETE FROM "+tableName+" WHERE faceId=?;";
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