package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanSysSite;
import com.genogram.entity.ProSysSite;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联谊会后台
 *
 * @Author: Toxicant
 * @Date: 2018-11-09
 * @Time: 14:23
 * @Param:
 * @return:
 * @Description:
 */
@Api(description = "后台用户管理")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/person")
public class PersonController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @ApiOperation(value = "查询用户",notes = "userName:用户名,realName:真实名,nickName:别名,mobilePhone:手机,picUrl:头像,siteId:网站Id,role:角色(1-县级管理员,2-省级管理员,0-不是管理员),familyCode:姓氏,region:地区,token:token")
    @RequestMapping(value = "getUserLoginList", method = RequestMethod.POST)
    public Response<AllUserLogin> getUserLoginList(@ApiParam("用户名")@RequestParam(value = "userName",required = false)String userName,
                                               @ApiParam("token")@RequestParam(value = "token",required = false)String token,
                                               @RequestParam(value = "pageNo",defaultValue = "1")Integer pageNo,
                                               @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不正确");
        }

        Wrapper<AllUserLogin> wrapper = new EntityWrapper<>();

        if (StringUtils.isEmpty(userName)) {
            wrapper = null;
        } else {
            wrapper.like("user_name", userName);
        }
        Page<AllUserLogin> userLoginPage = allUserLoginService.getAllUserLoginPage(wrapper, pageNo, pageSize);

        return ResponseUtlis.success(userLoginPage);
    }

    @ApiOperation(value = "县级网站",notes = "id-主键,familyCode-姓氏,regionCode-地区,name-网站名,pic-图腾")
    @RequestMapping(value = "getFanSysSite", method = RequestMethod.POST)
    public Response<FanSysSite> getFanSysSite(FanSysSite fanSysSite,@ApiParam("token")@RequestParam(value = "token", required = false)String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不正确");
        }

        Wrapper<FanSysSite> wrapper = new EntityWrapper<>();

        List<FanSysSite> fanSysSiteList = allUserLoginService.getFanSysSite(wrapper);

        return ResponseUtlis.success(fanSysSiteList);
    }

    @ApiOperation(value = "省级网站",notes = "id-主键,familyCode-姓氏,regionCode-地区,name-网站名,pic-图腾")
    @RequestMapping(value = "getProSysSite", method = RequestMethod.POST)
    public Response<ProSysSite> getProSysSite(ProSysSite proSysSite, @ApiParam("token")@RequestParam(value = "token",required = false)String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不存在");
        }

        Wrapper<ProSysSite> wrapper = new EntityWrapper<>();
        List<ProSysSite> proSysSiteList = allUserLoginService.getProSysSite(wrapper);

        return ResponseUtlis.success(proSysSiteList);
    }

    @ApiOperation(value = "个人资料查询", notes = "userName:用户名,realName:真实名,nickName:别名,mobilePhone:手机,picUrl:头像,siteId:网站Id,role:角色(1-县级管理员,2-省级管理员,0-不是管理员),familyCode:姓氏,region:地区,token:token")
    @RequestMapping(value = "getUserLogin", method = RequestMethod.POST)
    public Response<AllUserLogin> getUserLogin(@ApiParam("token")@RequestParam(value = "token",defaultValue = "")String token,
                                               @ApiParam("主键")@RequestParam("id")Integer id) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        AllUserLogin login = allUserLoginService.getAllUserLoginById(userLogin.getId());

        if (login.getRole()!=3) {
            return ResponseUtlis.error(204, "您没有权限");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(id);

        return ResponseUtlis.success(allUserLogin);
    }

    /**
     * 修改个人资料
     * @param allUserLogin
     * @param token
     * @return
     */
    @ApiOperation(value = "个人资料修改", notes = "userName:用户名,realName:真实名,nickName:别名,mobilePhone:手机,picUrl:头像,siteId:网站Id,role:角色(1-县级管理员,2-省级管理员,0-不是管理员),familyCode:姓氏,region:地区,token:token")
    @RequestMapping(value = "updatePerson", method = RequestMethod.POST)
    public Response<AllUserLogin> updatePerson(AllUserLogin allUserLogin,@ApiParam("token")@RequestParam(value = "token",defaultValue = "")String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }
        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        Integer id = userLogin.getId();

        AllUserLogin login = allUserLoginService.getAllUserLoginById(id);

        if (login.getRole()!=3) {
            return ResponseUtlis.error(204, "您没有权限");
        }

        allUserLogin.setUpdateUser(login.getId());

        Boolean result = allUserLoginService.updateUserLogin(allUserLogin);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}
