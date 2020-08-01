package com.ivm.CustomerDetect.service.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ivm.CustomerDetect.model.EncodedFaceModel;
import com.ivm.CustomerDetect.model.FaceImagePathModel;
import com.ivm.CustomerDetect.service.BeanHandler;
import com.ivm.CustomerDetect.service.CURDTool;
import com.ivm.CustomerDetect.service.ReadonlyObjectBaseDAO;
import com.ivm.CustomerDetect.service.WritableObjectBaseDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FaceImgPathDAO
        implements WritableObjectBaseDAO<FaceImagePathModel>, ReadonlyObjectBaseDAO<FaceImagePathModel>
{
    @Autowired
    private CURDTool curdTool;
    @Value("${sql.table.faceimgpath}")
    private String tableName;
    @Autowired
    private EncodedFaceDAO faceDao;

    @Override
    @Transactional
    public List<FaceImagePathModel> retrieveByCondition(List<String> whereClause) throws Exception
    {
        Connection connection = curdTool.getConnection();
        StringBuilder clauseBuilder = new StringBuilder();
        clauseBuilder.append("SELECT * FROM ");
        clauseBuilder.append(tableName);
        if(whereClause!=null)
        {
            clauseBuilder.append(" WHERE ");
            for(int i=0; i<whereClause.size()-1; i++)
            {
                clauseBuilder.append(" "+whereClause.get(i)+" and");
            }
            clauseBuilder.append(" "+whereClause.get(whereClause.size()-1));
        }

        PreparedStatement statement = curdTool.getPreparedStatment(connection, clauseBuilder.toString());

        ArrayList<FaceImagePathModel> retVal = new ArrayList<>();
        for(Object each : curdTool.query(statement, new Object[]{}, new BeanHandler(FaceImagePathModel.class)))
        {
            retVal.add((FaceImagePathModel) each);
        }
        return retVal;
    }

    @Override
    @Transactional
    public List<FaceImagePathModel> retrieveAll() throws Exception
    {
        return this.retrieveByCondition(null);
    }

    @Override
    @Transactional
    public FaceImagePathModel retrieveById(Integer id) throws Exception
    {
        Connection connection = curdTool.getConnection();
        StringBuilder clauseBuilder = new StringBuilder();
        clauseBuilder.append("SELECT * FROM ");
        clauseBuilder.append(tableName);
        clauseBuilder.append(" WHERE imgId = ?");

        PreparedStatement statement = curdTool.getPreparedStatment(connection, clauseBuilder.toString());

        ArrayList<FaceImagePathModel> retVal = new ArrayList<>();
        for(Object each : curdTool.query(statement, new Object[]{id}, new BeanHandler(FaceImagePathModel.class)))
        {
            retVal.add((FaceImagePathModel) each);
        }
        return retVal.get(0);
    }

    @Override
    @Transactional
    public boolean createModel(FaceImagePathModel image) throws SQLException
    {

        //First check whether the UID and FACEID are correct
        try
        {
            EncodedFaceModel faceEntry = faceDao.retrieveById(image.getFaceId());
            if(faceEntry.getUid() != image.getUid())
            {
                return false;
            }
        }
        catch(SQLException e)
        {
            throw e;
        }
        catch(Exception e)
        {
            return false;
        }

        Connection connection = curdTool.getConnection();
        String sql = "INSERT INTO "+tableName+" (imgId, uid, faceId, ImgPath) VALUES ( ?, ?, ?, ? );";
        PreparedStatement statement = curdTool.getPreparedStatment(connection, sql);
        curdTool.update
        (
            statement,
            new Object[]
            {
                null,
                image.getUid(),
                image.getFaceId(),
                image.getImgPath()
            }
        );
        return true;
    }

    @Override
    @Transactional
    public boolean updateModel(FaceImagePathModel image) throws SQLException
    {
        //First check whether the UID and FACEID are correct
        try
        {
            EncodedFaceModel faceEntry = faceDao.retrieveById(image.getFaceId());
            if(faceEntry.getUid() != image.getUid())
            {
                return false;
            }
        }
        catch(SQLException e)
        {
            throw e;
        }
        catch(Exception e)
        {
            return false;
        }

        Connection connection = curdTool.getConnection();
        String sql = "UPDATE "+tableName+" SET uid = ?, faceId = ?, ImgPath = ? WHERE faceId=?;";
        PreparedStatement statement = curdTool.getPreparedStatment(connection, sql);
        curdTool.update
        (
            statement,
            new Object[]
            {
                image.getUid(),
                image.getFaceId(),
                image.getImgPath(),
                image.getImgId()
            }
        );
        return true;
    }

    @Override
    @Transactional
    public boolean deleteModel(Integer id) throws SQLException
    {
        Connection connection = curdTool.getConnection();
        String sql = "DELETE FROM "+tableName+" WHERE imgId=?;";
        PreparedStatement statement = curdTool.getPreparedStatment(connection, sql);
        curdTool.update
        (
            statement,
            new Object[]
            {
                (Object)id
            }
        );
        return true;
    }
    
}