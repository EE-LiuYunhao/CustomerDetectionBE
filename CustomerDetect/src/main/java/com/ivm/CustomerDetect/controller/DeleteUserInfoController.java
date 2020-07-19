package com.ivm.CustomerDetect.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteUserInfoController
{
    @RequestMapping(value="/user/{uid}", method=RequestMethod.DELETE)
    public void deleteUserById(@PathVariable("uid") Integer uid)
    {
        
    }
}