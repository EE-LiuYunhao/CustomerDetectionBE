package com.ivm.CustomerDetect.controller;

import com.ivm.CustomerDetect.model.UserInfoModel;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GetUserInfoController
{
    @RequestMapping(value="/user/uid/{uid}")
    public UserInfoModel retriveUserInfoById(@PathVariable("uid") Integer uid)
    {
        UserInfoModel model = new UserInfoModel();

        model.setUid(uid);
        model.setName( "David");
        model.setGender('M');
        model.setImgPath(new String []{"/image/0593f23ade.jpg"});
        model.setEncodedFacePath(new String [] {"/face/445ee22110a.mat"});
        model.setAvgStay(3422L);
        return model;
    }
    
    @RequestMapping(value="/user/name/{name}")
    public UserInfoModel [] retriveUserInfoByName(@PathVariable("name") String uName)
    {
        UserInfoModel model = new UserInfoModel();

        model.setUid(3);
        model.setName( uName );
        model.setGender('M');
        model.setImgPath(new String []{"/image/0593f23ade.jpg"});
        model.setEncodedFacePath(new String [] {"/face/445ee22110a.mat"});
        model.setAvgStay(3422L);
        return new UserInfoModel[]{model};
    }
    
    @RequestMapping(value="/user/lists")
    public UserInfoModel [] retrieveUserInfoAll()
    {
        UserInfoModel model = new UserInfoModel();

        model.setUid(3);
        model.setName( "David");
        model.setGender('M');
        model.setImgPath(new String []{"/image/0593f23ade.jpg"});
        model.setEncodedFacePath(new String [] {"/face/445ee22110a.mat"});
        model.setAvgStay(3422L);
        return new UserInfoModel[]{model};
    }

    @RequestMapping(value="/user/count")
    public Integer countCurrentUsers()
    {
        return 0;
    }
}