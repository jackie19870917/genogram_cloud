package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.*;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.service.*;
import com.genogram.unit.DateUtil;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 省级首页-设置 后端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Api(description = "省级首页(后台)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/proIndex")
public class ProIndexController {

    @Autowired
    private IProIndexInfoService proIndexInfoService;

    @Autowired
    private IProIndexSlidePicService proIndexSlidePicService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserNewsInfoService allUserNewsInfoService;

    @Autowired
    private IProSysSiteService proSysSiteService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    private List getList() {

        List list = new ArrayList();
        /**
         * 角色权限 (0.不是管理员,1.县级管理员,2省级管理员,3.全国管理员,4县级副管理员,5省级副管理员,6全国副管理员,9.超级管理员)
         */
        list.add(2);
        list.add(5);
        list.add(9);

        return list;
    }

    /**
     * 省级网站信息
     *
     * @param siteId 网站ID
     * @return
     */
    @ApiOperation(value = "基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名称,regionCode;地区编号,totemPicSrc:图腾,title:宣言,description;简介")
    @RequestMapping(value = "getIndexInfo", method = RequestMethod.POST)
    public Response<IndexInfoVo> getIndexInfoVo(@ApiParam("网站Id") @RequestParam Integer siteId,
                                                @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

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

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        IndexInfoVo indexInfoVo = proIndexInfoService.getFanIndexInfoVo(siteId);

        if (StringUtils.isEmpty(indexInfoVo)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(indexInfoVo);
    }

    @ApiOperation(value = "新增/修改基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名字,totemPicSrc:图腾,title:宣言,description:简介")
    @RequestMapping(value = "insertOrUpdateIndexInfo", method = RequestMethod.POST)
    public Response<IndexInfoVo> insertOrUpdateIndexInfo(IndexInfoVo indexInfoVo,
                                                         @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

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

        if (StringUtils.isEmpty(indexInfoVo.getId())) {
            indexInfoVo.setCreateUser(userLogin.getId());
        }

        indexInfoVo.setUpdateUser(userLogin.getId());
        Boolean result = proIndexInfoService.insertOrUpdateIndexInfoVo(indexInfoVo);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 删除   省级基本信息
     *
     * @param proIndexInfo
     * @return
     */
    @ApiOperation(value = "删除基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名字,totemPicSrc:图腾,title:宣言,description:简介")
    @RequestMapping(value = "deleteProIndexInfo", method = RequestMethod.POST)
    public Response<ProIndexInfo> deleteFanIndexInfo(ProIndexInfo proIndexInfo,
                                                     @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

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

        proIndexInfo.setUpdateUser(userLogin.getId());

        Boolean result = proIndexInfoService.deleteProIndexInfo(proIndexInfo);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 轮播图
     *
     * @param siteId
     * @return
     */
    @ApiOperation(value = "轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片url,sort:排序")
    @RequestMapping(value = "index/getProIndexSlidePic", method = RequestMethod.POST)
    public Response<ProIndexSlidePic> getProIndexSlidePic(@ApiParam("网站Id") @RequestParam Integer siteId,
                                                          @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

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

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();

        //状态   1-前后台显示    2-前台不显示      0-前后台都不显示
        list.add(1);
        list.add(2);

        List<ProIndexSlidePic> proIndexSlidePic = proIndexSlidePicService.getProIndexSlidePic(siteId, list);

        if (StringUtils.isEmpty(proIndexSlidePic)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(proIndexSlidePic);
    }

    /**
     * 新增/修改  轮播图
     *
     * @param proIndexSlidePic
     * @return
     */
    @ApiOperation(value = "新增/修改 轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片,sort:排序")
    @RequestMapping(value = "insertOrUpdateProIndexSlidePic", method = RequestMethod.POST)
    public Response<ProIndexSlidePic> insertOrUpdateProIndexSlidePic(ProIndexSlidePic proIndexSlidePic,
                                                                     @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

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

        if (StringUtils.isEmpty(proIndexSlidePic.getId())) {
            proIndexSlidePic.setCreateUser(userLogin.getId());
        }

        proIndexSlidePic.setUpdateUser(userLogin.getId());
        Boolean result = proIndexSlidePicService.insertOrUpdateProIndexSlidePic(proIndexSlidePic);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 逻辑删除  轮播图
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片url,sort:排序")
    @RequestMapping(value = "deleteProIndexSlidePic", method = RequestMethod.POST)
    public Response<ProIndexSlidePic> deleteProIndexSlidePic(@ApiParam("主键") @RequestParam Integer id,
                                                             @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

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

        Boolean result = proIndexSlidePicService.deleteProIndexSlidePic(id, userLogin.getId());

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    @ApiOperation(value = "最新发布", notes = "id-主键,userId-个人Id,title-文章标题,newsFaceUrl-文章封面URL,content-文章内容,status-状态(0-删除,1-正常,2-草稿),sysStatus-系统管理员操作状态(1-展示,2-不展示)")
    @RequestMapping(value = "getAllUserNewsInfoPage", method = RequestMethod.GET)
    public Response<AllUserNewsInfo> getAllUserNewsInfoPage(@ApiParam("主键") @RequestParam("siteId") Integer siteId,
                                                            @ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {

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

        ProSysSite proSysSite = proSysSiteService.getProSysSite(siteId);

        if (StringUtils.isEmpty(proSysSite)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        Map map = new HashMap(16);
        map.put("pro_code", proSysSite.getRegionCode());
        map.put("status", 1);
        map.put("sys_status", 1);

        Page page = new Page();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<AllUserNewsInfo> mapPage = new Page<>(page.getCurrent(), page.getSize());

        Page<AllUserNewsInfo> userNewsInfoPage = allUserNewsInfoService.getAllUserNewsInfoList(mapPage, map);

        if (StringUtils.isEmpty(userNewsInfoPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(userNewsInfoPage);
    }

    @ApiOperation(value = "修改  最新发布(是否显示)", notes = "id-主键,userId-个人Id,title-文章标题,newsFaceUrl-文章封面URL,content-文章内容,status-状态(0-删除,1-正常,2-草稿),sysStatus-系统管理员操作状态(1-展示,2-不展示)")
    @RequestMapping(value = "updateAllUserNewsInfo", method = RequestMethod.GET)
    public Response<AllUserNewsInfo> updateAllUserNewsInfo(@ApiParam("主键") @RequestParam("id") Integer id,
                                                           @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

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

        AllUserNewsInfo allUserNewsInfo = new AllUserNewsInfo();
        allUserNewsInfo.setId(id);
        allUserNewsInfo.setSysStatus(2);
        allUserNewsInfo.setUpdateTime(DateUtil.getCurrentTimeStamp());
        allUserNewsInfo.setUpdateUser(userLogin.getId());

        AllUserNewsInfo userNewsInfo = allUserNewsInfoService.insertOrUpdateAllUserNewsInfo(allUserNewsInfo);

        if (StringUtils.isEmpty(userNewsInfo)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "修改失败");
        } else {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        }

    }
}
