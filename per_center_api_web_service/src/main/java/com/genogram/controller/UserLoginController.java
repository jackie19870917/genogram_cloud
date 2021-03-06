package com.genogram.controller;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.config.Constants;
import com.genogram.entity.AllFamily;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.AllUserReg;
import com.genogram.entityvo.PersonVo;
import com.genogram.entityvo.UserVo;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IAllUserRegService;
import com.genogram.service.IUserService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.MessageUtil;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
public class UserLoginController {

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
                                            @ApiParam("密码") @RequestParam String password,
                                            HttpServletRequest request,
                                            @ApiParam("openId") @RequestParam(value = "openId", required = false) String openId) {

        AllUserLogin allUserLogin = new AllUserLogin();
        allUserLogin.setMobilePhone(userName);
        allUserLogin.setPassword(password);

        AllUserLogin userLogin = allUserLoginService.getAllUserLogin(allUserLogin);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "用户名不存在");
        } else {
            if (userLogin.getPassword().equals(allUserLogin.getPassword())) {

                if (!StringUtils.isEmpty(openId)) {

                    String opedId = (String) request.getSession().getAttribute("opedId");

                    if (StringUtils.isEmpty(opedId)) {
                        return ResponseUtils.error(Constants.NOSUPPORT, "您还没授权");
                    }

                    userLogin.setOpenId(opedId);
                }

                allUserLoginService.insertAllUserLogin(userLogin);

                PersonVo personVo = getPersonVo(userLogin);

                return ResponseUtils.success(personVo);
            } else {
                return ResponseUtils.error(Constants.FAILURE_CODE, "用户名或密码错误");
            }
        }

    }

    @ApiOperation(value = "姓氏", notes = "value-姓氏姓名")
    @RequestMapping(value = "getAllFamily", method = RequestMethod.GET)
    public Response<AllFamily> getAllFamily(@ApiParam("姓氏") @RequestParam(value = "value", required = false) String value) {

        Wrapper<AllFamily> wrapper = new EntityWrapper<>();

        if (!StringUtils.isEmpty(value)) {
            wrapper.eq("value", value);
        }

        List<AllFamily> familyList = allUserLoginService.getAllFamily(wrapper);

        return ResponseUtils.success(familyList);
    }

    String message = null;

    @ApiOperation("短信验证码")
    @RequestMapping(value = "sendVerificationCode", method = RequestMethod.POST)
    public Response sendVerificationCode(@ApiParam("手机号") @RequestParam("mobilePhone") String mobilePhone) throws IOException, ClientException {

        message = MessageUtil.sendMessage(mobilePhone);

        if (StringUtils.isEmpty(message)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "发送失败");
        } else {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
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
    public Response<AllUserLogin> insertAllUserLogin(AllUserLogin allUserLogin, HttpServletRequest request,
                                                     @ApiParam("验证码") @RequestParam("verificationCode") String verificationCode,
                                                     @ApiParam("openId") @RequestParam(value = "openId", required = false) String openId) {

        if (!verificationCode.equals(message)) {
            return ResponseUtils.error(Constants.ERRO_CODE, "验证码错误");
        }

        if (!StringUtils.isEmpty(openId)) {

            String opedId = (String) request.getSession().getAttribute("opedId");
            allUserLogin.setOpenId(opedId);
        }

        AllUserLogin userLogin = allUserLoginService.insertAllUserLogin(allUserLogin);

        if (StringUtils.isEmpty(allUserLogin.getFamilyCode())) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "姓氏不能为空");
        }

        if (!StringUtils.isEmpty(userLogin)) {

            AllUserReg allUserReg = new AllUserReg();
            allUserReg.setAllUserLoginId(userLogin.getId());
            allUserReg.setCreateUser(userLogin.getId());

            allUserRegService.insertAllUserReg(allUserReg);

            PersonVo personVo = getPersonVo(userLogin);

            return ResponseUtils.success(personVo);

        } else {
            return ResponseUtils.error(Constants.FAILURE_CODE, "用户名已注册");
        }
    }

    private PersonVo getPersonVo(AllUserLogin allUserLogin) {
        Map<String, Object> map = new HashMap(16);

        String time = DateUtil.getAllTime();

        String user = allUserLogin.getMobilePhone() + "=" + allUserLogin.getPassword() + "=" + time;
        String value = allUserLogin.getId() + "=" + allUserLogin.getUserName();
        map.put(user, value);

        //Base64加密
        byte[] bytes = Base64.encodeBase64(map.toString().getBytes(), true);
        String str = new String(bytes);

        PersonVo personVo = allUserRegService.getAllUserRegByUserId(allUserLogin.getId());

        BeanUtils.copyProperties(allUserLogin, personVo);
        personVo.setToken(str);

        return personVo;
    }

    @ApiOperation("忘了密码")
    @RequestMapping(value = "forgetPassword", method = RequestMethod.POST)
    public Response<AllUserLogin> rebuiltPassword(@ApiParam("验证码") @RequestParam("verificationCode") String verificationCode,
                                                  @ApiParam("手机号") @RequestParam("mobilePhone") String mobilePhone,
                                                  @ApiParam("新密码") @RequestParam("password") String password) {

        if (!verificationCode.equals(message)) {
            return ResponseUtils.error(Constants.ERRO_CODE, "验证码错误");
        }

        AllUserLogin allUserLogin = new AllUserLogin();
        allUserLogin.setMobilePhone(mobilePhone);

        AllUserLogin userLogin = allUserLoginService.getAllUserLogin(allUserLogin);

        allUserLogin.setId(userLogin.getId());
        allUserLogin.setPassword(password);

        allUserLoginService.updateAllUserLogin(allUserLogin);

        return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
    }

    @ApiOperation("修改密码")
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public Response<AllUserLogin> updatePassword(@ApiParam("旧密码") @RequestParam String oldPassword,
                                                 @ApiParam("新密码") @RequestParam String newPassword,
                                                 @ApiParam("token") @RequestParam(value = "token", defaultValue = "") String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        AllUserLogin login = allUserLoginService.getAllUserLogin(userLogin);

        if (!oldPassword.equals(login.getPassword())) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "您输入的密码不正确");
        }

        AllUserLogin allUserLogin = new AllUserLogin();
        allUserLogin.setId(login.getId());
        allUserLogin.setPassword(newPassword);

        allUserLoginService.updateAllUserLogin(allUserLogin);

        return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
    }

    @ApiOperation(value = "个人资料查询", notes = "userName:用户名,realName:真实名,nickName:别名,mobilePhone:手机,picUrl:头像,siteId:网站Id,role:角色(1-县级管理员,2-省级管理员,0-不是管理员),familyCode:姓氏,region:地区,token:token")
    @RequestMapping(value = "getUserLogin", method = RequestMethod.POST)
    public Response<AllUserLogin> getUserLogin(@ApiParam("token") @RequestParam(value = "token", defaultValue = "") String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        return ResponseUtils.success(allUserLogin);
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
            return ResponseUtils.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

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

        PersonVo personVo = allUserRegService.getAllUserRegByUserId(allUserLogin.getId());

        BeanUtils.copyProperties(allUserLogin, personVo);
        personVo.setToken(str);

        if (result) {
            return ResponseUtils.success(personVo);
        } else {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }
}
