package com.ivm.CustomerDetect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.ivm.CustomerDetect.model.UserModel;
import com.ivm.CustomerDetect.service.BeanHandler;
import com.ivm.CustomerDetect.service.CURDTool;

import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JDBCTest
{
    @Autowired
    CURDTool curdTool;

    @Test
    public void testJDBCConnection()
    {
        try
        {
            Connection connection = curdTool.getConnection();
            Assert.notNull(connection, "Fail to get JDBC Connection");
        }
        catch(SQLException e)
        {
            Assert.isTrue(false, "Exception in getting JDBC Connection");
        }
        
    }
    
    @Test
    public void testJDBCPreparedStatement()
    {
        try
        {
            Connection connection = curdTool.getConnection();
            Assert.notNull(connection, "Fail to get JDBC Connection");
            PreparedStatement statement = curdTool.getPreparedStatment(connection, "show tables;");
            Assert.notNull(statement, "Fail to generate JDBC statement");
        }
        catch(SQLException e)
        {
            Assert.isTrue(false, "Exception in getting JDBC Connection");
        }
    }

    @Test
    public void testUpdateData()
    {
        try
        {
            Connection connection = curdTool.getConnection();
            Assert.notNull(connection, "Fail to get JDBC Connection");
            PreparedStatement statement = curdTool.getPreparedStatment(connection, "INSERT INTO UserInfo (gender, name) VALUES (?, ?)");
            Assert.notNull(statement, "Fail to generate JDBC statement");
            curdTool.update(statement, new Object[]{"M", "DavidTested"});
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            Assert.isTrue(false, "Exception in inserting UserEntry into table `UserInfo`");
        }
    }
    
    @Test
    public void testQuery()
    {
        try
        {
            Connection connection = curdTool.getConnection();
            Assert.notNull(connection, "Fail to get JDBC Connection");
            PreparedStatement statement = curdTool.getPreparedStatment(connection, "SELECT * FROM UserInfo");
            Assert.notNull(statement, "Fail to generate JDBC statement");
            List<Object> result = curdTool.query(statement, new Object[]{}, new BeanHandler(UserModel.class));
            for(Object each : result)
            {
                Assert.notNull(each, "Null output from the parser");
                Assert.isTrue(each instanceof UserModel, "Wrong type for the results from user");
                UserModel eachUser = (UserModel) each;
                System.out.println("{ uid:"+eachUser.getUid()+", gender:"+eachUser.getGender()+", name:"+eachUser.getName()+" };");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            Assert.isTrue(false, "Exception in querying for the entries from table `UserInfo`");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assert.isTrue(false, "Exception in parsing the data from SQL results");
        }
    }
}