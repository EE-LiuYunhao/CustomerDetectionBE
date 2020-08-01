package com.ivm.CustomerDetect;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ivm.CustomerDetect.model.EncodedFaceModel;
import com.ivm.CustomerDetect.service.DAO.EncodedFaceDAO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EncodedFaceDaoTest
{
    @Autowired
    EncodedFaceDAO dao;

    @Test
    public void testRetrieve()
    {
        try
        {
            Assert.notNull(dao, "Fail to autowire the UserDAO instance");
            List<String> cond = new ArrayList<>();
            cond.add("uid < 4");
            List<EncodedFaceModel> faces = dao.retrieveByCondition(cond);
            Assert.notNull(faces, "Null retrieving results");
            Assert.isTrue(faces.size()==5, "Wrong retrieve result");

            faces = dao.retrieveAll();
            Assert.notNull(faces, "Null retrieving results");
            Assert.isTrue(faces.size()==6, "Wrong retrieve result");

            EncodedFaceModel face = dao.retrieveById(5);
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
            EncodedFaceModel face = new EncodedFaceModel();
            face.setUid("2");
            face.setTimeStamp("2020-7-30 12:50:00");
            face.setEncodedFacePath("FFFFFFFF.mat");
            dao.createModel(face);

            List<String> cond = new ArrayList<>();
            cond.add("encodedFacePath LIKE 'FFFFFFFF%'");
            List<EncodedFaceModel> afterInsersion = dao.retrieveByCondition(cond);
            Assert.notNull(afterInsersion, "Insert fails as null outputs");
            Assert.notEmpty(afterInsersion, "Insert fails as null outputs");
            Assert.isTrue(afterInsersion.get(0).getUid()==2, "Wrong result");
            
            dao.deleteModel(afterInsersion.get(0).getFaceId());
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
            EncodedFaceModel face = new EncodedFaceModel();
            face.setEncodedFacePath("00000000.mat");
            face.setTimeStamp("2020-02-02 19:00:00");
            face.setUid("1");
            face.setFaceId("1");
            dao.updateModel(face);

            List<String> cond = new ArrayList<>();
            cond.add("encodedFacePath LIKE '00000000.mat'");
            List<EncodedFaceModel> afterInsersion = dao.retrieveByCondition(cond);
            Assert.notNull(afterInsersion, "Insert fails as null outputs");
            Assert.notEmpty(afterInsersion, "Insert fails as null outputs");
            Assert.isTrue(afterInsersion.get(0).getEncodedFacePath().equals("00000000.mat"), "Wrong result");
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