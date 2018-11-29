package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.config.Constants;
import com.genogram.entity.*;
import com.genogram.entityvo.SysSiteVo;
import com.genogram.service.*;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private IFanIndexFundService fanIndexFundService;

    @Autowired
    private IFanIndexInfoService fanIndexInfoService;

    @Autowired
    private IFanProIndexFundService fanProIndexFundService;

    @Autowired
    private IFanProIndexInfoService fanProIndexInfoService;

    @ApiOperation(value = "姓氏",notes = "value-姓氏姓名")
    @RequestMapping(value = "getAllFamily",method = RequestMethod.GET)
    public Response<AllFamily> getAllFamily(@ApiParam("姓氏") @RequestParam(value = "value",required = false) String value) {

        Wrapper<AllFamily> wrapper = new EntityWrapper<>();

        if (!StringUtils.isEmpty(value)) {
            wrapper.eq("value", value);
        }

        List<AllFamily> familyList = allUserLoginService.getAllFamily(wrapper);

        if (StringUtils.isEmpty(familyList)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(familyList);
    }

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
        Integer userId;

        Wrapper<AllFamily> familyWrapper = new EntityWrapper<>();
        familyWrapper.eq("id", sysSiteVo.getFamilyCode());
        AllFamily allFamily = allUserLoginService.getAllFamily(familyWrapper).get(0);

        AllUserLogin userLogin = new AllUserLogin();
        if ("fan".equals(siteType)) {

            FanSysSite fanSysSite = new FanSysSite();
            BeanUtils.copyProperties(sysSiteVo, fanSysSite);

            fanSysSite.setCreateUser(id);
            fanSysSite.setUpdateUser(id);
            String name = allFamily.getValue() + "氏联谊会";
            fanSysSite.setName(name);

            FanSysSite fanSysSite1 = sysSiteService.insertFanSysSite(fanSysSite);

            //新增的网站ID
            siteId = fanSysSite1.getId();

            //新增的管理员
            userId = fanSysSite1.getAdmin();
            userLogin = allUserLoginService.getAllUserLoginById(userId);

            FanIndexFund fanIndexFund = new FanIndexFund();
            fanIndexFund.setSiteId(siteId);
            fanIndexFund.setCreateUser(id);
            fanIndexFund.setUpdateUser(id);

            fanIndexFundService.insertFanIndexFund(fanIndexFund);

            FanIndexInfo fanIndexInfo = new FanIndexInfo();
            fanIndexInfo.setSiteId(siteId);
            fanIndexInfo.setCreateUser(id);
            fanIndexInfo.setUpdateUser(id);

            if (StringUtils.isEmpty(sysSiteVo.getUrl())) {
                fanIndexInfo.setTotemPicSrc("00/04/wKgChFv-eoeAHXUWAAIBsiLZEJ4138.png");
            }
            fanIndexInfoService.insertFanIndexInfo(fanIndexInfo);

            //设置管理员权限
            userLogin.setRole(1);

        } else if ("pro".equals(siteType)) {
            ProSysSite proSysSite = new ProSysSite();
            BeanUtils.copyProperties(sysSiteVo, proSysSite);

            proSysSite.setCreateUser(id);
            proSysSite.setUpdateUser(id);
            String name = allFamily.getValue() + "氏官网";
            proSysSite.setName(name);
            proSysSite.setParent(0);

            ProSysSite proSysSite1 = sysSiteService.insertProSysSite(proSysSite);

            siteId =proSysSite1.getId();
            userId = proSysSite1.getAdmin();
            userLogin = allUserLoginService.getAllUserLoginById(userId);

            ProIndexFund proIndexFund = new ProIndexFund();
            proIndexFund.setSiteId(siteId);
            proIndexFund.setCreateUser(id);
            proIndexFund.setUpdateUser(id);

            fanProIndexFundService.insertProIndexFund(proIndexFund);

            ProIndexInfo proIndexInfo = new ProIndexInfo();
            proIndexInfo.setSiteId(siteId);
            proIndexInfo.setCreateUser(id);
            proIndexInfo.setUpdateUser(id);

            if (StringUtils.isEmpty(sysSiteVo.getUrl())) {
                proIndexInfo.setTotemPicSrc("00/04/wKgChFv-eoeAHXUWAAIBsiLZEJ4138.png");
            }

            fanProIndexInfoService.insertProIndexInfo(proIndexInfo);

            userLogin.setRole(2);
        }

        if (true) {

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
