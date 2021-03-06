package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsIndustry;
import com.genogram.entityvo.FamilyIndustryVo;
import com.genogram.entityvo.IndustryDetailVo;
import com.genogram.service.*;
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
import java.util.Set;

/**
 * 家族产业增删改查
 *
 * @Author: yuzhou
 * @Date: 2018-11-09
 * @Time: 16:06
 * @Param:
 * @return:
 * @Description:
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanNewsIndustry")
@Api(description = "联谊会家族产业增删改查")
public class FanNewsIndustryController {

    @Autowired
    private IFanNewsIndustryService fanNewsIndustryService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllCheckOutService allCheckOutService;

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
     * 联谊会家族产业后台查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:24
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会家族产业后台查询", notes =
            "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键Id --" +
                    "industryLocation 家族产业具体地址 --" +
                    "newsText 内容 --" +
                    "newsTitle 标题 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数")
    @RequestMapping(value = "/getFamilyIndustryPage", method = RequestMethod.GET)
    public Response<FamilyIndustryVo> getFamilyCulturePage(
            @ApiParam(value = "显示位置Id") @RequestParam(value = "showId") Integer showId,
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
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

            //判断showId是否有值
            if (showId == null) {
                return ResponseUtils.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            List statusList = new ArrayList();
            statusList.add(1);
            statusList.add(2);
            //查询文章信息的条件
            Wrapper<FanNewsIndustry> entity = new EntityWrapper<FanNewsIndustry>();
            entity.eq("show_id", showId);
            if (statusList.size() != 0) {
                entity.in("status", statusList);
            }
            entity.orderBy("create_time", false);
            Page<FamilyIndustryVo> familyCultureVo = fanNewsIndustryService.getFamilyIndustryPage(entity, pageNo, pageSize);
            if (familyCultureVo == null) {
                //没有取到参数,返回空参
                Page<FamilyIndustryVo> emptfamilyCultureVo = new Page<FamilyIndustryVo>();
                return ResponseUtils.error(Constants.ERRO_CODE, "familyCultureVo为空");
            }
            return ResponseUtils.success(familyCultureVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会家族产业各个产业的详情
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:24
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会家族产业各个产业的详情", notes =
            "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键Id --" +
                    "industryLocation 家族产业具体地址 --" +
                    "newsText 内容 --" +
                    "newsTitle 标题 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数")
    @RequestMapping(value = "/getFamilyIndustryDetail", method = RequestMethod.GET)
    public Response<IndustryDetailVo> getFamilyIndustryDetail(
            @ApiParam(value = "主键Id") @RequestParam(value = "id") Integer id, // 家族文化详情显示位置
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        return getNewsDetailVoResponse(id, token);
    }

    /**
     * 联谊会家族产业各个产业进入修改
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:25
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会家族产业各个产业进入修改", notes =
            "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键Id --" +
                    "industryLocation 家族产业具体地址 --" +
                    "newsText 内容 --" +
                    "newsTitle 标题 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数")
    @RequestMapping(value = "/getFamilyIndustryAmend", method = RequestMethod.GET)
    public Response<IndustryDetailVo> getFamilyIndustryAmend(
            @ApiParam(value = "主键Id") @RequestParam(value = "id") Integer id, // 家族文化详情显示位置
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        return getNewsDetailVoResponse(id, token);
    }

    /**
     * 联谊会家族产业各个产业文章进入修改页面抽取方法
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:24
     * @Param:
     * @return:
     * @Description:
     */
    private Response<IndustryDetailVo> getNewsDetailVoResponse(@RequestParam("id") Integer id, String token) {
        try {
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
            if (id == null) {
                return ResponseUtils.error(Constants.IS_EMPTY, null);
            }
            IndustryDetailVo industryDetailVo = fanNewsIndustryService.getFamilyIndustryDetail(id);
            return ResponseUtils.success(industryDetailVo);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会家族产业后台添加和修改 发表
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:24
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会家族产业后台添加和修改 发表", notes =
            "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键Id --" +
                    "industryLocation 家族产业具体地址 --" +
                    "newsText 内容 --" +
                    "newsTitle 标题 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数")
    @RequestMapping(value = "/addOrUpdateIndustry", method = RequestMethod.POST)
    public Response<FanNewsIndustry> addOrUpdateIndustry(
            @ApiParam(value = "县级家族产业表") FanNewsIndustry fanNewsIndustry,
            @ApiParam(value = "上传文件名称") @RequestParam(value = "fileName", required = false) String fileName,
            @ApiParam(value = "上传文件地址") @RequestParam(value = "filePath", required = false) String filePath,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  校验敏感词汇
        Set set = allCheckOutService.getSensitiveWord(fanNewsIndustry.getNewsText());

        if (set != null && set.size() >= 1) {
            return ResponseUtils.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set);
        }

        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsIndustry.setStatus(1);
        return getFanNewsIndustryResponse(fanNewsIndustry, fileName, filePath, token);
    }

    /**
     * 联谊会家族产业后台添加和修改 草稿
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 12:10
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会家族产业后台添加和修改 草稿", notes =
            "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键Id --" +
                    "industryLocation 家族产业具体地址 --" +
                    "newsText 内容 --" +
                    "newsTitle 标题 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数")
    @RequestMapping(value = "/addOrUpdateIndustryDrft", method = RequestMethod.POST)
    public Response<FanNewsIndustry> addOrUpdateIndustryDrft(@ApiParam(value = "县级家族产业表") FanNewsIndustry fanNewsIndustry,
                                                             @ApiParam(value = "上传文件名称") @RequestParam(value = "fileName", required = false) String fileName,
                                                             @ApiParam(value = "上传文件地址") @RequestParam(value = "filePath", required = false) String filePath,
                                                             @ApiParam("token") @RequestParam(value = "token", required = false) String token) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsIndustry.setStatus(2);
        return getFanNewsIndustryResponse(fanNewsIndustry, fileName, filePath, token);
    }

    /**
     * 联谊会家族产业后台添加和修改 抽取的方法
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 12:19
     * @Param:
     * @return:
     * @Description:
     */
    private Response<FanNewsIndustry> getFanNewsIndustryResponse(FanNewsIndustry fanNewsIndustry,
                                                                 String fileName, String filePath,
                                                                 @ApiParam("token") @RequestParam(value = "token", required = false) String token) {
        try {
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
            if (fanNewsIndustry.getId() == null) {
                //创建人
                fanNewsIndustry.setCreateUser(userLogin.getId());
            }
            //修改人
            fanNewsIndustry.setUpdateUser(userLogin.getId());
            // 插入数据
            fanNewsIndustryService.addOrUpdateIndustry(fanNewsIndustry, fileName, filePath);
            return ResponseUtils.error(Constants.SUCCESSFUL_CODE, null);
            //插入图片
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会家族产业后台删除
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 12:22
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会家族产业后台删除", notes = "")
    @RequestMapping(value = "/deleteIndustryById", method = RequestMethod.GET)
    public Response<FanNewsIndustry> deleteIndustryById(
            @ApiParam("主键ID") @RequestParam(value = "id") Integer id,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
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
            //判断id是否为空
            if (id == null) {
                return ResponseUtils.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status = 0;
            Boolean isDel = fanNewsIndustryService.deleteIndustryById(id, status, userLogin);
            if (isDel == null || !isDel) {
                return ResponseUtils.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtils.error(Constants.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会家族产业后台置顶
     *
     * @Author: yuzhou
     * @Date: 2018-12-15
     * @Time: 10:00
     * @Param:
     * @return:
     * @Description:i
     */
    @ApiOperation(value = "联谊会家族产业后台置顶", notes = "")
    @RequestMapping(value = "/industryStick", method = RequestMethod.GET)
    public Response<FanNewsIndustry> industryStick(
            @ApiParam("主键ID") @RequestParam(value = "id") Integer id,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
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
            //判断id是否为空
            if (id == null) {
                return ResponseUtils.error(Constants.IS_EMPTY, null);
            }
            Boolean isDel = fanNewsIndustryService.industryStick(id, userLogin);
            if (isDel == null || !isDel) {
                return ResponseUtils.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtils.error(Constants.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }
}
