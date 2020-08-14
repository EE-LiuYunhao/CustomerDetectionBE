package com.ivm.CustomerDetect.controller;

import com.ivm.CustomerDetect.IllegalURLParameter;
import com.ivm.CustomerDetect.model.UserModel;
import com.ivm.CustomerDetect.service.DAO.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;


@RestController
public class PostNewUserInfoController
{
    @Autowired
    private UserDAO userDao;

    @RequestMapping(value="/visitor/insert", method=RequestMethod.POST)
    public void insertOneById(@RequestBody UserModel inputUser) throws Exception
    {
        if(inputUser == null || inputUser.getName() == null)
            throw new IllegalURLParameter("/visitor/insert","Null request body or null name");
        
        while(inputUser.getName().contains("'"))
        {
            int id = inputUser.getName().indexOf('\'');
            inputUser.setName(inputUser.getName().substring(0, id) + inputUser.getName().substring(id+1));
        }

        if(inputUser.getGender() == null)
            inputUser.setGender("U"); //U stands for Unknown
        if(inputUser.getUid()!=null && userDao.retrieveById(inputUser.getUid())!=null)
        {
            userDao.updateModel(inputUser);
        }
        else
        {
            inputUser.setUid("0");
            userDao.createModel(inputUser);
        }
    }

    @RequestMapping(value="/visitor/multiInsert", method=RequestMethod.POST)
    @Transactional
    public void insertManyById(@RequestBody UserModel [] inputUsers) throws Exception
    {
        if(inputUsers == null || inputUsers.length == 0)
            throw new IllegalURLParameter("/visitor/multiInsert","Null request body or empty list");
        for(UserModel user : inputUsers)
        {
            insertOneById(user);
        }
    }
}