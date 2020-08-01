package com.ivm.CustomerDetect;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ivm.CustomerDetect.model.FaceImagePathModel;
import com.ivm.CustomerDetect.service.DAO.FaceImgPathDAO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FaceImgPathDaoTest {
    @Autowired
    FaceImgPathDAO dao;

    @Test
    public void testRetrieve() {
        try {
            Assert.notNull(dao, "Fail to autowire the UserDAO instance");
            List<String> cond = new ArrayList<>();
            cond.add("uid < 4");
            List<FaceImagePathModel> faces = dao.retrieveByCondition(cond);
            Assert.notNull(faces, "Null retrieving results");
            Assert.isTrue(faces.size()==5, "Wrong retrieve result");

            faces = dao.retrieveAll();
            Assert.notNull(faces, "Null retrieving results");
            Assert.isTrue(faces.size()==6, "Wrong retrieve result");

            FaceImagePathModel face = dao.retrieveById(5);
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
            FaceImagePathModel image = new FaceImagePathModel();
            image.setFaceId("6");
            image.setUid("3");
            image.setImgPath("FFFFFFFF.jpg");
            dao.createModel(image);

            List<String> cond = new ArrayList<>();
            cond.add("ImgPath LIKE 'FFFFFFFF%'");
            List<FaceImagePathModel> afterInsersion = dao.retrieveByCondition(cond);
            Assert.notNull(afterInsersion, "Insert fails as null outputs");
            Assert.notEmpty(afterInsersion, "Insert fails as null outputs");
            Assert.isTrue(afterInsersion.get(0).getUid()==3, "Wrong result");
            
            dao.deleteModel(afterInsersion.get(0).getImgId());
            afterInsersion = dao.retrieveByCondition(cond);
            Assert.isTrue(afterInsersion == null || afterInsersion.size()==0, "Fail to delete");

            // wrong relationship
            image.setFaceId("2");
            image.setUid("3");
            image.setImgPath("FFFFFFFF.jpg");
            Assert.isTrue(!dao.createModel(image), "Fail to detect the wrong relationship between foreign keys");

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
            FaceImagePathModel image = new FaceImagePathModel();
            image.setImgPath("00000000.jpg");
            image.setUid("1");
            image.setFaceId("1");
            image.setImgId("1");
            dao.updateModel(image);

            List<String> cond = new ArrayList<>();
            cond.add("ImgPath LIKE '00000000%'");
            List<FaceImagePathModel> afterInsersion = dao.retrieveByCondition(cond);
            Assert.notNull(afterInsersion, "Insert fails as null outputs");
            Assert.notEmpty(afterInsersion, "Insert fails as null outputs");
            Assert.isTrue(afterInsersion.get(0).getImgPath().equals("00000000.jpg"), "Wrong result");
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