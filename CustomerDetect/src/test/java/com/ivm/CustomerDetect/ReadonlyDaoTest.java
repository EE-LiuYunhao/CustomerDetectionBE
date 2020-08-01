package com.ivm.CustomerDetect;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ivm.CustomerDetect.model.AggregatedUserInfoModel;
import com.ivm.CustomerDetect.model.AverageStayModel;
import com.ivm.CustomerDetect.service.DAO.ReadonlyAverageStay;
import com.ivm.CustomerDetect.service.DAO.StoredProcedureSelectByName;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReadonlyDaoTest
{
    @Autowired
    ReadonlyAverageStay averageStayDao;

    @Autowired
    StoredProcedureSelectByName procedureDao;

    @Test
    public void averageStayTest()
    {
        try
        {
            Assert.notNull(averageStayDao, "Fail to autowire the UserDAO instance");
            List<String> cond = new ArrayList<>();
            cond.add("uid < 4");
            List<AverageStayModel> entries = averageStayDao.retrieveByCondition(cond);
            Assert.notNull(entries, "Null retrieving results");
            Assert.isTrue(entries.size()==3, "Wrong retrieve result");

            entries = averageStayDao.retrieveAll();
            Assert.notNull(entries, "Null retrieving results");
            Assert.isTrue(entries.size()==4, "Wrong retrieve result");

            AverageStayModel oneEntry = averageStayDao.retrieveById(1);
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

    @Test
    public void selectByNameTest()
    {
        try
        {
            Assert.notNull(procedureDao, "Fail to autowire the UserDAO instance");
            List<String> cond = new ArrayList<>();
            cond.add("David");
            List<AggregatedUserInfoModel> entries = procedureDao.retrieveByCondition(cond);
            Assert.notNull(entries, "Null retrieving results");
            Assert.isTrue(entries.size()==2, "Wrong retrieve result");
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
