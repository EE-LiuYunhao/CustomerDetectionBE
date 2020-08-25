package com.ivm.CustomerDetect.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import java.util.Date;

import com.ivm.CustomerDetect.IllegalURLParameter;
import com.ivm.CustomerDetect.model.AverageStayModel;
import com.ivm.CustomerDetect.model.EncodedFaceModel;
import com.ivm.CustomerDetect.model.FaceImagePathModel;
import com.ivm.CustomerDetect.model.StayRecordModel;
import com.ivm.CustomerDetect.model.UserInfoModel;
import com.ivm.CustomerDetect.model.UserModel;
import com.ivm.CustomerDetect.service.DAO.EncodedFaceDAO;
import com.ivm.CustomerDetect.service.DAO.FaceImgPathDAO;
import com.ivm.CustomerDetect.service.DAO.ReadonlyAverageStay;
import com.ivm.CustomerDetect.service.DAO.StayRecordDAO;
import com.ivm.CustomerDetect.service.DAO.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GetUserInfoController
{
    @Autowired
    private UserDAO userDao;
    @Autowired
    private FaceImgPathDAO faceImgPathDao;
    @Autowired
    private EncodedFaceDAO encodedFaceDao;
    @Autowired
    private ReadonlyAverageStay averageStayDao;
    @Autowired
    private StayRecordDAO stayRecordDAO;

    @Value("${spring.jackson.time-zone}")
    private String location;


    private void setRelevantInfoForModel(UserInfoModel model) throws Exception
    {
        Integer uid = model.getUid();

        List<String> whereClause = new ArrayList<>();
        whereClause.add("uid = "+uid);
        List<String> pathList = new ArrayList<>();

        List<EncodedFaceModel> facePaths = encodedFaceDao.retrieveByCondition(whereClause);
        if(facePaths.size()!=0)
            facePaths.forEach( eachPath -> pathList.add(eachPath.getEncodedFacePath()) );
        model.setEncodedFacePath(pathList.toArray(new String[pathList.size()]));


        List<FaceImagePathModel> faceImagePaths = faceImgPathDao.retrieveByCondition(whereClause);
        if(faceImagePaths.size() != 0)
        {
            pathList.clear();
            faceImagePaths.forEach( eachPath -> pathList.add(eachPath.getImgPath()) );
        }
        model.setImgPath(pathList.toArray(new String[pathList.size()]));

        AverageStayModel stayEntry = averageStayDao.retrieveById(uid);
        if(stayEntry != null)
            model.setAvgStay(stayEntry.getAverageStay());

        List<StayRecordModel> selfRecords = stayRecordDAO.retrieveByCondition(whereClause);
        if(selfRecords != null)
        {
            for(StayRecordModel eachRecord : selfRecords)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                localFormater.setTimeZone(TimeZone.getTimeZone(location));
                
                Date utcDate = sdf.parse(eachRecord.getDatetimeIn());
                eachRecord.setDatetimeIn(localFormater.format(utcDate));
                
                if(eachRecord.getDatetimeOut()!=null)
                {
                    utcDate = sdf.parse(eachRecord.getDatetimeOut());
                    eachRecord.setDatetimeOut(localFormater.format(utcDate));
                }
            }
            model.setRecords( selfRecords.toArray(new StayRecordModel[selfRecords.size()]) );
        }
    }

    @RequestMapping(value="/visitor/uid/{uid}")
    public UserInfoModel retriveUserInfoById(@PathVariable("uid") Integer uid) throws Exception
    {
        UserInfoModel model = new UserInfoModel();
        UserModel user = userDao.retrieveById(uid);
        if(user != null)
        {
            model.setName(user.getName());
            model.setUid(user.getUid());
            model.setGender(user.getGender());
        }
        else
            throw new IllegalURLParameter("/visitor/uid/"+uid,"The UID cannot be found");

        setRelevantInfoForModel(model);
        return model;
    }
    
    @RequestMapping(value="/visitor/name/{name}")
    public UserInfoModel [] retriveUserInfoByName(@PathVariable("name") String uName) throws Exception
    {
        List<UserInfoModel> models = new ArrayList<>();
        while(uName.contains("'"))
        {
            int id = uName.indexOf('\'');
            uName = uName.substring(0, id) + uName.substring(id+1);
        }
        for(UserModel eachModel : userDao.retrieveByCondition( Arrays.asList(new String []{"name = '"+uName+"'"}) ))
        {
            UserInfoModel oneUser = new UserInfoModel();
            oneUser.setName(eachModel.getName());
            oneUser.setGender(eachModel.getGender());
            oneUser.setUid(eachModel.getUid());

            setRelevantInfoForModel(oneUser);
            models.add(oneUser);
        }
        return models.toArray(new UserInfoModel[models.size()]);
    }
    
    @RequestMapping(value="/visitor/lists")
    public UserInfoModel [] retrieveUserInfoAll() throws Exception
    {
        List<UserInfoModel> models = new ArrayList<>();
        for( UserModel eachModel : userDao.retrieveAll() )
        {
            UserInfoModel oneUser = new UserInfoModel();
            oneUser.setName(eachModel.getName());
            oneUser.setGender(eachModel.getGender());
            oneUser.setUid(eachModel.getUid());

            setRelevantInfoForModel(oneUser);
            models.add(oneUser);
        }
        return models.toArray(new UserInfoModel[models.size()]);
    }

    @RequestMapping(value="/visitor/count")
    public Integer countCurrentUsers() throws Exception
    {
        String [] whereClause = new String[]{"datetimeOut is NULL"};
        return stayRecordDAO.retrieveByCondition(Arrays.asList(whereClause)).size();
    }
}