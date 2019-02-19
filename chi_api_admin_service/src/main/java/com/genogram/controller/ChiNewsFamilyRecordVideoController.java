package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ChiNewsFamilyRecordVideo;
import com.genogram.service.IAllCheckOutService;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IChiNewsFamilyRecordVideoService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtils;
import com.genogram.unit.StringsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 省级记录家族视频上传-视频概要 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@CrossOrigin(origins = "*")
@Api(description = "全国记录家族视频")
@RestController
@RequestMapping("/genogram/chiNewsFamilyRecordVideo")
public class ChiNewsFamilyRecordVideoController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IChiNewsFamilyRecordVideoService chiNewsFamilyRecordVideoService;

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
     * 全国记录家族视频新增
     *
     * @Author: yuzhou
     * @Date: 2019-02-19
     * @Time: 9:34
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "全国记录家族视频新增 修改", notes = "")
    @RequestMapping(value = "/addOrUpdateRecordVideo", method = RequestMethod.POST)
    public Response<ChiNewsFamilyRecordVideo> addOrUpdateRecordVideo(@ApiParam(value = "省级字派实体类") ChiNewsFamilyRecordVideo chiNewsFamilyRecordVideo,
                                                                     @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringsUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringsUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        //状态(0:删除;1:已发布;2:草稿3:不显示)
        int status = 1;
        chiNewsFamilyRecordVideo.setStatus(status);
        Boolean aBoolean = chiNewsFamilyRecordVideoService.addOrUpdateRecordVideo(chiNewsFamilyRecordVideo, userLogin);
        if (!aBoolean) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "失败");
        }

        return ResponseUtils.error(Constants.SUCCESSFUL_CODE, "成功");
    }

    /**
     *全国记录家族视频删除
     *@Author: yuzhou
     *@Date: 2019-02-19
     *@Time: 16:43
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "全国记录家族视频删除", notes = "")
    @RequestMapping(value = "/deleteRecordVideo", method = RequestMethod.POST)
    public Response<ChiNewsFamilyRecordVideo> deleteRecordVideo(@ApiParam(value = "主键") Integer id,
                                                                @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringsUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringsUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        //判断ID是否为空
        if (id == null) {
            return ResponseUtils.error(Constants.ERRO_CODE, "id为空");
        }
        //状态(0:删除;1:已发布;2:不显示)
        int status = 0;
        Boolean aBoolean = chiNewsFamilyRecordVideoService.deleteOrigin(id, status, userLogin);
        if (!aBoolean) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "删除失败");
        }

        return ResponseUtils.error(Constants.SUCCESSFUL_CODE, "删除成功");
    }

    /**
     *全国记录家族视频查询
     *@Author: yuzhou
     *@Date: 2019-02-19
     *@Time: 17:05
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "全国记录家族视频查询", notes = "")
    @RequestMapping(value = "/getRecordVideoPage", method = RequestMethod.POST)
    public Response<ChiNewsFamilyRecordVideo> getRecordVideoPage(@ApiParam("显示位置的Id") @RequestParam(value = "showId") Integer showId,
                                                       @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                       @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
                                                       @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringsUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringsUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        if (showId == null) {
            return ResponseUtils.error(Constants.IS_EMPTY, "请输入正确参数");
        }

        //状态(0:删除;1:已发布;2:不显示)
        List statusList = new ArrayList();
        statusList.add(1);

        //查询条件
        Wrapper<ChiNewsFamilyRecordVideo> entity = new EntityWrapper<ChiNewsFamilyRecordVideo>();
        entity.eq("show_id", showId);
        if (statusList.size() != 0) {
            entity.in("status", statusList);
        }
        entity.orderBy("update_time", false);
        Page<ChiNewsFamilyRecordVideo> chiNewsFamilyRecord = chiNewsFamilyRecordVideoService.getRecordVideoPage(entity,pageNo,pageSize);
        if (StringsUtils.isEmpty(chiNewsFamilyRecord)) {
            return ResponseUtils.error(Constants.ERRO_CODE, "fanNewsCultureZipai为空");
        }
        return ResponseUtils.success(chiNewsFamilyRecord);
    }

}

