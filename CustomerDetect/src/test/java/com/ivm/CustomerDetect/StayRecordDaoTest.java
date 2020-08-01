package com.ivm.CustomerDetect;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ivm.CustomerDetect.model.StayRecordModel;
import com.ivm.CustomerDetect.service.DAO.StayRecordDAO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StayRecordDaoTest
{
    @Autowired
    StayRecordDAO dao;

    @Test
    public void testRetrieve()
    {
        try
        {
            Assert.notNull(dao, "Fail to autowire the UserDAO instance");
            List<String> cond = new ArrayList<>();
            cond.add("uid < 4");
            List<StayRecordModel> faces = dao.retrieveByCondition(cond);
            Assert.notNull(faces, "Null retrieving results");
            Assert.isTrue(faces.size()==8, "Wrong retrieve result");

            faces = dao.retrieveAll();
            Assert.notNull(faces, "Null retrieving results");
            Assert.isTrue(faces.size()==10, "Wrong retrieve result");

            StayRecordModel face = dao.retrieveById(5);
            Assert.notNull(face, "Null retrieving results");
            Assert.isTrue(face.getUid() == 1, "Wrong retrieve result");
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
            StayRecordModel face = new StayRecordModel();
            face.setUid("2");
            face.setDatetimeIn("2020-7-30 12:50:00");
            face.setDatetimeOut("2020-7-30 12:59:00");
            dao.createModel(face);

            List<String> cond = new ArrayList<>();
            cond.add("datetimeIn = '2020-7-30 04:50:00'");
            List<StayRecordModel> afterInsersion = dao.retrieveByCondition(cond);
            Assert.notNull(afterInsersion, "Insert fails as null outputs");
            Assert.notEmpty(afterInsersion, "Insert fails as null outputs");
            Assert.isTrue(afterInsersion.get(0).getUid()==2, "Wrong result");
            
            dao.deleteModel(afterInsersion.get(0).getRecordId());
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
            StayRecordModel record = new StayRecordModel();
            record.setRecordId("10");
            record.setDatetimeIn("2020-07-31 10:00:00");
            record.setDatetimeOut("2020-07-31 19:00:00");
            record.setUid("1");
            dao.updateModel(record);

            StayRecordModel afterUpdate = dao.retrieveById(10);
            Assert.notNull(afterUpdate, "Insert fails as null outputs");
            Assert.isTrue(afterUpdate.getUid()==1, "Wrong result");
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