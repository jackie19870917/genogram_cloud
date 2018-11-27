package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanIndexFamilySummarys;
import com.genogram.entity.FanIndexInfo;
import com.genogram.entity.FanIndexSlidePic;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.service.*;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    /**
     * 轮播图
     *
     * @param siteId 网站ID
     * @return
     */
    @ApiOperation(value = "轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片url,sort:排序")
    @RequestMapping(value = "getFanIndexSlidePicList", method = RequestMethod.GET)
    public Response<FanIndexSlidePic> getFanIndexSlidePicList(@ApiParam("网站Id") @RequestParam Integer siteId,
                                                              @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        //状态   1-前后台显示    2-前台不显示      0-前后台都不显示
        list.add(1);
        list.add(2);

        /*//根据token获取用户对象
        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        //根据用户对象获取用户信息
        AllUserLogin login = allUserLoginService.getAllUserLoginById(userLogin.getId());

        if (login.getRole() != 1 && !login.getSiteId().equals(siteId)) {
            return ResponseUtlis.error(403, "您没有权限访问");
        }*/

        List<FanIndexSlidePic> fanIndexSlidePicList = fanIndexSlidePicService.getFanIndexSlidePicListBySiteId(siteId, list);

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
                                                                     @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(fanIndexSlidePic.getId())) {
            fanIndexSlidePic.setCreateUser(userLogin.getId());
        }

        fanIndexSlidePic.setUpdateUser(userLogin.getId());

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
    @RequestMapping(value = "deleteFanIndexSlidePic", method = RequestMethod.GET)
    public Response<FanIndexSlidePic> deleteFanIndexSlidePic(@ApiParam("主键") @RequestParam Integer id,
                                                             @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        //o8likl
        Integer userId = userService.getUserLoginInfoByToken(token).getId();

        Boolean result = fanIndexSlidePicService.deleteFanIndexSlidePic(id, userId);

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
    @RequestMapping(value = "getFanIndexInfo", method = RequestMethod.GET)
    public Response<IndexInfoVo> getFanIndexInfo(@ApiParam("网站Id") @RequestParam Integer siteId,
                                                 @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        IndexInfoVo indexInfoVo = fanIndexInfoService.getFanIndexInfoVo(siteId);

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

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

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

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);
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
    @RequestMapping(value = "getFanIndexFamilySummarysPage", method = RequestMethod.GET)
    public Response<FanIndexFamilySummarys> getFanIndexFamilySummarysPage(@ApiParam("网站Id") @RequestParam Integer siteId,
                                                                          @ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                                          @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        //  1-正常    2-草稿
        list.add(1);
        list.add(2);

        Page<FanIndexFamilySummarys> fanIndexFamilySummarysPage = fanIndexFamilySummarysService.getFanIndexFamilySummarysPage(siteId, list, pageNo, pageSize);

        return ResponseUtlis.success(fanIndexFamilySummarysPage);
    }

    /**
     * 联谊堂详情
     *
     * @param id 主键
     * @return
     */
    @ApiOperation(value = "联谊堂详情", notes = "id:主键,siteId:网站Id,rootGroup:堂号,rootPerson:始迁祖,leader:负责人,leaderPhone:负责人电话,worshipNum:膜拜,praiseNum:赞")
    @RequestMapping(value = "getFanIndexFamilySummarys", method = RequestMethod.GET)
    public Response<FanIndexFamilySummarys> getFanIndexFamilySummarys(@ApiParam("主键") @RequestParam Integer id,
                                                                      @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
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

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        //状态   1-正常  2-草稿
        fanIndexFamilySummarys.setStatus(1);

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

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

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
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
    @RequestMapping(value = "deleteFanIndexFamilySummarys", method = RequestMethod.GET)
    public Response<FanIndexFamilySummarys> deleteFanIndexFamilySummarys(@ApiParam("主键") @RequestParam Integer id,
                                                                         @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }

        //用户Id
        Integer userId = userService.getUserLoginInfoByToken(token).getId();

        Boolean result = fanIndexFamilySummarysService.deleteFanIndexFamilySummarys(id, userId);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

}
