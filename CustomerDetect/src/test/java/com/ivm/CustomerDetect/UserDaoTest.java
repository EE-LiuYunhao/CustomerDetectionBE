package com.ivm.CustomerDetect;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ivm.CustomerDetect.model.UserModel;
import com.ivm.CustomerDetect.service.DAO.UserDAO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserDaoTest
{
    @Autowired
    UserDAO dao;

    @Test
    public void testRetrieve()
    {
        try
        {
            Assert.notNull(dao, "Fail to autowire the UserDAO instance");
            List<String> cond = new ArrayList<>();
            cond.add("uid < 4");
            List<UserModel> users = dao.retrieveByCondition(cond);
            Assert.notNull(users, "Null retrieving results");
            Assert.isTrue(users.size()==3, "Wrong retrieve result");

            users = dao.retrieveAll();
            Assert.notNull(users, "Null retrieving results");
            Assert.isTrue(users.size()==5, "Wrong retrieve result");

            UserModel user = dao.retrieveById(1);
            Assert.notNull(user, "Null retrieving results");
            Assert.isTrue(user.getName().equals("David"), "Wrong retrieve result");
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

    @Test
    public void testInsertAndDelete()
    {
        try
        {
            Assert.notNull(dao, "Fail to autowire the UserDAO instance");
            UserModel user = new UserModel();
            user.setGender("F");
            user.setName("David2");
            dao.createModel(user);

            List<String> cond = new ArrayList<>();
            cond.add("name = 'David2'");
            List<UserModel> afterInsersion = dao.retrieveByCondition(cond);
            Assert.notNull(afterInsersion, "Insert fails as null outputs");
            Assert.notEmpty(afterInsersion, "Insert fails as null outputs");
            Assert.isTrue(afterInsersion.get(0).getGender()=='F', "Wrong result");
            
            dao.deleteModel(afterInsersion.get(0).getUid());
            afterInsersion = dao.retrieveByCondition(cond);
            Assert.isTrue(afterInsersion == null || afterInsersion.size()==0, "Fail to delete");

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
    
    @Test
    public void testUpdate()
    {
        try
        {
            Assert.notNull(dao, "Fail to autowire the UserDAO instance");
            UserModel user = new UserModel();
            user.setGender("F");
            user.setName("DavidTested");
            user.setUid("5");
            dao.updateModel(user);

            List<String> cond = new ArrayList<>();
            cond.add("name = 'DavidTested'");
            List<UserModel> afterInsersion = dao.retrieveByCondition(cond);
            Assert.notNull(afterInsersion, "Insert fails as null outputs");
            Assert.notEmpty(afterInsersion, "Insert fails as null outputs");
            Assert.isTrue(afterInsersion.get(0).getGender()=='F', "Wrong result");
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