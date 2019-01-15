package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.*;
import com.genogram.entityvo.IndexFundDrowingVo;
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

    @Autowired
    private IFanIndexInfoService fanIndexInfoService;

    @Autowired
    private IFanProIndexInfoService proIndexInfoService;

    @Autowired
    private IAllFamilyService allFamilyService;

    @Autowired
    private IFanIndexFundDrowingService fanIndexFundDrowingService;

    String fan = "fan";
    String pro = "pro";

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

    @ApiOperation(value = "查询用户", notes = "userName:用户名,realName:真实名,nickName:别名,mobilePhone:手机,picUrl:头像,siteId:网站Id,role:角色(1-县级管理员,2-省级管理员,0-不是管理员),familyCode:姓氏,region:地区,token:token")
    @RequestMapping(value = "getUserLoginList", method = RequestMethod.POST)
    public Response<AllUserLogin> getUserLoginList(@ApiParam("主键") @RequestParam(value = "id", required = false) Integer id,
                                                   @ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                   @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        Wrapper<AllUserLogin> wrapper = new EntityWrapper<>();

        if (StringUtils.isEmpty(id)) {
            wrapper = null;
        } else {
            wrapper.eq("id", id);
        }
        Page<AllUserLogin> userLoginPage = allUserLoginService.getAllUserLoginPage(wrapper, pageNo, pageSize);

        if (StringUtils.isEmpty(userLoginPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(userLoginPage);
    }

    @ApiOperation(value = "查询非管理员用户", notes = "userName:用户名,realName:真实名,nickName:别名,mobilePhone:手机,picUrl:头像,siteId:网站Id,role:角色(1-县级管理员,2-省级管理员,0-不是管理员),familyCode:姓氏,region:地区,token:token")
    @RequestMapping(value = "getUser", method = RequestMethod.POST)
    public Response<AllUserLogin> getUserLogin(@ApiParam("主键") @RequestParam(value = "id", required = false) Integer id,
                                               @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        Wrapper<AllUserLogin> wrapper = new EntityWrapper<>();

        wrapper.eq("role", 0);

        if (!StringUtils.isEmpty(id)) {
            wrapper.eq("id", id);
        }
        Page<AllUserLogin> userLoginPage = allUserLoginService.getAllUserLoginPage(wrapper, pageNo, pageSize);

        if (StringUtils.isEmpty(userLoginPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(userLoginPage);
    }

    @ApiOperation(value = "查询网站管理员", notes = "userName:用户名,realName:真实名,nickName:别名,mobilePhone:手机,picUrl:头像,siteId:网站Id,role:角色(1-县级管理员,2-省级管理员,0-不是管理员),familyCode:姓氏,region:地区,token:token")
    @RequestMapping(value = "getUserLoginRoleList", method = RequestMethod.POST)
    public Response<AllUserLogin> getUserLoginRoleList(@ApiParam("网站ID") @RequestParam("siteId") Integer siteId,
                                                       @ApiParam("网站级别(fan-县级,pro-省级)") @RequestParam("siteType") String siteType,
                                                       @ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                       @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        List list = new ArrayList();

        // 角色(0.不是管理员,1.县级管理员,2省级管理员,3.全国管理员,4县级副管理员,5省级副管理员,6全国副管理员,9.超级管理员)
        Wrapper<AllUserLogin> wrapper = new EntityWrapper<>();

        wrapper.eq("site_id", siteId);
        if (fan.equals(siteType)) {
            list.add(1);
            list.add(4);
        } else if (pro.equals(siteType)) {
            list.add(2);
            list.add(5);
        } else {
            list.add(3);
            list.add(6);
        }
        wrapper.in("role", list);

        Page<AllUserLogin> userLoginPage = allUserLoginService.getAllUserLoginPage(wrapper, pageNo, pageSize);

        if (StringUtils.isEmpty(userLoginPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(userLoginPage);
    }

    @ApiOperation(value = "网站", notes = "id-主键,familyCode-姓氏,regionCode-地区,name-网站名,pic-图腾")
    @RequestMapping(value = "getSysSite", method = RequestMethod.POST)
    public Response<SysSiteVo> getSysSite(SysSiteVo sysSiteVo,
                                          @ApiParam("siteType(联谊会-fan,省级-pro)") @RequestParam(value = "siteType") String siteType,
                                          @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (fan.equals(siteType)) {

            Wrapper<FanSysSite> wrapper = new EntityWrapper<>();
            wrapper.eq("status", 1);
            Long total = (long) allUserLoginService.getFanSysSite(wrapper).size();
            List<FanSysSite> fanSysSiteList = allUserLoginService.getFanSysSitePage(wrapper, pageNo, pageSize);

            List<Integer> siteIdList = new ArrayList();
            List familyList = new ArrayList();
            fanSysSiteList.forEach((FanSysSite fanSysSite) -> {
                siteIdList.add(fanSysSite.getId());
                familyList.add(fanSysSite.getFamilyCode());
            });

            Wrapper<AllFamily> allFamilyWrapper = new EntityWrapper<>();
            allFamilyWrapper.in("id", familyList);
            List<AllFamily> allFamilies = allUserLoginService.getAllFamily(allFamilyWrapper);

            List<FanIndexInfo> fanIndexInfoList = new ArrayList<>();
            for (Integer siteId : siteIdList) {
                FanIndexInfo fanIndexInfo = fanIndexInfoService.getFanIndexInfo(siteId);
                fanIndexInfoList.add(fanIndexInfo);
            }

            List<SysSiteVo> sysSiteVoList = new ArrayList<>();
            fanSysSiteList.forEach((FanSysSite fanSysSite) -> {

                SysSiteVo sysSiteVo1 = new SysSiteVo();

                BeanUtils.copyProperties(fanSysSite, sysSiteVo1);

                List<AllFamily> families = new ArrayList<>();
                allFamilies.forEach((AllFamily allFamily) -> {

                    if (allFamily.getId().equals(fanSysSite.getFamilyCode())) {
                        families.add(allFamily);
                        sysSiteVo1.setFamilyName(allFamily.getValue());
                    }
                });

                List<FanIndexInfo> fanIndexInfos = new ArrayList<>();
                fanIndexInfoList.forEach((FanIndexInfo fanIndexInfo) -> {

                    if (!StringUtils.isEmpty(fanIndexInfo) && fanSysSite.getId().equals(fanIndexInfo.getSiteId())) {
                        fanIndexInfos.add(fanIndexInfo);
                        sysSiteVo1.setUrl(fanIndexInfo.getTotemPicSrc());
                    }
                });
                sysSiteVoList.add(sysSiteVo1);
            });

            if (StringUtils.isEmpty(sysSiteVoList)) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }

            Page<SysSiteVo> page = new Page<>(pageNo, pageSize);
            page.setRecords(sysSiteVoList);
            page.setTotal(total);
            return ResponseUtlis.success(page);

        } else if (pro.equals(siteType)) {
            Wrapper<ProSysSite> wrapper = new EntityWrapper<>();
            wrapper.eq("status", 1);
            Long total = (long) allUserLoginService.getProSysSite(wrapper).size();
            List<ProSysSite> proSysSiteList = allUserLoginService.getProSysSitePage(wrapper, pageNo, pageSize);

            List<Integer> siteIdList = new ArrayList();
            List familyList = new ArrayList();
            proSysSiteList.forEach((ProSysSite proSysSite) -> {
                siteIdList.add(proSysSite.getId());
                familyList.add(proSysSite.getFamilyCode());
            });

            Wrapper<AllFamily> allFamilyWrapper = new EntityWrapper<>();
            allFamilyWrapper.in("id", familyList);
            List<AllFamily> allFamilies = allUserLoginService.getAllFamily(allFamilyWrapper);

            List<ProIndexInfo> proIndexInfoList = new ArrayList<>();
            for (Integer siteId : siteIdList) {
                ProIndexInfo proIndexInfo = proIndexInfoService.getProIndexInfo(siteId);
                proIndexInfoList.add(proIndexInfo);
            }

            List<SysSiteVo> sysSiteVoList = new ArrayList<>();
            proSysSiteList.forEach((ProSysSite proSysSite) -> {

                SysSiteVo sysSiteVo1 = new SysSiteVo();

                BeanUtils.copyProperties(proSysSite, sysSiteVo1);

                List<AllFamily> families = new ArrayList<>();
                allFamilies.forEach((AllFamily allFamily) -> {

                    if (allFamily.getId().equals(proSysSite.getFamilyCode())) {
                        families.add(allFamily);
                        sysSiteVo1.setFamilyName(allFamily.getValue());
                    }
                });

                List<ProIndexInfo> proIndexInfos = new ArrayList<>();
                proIndexInfoList.forEach((ProIndexInfo proIndexInfo) -> {

                    if (!StringUtils.isEmpty(proIndexInfo) && proIndexInfo.getSiteId().equals(proSysSite.getId())) {
                        proIndexInfos.add(proIndexInfo);
                        sysSiteVo1.setUrl(proIndexInfo.getTotemPicSrc());
                    }
                });
                sysSiteVoList.add(sysSiteVo1);
            });

            if (StringUtils.isEmpty(sysSiteVoList)) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }

            Page<SysSiteVo> page = new Page<>(pageNo, pageSize);
            page.setRecords(sysSiteVoList);
            page.setTotal(total);
            return ResponseUtlis.success(page);
        } else {
            return null;
        }

    }

    @ApiOperation(value = "个人资料查询", notes = "userName:用户名,realName:真实名,nickName:别名,mobilePhone:手机,picUrl:头像,siteId:网站Id,role:角色(1-县级管理员,2-省级管理员,0-不是管理员),familyCode:姓氏,region:地区,token:token")
    @RequestMapping(value = "getUserLogin", method = RequestMethod.POST)
    public Response<AllUserLogin> getUserLogin(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                               @ApiParam("主键") @RequestParam("id") Integer id) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        AllUserLogin login = allUserLoginService.getAllUserLoginById(id);
        if (StringUtils.isEmpty(login)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(login);
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
    public Response<AllUserLogin> updatePerson(AllUserLogin allUserLogin,
                                               @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin login = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(login.getRole())) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        allUserLogin.setUpdateUser(login.getId());

        Boolean result = allUserLoginService.updateUserLogin(allUserLogin);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    @ApiOperation(value = "姓氏查询", notes = "value-姓氏,spell-拼音,first_letter-首字母")
    @RequestMapping(value = "familyList", method = RequestMethod.POST)
    public Response<AllFamily> getFamilyList(@ApiParam("首字母") @RequestParam(value = "firstLetter", required = false) String firstLetter) {

        List<AllFamily> familyList = allFamilyService.getAllFamilyByFirstLetter(firstLetter);

        if (StringUtils.isEmpty(familyList)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(familyList);
    }

    @ApiOperation(value = "姓氏修改", notes = "value-姓氏,spell-拼音,first_letter-首字母")
    @RequestMapping(value = "updateAllFamily", method = RequestMethod.POST)
    public Response<AllFamily> updateAllFamily(@ApiParam("token") @RequestParam(value = "token", required = false) String token, AllFamily allFamily) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        AllFamily family = allFamilyService.updateAllFamily(allFamily);

        return ResponseUtlis.success(family);
    }

    /**
     * 线上提现记录
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "线上提现记录", notes = "id:主键,siteId:网站Id,drowAmount:提现金额,drowBank;提现银行,drowBankSub:支行名称,drowTime:提现时间,drowInAccountName:账户名,drowInAccountCard:账户")
    @RequestMapping(value = "getFanIndexFundDrowing", method = RequestMethod.POST)
    public Response<IndexFundDrowingVo> getFanIndexFundDrowing(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                               @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!role09.equals(allUserLogin.getRole())) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        Page<IndexFundDrowingVo> indexFundDrowingVoPage = fanIndexFundDrowingService.getIndexFundDrowingVoPage( pageNo, pageSize);

        if (StringUtils.isEmpty(indexFundDrowingVoPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(indexFundDrowingVoPage);
    }
}
