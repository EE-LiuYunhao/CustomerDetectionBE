package com.ivm.CustomerDetect.controller;

import java.sql.SQLException;

import com.ivm.CustomerDetect.service.DAO.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteUserInfoController
{
    @Autowired
    private UserDAO userDao;

    @RequestMapping(value="/visitor/{uid}", method=RequestMethod.DELETE)
    public void deleteUserById(@PathVariable("uid") Integer uid) throws SQLException
    {
        userDao.deleteModel(uid);
    }
}