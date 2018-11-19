package com.genogram.controller;

import com.genogram.entity.AllUserLogin;
import com.genogram.service.IAllUserLoginService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *用户登录
 *@Author: Toxicant
 *@Date: 2018-11-09
 *@Time: 14:23
 *@Param:
 *@return:
 *@Description:
 */
@Api(description = "登录注册")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/userLogin")
public class FanUserLoginController {

    @Autowired
    private IAllUserLoginService allUserLoginService;

    /**
     * 登陆
     * @param allUserLogin
     * @return
     */
    @ApiOperation("登陆")
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Response<AllUserLogin> getAllUserLogin(AllUserLogin allUserLogin) {

        AllUserLogin userLogin = allUserLoginService.getAllUserLogin(allUserLogin);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(400, "用户名不存在");
        } else {
            if (userLogin.getPassword().equals(allUserLogin.getPassword())) {
                return ResponseUtlis.success(200);
            } else {
                return ResponseUtlis.error(500, "用户名或密码错误");
            }
        }

    }

    /**
     * 注册
     * @param allUserLogin
     * @return
     */
    @ApiOperation("注册")
    @RequestMapping(value = "signIn", method = RequestMethod.POST)
    public Response<AllUserLogin> insertAllUserLogin(AllUserLogin allUserLogin) {

        Boolean result = allUserLoginService.insertAllUserLogin(allUserLogin);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }
}
