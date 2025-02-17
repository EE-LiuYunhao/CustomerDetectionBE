package com.ivm.CustomerDetect.service.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ivm.CustomerDetect.model.UserModel;
import com.ivm.CustomerDetect.service.BeanHandler;
import com.ivm.CustomerDetect.service.CURDTool;
import com.ivm.CustomerDetect.service.ReadonlyObjectBaseDAO;
import com.ivm.CustomerDetect.service.WritableObjectBaseDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class UserDAO implements ReadonlyObjectBaseDAO<UserModel>, WritableObjectBaseDAO<UserModel>
{
    @Autowired
    private CURDTool curdTool;

    @Value("${sql.table.userinfo}")
    private String tableName;
    

    @Override
    @Transactional
    public List<UserModel> retrieveByCondition(List<String> whereClause)  throws Exception
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
        
        ArrayList<UserModel> retVal = new ArrayList<>();
        for(Object each : curdTool.query(statement, new Object[]{}, new BeanHandler(UserModel.class)))
        {
            retVal.add((UserModel) each);
        }
        return retVal;
    }

    @Override
    @Transactional
    public List<UserModel> retrieveAll() throws Exception
    {
        return this.retrieveByCondition(null);
    }
    
    @Override
    @Transactional
    public UserModel retrieveById(Integer id) throws Exception
    {
        Connection connection = curdTool.getConnection();
        StringBuilder clauseBuilder = new StringBuilder();
        clauseBuilder.append("SELECT * FROM ");
        clauseBuilder.append(tableName);
        clauseBuilder.append(" WHERE uid = ?");

        PreparedStatement statement = curdTool.getPreparedStatment(connection, clauseBuilder.toString());
        
        ArrayList<UserModel> retVal = new ArrayList<>();
        for(Object each : curdTool.query(statement, new Object[]{id}, new BeanHandler(UserModel.class)))
        {
            retVal.add((UserModel) each);
        }
        return retVal.size()==0 ? null : retVal.get(0);
    }

    
    @Override
    @Transactional
    public boolean createModel(UserModel user) throws SQLException
    {
        Connection connection = curdTool.getConnection();
        String sql = "INSERT INTO "+tableName+" (uid, gender, name) VALUES ( ?, ?, ? );";
        PreparedStatement statement = curdTool.getPreparedStatment(connection, sql);
        curdTool.update
        (
            statement,
            new Object[]
            {
                null,
                (Object)(user.getGender()==null?null:user.getGender().toString()),
                (Object)(user.getName()==null?null:user.getName().toString())
            }
        );
        return true;
    }

    @Override
    @Transactional
    public boolean updateModel(UserModel user) throws SQLException
    {
        Connection connection = curdTool.getConnection();
        String sql = "UPDATE "+tableName+" SET gender=?, name=? WHERE uid=?;";
        PreparedStatement statement = curdTool.getPreparedStatment(connection, sql);
        curdTool.update
        (
            statement,
            new Object[]
            {
                (Object)user.getGender().toString(),
                (Object)user.getName(),
                (Object)user.getUid().toString()
            }
        );
        return true;
    }

    @Override
    @Transactional
    public boolean deleteModel(Integer id) throws SQLException
    {
        Connection connection = curdTool.getConnection();
        String sql = "DELETE FROM "+tableName+" WHERE uid=?;";
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