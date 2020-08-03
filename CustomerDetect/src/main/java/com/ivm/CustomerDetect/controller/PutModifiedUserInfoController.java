package com.ivm.CustomerDetect.controller;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.ivm.CustomerDetect.model.EncodedFaceModel;
import com.ivm.CustomerDetect.model.FaceImagePathModel;
import com.ivm.CustomerDetect.model.UserModel;
import com.ivm.CustomerDetect.service.DAO.EncodedFaceDAO;
import com.ivm.CustomerDetect.service.DAO.FaceImgPathDAO;
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

    @Value("${static.img}")
    private String imageFolder;

    @Value("${static.encodedFace}")
    private String faceFolder;

    @RequestMapping(value="/visitor/modify/{uid}", method=RequestMethod.PUT)
    public void modifyUserInfoById(@PathVariable Integer uid, @RequestBody UserModel newInfo) throws SQLException
    {
        newInfo.setUid(""+uid);
        userDao.createModel(newInfo);
    }
    
    @RequestMapping(value="/visitor/uploadImg/{uid}", method=RequestMethod.PUT)
    @Transactional
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
