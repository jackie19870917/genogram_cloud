package com.genogram.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.PuBaseInfo;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IPuBaseInfoService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yizx
 * @since 2019-01-16
 */

@Api(description = "修谱controller")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/puBaseInfo")
public class PuBaseInfoController {

    @Autowired
    IPuBaseInfoService puBaseInfoService;

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

    @ApiOperation(value = "查询谱基本信息", notes = "")
    @RequestMapping(value = "getPuBaseInfoPage", method = RequestMethod.POST)
    public Response<Boolean> getPuBaseInfoPage(@ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                               @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
                                               @ApiParam("token") @RequestParam(value = "token", required = false) String token) {
        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        //状态(0:删除;1:已完成;2:完善中3:不显示)
        List statusList = new ArrayList();
        statusList.add(1);
        statusList.add(2);
        Page<PuBaseInfo> puBaseInfo = puBaseInfoService.getPuBaseInfoPage(statusList, pageNo, pageSize, userLogin);
        if (StringUtils.isEmpty(puBaseInfo)) {
            return ResponseUtils.error(Constants.ERRO_CODE, "没有数据");
        }
        return ResponseUtils.success(puBaseInfo);
    }

    @ApiOperation(value = "创建谱基本信息 修改", notes = "puBaseInfo-谱实体")
    @RequestMapping(value = "addPuBaseInfo", method = RequestMethod.POST)
    public Response<Boolean> addPuBaseInfo(@RequestBody PuBaseInfo puBaseInfo,
                                           @ApiParam("token") @RequestParam(value = "token", required = false) String token) {
        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);
        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        if (StringUtils.isEmpty(puBaseInfo)) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "puBaseInfo为空");
        }
        //状态(0:删除;1:已完成;2:完善中3:不显示)
        int status = 2;
        puBaseInfo.setStatus(status);
        Boolean aBoolean = puBaseInfoService.addPuBaseInfo(puBaseInfo, userLogin);
        if (!aBoolean) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "失败");
        }
        return ResponseUtils.error(Constants.SUCCESSFUL_CODE, "成功");
    }


    @ApiOperation(value = "删除谱书", notes = "")
    @RequestMapping(value = "deletePuBaseInfoById", method = RequestMethod.GET)
    public Response<Boolean> deletePuBaseInfoById(@ApiParam(value = "主键ID") @RequestParam(value = "id") Integer id,
                                                  @ApiParam("token") @RequestParam(value = "token", required = false) String token) {
        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        //判断主键是否为空
        if (id == null) {
            return ResponseUtils.error(Constants.IS_EMPTY, null);
        }
        //状态(0:删除;1:已完成;2:完善中3:不显示)
        int status = 0;
        Boolean aBoolean = puBaseInfoService.deletePuBaseInfoById(id, status, userLogin);
        if (aBoolean == null || !aBoolean) {
            return ResponseUtils.error(Constants.ERRO_CODE, null);
        }
        return ResponseUtils.error(Constants.SUCCESSFUL_CODE, null);
    }
}

