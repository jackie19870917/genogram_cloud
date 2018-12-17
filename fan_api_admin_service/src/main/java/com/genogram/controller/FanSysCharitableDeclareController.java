package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.*;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.ProFamilyPersonVo;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IFanSysCharitableDeclareService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import com.genogram.unit.StringsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiaohei
 * @since 2018-11-29
 */
@Api(description = "联谊会后台家族慈善申报")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanSysCharitableDeclare")
public class FanSysCharitableDeclareController {
    @Autowired
    private IFanSysCharitableDeclareService fanSysCharitableDeclareService;

    @Autowired
    private IUserService userService;

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
     * 联谊会慈善帮扶申报查询
     *
     * @Author: yuzhou
     * @Date: 2018-12-12
     * @Time: 15:32
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会慈善帮扶申报查询", notes = "show_id:网站id")
    @RequestMapping(value = "getSysCharitableDeclare", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> getFamilyStructureList(
            @ApiParam("显示位置Id") @RequestParam(value = "showId") Integer showId,
            @ApiParam("当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam("每页信息条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token) {
        try {
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
            //判断id是否为空
            if (showId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "请输入showId");
            }
            Wrapper<FanSysCharitableDeclare> entity = new EntityWrapper<FanSysCharitableDeclare>();
            entity.eq("show_id", showId);
            Page<FanSysCharitableDeclare> fanSysCharitableDeclarePage = fanSysCharitableDeclareService.getCharitableDeclarePage(entity, pageNo, pageSize);
            if (fanSysCharitableDeclarePage == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "查询失败");
            }
            return ResponseUtlis.success(fanSysCharitableDeclarePage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会慈善帮扶申报删除
     *
     * @Author: yuzhou
     * @Date: 2018-12-12
     * @Time: 15:32
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会慈善帮扶申报删除", notes = "")
    @RequestMapping(value = "deleteSysCharitableDeclare", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> deleteRecordById(
            @ApiParam("主键Id") @RequestParam(value = "id") Integer id,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
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
            //判断id是否为空
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "请输入Id");
            }
            Boolean aBoolean = fanSysCharitableDeclareService.deleteCharitableDeclareById(id);
            if (!aBoolean) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "删除失败");
            }
            if(aBoolean==null){
                return ResponseUtlis.error(Constants.ERRO_CODE,"您好,数据不能删除");
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会慈善帮扶申报详情
     *
     * @Author: yuzhou
     * @Date: 2018-12-12
     * @Time: 16:13
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会慈善帮扶申报详情", notes = "")
    @RequestMapping(value = "getFamilyStructureDetails", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> getFamilyStructureDetails(
            @ApiParam("主键Id") @RequestParam(value = "id") Integer id,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
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
            //判断id是否为空
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "请输入Id");
            }
            FanSysCharitableDeclare fanSysCharitableDeclare = fanSysCharitableDeclareService.getFamilyStructureDetails(id);
            if (fanSysCharitableDeclare == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "查询失败");
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会慈善帮扶申报详情帮扶反馈添加
     *
     * @Author: yuzhou
     * @Date: 2018-12-12
     * @Time: 17:52
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会慈善帮扶申报详情帮扶反馈添加", notes = "")
    @RequestMapping(value = "addCharityAssistFeedback", method = RequestMethod.POST)
    public Response<FanSysCharitableDeclare> addCharityAssistFeedback(
            @ApiParam("主键Id") @RequestParam(value = "id") Integer id,
            @ApiParam("帮助反馈") @RequestParam(value = "helpFeedback", required = false) String helpFeedback,
            @ApiParam("上传帮助反馈的图片") @RequestParam(value = "helpFeedbackFile", required = false) String helpFeedbackFile,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
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
            //判断id是否为空
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "请输入Id");
            }
            Boolean aBoolean = fanSysCharitableDeclareService.addCharityAssistFeedback(id, helpFeedback, helpFeedbackFile, userLogin);
            if (!aBoolean) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "新增失败");
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, "新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }


    /**
     * 联谊会慈善帮扶申报添加或修改  申报
     *
     * @Author: yuzhou
     * @Date: 2018-12-12
     * @Time: 13:37
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会慈善帮扶申报添加或修改 申报", notes = "")
    @RequestMapping(value = "addCharityAssist", method = RequestMethod.POST)
    public Response<FanSysCharitableDeclare> addCharityAssist(
            @ApiParam(value = "慈善帮扶申报表") FanSysCharitableDeclare fanSysCharitableDeclare,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
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
            //添加创建人或者修改人Id
            if (fanSysCharitableDeclare.getId() == null) {
                //创建人
                fanSysCharitableDeclare.setCreateUser(userLogin.getId());
            }
            //修改人
            fanSysCharitableDeclare.setUpdateUser(userLogin.getId());
            //状态(0:审核通过;1:审核中;2:草稿3:审核不通过)
            fanSysCharitableDeclare.setStatus(1);
            Boolean aBoolean = fanSysCharitableDeclareService.addCharityAssist(fanSysCharitableDeclare);
            if (!aBoolean) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "新增失败");
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, "新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会慈善帮扶申报添加或修改  草稿
     *
     * @Author: yuzhou
     * @Date: 2018-12-12
     * @Time: 13:37
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会慈善帮扶申报添加或修改 草稿", notes = "")
    @RequestMapping(value = "draftCharityAssist", method = RequestMethod.POST)
    public Response<FanSysCharitableDeclare> draftCharityAssist(
            @ApiParam(value = "慈善帮扶申报表") FanSysCharitableDeclare fanSysCharitableDeclare,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
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
            //添加创建人或者修改人Id
            if (fanSysCharitableDeclare.getId() == null) {
                //创建人
                fanSysCharitableDeclare.setCreateUser(userLogin.getId());
            }
            //修改人
            fanSysCharitableDeclare.setUpdateUser(userLogin.getId());
            //状态(0:审核通过;1:审核中;2:草稿3:审核不通过)
            fanSysCharitableDeclare.setStatus(2);
            Boolean aBoolean = fanSysCharitableDeclareService.addCharityAssist(fanSysCharitableDeclare);
            if (!aBoolean) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "修改失败");
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }


}

