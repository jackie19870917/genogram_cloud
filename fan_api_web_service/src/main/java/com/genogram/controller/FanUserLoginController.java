package com.genogram.controller;

import com.genogram.entity.AllUserLogin;
import com.genogram.entityvo.UserVo;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IUserService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IUserService userService;

    /**
     * 登陆
     // @param allUserLogin
     * @return
     */
    @ApiOperation(value = "登陆",notes = "userName:用户名,realName:真实名,nickName:别名,mobilePhone:手机,picUrl:头像,siteId:网站Id,role:角色(1-县级管理员,2-省级管理员,0-不是管理员),familyCode:姓氏,region:地区,token:token")
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Response<UserVo> getAllUserLogin(@ApiParam("用户名")@RequestParam String userName,
                                            @ApiParam("密码")@RequestParam String password) {

        AllUserLogin allUserLogin = new AllUserLogin();
        allUserLogin.setMobilePhone(userName);
        allUserLogin.setPassword(password);

        AllUserLogin userLogin = allUserLoginService.getAllUserLogin(allUserLogin);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(400, "用户名不存在");
        } else {
            if (userLogin.getPassword().equals(allUserLogin.getPassword())) {

                Map<String,Object> map = new HashMap();

                String time = DateUtil.getAllTime();

                String user = userLogin.getMobilePhone() +"="+ userLogin.getPassword()+"=" + time;
                String value = userLogin.getId()+"="+userLogin.getUserName();
                map.put(user, value);

                //Base64加密
                byte[] bytes = Base64.encodeBase64(map.toString().getBytes(),true);
                String str = new String(bytes);

                UserVo userVo = new UserVo();
                BeanUtils.copyProperties(userLogin,userVo);
                userVo.setToken(str);

                return ResponseUtlis.success(userVo);
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
    @ApiOperation(value = "注册",notes = "userName:用户名,mobilePhone:手机号,password:密码,familyCode:姓氏,region:地区")
    @RequestMapping(value = "signIn", method = RequestMethod.POST)
    public Response<AllUserLogin> insertAllUserLogin(AllUserLogin allUserLogin) {

        Boolean result = allUserLoginService.insertAllUserLogin(allUserLogin);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    @ApiOperation("修改密码")
    @RequestMapping(value = "updatePassword",method = RequestMethod.POST)
    public Response<AllUserLogin> updatePassword(@ApiParam("旧密码")@RequestParam String oldPassword,
                                                 @ApiParam("新密码")@RequestParam String newPassword,
                                                 @ApiParam("token")@RequestParam String token) {

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        AllUserLogin login = allUserLoginService.getAllUserLogin(userLogin);

        if (!oldPassword.equals(login.getPassword())) {
            return ResponseUtlis.error(400, "您输入的密码不正确");
        }

        AllUserLogin allUserLogin = new AllUserLogin();
        allUserLogin.setId(login.getId());
        allUserLogin.setPassword(newPassword);

        allUserLoginService.updateAllUserLogin(allUserLogin);

        return ResponseUtlis.success(200);
    }
}
