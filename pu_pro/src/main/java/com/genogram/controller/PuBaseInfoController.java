package com.genogram.controller;


import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.PuBaseInfo;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IPuBaseInfoService;
import com.genogram.service.IUserService;
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

    @ApiOperation(value = "创建谱基本信息", notes = "puBaseInfo-谱实体")
    @RequestMapping(value = "addPuBaseInfo", method = RequestMethod.POST)
    public Response<Boolean> addPuBaseInfo(@RequestBody PuBaseInfo puBaseInfo,
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
        if (StringUtils.isEmpty(puBaseInfo)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "puBaseInfo为空");
        }
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        int status = 1;
        puBaseInfo.setStatus(status);
        return ResponseUtlis.success(puBaseInfoService.insertOrUpdate(puBaseInfo));
    }


    @ApiOperation(value = "删除谱书", notes = "")
    @RequestMapping(value = "deletePuBaseInfoById", method = RequestMethod.GET)
    public Response<Boolean> deletePuBaseInfoById(@ApiParam(value = "主键ID") @RequestParam(value = "id") Integer id,
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
        //判断主键是否为空
        if (id == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        int status = 0;
        Boolean aBoolean = puBaseInfoService.deletePuBaseInfoById(id, status, userLogin);
        if (aBoolean == null || !aBoolean) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }
        return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
    }
}

