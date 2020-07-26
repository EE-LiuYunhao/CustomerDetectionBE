package com.ivm.CustomerDetect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ivm.CustomerDetect.service.CURDTool;

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
    
}