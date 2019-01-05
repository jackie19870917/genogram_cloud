package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.AllUserReg;
import com.genogram.entityvo.UserVo;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IAllUserRegService;
import com.genogram.service.IUserService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录
 *
 * @Author: Toxicant
 * @Date: 2018-11-09
 * @Time: 14:23
 * @Param:
 * @return:
 * @Description:
 */
@Api(description = "登录注册")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/userLogin")
public class ProUserLoginController {

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserRegService allUserRegService;

    /**
     * 登陆
     *
     * @param userName
     * @param password
     * @return
     */
    @ApiOperation(value = "登陆", notes = "userName:用户名,realName:真实名,nickName:别名,mobilePhone:手机,picUrl:头像,siteId:网站Id,role:角色(1-县级管理员,2-省级管理员,0-不是管理员),familyCode:姓氏,region:地区,token:token")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Response<UserVo> getAllUserLogin(@ApiParam("用户名") @RequestParam String userName,
                                            @ApiParam("密码") @RequestParam String password) {

        AllUserLogin allUserLogin = new AllUserLogin();
        allUserLogin.setMobilePhone(userName);
        allUserLogin.setPassword(password);

        AllUserLogin userLogin = allUserLoginService.getAllUserLogin(allUserLogin);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "用户名不存在");
        } else {
            if (userLogin.getPassword().equals(allUserLogin.getPassword())) {

                UserVo userVo = getPersonVo(userLogin);

                return ResponseUtlis.success(userVo);
            } else {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "用户名或密码错误");
            }
        }

    }

    /**
     * 注册
     *
     * @param allUserLogin
     * @return
     */
    @ApiOperation(value = "注册", notes = "userName:用户名,mobilePhone:手机号,password:密码,familyCode:姓氏,regionCode:地区")
    @RequestMapping(value = "signIn", method = RequestMethod.POST)
    public Response<AllUserLogin> insertAllUserLogin(AllUserLogin allUserLogin) {

        AllUserLogin userLogin = allUserLoginService.insertAllUserLogin(allUserLogin);

        if (!StringUtils.isEmpty(userLogin)) {

            UserVo userVo = getPersonVo(userLogin);

            AllUserReg allUserReg = new AllUserReg();
            allUserReg.setAllUserLoginId(userLogin.getId());
            allUserReg.setCreateUser(userLogin.getId());

            allUserRegService.insertAllUserReg(allUserReg);

            return ResponseUtlis.success(userVo);

        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "用户名已注册");
        }
    }

    private UserVo getPersonVo(AllUserLogin allUserLogin) {
        Map<String, Object> map = new HashMap(16);

        String time = DateUtil.getAllTime();

        String user = allUserLogin.getMobilePhone() + "=" + allUserLogin.getPassword() + "=" + time;
        String value = allUserLogin.getId() + "=" + allUserLogin.getUserName();
        map.put(user, value);

        //Base64加密
        byte[] bytes = Base64.encodeBase64(map.toString().getBytes(), true);
        String str = new String(bytes);

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(allUserLogin, userVo);
        userVo.setToken(str);
        return userVo;
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param token
     * @return
     */
    @ApiOperation("修改密码")
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public Response<AllUserLogin> updatePassword(@ApiParam("旧密码") @RequestParam String oldPassword,
                                                 @ApiParam("新密码") @RequestParam String newPassword,
                                                 @ApiParam("token") @RequestParam(value = "token", defaultValue = "") String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        AllUserLogin login = allUserLoginService.getAllUserLogin(userLogin);

        if (!oldPassword.equals(login.getPassword())) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "您输入的密码不正确");
        }

        AllUserLogin allUserLogin = new AllUserLogin();
        allUserLogin.setId(login.getId());
        allUserLogin.setPassword(newPassword);

        allUserLoginService.updateAllUserLogin(allUserLogin);

        return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
    }

    /**
     * 个人资料查询
     *
     * @param token
     * @return
     */
    @ApiOperation(value = "个人资料查询", notes = "userName:用户名,realName:真实名,nickName:别名,mobilePhone:手机,picUrl:头像,siteId:网站Id,role:角色(1-县级管理员,2-省级管理员,0-不是管理员),familyCode:姓氏,region:地区,token:token")
    @RequestMapping(value = "getUserLogin", method = RequestMethod.POST)
    public Response<AllUserLogin> getUserLogin(@ApiParam("token") @RequestParam(value = "token", defaultValue = "") String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        return ResponseUtlis.success(allUserLogin);
    }

    /**
     * 修改个人资料
     *
     * @param allUserLogin
     * @param token
     * @return
     */
    @ApiOperation(value = "个人资料修改", notes = "userName:用户名,realName:真实名,nickName:别名,mobilePhone:手机,picUrl:头像,siteId:网站Id,role:角色(1-县级管理员,2-省级管理员,0-不是管理员),familyCode:姓氏,region:地区,token:token")
    @RequestMapping(value = "updatePerson", method = RequestMethod.POST)
    public Response<AllUserLogin> updatePerson(AllUserLogin allUserLogin, @ApiParam("token") @RequestParam(value = "token", defaultValue = "") String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }
        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        allUserLogin.setId(userLogin.getId());

        Boolean result = allUserLoginService.updateUserLogin(allUserLogin);

        allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        Map<String, Object> map = new HashMap(16);

        String time = DateUtil.getAllTime();

        String user = userLogin.getMobilePhone() + "=" + userLogin.getPassword() + "=" + time;
        String value = allUserLogin.getId() + "=" + allUserLogin.getUserName();
        map.put(user, value);

        //Base64加密
        byte[] bytes = Base64.encodeBase64(map.toString().getBytes(), true);
        String str = new String(bytes);

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(allUserLogin, userVo);

        userVo.setToken(str);

        if (result) {
            return ResponseUtlis.success(userVo);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

}
