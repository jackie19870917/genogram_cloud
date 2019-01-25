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

import java.util.*;


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
@Api(description = "联谊会首页(后台)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/fanIndex")
public class FanIndexController {

    @Autowired
    private IFanIndexInfoService fanIndexInfoService;

    @Autowired
    private IFanIndexFamilySummarysService fanIndexFamilySummarysService;

    @Autowired
    private IFanIndexSlidePicService fanIndexSlidePicService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserNewsInfoService allUserNewsInfoService;

    @Autowired
    private IFanSysSiteService fanSysSiteService;

    @Autowired
    private IAllCheckOutService allCheckOutService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

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

    /**
     * 轮播图
     *
     * @param siteId 网站ID
     * @return
     */
    @ApiOperation(value = "轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片url,sort:排序")
    @RequestMapping(value = "getFanIndexSlidePicList", method = RequestMethod.POST)
    public Response<FanIndexSlidePic> getFanIndexSlidePicList(@ApiParam("网站Id") @RequestParam Integer siteId,
                                                              @ApiParam("属性属性(1-pc端,2-移动端)") @RequestParam(value = "classes", defaultValue = "1") Integer classes,
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

        List<FanIndexSlidePic> fanIndexSlidePicList = fanIndexSlidePicService.getFanIndexSlidePicListBySiteId(siteId, classes, list);

        if (StringUtils.isEmpty(fanIndexSlidePicList)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(fanIndexSlidePicList);
    }

    /**
     * 新增/修改  轮播图
     *
     * @param fanIndexSlidePic
     * @return
     */
    @ApiOperation(value = "新增/修改 轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片,sort:排序")
    @RequestMapping(value = "insertOrUpdateFanIndexSlidePic", method = RequestMethod.POST)
    public Response<FanIndexSlidePic> insertOrUpdateFanIndexSlidePic(FanIndexSlidePic fanIndexSlidePic,
                                                                     @ApiParam("属性属性(1-pc端,2-移动端)") @RequestParam(value = "classes", defaultValue = "1") Integer classes,
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

        if (StringUtils.isEmpty(fanIndexSlidePic.getId())) {
            fanIndexSlidePic.setCreateUser(userLogin.getId());
        }

        fanIndexSlidePic.setUpdateUser(userLogin.getId());
        fanIndexSlidePic.setClasses(classes);

        Boolean result = fanIndexSlidePicService.insertOrUpdateFanIndexSlidePic(fanIndexSlidePic);

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
    @RequestMapping(value = "deleteFanIndexSlidePic", method = RequestMethod.POST)
    public Response<FanIndexSlidePic> deleteFanIndexSlidePic(@ApiParam("主键") @RequestParam Integer id,
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

        Boolean result = fanIndexSlidePicService.deleteFanIndexSlidePic(id, userLogin.getId());

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会简介,宣言
     *
     * @param siteId 网站ID
     * @return
     */
    @ApiOperation(value = "基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名称,regionCode;地区编号,totemPicSrc:图腾,title:宣言,description;简介")
    @RequestMapping(value = "getFanIndexInfo", method = RequestMethod.POST)
    public Response<IndexInfoVo> getFanIndexInfo(@ApiParam("网站Id") @RequestParam Integer siteId,
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

        IndexInfoVo indexInfoVo = fanIndexInfoService.getFanIndexInfoVo(siteId);

        if (StringUtils.isEmpty(indexInfoVo)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(indexInfoVo);
    }

    /**
     * 新增或修改    联谊会简介,宣言
     *
     * @param indexInfoVo 实体类
     * @return
     */
    @ApiOperation(value = "新增/修改基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名字,totemPicSrc:图腾,title:宣言,description:简介")
    @RequestMapping(value = "insertOrUpdateFanIndexInfo", method = RequestMethod.POST)
    public Response<FanIndexInfo> insertOrUpdateFanIndexInfo(IndexInfoVo indexInfoVo,
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

        // 校验敏感词汇
        Set set = allCheckOutService.getSensitiveWord(indexInfoVo.getDescription());

        if (set != null && set.size() >= 1) {
            return ResponseUtlis.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set);
        }

        if (StringUtils.isEmpty(indexInfoVo.getId())) {
            indexInfoVo.setCreateUser(userLogin.getId());
        }

        indexInfoVo.setUpdateUser(userLogin.getId());
        Boolean result = fanIndexInfoService.insertOrUpdateIndexInfoVo(indexInfoVo);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 删除   联谊会简介,宣言
     *
     * @param fanIndexInfo
     * @return
     */
    @ApiOperation(value = "删除基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名字,totemPicSrc:图腾,title:宣言,description:简介")
    @RequestMapping(value = "deleteFanIndexInfo", method = RequestMethod.POST)
    public Response<FanIndexInfo> deleteFanIndexInfo(FanIndexInfo fanIndexInfo,
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

        fanIndexInfo.setUpdateUser(userLogin.getId());

        Boolean result = fanIndexInfoService.deleteFanIndexInfo(fanIndexInfo);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊堂
     *
     * @param siteId   网站ID
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */
    @ApiOperation(value = "联谊堂", notes = "id:主键,siteId:网站Id,rootGroup:堂号,rootPerson:始迁祖,leader:负责人,leaderPhone:负责人电话,worshipNum:膜拜,praiseNum:赞")
    @RequestMapping(value = "getFanIndexFamilySummarysPage", method = RequestMethod.POST)
    public Response<FanIndexFamilySummarys> getFanIndexFamilySummarysPage(@ApiParam("网站Id") @RequestParam Integer siteId,
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

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        //  1-正常    2-草稿
        list.add(1);
        list.add(2);

        Page<FanIndexFamilySummarys> fanIndexFamilySummarysPage = fanIndexFamilySummarysService.getFanIndexFamilySummarysPage(siteId, list, pageNo, pageSize);

        if (StringUtils.isEmpty(fanIndexFamilySummarysPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(fanIndexFamilySummarysPage);
    }

    /**
     * 联谊堂详情
     *
     * @param id 主键
     * @return
     */
    @ApiOperation(value = "联谊堂详情", notes = "id:主键,siteId:网站Id,rootGroup:堂号,rootPerson:始迁祖,leader:负责人,leaderPhone:负责人电话,worshipNum:膜拜,praiseNum:赞")
    @RequestMapping(value = "getFanIndexFamilySummarys", method = RequestMethod.POST)
    public Response<FanIndexFamilySummarys> getFanIndexFamilySummarys(@ApiParam("主键") @RequestParam Integer id,
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

        FanIndexFamilySummarys fanIndexFamilySummarys = fanIndexFamilySummarysService.getFanIndexFamilySummarys(id);

        return ResponseUtlis.success(fanIndexFamilySummarys);
    }

    /**
     * 新增或修改    联谊堂
     *
     * @param fanIndexFamilySummarys
     * @return
     */
    @ApiOperation(value = "新增或修改    联谊堂", notes = "id:主键,siteId:网站Id,rootGroup:堂号,rootPerson:始迁祖,leader:负责人,leaderPhone:负责人电话,worshipNum:膜拜,praiseNum:赞")
    @RequestMapping(value = "insertOrUpdateFanIndexFamilySummarys", method = RequestMethod.POST)
    public Response<FanIndexFamilySummarys> insertOrUpdateFanIndexFamilySummarys(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                                                 FanIndexFamilySummarys fanIndexFamilySummarys) {

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

        //状态   1-正常  2-草稿
        fanIndexFamilySummarys.setStatus(1);

        if (StringUtils.isEmpty(fanIndexFamilySummarys.getId())) {
            fanIndexFamilySummarys.setCreateUser(userLogin.getId());
        }

        fanIndexFamilySummarys.setUpdateUser(userLogin.getId());

        Boolean result = fanIndexFamilySummarysService.insertOrUpdateFanIndexFamilySummarys(fanIndexFamilySummarys);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊堂 草稿
     *
     * @param fanIndexFamilySummarys
     * @return
     */
    @ApiOperation(value = "联谊堂 草稿    联谊堂", notes = "id:主键,siteId:网站Id,rootGroup:堂号,rootPerson:始迁祖,leader:负责人,leaderPhone:负责人电话,worshipNum:膜拜,praiseNum:赞")
    @RequestMapping(value = "insertOrUpdateFanIndexFamilySummarysDrft", method = RequestMethod.POST)
    public Response<FanIndexFamilySummarys> insertOrUpdateFanIndexFamilySummarysDrft(FanIndexFamilySummarys fanIndexFamilySummarys,
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

        //状态   1-正常  2-草稿
        fanIndexFamilySummarys.setStatus(2);
        Boolean result = fanIndexFamilySummarysService.insertOrUpdateFanIndexFamilySummarys(fanIndexFamilySummarys);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 逻辑删除 联谊堂
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除 联谊堂 ", notes = "id:主键,siteId:网站Id,rootGroup:堂号,rootPerson:始迁祖,leader:负责人,leaderPhone:负责人电话,worshipNum:膜拜,praiseNum:赞")
    @RequestMapping(value = "deleteFanIndexFamilySummarys", method = RequestMethod.POST)
    public Response<FanIndexFamilySummarys> deleteFanIndexFamilySummarys(@ApiParam("主键") @RequestParam Integer id,
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

        //用户Id
        Integer userId = userLogin.getId();

        Boolean result = fanIndexFamilySummarysService.deleteFanIndexFamilySummarys(id, userId);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    @ApiOperation(value = "最新发布", notes = "id-主键,userId-个人Id,title-文章标题,newsFaceUrl-文章封面URL,content-文章内容,status-状态(0-删除,1-正常,2-草稿),sysStatus-系统管理员操作状态(1-展示,2-不展示)")
    @RequestMapping(value = "getAllUserNewsInfoPage", method = RequestMethod.POST)
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

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        FanSysSite fanSysSite = fanSysSiteService.getFanSysSite(siteId);

        if (StringUtils.isEmpty(fanSysSite)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        Map map = new HashMap(16);
        map.put("region_id", fanSysSite.getRegionCode());
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
