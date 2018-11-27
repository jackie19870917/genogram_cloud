package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanSysSite;
import com.genogram.entity.ProSysSite;
import com.genogram.entityvo.SysSiteVo;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IFanSysWebNewsShowService;
import com.genogram.service.ISysSiteService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Toxicant
 * @date: 2018-11-26
 * @time: 14:19
 * @param:
 * @return:
 * @Description:
 */
@Api(description = "开通网站")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/sysSite")
public class SysSiteController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISysSiteService sysSiteService;

    @Autowired
    private IFanSysWebNewsShowService fanSysWebNewsShowService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @ApiOperation(value = "开通网站", notes = "id-网站ID,familyCode-姓氏,regionCode-地区编号,name-网站名称,oneUrl-公司指定域名,twoUrl-自费域名")
    @RequestMapping(value = "insertSysSite", method = RequestMethod.POST)
    public Response<FanSysSite> insertSysSite(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                              @ApiParam("网站级别(fan-县级,pro-省级)") @RequestParam("siteType") String siteType,
                                              SysSiteVo sysSiteVo) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        AllUserLogin allUserLogin = userService.getUserLoginInfoByToken(token);
        Integer id = allUserLogin.getId();

        Integer siteId = null;
        Boolean result = true;
        Integer userId = null;

        AllUserLogin userLogin = new AllUserLogin();
        if ("fan".equals(siteType)) {

            FanSysSite fanSysSite = new FanSysSite();
            BeanUtils.copyProperties(sysSiteVo, fanSysSite);

            fanSysSite.setCreateUser(id);
            fanSysSite.setUpdateUser(id);
            String name = fanSysSite.getFamilyCode() + "氏联谊会";
            fanSysSite.setName(name);

            result = sysSiteService.insertFanSysSite(fanSysSite);

            //新增的网站ID
            siteId = sysSiteService.getFanSysSite().getId();

            //新增的管理员
            userId = sysSiteService.getFanSysSite().getAdmin();
            userLogin = allUserLoginService.getAllUserLoginById(userId);

            //设置管理员权限
            userLogin.setRole(1);

        } else if ("pro".equals(siteType)) {
            ProSysSite proSysSite = new ProSysSite();
            BeanUtils.copyProperties(sysSiteVo, proSysSite);

            proSysSite.setCreateUser(id);
            proSysSite.setUpdateUser(id);
            String name = proSysSite.getFamilyCode() + "氏官网";
            proSysSite.setName(name);
            proSysSite.setParent(0);

            result = sysSiteService.insertProSysSite(proSysSite);

            siteId = sysSiteService.getProSysSite().getId();
            userId = sysSiteService.getProSysSite().getAdmin();
            userLogin = allUserLoginService.getAllUserLoginById(userId);
            userLogin.setRole(2);
        }

        if (result) {

            //修改权限
            allUserLoginService.updateUserLogin(userLogin);

            //初始化栏目
            fanSysWebNewsShowService.initWebMenu(siteId);

            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}
