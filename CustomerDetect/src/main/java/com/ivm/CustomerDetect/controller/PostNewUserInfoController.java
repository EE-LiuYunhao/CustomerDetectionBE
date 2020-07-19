package com.ivm.CustomerDetect.controller;

import com.ivm.CustomerDetect.model.UserModel;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PostNewUserInfoController
{
    @RequestMapping(value="/user/insert", method=RequestMethod.POST)
    public void insertOneById(@RequestBody UserModel inputUser)
    {
        inputUser.setUid(inputUser.hashCode());
    }

    @RequestMapping(value="/user/multiInsert", method=RequestMethod.POST)
    public void insertManyById(@RequestBody UserModel [] inputUsers)
    {
        for(UserModel user : inputUsers)
        {
            user.setUid(user.hashCode());
        }
    }
}