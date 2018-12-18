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

import java.util.ArrayList;
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
    private IProSysWebNewsShowService proSysWebNewsShowService;

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

    @Autowired
    private IFanIndexSlidePicService fanIndexSlidePicService;

    @Autowired
    private IFanProIndexSlidePicService fanProIndexSlidePicService;

    /**
     * 角色权限 (0.不是管理员,1.县级管理员,2省级管理员,3.全国管理员,4县级副管理员,5省级副管理员,6全国副管理员,9.超级管理员)
     */
    Integer role01 = 1;
    Integer role04 = 4;
    Integer role09 = 9;

    private List getList() {

        List list = new ArrayList();
        /**
         * 角色权限 (0.不是管理员,1.县级管理员,2省级管理员,3.全国管理员,4县级副管理员,5省级副管理员,6全国副管理员,9.超级管理员)
         */
        list.add(1);
        list.add(4);
        list.add(9);

        return list;
    }

    String fan = "fan";
    String pro = "pro";

    @ApiOperation(value = "姓氏", notes = "value-姓氏姓名")
    @RequestMapping(value = "getAllFamily", method = RequestMethod.POST)
    public Response<AllFamily> getAllFamily(@ApiParam("姓氏") @RequestParam(value = "value", required = false) String value) {

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

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin allUserLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(allUserLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin userLogin01 = allUserLoginService.getAllUserLoginById(allUserLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(userLogin01.getRole())) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        Integer id = allUserLogin.getId();

        Integer siteId;
        Integer userId;

        Wrapper<AllFamily> familyWrapper = new EntityWrapper<>();
        familyWrapper.eq("id", sysSiteVo.getFamilyCode());
        AllFamily allFamily = allUserLoginService.getAllFamily(familyWrapper).get(0);

        AllUserLogin userLogin = new AllUserLogin();
        if (fan.equals(siteType)) {

            Wrapper<FanSysSite> wrapper = new EntityWrapper<>();
            wrapper.eq("region_code", sysSiteVo.getRegionCode());
            wrapper.eq("family_code", sysSiteVo.getFamilyCode());

            List<FanSysSite> fanSysSiteList = allUserLoginService.getFanSysSite(wrapper);

            if (fanSysSiteList.size() != 0) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "该联谊会已开通");
            }

            FanSysSite fanSysSite = new FanSysSite();
            BeanUtils.copyProperties(sysSiteVo, fanSysSite);

            fanSysSite.setCreateUser(id);
            fanSysSite.setUpdateUser(id);

            if (StringUtils.isEmpty(sysSiteVo.getName())) {
                String name = allFamily.getValue() + "氏联谊会";
                fanSysSite.setName(name);
            }

            Integer regionNum = Integer.valueOf(sysSiteVo.getRegionCode());
            regionNum = regionNum / 10000 * 10000;

            fanSysSite.setParent(regionNum);
            FanSysSite fanSysSite1 = sysSiteService.insertFanSysSite(fanSysSite);

            //新增的网站ID
            siteId = fanSysSite1.getId();

            //新增的管理员
            userId = fanSysSite1.getAdmin();
            userLogin = allUserLoginService.getAllUserLoginById(userId);

            if (!StringUtils.isEmpty(userId)) {
                userLogin = allUserLoginService.getAllUserLoginById(userId);

                userLogin.setRole(1);
                //修改权限
                allUserLoginService.updateUserLogin(userLogin);

            }

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
                fanIndexInfo.setTotemPicSrc("00/05/default_tuteng.png");
            }
            fanIndexInfoService.insertFanIndexInfo(fanIndexInfo);

            FanIndexSlidePic fanIndexSlidePic = new FanIndexSlidePic();
            fanIndexSlidePic.setSiteId(siteId);
            fanIndexSlidePic.setCreateUser(id);
            fanIndexSlidePic.setUpdateUser(id);

            fanIndexSlidePicService.insertFanIndexSlidePic(fanIndexSlidePic);

            //初始化栏目
            fanSysWebNewsShowService.initWebMenu(siteId);

        } else if (pro.equals(siteType)) {

            Wrapper<ProSysSite> wrapper = new EntityWrapper<>();
            wrapper.eq("region_code", sysSiteVo.getRegionCode());
            wrapper.eq("family_code", sysSiteVo.getFamilyCode());

            List<ProSysSite> proSysSiteList = allUserLoginService.getProSysSite(wrapper);

            if (proSysSiteList.size() != 0) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "该省级网站已开通");
            }

            ProSysSite proSysSite = new ProSysSite();
            BeanUtils.copyProperties(sysSiteVo, proSysSite);

            proSysSite.setCreateUser(id);
            proSysSite.setUpdateUser(id);

            if (StringUtils.isEmpty(sysSiteVo.getName())) {
                String name = allFamily.getValue() + "氏官网";
                proSysSite.setName(name);
            }

            ProSysSite proSysSite1 = sysSiteService.insertProSysSite(proSysSite);

            siteId = proSysSite1.getId();
            userId = proSysSite1.getAdmin();

            if (!StringUtils.isEmpty(userId)) {
                userLogin = allUserLoginService.getAllUserLoginById(userId);

                userLogin.setRole(2);
                //修改权限
                allUserLoginService.updateUserLogin(userLogin);

            }


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
                proIndexInfo.setTotemPicSrc("00/05/default_tuteng.png");
            }

            fanProIndexInfoService.insertProIndexInfo(proIndexInfo);

            ProIndexSlidePic proIndexSlidePic = new ProIndexSlidePic();
            proIndexSlidePic.setSiteId(siteId);
            proIndexSlidePic.setCreateUser(id);
            proIndexSlidePic.setUpdateUser(id);

            fanProIndexSlidePicService.insertProIndexSlidePic(proIndexSlidePic);

            //初始化栏目
            proSysWebNewsShowService.initWebMenu(siteId);
        }

        if (true) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}
