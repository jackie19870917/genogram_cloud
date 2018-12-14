package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ProNewsFamilyRecord;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.entityvo.ProFamilyRecordVo;
import com.genogram.service.*;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Administrator
 */
@Api(description = "省级家族动态")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/proNewsFamilyRecord")
public class ProNewsRecordController {
    @Autowired
    private IProNewsFamilyRecordService iProNewsFamilyRecordService;

    @Autowired
    private IProNewsFamilyRecordVedioService iProNewsFamilyRecordVedioServices;

    @Autowired
    private IAllCheckOutService allCheckOutService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    /**
     * 角色权限 (0.不是管理员,1.县级管理员,2省级管理员,3.全国管理员,4县级副管理员,5省级副管理员,6全国副管理员,9.超级管理员)
     */
    Integer role02 = 2;
    Integer role05 = 5;
    Integer role09 = 9;

    /**
     * 省级家族动态查询
     */
    @ApiOperation("省级家族动态查询")
    @RequestMapping(value = "proSelectRecortPage", method = RequestMethod.GET)
    public Response<ProNewsFamilyRecord> selectRecortPage(
            @RequestParam(value = "showId") Integer showId, // 显示位置
            @ApiParam("token") @RequestParam(value = "token", required = false) String token,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
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
            if (!allUserLogin.getRole().equals(role02) || !allUserLogin.getRole().equals(role05) || !allUserLogin.getRole().equals(role09)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
            }

            int status = 1;
            Page<ProFamilyRecordVo> proFamilyRecordPage = iProNewsFamilyRecordService.getProFamilyRecordPage(showId, status, pageNo, pageSize);
            if (proFamilyRecordPage == null) {
                //没有取到参数,返回空参
                Page<FamilyRecordVo> emptfamilyRecordVo = new Page<FamilyRecordVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, "proFamilyRecordPage为空");
            }
            return ResponseUtlis.success(proFamilyRecordPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级记录家族的详情
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:24
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation("省级记录家族的详情")
    @RequestMapping(value = "/getProFamilyRecordDetail", method = RequestMethod.GET)
    public Response<ProFamilyRecordVo> getFamilyRecordDetail(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        return getNewsDetailVoProResponse(id);
    }

    /**
     * 省级记录家族进入修改
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:25
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation("省级记录家族进入修改")
    @RequestMapping(value = "/getProFamilyRecordAmend", method = RequestMethod.GET)
    public Response<ProFamilyRecordVo> getFamilyRecordAmend(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        return getNewsDetailVoProResponse(id);
    }

    /**
     * 省级记录家族各个产业文章进入修改页面抽取方法
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:24
     * @Param:
     * @return:
     * @Description:
     */
    private Response<ProFamilyRecordVo> getNewsDetailVoProResponse(@RequestParam("id") Integer id) {
        try {
            NewsDetailVo newsDetailVo = iProNewsFamilyRecordService.getProFamilyRecord(id);
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会记录家族后台添加和修改 发表
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:24
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation("记录家族后台添加和修改 发表")
    @RequestMapping(value = "/addOrUpdateRecord", method = RequestMethod.POST)
    public Response<ProNewsFamilyRecord> addOrUpdateRecord(ProNewsFamilyRecord proNewsFamilyRecord, String fileName, String filePath) {

        Set set = allCheckOutService.getSensitiveWord(proNewsFamilyRecord.getNewsText());

        if (set != null && set.size() >= 1) {
            return ResponseUtlis.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set);
        }

        //状态(0:删除;1:已发布;2:草稿3:不显示)
        proNewsFamilyRecord.setStatus(1);
        return getProNewsRecordResponse(proNewsFamilyRecord, fileName, filePath);
    }

    /**
     * 省级家族产业后台添加和修改 草稿
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 12:10
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation("省级家族产业后台添加和修改 草稿")
    @RequestMapping(value = "/addOrUpdateRecordDrft", method = RequestMethod.POST)
    public Response<ProNewsFamilyRecord> addOrUpdateRecordDrft(ProNewsFamilyRecord proNewsFamilyRecord, String fileName, String filePath) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        proNewsFamilyRecord.setStatus(2);
        return getProNewsRecordResponse(proNewsFamilyRecord, fileName, filePath);
    }

    /**
     * 省级记录家族后台添加和修改 抽取的方法
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 12:19
     * @Param:
     * @return:
     * @Description:
     */
    private Response<ProNewsFamilyRecord> getProNewsRecordResponse(ProNewsFamilyRecord proNewsFamilyRecord, String fileName, String filePath) {
        try {
            // 插入数据
            boolean b = iProNewsFamilyRecordService.addOrUpdateRecord(proNewsFamilyRecord, fileName, filePath);
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
            //插入图片
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级记录家族后台删除
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 12:22
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation("省级记录家族后台删除")
    @RequestMapping(value = "/deleteProRecordById", method = RequestMethod.GET)
    public Response<ProNewsFamilyRecord> deleteProRecordById(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        try {
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status = 0;
            Boolean aBoolean = iProNewsFamilyRecordService.deleteProRecordById(id, status);
            if (!aBoolean) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}
