package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanSysSite;
import com.genogram.entity.ProSysSite;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RequestMapping(value = "getUserLogin", method = RequestMethod.POST)
    public Response<AllUserLogin> getUserLogin(AllUserLogin allUserLogin,
                                               @ApiParam("token")@RequestParam(value = "token",required = false)String token,
                                               @RequestParam(value = "pageNo",defaultValue = "1")Integer pageNo,
                                               @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不正确");
        }

        Wrapper<AllUserLogin> wrapper = new EntityWrapper<>();
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
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不正确");
        }

        Wrapper<ProSysSite> wrapper = new EntityWrapper<>();
        List<ProSysSite> proSysSiteList = allUserLoginService.getProSysSite(wrapper);

        return ResponseUtlis.success(proSysSiteList);
    }
}
