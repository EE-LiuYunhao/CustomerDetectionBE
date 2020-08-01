package com.ivm.CustomerDetect;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ivm.CustomerDetect.model.AverageStayModel;
import com.ivm.CustomerDetect.service.DAO.ReadonlyAverageStay;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AverageStayTest
{
    @Autowired
    ReadonlyAverageStay dao;

    @Test
    public void testRetrieve()
    {
        try
        {
            Assert.notNull(dao, "Fail to autowire the UserDAO instance");
            List<String> cond = new ArrayList<>();
            cond.add("uid < 4");
            List<AverageStayModel> entries = dao.retrieveByCondition(cond);
            Assert.notNull(entries, "Null retrieving results");
            Assert.isTrue(entries.size()==3, "Wrong retrieve result");

            entries = dao.retrieveAll();
            Assert.notNull(entries, "Null retrieving results");
            Assert.isTrue(entries.size()==4, "Wrong retrieve result");

            AverageStayModel oneEntry = dao.retrieveById(1);
            Assert.notNull(oneEntry, "Null retrieving results");
            Assert.isTrue(Math.round(oneEntry.getAverageStay()) == 495, "Wrong retrieve result");
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
