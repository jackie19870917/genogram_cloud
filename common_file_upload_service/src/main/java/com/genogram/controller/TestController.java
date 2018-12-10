package com.genogram.controller;

import com.genogram.entity.AllUserLogin;
import com.genogram.service.ITestService;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/test")
@Controller
public class TestController {

    @Autowired
    ITestService iTestService;

    //联谊会家族字派查询
    @ResponseBody
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public AllUserLogin getAllUserLogin() {
        AllUserLogin allUserLogin = iTestService.getAllUserLogin();
        return allUserLogin;
    }
}
