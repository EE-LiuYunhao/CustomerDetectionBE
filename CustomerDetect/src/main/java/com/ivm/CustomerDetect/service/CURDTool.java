package com.ivm.CustomerDetect.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

public class CURDTool
{
    @Value("sql.driver")
    private static String driver;

    @Value("sql.url")
    private static String url;

    @Value("sql.username")
    private static String userName;
    
    @Value("sql.password")
    private static String passWord;

    static
    {
        try
        {
            Class.forName(driver);
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }


     /**
      * Get the connection to the DB
      */
    public static Connection getConnection() throws SQLException
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
    public static PreparedStatement getPreparedStatment(Connection connection, String sql) throws SQLException
    {
        return connection.prepareStatement(sql);
    }

    /**
     * Release the connection after execution the statement
     * @param connection The connection to be released
     * @param statement The relevant SQL statement after whose execution the resource should be deprecated
     * @param resultSet The result set of the `statement` variable
     */
    public static void release(Connection connection, Statement statement, ResultSet resultSet)
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
    public static int update(PreparedStatement statement, Object[] param) throws SQLException
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
    public static List<Object> query(PreparedStatement statement, Object[] param, ResultSetHandler rsh) throws Exception
    {
        for(int i=0; i<param.length; i++)
        {
            statement.setObject(i+1, param[i]);
        }
        ResultSet resultSet =  statement.executeQuery();
        return rsh.handler(resultSet);
    }
}