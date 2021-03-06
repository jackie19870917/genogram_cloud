package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ChiNewsFamilyRecord;
import com.genogram.service.IAllCheckOutService;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IChiNewsFamilyRecordService;
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
 * 省级-记录家族-家族动态,家族通告文章表 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@CrossOrigin(origins = "*")
@Api(description = "全国记录家族文章")
@RestController
@RequestMapping("/genogram/chiNewsFamilyRecord")
public class ChiNewsFamilyRecordController {

    @Autowired
    private IAllCheckOutService allCheckOutService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Autowired
    private IChiNewsFamilyRecordService chiNewsFamilyRecordService;


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
     * 全国记录家族文章新增
     *
     * @Author: yuzhou
     * @Date: 2019-02-18
     * @Time: 11:32
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "全国记录家族文章新增 修改", notes = "")
    @RequestMapping(value = "/addOrUpdateRecord", method = RequestMethod.POST)
    public Response<ChiNewsFamilyRecord> addOrUpdateRecord(@ApiParam(value = "省级字派实体类") ChiNewsFamilyRecord chiNewsFamilyRecord,
                                                           @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        // 校验敏感词汇
        Set set = allCheckOutService.getSensitiveWord(chiNewsFamilyRecord.getNewsText());

        if (set != null && set.size() >= 1) {
            return ResponseUtils.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set);
        }

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
        chiNewsFamilyRecord.setStatus(status);
        Boolean aBoolean = chiNewsFamilyRecordService.addOrUpdateRecord(chiNewsFamilyRecord, userLogin);
        if (!aBoolean) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "失败");
        }

        return ResponseUtils.error(Constants.SUCCESSFUL_CODE, "成功");
    }

    /**
     * 全国记录家族文章删除
     *
     * @Author: yuzhou
     * @Date: 2019-02-19
     * @Time: 16:30
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "全国记录家族文章删除", notes = "")
    @RequestMapping(value = "/deleteRecord", method = RequestMethod.POST)
    public Response<ChiNewsFamilyRecord> deleteRecord(@ApiParam("主键") @RequestParam(value = "id") Integer id,
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

        if (id == null) {
            return ResponseUtils.error(Constants.IS_EMPTY, "请输入正确参数");
        }

        //状态(0:删除;1:已发布;2:草稿3:不显示)
        int status = 0;
        Boolean aBoolean = chiNewsFamilyRecordService.deleteRecord(id, userLogin, status);
        if (!aBoolean) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "失败");
        }

        return ResponseUtils.error(Constants.SUCCESSFUL_CODE, "成功");
    }

    /**
     * 全国记录家族文章查询
     *
     * @Author: yuzhou
     * @Date: 2019-02-19
     * @Time: 16:35
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "全国记录家族文章查询", notes = "")
    @RequestMapping(value = "/getRecordPage", method = RequestMethod.POST)
    public Response<ChiNewsFamilyRecord> getRecordPage(@ApiParam("显示位置的Id") @RequestParam(value = "showId") Integer showId,
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
        Wrapper<ChiNewsFamilyRecord> entity = new EntityWrapper<ChiNewsFamilyRecord>();
        entity.eq("show_id", showId);
        if (statusList.size() != 0) {
            entity.in("status", statusList);
        }
        entity.orderBy("update_time", false);
        Page<ChiNewsFamilyRecord> chiNewsFamilyRecord = chiNewsFamilyRecordService.getRecordPage(entity, pageNo, pageSize);
        if (StringsUtils.isEmpty(chiNewsFamilyRecord)) {
            return ResponseUtils.error(Constants.ERRO_CODE, "fanNewsCultureZipai为空");
        }
        return ResponseUtils.success(chiNewsFamilyRecord);
    }

    /**
     *全国记录家族文章详情
     *@Author: yuzhou
     *@Date: 2019-02-20
     *@Time: 16:46
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "全国记录家族文章详情", notes ="")
    @RequestMapping(value = "/getFamilyRecordDetail", method = RequestMethod.GET)
    public Response<ChiNewsFamilyRecord> getFamilyRecordDetail(
            @ApiParam(value = "主键ID") @RequestParam(value = "id") Integer id,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
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
        //判断id是否为空
        if (id == null) {
            return ResponseUtils.error(Constants.IS_EMPTY, null);
        }
        ChiNewsFamilyRecord chiNewsFamilyRecord = chiNewsFamilyRecordService.getFamilyRecordDetail(id);
        if(StringsUtils.isEmpty(chiNewsFamilyRecord)){
            return ResponseUtils.error(Constants.ERRO_CODE,"没有查到数据");
        }
        return ResponseUtils.success(chiNewsFamilyRecord);
    }

}

