package com.genogram.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.*;
import com.genogram.entityvo.PersonVo;
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
 * 个人中心
 *
 * @author keriezhang
 * @date 2016/10/31
 */
@Api(description = "个人中心(前台)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserRegService allUserRegService;

    @Autowired
    private IAllUserNewsInfoService allUserNewsInfoService;

    @Autowired
    private IAllUserPicsService allUserPicsService;

    @Autowired
    private IAllUserSaysService allUserSaysService;

    @Autowired
    private IAllUserVideosService allUserVideosService;

    @ApiOperation(value = "个人资料")
    @RequestMapping(value = "getAllUserReg", method = RequestMethod.POST)
    public Response<PersonVo> getAllUserReg(@ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        PersonVo personVo = allUserRegService.getAllUserRegByUserId(userLogin.getId());

        if (StringUtils.isEmpty(personVo)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(personVo);
    }

    @ApiOperation(value = "个人日志")
    @RequestMapping(value = "getAllUserNewsInfoList", method = RequestMethod.POST)
    public Response<AllUserNewsInfo> getAllUserNewsInfoList(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        List list = new ArrayList<>();

        // 状态 1-发表 2-草稿 0-删除
        list.add(1);

        Page<AllUserNewsInfo> userNewsInfoPage = allUserNewsInfoService.getAllUserNewsInfoPage(userLogin.getId(), list, pageNo, pageSize);

        if (StringUtils.isEmpty(userNewsInfoPage)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(userNewsInfoPage);
    }

    @ApiOperation(value = "个人说说")
    @RequestMapping(value = "getAllUserSaysList", method = RequestMethod.POST)
    public Response<AllUserSays> getAllUserSaysList(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                    @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        List list = new ArrayList<>();

        // 状态 1-发表 2-草稿 0-删除
        list.add(1);

        Page<AllUserSays> userSaysPage = allUserSaysService.getAllUserSaysPage(userLogin.getId(), list, pageNo, pageSize);

        if (StringUtils.isEmpty(userSaysPage)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(userSaysPage);
    }

    @ApiOperation(value = "个人照片")
    @RequestMapping(value = "getAllUserPicsList", method = RequestMethod.POST)
    public Response<AllUserPics> getAllUserPicsList(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                    @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        List list = new ArrayList<>();

        // 状态 1-发表 2-草稿 0-删除
        list.add(1);

        Page<AllUserPics> userPicsPage = allUserPicsService.getAllUserPicsPage(userLogin.getId(), list, pageNo, pageSize);

        if (StringUtils.isEmpty(userPicsPage)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(userPicsPage);
    }

    @ApiOperation(value = "个人视频")
    @RequestMapping(value = "getAllUserVideosList", method = RequestMethod.POST)
    public Response<AllUserVideos> getAllUserVideosList(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                         @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        List list = new ArrayList<>();

        // 状态 1-发表 2-草稿 0-删除
        list.add(1);

        Page<AllUserVideos> userVideosPage = allUserVideosService.getAllUserVideosPage(userLogin.getId(), list, pageNo, pageSize);

        if (StringUtils.isEmpty(userVideosPage)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(userVideosPage);
    }
}
