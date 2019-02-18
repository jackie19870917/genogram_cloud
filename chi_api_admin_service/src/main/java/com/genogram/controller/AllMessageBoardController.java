package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllMessageBoard;
import com.genogram.entity.AllUserLogin;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IProMessageBoardServices;
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
 * @author Administrator
 */
@Api(description = "省级留言板查询")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/allMessageBoard")
public class AllMessageBoardController {
    @Autowired
    private IProMessageBoardServices iProMessageBoardServices;

    @Autowired
    private IUserService userService;

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

    @ApiOperation(value = "留言板查询")
    @RequestMapping(value = "/selectMessage", method = RequestMethod.GET)
    public Response<AllMessageBoard> getMessageBoardDetail(
            @RequestParam(value = "site_id") Integer siteId,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token,
            @RequestParam(value = "source_type") Integer sourceType,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
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

        Page<AllMessageBoard> allMessageBoardPage = iProMessageBoardServices.getMessageBoard(siteId, sourceType, pageNo, pageSize);
        return ResponseUtils.success(allMessageBoardPage);
    }
}
