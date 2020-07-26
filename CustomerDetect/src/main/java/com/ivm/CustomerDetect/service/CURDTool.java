package com.ivm.CustomerDetect.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CURDTool
{
    
    private String driver;
    private String url;
    private String userName;
    private String passWord;

    public CURDTool
    (
        @Value("${sql.driver}") String driver, 
        @Value("${sql.url}") String url, 
        @Value("${sql.username}") String userName,
        @Value("${sql.password}") String passWord
    )
    {
        this.driver   = driver;
        this.url      = url;
        this.userName = userName;
        this.passWord = passWord;
        try
        {
            Class.forName(this.driver);
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }


     /**
      * Get the connection to the DB
      */
    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url, userName, passWord);
    }

    /**
     * Create a prepared statement based on the given connection
     * @param connection
     * @param sql The SQL statement from which the prepared statement is generated
     * @return the prepared statement
     * @throws SQLException
     */
    public PreparedStatement getPreparedStatment(Connection connection, String sql) throws SQLException
    {
        return connection.prepareStatement(sql);
    }

    /**
     * Release the connection after execution the statement
     * @param connection The connection to be released
     * @param statement The relevant SQL statement after whose execution the resource should be deprecated
     * @param resultSet The result set of the `statement` variable
     */
    public void release(Connection connection, Statement statement, ResultSet resultSet)
    {
        if(resultSet != null)
        {
            try{resultSet.close();}
            catch(SQLException e){e.printStackTrace();}
        }

        if(statement != null)
        {
            try{statement.close();}
            catch(SQLException e){e.printStackTrace();}
        }

        if(connection != null)
        {
            try{connection.close();}
            catch(SQLException e){e.printStackTrace();}
        }

    }


    /**
     * Executed a prepared statement for C, U, and D
     * @param statement The prepared statment to be executed 
     * @param param Set of param for the prepared statement
     * @return The returned INT from preparedStatement.executeUpdate()
     * @throws SQLException
     */
    public int update(PreparedStatement statement, Object[] param) throws SQLException
    {
        for(int i=0; i<param.length; i++)
        {
            statement.setObject(i+1, param[i]);
        }
        return statement.executeUpdate();
    }

    /**
     * Executed a prepared statement for R
     * @param statement The prepared statment to be executed 
     * @param param Set of param for the prepared statement
     * @param rsh The resultSetHandler to load result into JavaBean
     * @return The returned JavaBean List
     * @throws Exception
     */
    public List<Object> query(PreparedStatement statement, Object[] param, ResultSetHandler rsh) throws Exception
    {
        for(int i=0; i<param.length; i++)
        {
            statement.setObject(i+1, param[i]);
        }
        ResultSet resultSet =  statement.executeQuery();
        return rsh.handler(resultSet);
    }
}