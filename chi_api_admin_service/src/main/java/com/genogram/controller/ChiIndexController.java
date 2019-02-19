package com.genogram.controller;


import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ChiIndexInfo;
import com.genogram.entity.ChiIndexSlidePic;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.service.*;
import com.genogram.unit.DateUtil;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 全国-首页--后台 后端控制器
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@Api(description = "全国首页（后台）")
@RestController
@CrossOrigin
@RequestMapping("/genogram/admin/chiIndex")
public class ChiIndexController {

    @Autowired
    private IChiIndexSlidePicService chiIndexSlidePicService;

    @Autowired
    private IChiIndexInfoService chiIndexInfoService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IAllCheckOutService allCheckOutService;

    private List getList() {

        List list = new ArrayList();
        /**
         * 角色权限 (0.不是管理员,1.县级管理员,2省级管理员,3.全国管理员,4县级副管理员,5省级副管理员,6全国副管理员,9.超级管理员)
         */
        list.add(3);
        list.add(6);
        list.add(9);

        return list;
    }

    /**
     * 轮播图
     *
     * @param siteId
     * @return
     */
    @ApiOperation(value = "轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片url,sort:排序")
    @RequestMapping(value = "index/getChiIndexSlidePic", method = RequestMethod.POST)
    public Response<ChiIndexSlidePic> getChiIndexSlidePic(@ApiParam("网站ID") @RequestParam Integer siteId,
                                                          @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        if (siteId == null) {
            return ResponseUtils.error(Constants.IS_EMPTY, null);
        }

        List<ChiIndexSlidePic> chiIndexSlidePicList = chiIndexSlidePicService.getChiIndexSlidePicBySiteId(siteId, 1);

        if (StringUtils.isEmpty(chiIndexSlidePicList)) {
            return ResponseUtils.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtils.success(chiIndexSlidePicList);
    }

    /**
     * 新增/修改  轮播图
     *
     * @param chiIndexSlidePic
     * @return
     */
    @ApiOperation(value = "新增/修改 轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片,sort:排序")
    @RequestMapping(value = "insertOrUpdateChiIndexSlidePic", method = RequestMethod.POST)
    public Response<ChiIndexSlidePic> insertOrUpdateChiIndexSlidePic(ChiIndexSlidePic chiIndexSlidePic,
                                                                     @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        Timestamp timestamp = DateUtil.getCurrentTimeStamp();

        if (StringUtils.isEmpty(chiIndexSlidePic.getId())) {
            chiIndexSlidePic.setCreateUser(userLogin.getId());
            chiIndexSlidePic.setCreateTime(timestamp);
            chiIndexSlidePic.setStatus(1);
        }

        chiIndexSlidePic.setUpdateUser(userLogin.getId());
        chiIndexSlidePic.setUpdateTime(timestamp);

        Boolean result = chiIndexSlidePicService.insertOrUpdateChiIndexSlidePic(chiIndexSlidePic);

        if (result) {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 逻辑删除  轮播图
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片url,sort:排序")
    @RequestMapping(value = "deleteChiIndexSlidePic", method = RequestMethod.POST)
    public Response<ChiIndexSlidePic> deleteChiIndexSlidePic(@ApiParam("主键") @RequestParam Integer id,
                                                             @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        Boolean result = chiIndexSlidePicService.chiIndexSlidePic(id, userLogin.getId());

        if (result) {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 全国基本信息
     *
     * @param siteId
     * @return
     */
    @ApiOperation(value = "基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名称,regionCode;地区编号,totemPicSrc:图腾,title:宣言,description;简介")
    @RequestMapping(value = "index/getChiIndexInfo", method = RequestMethod.POST)
    public Response<IndexInfoVo> getChiIndexInfo(@ApiParam("网站ID") @RequestParam Integer siteId,
                                                 @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        if (siteId == null) {
            return ResponseUtils.error(Constants.IS_EMPTY, null);
        }

        IndexInfoVo indexInfoVo = chiIndexInfoService.getIndexInfoVoBySiteId(siteId);

        if (StringUtils.isEmpty(indexInfoVo)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtils.success(indexInfoVo);
    }

    /**
     * 新增或修改    全国简介,宣言
     *
     * @param indexInfoVo 实体类
     * @return
     */
    @ApiOperation(value = "新增/修改基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名字,totemPicSrc:图腾,title:宣言,description:简介")
    @RequestMapping(value = "insertOrUpdateChiIndexInfo", method = RequestMethod.POST)
    public Response<ChiIndexInfo> insertOrUpdateChiIndexInfo(IndexInfoVo indexInfoVo,
                                                             @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        // 校验敏感词汇
        Set set = allCheckOutService.getSensitiveWord(indexInfoVo.getDescription());

        if (set != null && set.size() >= 1) {
            return ResponseUtils.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set);
        }

        if (StringUtils.isEmpty(indexInfoVo.getId())) {
            indexInfoVo.setCreateUser(userLogin.getId());
        }

        indexInfoVo.setUpdateUser(userLogin.getId());
        Boolean result = chiIndexInfoService.insertOrUpdateIndexInfoVo(indexInfoVo);

        if (result) {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 删除   联谊会简介,宣言
     *
     * @param chiIndexInfo
     * @return
     */
    @ApiOperation(value = "删除基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名字,totemPicSrc:图腾,title:宣言,description:简介")
    @RequestMapping(value = "deleteChiIndexInfo", method = RequestMethod.POST)
    public Response<ChiIndexInfo> deleteChiIndexInfo(ChiIndexInfo chiIndexInfo,
                                                     @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        chiIndexInfo.setUpdateUser(userLogin.getId());

        Boolean result = chiIndexInfoService.deleteChiIndexInfo(chiIndexInfo);

        if (result) {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }
}

