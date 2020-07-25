package com.ivm.CustomerDetect.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.ivm.CustomerDetect.model.UserModel;

import org.apache.tomcat.util.http.fileupload.IOUtils;
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
    @RequestMapping(value="/user/modify/{uid}", method=RequestMethod.PUT)
    public void modifyUserInfoById(@PathVariable Integer uid, @RequestBody UserModel newInfo, HttpServletResponse response)
    {
        if(newInfo.getUid() != uid)
        {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            PrintWriter out = null;
            try
            {
                out = response.getWriter();
                out.print("<script>alert('The user id does not match');window.location.href='/user/modify/"
                + uid
                +"';</script>");
                out.flush();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                IOUtils.closeQuietly(out);
            }
        }
    }
    
    @RequestMapping(value="/user/uploadImg/{uid}", method=RequestMethod.PUT)
    public void acceptImageUploading
    (
        @PathVariable Integer uid,
        @RequestParam("image") MultipartFile multipartFile,
        @RequestParam("type") String type,
        HttpServletResponse response
    )
    {
        if(type != "encodedFace" && type != "image")
        {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            PrintWriter out = null;
            try
            {
                out = response.getWriter();
                out.print("<script>alert('The type:"
                +type
                +" is not recognazied');window.location.href='/user/uploadImg/"
                + uid
                +"';</script>");
                out.flush();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                IOUtils.closeQuietly(out);
            }
        }        
        // try
        // {
        //     File file = new File( type+ '/' + multipartFile.getOriginalFilename());
        //     multipartFile.transferTo(file);
        // } catch (IOException e)
        // {
        //     e.printStackTrace();
        // }
    }
}