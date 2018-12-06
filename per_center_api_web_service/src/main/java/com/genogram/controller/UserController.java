package com.genogram.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.*;
import com.genogram.entityvo.PersonVo;
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

import java.sql.Timestamp;
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

    @ApiOperation(value = "个人资料", notes = "nickName-昵称,englishName-英文名,nation-国籍,birthplace-出生地,job-职业,lidai-历代,jinshi-近世,laopai-老派,xinpai-新派,tongpai-统派,presentAddress-现居,oldAddress-故居,alias-现居别称,summary-简介,fans-粉丝,honesty-诚信值,url-头像")
    @RequestMapping(value = "getAllUserReg", method = RequestMethod.POST)
    public Response<PersonVo> getAllUserReg(@ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
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

    @ApiOperation(value = "修改   个人资料", notes = "nickName-昵称,englishName-英文名,nation-国籍,birthplace-出生地,job-职业,lidai-历代,jinshi-近世,laopai-老派,xinpai-新派,tongpai-统派,presentAddress-现居,oldAddress-故居,alias-现居别称,summary-简介,fans-粉丝,honesty-诚信值,url-头像")
    @RequestMapping(value = "updatePersonVo", method = RequestMethod.POST)
    public Response<PersonVo> updatePersonVo(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                             PersonVo personVo) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        personVo.setUpdateUser(userLogin.getId());
        Boolean result = allUserRegService.updateAllUserReg(personVo);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    @ApiOperation(value = "个人日志")
    @RequestMapping(value = "getAllUserNewsInfoList", method = RequestMethod.POST)
    public Response<AllUserNewsInfo> getAllUserNewsInfoList(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
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

    @ApiOperation("新增/修改   个人日志")
    @RequestMapping(value = "insertAllUserNewsInfo", method = RequestMethod.POST)
    public Response<AllUserNewsInfo> insertAllUserNewsInfo(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                           @ApiParam("日志类型(1-正常,2-草稿)") @RequestParam("status") Integer status,
                                                           AllUserNewsInfo allUserNewsInfo) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        if (StringUtils.isEmpty(allUserNewsInfo.getId())) {
            allUserNewsInfo.setUserId(userLogin.getId());
            allUserNewsInfo.setCreateUser(userLogin.getId());
            allUserNewsInfo.setCreateTime(timeStamp);
            allUserNewsInfo.setStatus(status);
        }
        allUserNewsInfo.setUpdateUser(userLogin.getId());
        allUserNewsInfo.setUpdateTime(timeStamp);

        AllUserNewsInfo userNewsInfo = allUserNewsInfoService.insertOrUpdateAllUserNewsInfo(allUserNewsInfo);

        if (StringUtils.isEmpty(userNewsInfo)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(userNewsInfo);
    }

    @ApiOperation("个人日志详情")
    @RequestMapping(value = "getAllUserNewsInfo", method = RequestMethod.POST)
    public Response<AllUserNewsInfo> getAllUserNewsInfo(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                        @ApiParam("主键") @RequestParam("id") Integer id) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserNewsInfo allUserNewsInfo = allUserNewsInfoService.getAllUserNewsInfoById(id);

        if (StringUtils.isEmpty(allUserNewsInfo)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(allUserNewsInfo);
    }

    @ApiOperation("删除日志")
    @RequestMapping(value = "deleteAllUserNewsInfo", method = RequestMethod.POST)
    public Response<AllUserNewsInfo> deleteAllUserNewsInfo(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                           @ApiParam("主键") @RequestParam("id") Integer id) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        Boolean result = allUserNewsInfoService.deleteAllUserNewsInfo(id, userLogin.getId());

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    @ApiOperation(value = "个人说说")
    @RequestMapping(value = "getAllUserSaysList", method = RequestMethod.POST)
    public Response<AllUserSays> getAllUserSaysList(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                    @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
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

    @ApiOperation("个人说说详情")
    @RequestMapping(value = "getAllUserSays", method = RequestMethod.POST)
    public Response<AllUserSays> getAllUserSays(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                @ApiParam("主键") @RequestParam("id") Integer id) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserSays allUserSays = allUserSaysService.getAllUserSaysById(id);

        if (StringUtils.isEmpty(allUserSays)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(allUserSays);
    }

    @ApiOperation("新增    个人说说")
    @RequestMapping(value = "insertAllUserSays", method = RequestMethod.POST)
    public Response<AllUserSays> insertAllUserSays(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                   AllUserSays allUserSays) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        allUserSays.setCreateTime(timeStamp);
        allUserSays.setUserId(userLogin.getId());
        allUserSays.setCreateUser(userLogin.getId());
        allUserSays.setStatus(1);

        allUserSays.setUpdateTime(timeStamp);
        allUserSays.setUpdateUser(userLogin.getId());

        AllUserSays userSays = allUserSaysService.insertAllUserSays(allUserSays);

        if (StringUtils.isEmpty(userSays)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(userSays);
    }

    @ApiOperation("删除说说")
    @RequestMapping(value = "deleteAllUserSays", method = RequestMethod.POST)
    public Response<AllUserSays> deleteAllUserSays(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                   @ApiParam("主键") @RequestParam("id") Integer id) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        Boolean result = allUserSaysService.deleteAllUserSays(id, userLogin.getId());

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    @ApiOperation(value = "个人照片")
    @RequestMapping(value = "getAllUserPicsList", method = RequestMethod.POST)
    public Response<AllUserPics> getAllUserPicsList(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                    @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
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

    @ApiOperation("个人照片详情")
    @RequestMapping(value = "getAllUserPics", method = RequestMethod.POST)
    public Response<AllUserPics> getAllUserPics(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                @ApiParam("主键") @RequestParam("id") Integer id) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserPics allUserPics = allUserPicsService.getAllUserPicsById(id);

        if (StringUtils.isEmpty(allUserPics)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(allUserPics);
    }

    @ApiOperation("新增/修改    个人照片")
    @RequestMapping(value = "insertOrUpdateAllUserPics", method = RequestMethod.POST)
    public Response<AllUserPics> insertOrUpdateAllUserPics(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                           AllUserPics allUserPics) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        if (StringUtils.isEmpty(allUserPics.getId())) {
            allUserPics.setCreateTime(timeStamp);
            allUserPics.setUserId(userLogin.getId());
            allUserPics.setCreateUser(userLogin.getId());
            allUserPics.setStatus(1);
        }
        allUserPics.setUpdateTime(timeStamp);
        allUserPics.setUpdateUser(userLogin.getId());

        AllUserPics userPics = allUserPicsService.insertOrUpdateAllUserPics(allUserPics);

        if (StringUtils.isEmpty(userPics)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(userPics);
    }

    @ApiOperation("删除照片")
    @RequestMapping(value = "deleteAllUserPics", method = RequestMethod.POST)
    public Response<AllUserPics> deleteAllUserPics(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                   @ApiParam("主键") @RequestParam("id") Integer id) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        Boolean result = allUserPicsService.deleteAllUserPics(id, userLogin.getId());

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    @ApiOperation(value = "个人视频")
    @RequestMapping(value = "getAllUserVideosList", method = RequestMethod.POST)
    public Response<AllUserVideos> getAllUserVideosList(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
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

    @ApiOperation("个人视频详情")
    @RequestMapping(value = "getAllUserVideos", method = RequestMethod.POST)
    public Response<AllUserVideos> getAllUserVideos(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                    @ApiParam("主键") @RequestParam("id") Integer id) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserVideos allUserVideos = allUserVideosService.getAllUserVideosById(id);

        if (StringUtils.isEmpty(allUserVideos)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(allUserVideos);
    }

    @ApiOperation("新增/修改   个人视频")
    @RequestMapping(value = "insertAllUserVideos", method = RequestMethod.POST)
    public Response<AllUserVideos> insertAllUserVideos(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                       AllUserVideos allUserVideos) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        if (StringUtils.isEmpty(allUserVideos.getId())) {
            allUserVideos.setUserId(userLogin.getId());
            allUserVideos.setCreateUser(userLogin.getId());
            allUserVideos.setCreateTime(timeStamp);
            allUserVideos.setStatus(1);
        }
        allUserVideos.setUpdateTime(timeStamp);
        allUserVideos.setUpdateUser(userLogin.getId());

        AllUserVideos userNewsInfo = allUserVideosService.insertOrUpdateAllUserVideos(allUserVideos);

        if (StringUtils.isEmpty(userNewsInfo)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(userNewsInfo);
    }

    @ApiOperation("删除视频")
    @RequestMapping(value = "deleteAllUserVideos", method = RequestMethod.POST)
    public Response<AllUserVideos> deleteAllUserVideos(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                       @ApiParam("主键") @RequestParam("id") Integer id) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        Boolean result = allUserVideosService.deleteAllUserVideos(id, userLogin.getId());

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}
