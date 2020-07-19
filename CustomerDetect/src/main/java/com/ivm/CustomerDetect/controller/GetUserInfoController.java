package com.ivm.CustomerDetect.controller;

import java.util.ArrayList;

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
        ArrayList<String> imgPath = new ArrayList<>();
        imgPath.add("/image/0593f23ade.jpg");
        ArrayList<String> facePath = new ArrayList<>();
        facePath.add("/face/445ee22110a.mat");

        model.testSetter(1, "David", 'M', (String [])imgPath.toArray(), (String [])facePath.toArray(), 3422L);
        return model;
    }
    
    @RequestMapping(value="/user/name/{name}")
    public UserInfoModel [] retriveUserInfoByName(@PathVariable("name") String uName)
    {
        UserInfoModel model = new UserInfoModel();
        model.testSetter(1, "David", 'M', new String[]{"/image/0593f23ade.jpg"}, new String[]{"/face/445ee22110a.mat"}, 3422L);
        return new UserInfoModel[]{model};
    }
    
    @RequestMapping(value="/user/lists")
    public UserInfoModel [] retrieveUserInfoAll()
    {
        UserInfoModel model = new UserInfoModel();
        model.testSetter(1, "David", 'M', new String[]{"/image/0593f23ade.jpg"}, new String[]{"/face/445ee22110a.mat"}, 3422L);
        return new UserInfoModel[]{model};
    }
}