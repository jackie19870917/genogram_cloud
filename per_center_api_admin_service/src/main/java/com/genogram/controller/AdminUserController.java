package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
 * @author yizx
 * @date 2018/12/5
 */
@Api(description = "个人中心(后台)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/user")
public class AdminUserController {

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

    @Autowired
    private IAllUserLoginService allUserLoginService;

    Integer role09 = 9;

    @ApiOperation(value = "查询用户", notes = "userName:用户名,realName:真实名,nickName:别名,mobilePhone:手机,picUrl:头像,siteId:网站Id,role:角色(1-县级管理员,2-省级管理员,0-不是管理员),familyCode:姓氏,region:地区,token:token")
    @RequestMapping(value = "getUserLoginList", method = RequestMethod.POST)
    public Response<AllUserLogin> getUserLoginList(@ApiParam("主键") @RequestParam(value = "id", required = false) Integer id,
                                                   @ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                   @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不正确");
        }

        AllUserLogin allUserLogin = userService.getUserLoginInfoByToken(token);

        AllUserLogin userLogin = allUserLoginService.getAllUserLoginById(allUserLogin.getId());

        if (!userLogin.getRole().equals(role09)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限");
        }

        Wrapper<AllUserLogin> wrapper = new EntityWrapper<>();

        if (StringUtils.isEmpty(id)) {
            wrapper = null;
        } else {
            wrapper.eq("id", id);
        }
        Page<AllUserLogin> userLoginPage = allUserLoginService.getAllUserLoginPage(wrapper, pageNo, pageSize);

        if (StringUtils.isEmpty(userLoginPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(userLoginPage);
    }

    @ApiOperation(value = "个人资料", notes = "nickName-昵称,englishName-英文名,nation-国籍,birthplace-出生地,job-职业,lidai-历代,jinshi-近世,laopai-老派,xinpai-新派,tongpai-统派,presentAddress-现居,oldAddress-故居,alias-现居别称,summary-简介,fans-粉丝,honesty-诚信值,url-头像")
    @RequestMapping(value = "getAllUserReg", method = RequestMethod.POST)
    public Response<PersonVo> getAllUserReg(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                            @ApiParam("主键") @RequestParam("id") Integer id) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        PersonVo personVo = allUserRegService.getAllUserRegByUserId(id);

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

    @ApiOperation(value = "个人日志",notes = "id-主键,userId-个人Id,title-文章标题,newsFaceUrl-文章封面URL,content-文章内容,status-状态(0-删除,1-正常,2-草稿)")
    @RequestMapping(value = "getAllUserNewsInfoList", method = RequestMethod.POST)
    public Response<AllUserNewsInfo> getAllUserNewsInfoList(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                            @ApiParam("用户Id") @RequestParam("userId") Integer userId,
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

        Page<AllUserNewsInfo> userNewsInfoPage = allUserNewsInfoService.getAllUserNewsInfoPage(userId, list, pageNo, pageSize);

        if (StringUtils.isEmpty(userNewsInfoPage)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(userNewsInfoPage);
    }

    @ApiOperation(value = "个人日志详情",notes = "id-主键,userId-个人Id,title-文章标题,newsFaceUrl-文章封面URL,content-文章内容,status-状态(0-删除,1-正常,2-草稿)")
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

    @ApiOperation(value = "个人说说",notes = "id-主键,userId-个人id,content-说说内容,status-状态(1-正常,0-删除)")
    @RequestMapping(value = "getAllUserSaysList", method = RequestMethod.POST)
    public Response<AllUserSays> getAllUserSaysList(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                    @ApiParam("用户Id") @RequestParam("userId") Integer userId,
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

        Page<AllUserSays> userSaysPage = allUserSaysService.getAllUserSaysPage(userId, list, pageNo, pageSize);

        if (StringUtils.isEmpty(userSaysPage)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(userSaysPage);
    }

    @ApiOperation(value = "个人说说详情",notes = "id-主键,userId-个人id,content-说说内容,status-状态(1-正常,0-删除)")
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

    @ApiOperation(value = "个人照片",notes = "id-主键,userId-个人id,picUrl-图片url,status-状态(1-正常,0-删除)")
    @RequestMapping(value = "getAllUserPicsList", method = RequestMethod.POST)
    public Response<AllUserPics> getAllUserPicsList(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                    @ApiParam("用户Id") @RequestParam("userId") Integer userId,
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

        Page<AllUserPics> userPicsPage = allUserPicsService.getAllUserPicsPage(userId, list, pageNo, pageSize);

        if (StringUtils.isEmpty(userPicsPage)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(userPicsPage);
    }

    @ApiOperation(value = "个人照片详情",notes = "id-主键,userId-个人id,picUrl-图片url,status-状态(1-正常,0-删除)")
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

    @ApiOperation(value = "个人视频",notes = "id-主键,userId-个人Id,status-状态(0-删除,1-正常),title-内容,videoPicUrl-视频封面URL,videoUrl-视频URL")
    @RequestMapping(value = "getAllUserVideosList", method = RequestMethod.POST)
    public Response<AllUserVideos> getAllUserVideosList(@ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                        @ApiParam("用户Id") @RequestParam("userId") Integer userId,
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

        Page<AllUserVideos> userVideosPage = allUserVideosService.getAllUserVideosPage(userId, list, pageNo, pageSize);

        if (StringUtils.isEmpty(userVideosPage)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(userVideosPage);
    }

    @ApiOperation(value = "个人视频详情",notes = "id-主键,userId-个人Id,status-状态(0-删除,1-正常),title-内容,videoPicUrl-视频封面URL,videoUrl-视频URL")
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
