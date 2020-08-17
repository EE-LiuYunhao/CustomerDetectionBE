package com.ivm.CustomerDetect.controller;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import com.ivm.CustomerDetect.IllegalURLParameter;
import com.ivm.CustomerDetect.model.EncodedFaceModel;
import com.ivm.CustomerDetect.model.FaceImagePathModel;
import com.ivm.CustomerDetect.model.StayRecordModel;
import com.ivm.CustomerDetect.model.UserModel;
import com.ivm.CustomerDetect.service.DAO.EncodedFaceDAO;
import com.ivm.CustomerDetect.service.DAO.FaceImgPathDAO;
import com.ivm.CustomerDetect.service.DAO.StayRecordDAO;
import com.ivm.CustomerDetect.service.DAO.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class PutModifiedUserInfoController
{
    @Autowired
    private UserDAO userDao;
    @Autowired
    private FaceImgPathDAO faceImgPathDao;
    @Autowired
    private EncodedFaceDAO encodedFaceDao;
    @Autowired
    private StayRecordDAO stayRecordDAO;

    @Value("${static.img}")
    private String imageFolder;
    
    @Value("${static.imgExtension}")
    private String imageExtension;

    @Value("${static.encodedFace}")
    private String faceFolder;
    
    @Value("${static.encodedFaceExtension}")
    private String faceExtension;

    @Value("${sql.timeLocale}")
    private String location;

    @RequestMapping(value = "/visitor/stay", method=RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public void insertOrModifyStayRecord
    (
        @RequestBody StayRecordModel newStay,
        @RequestParam(name="name", required=false) String newName
    ) throws Exception
    {
        if(newStay == null)
            throw new IllegalURLParameter("/visitor/stay", "Empty stay entry");
        //case one & two: leave time is null while the other two are not / or both not null
        if(newStay.getDatetimeIn()!=null && newStay.getUid()!=null)
        {
            //first check whether the user id is valid:
            if(newStay.getUid()<=0 || userDao.retrieveById(newStay.getUid()) == null)
            {
                int oldUid = newStay.getUid();
                // no such user id: create a new one
                // newName is mandantory in this case
                if(newName == null || newName.length()==0)
                    throw new IllegalURLParameter("/visitor/stay", "name is a must as there is no entry for"+newStay.getUid()); 
                UserModel newUser = new UserModel();
                newUser.setName(newName);
                userDao.createModel(newUser);


                List<UserModel> users = userDao.retrieveByCondition(Arrays.asList(new String []{"name = '"+newName+"'"}) );
                newStay.setUid(users.get(users.size()-1).getUid().toString());
                if(oldUid == 0)
                {
                    String name = users.get(users.size()-1).getName();
                    appendImageAndFaceRecordsOnly(newStay.getUid(), name, name);
                }
            }

            newStay.setRecordId("0");
            stayRecordDAO.createModel(newStay);
            return;
        }
        //case three: only leave time
        if(newStay.getDatetimeOut()!=null && newStay.getDatetimeIn()==null && newStay.getUid()==null)
        {
            List<StayRecordModel> collection = stayRecordDAO.retrieveByCondition(Arrays.asList(new String[]{"datetimeOut is NULL"}));
            if(collection == null ||collection.size()==0)
                return;
            StayRecordModel firstVisitor = collection.get(0);

            //NOTE: the MySQL by default converts input string into UTC
            //Hence, the firstVisitor.getDatetimeIn() is UTC
            //We need to convert it back to local time
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date utcDate = sdf.parse(firstVisitor.getDatetimeIn().toString());

            SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            localFormater.setTimeZone(TimeZone.getTimeZone(location));
            String localTime = localFormater.format(utcDate);

            firstVisitor.setDatetimeIn(localTime);
            firstVisitor.setDatetimeOut(newStay.getDatetimeOut().toString());
            stayRecordDAO.updateModel(firstVisitor);
            return;
        }
        //otherwise: excetion
        throw new IllegalURLParameter("/visitor/stay", "Invalid entry");
    }


    @RequestMapping(value="/visitor/modify/{uid}", method=RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public void modifyUserInfoById(@PathVariable Integer uid, @RequestBody UserModel newInfo) throws Exception
    {
        if(newInfo == null)
            throw new IllegalURLParameter("/visitor/modify/"+uid,"Null request body");
        newInfo.setUid(""+uid);
        if(userDao.retrieveById(uid) == null)
        throw new IllegalURLParameter("/visitor/modify/"+uid,"Cannot find the target visitor");
        userDao.updateModel(newInfo);
    }
    
    @RequestMapping(value="/visitor/image/{uid}", method=RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public void appendImageAndFaceRecordsOnly
    (
        @PathVariable             Integer uid,
        @RequestParam(name="imgPath", required=false)  String imgPath,
        @RequestParam(name="facePath", required=false) String facePath
    ) throws Exception
    {
        if(imgPath == null || imgPath.length()==0 || facePath == null || facePath.length()==0)
        {
            UserModel user = userDao.retrieveById(uid);
            imgPath = user.getName();
            facePath = user.getName();
        }

        facePath = facePath + "." + faceExtension;
        imgPath  = imgPath  + "." + imageExtension;
        
        EncodedFaceModel face = new EncodedFaceModel();
        face.setUid(""+uid);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        face.setTimeStamp(formatter.format(date));
        face.setEncodedFacePath(facePath);
        encodedFaceDao.createModel(face);
        
        List<String> whereClause = new ArrayList<>();
        whereClause.add("uid = "+uid);
        whereClause.add("encodedFacePath = '"+facePath+"'");
        whereClause.add("timeStamp = '"+formatter.format(date)+"'");
        List<EncodedFaceModel> tempFaceList = encodedFaceDao.retrieveByCondition(whereClause);
        if(tempFaceList == null || tempFaceList.size() == 0)
            throw new SQLException("Insertion fails with no entries in the DB");
        
        face = tempFaceList.get(tempFaceList.size()-1);
        
        FaceImagePathModel image = new FaceImagePathModel();
        image.setFaceId(""+face.getFaceId());
        image.setImgPath(imgPath);
        image.setUid(""+uid);
        faceImgPathDao.createModel(image);
    }
    
    @RequestMapping(value="/visitor/uploadImg/{uid}", method=RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public void acceptImageUploading
    (
        @PathVariable String uid,
        @RequestParam("image") MultipartFile multipartFile,
        @RequestParam("type") String type,
        @RequestParam(name="faceId", required=false) String faceId,
        HttpServletResponse response
    ) throws Exception
    {
        if(type == null || !type.equals("encodedFace") && !type.equals("image"))
        {
            throw new IllegalURLParameter
            (
                "/visitor/uploadImg/"+uid+"?type="+type+ faceId==null? "" : ("&faceId="+faceId), 
                "The required parameter `type` should either be `encodedFace` or `image`"
            );
        }
        else
        {
            String extensionName = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.'));
            if (type.equals("image") && faceId != null)
            {
                File file = new File( imageFolder+ '/' + (multipartFile.getOriginalFilename()+uid).hashCode() + extensionName );
                multipartFile.transferTo(file);

                FaceImagePathModel image = new FaceImagePathModel();
                image.setFaceId(faceId);
                image.setImgPath(file.toPath().toString());
                image.setUid(uid);
                faceImgPathDao.createModel(image);
            }
            else if (type.equals("encodedFace"))
            {
                File file = new File( faceFolder+ '/' + (multipartFile.getOriginalFilename()+uid).hashCode() + extensionName );
                multipartFile.transferTo(file);

                EncodedFaceModel face = new EncodedFaceModel();
                face.setUid(uid);
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                face.setTimeStamp(formatter.format(date));
                face.setEncodedFacePath(file.toPath().toString());
                encodedFaceDao.createModel(face);
            } // else if
            else
            {
                throw new IllegalURLParameter
                (
                    "/visitor/uploadImg/"+uid+"?type="+type+ faceId==null? "" : ("&faceId="+faceId), 
                    "`faceId` is required when the type is set to be `image`"
                );
            } // else
        } // else
    } // acceptImageUploading
}
