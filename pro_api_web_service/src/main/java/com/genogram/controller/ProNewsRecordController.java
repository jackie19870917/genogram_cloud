package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.AllUserVideos;
import com.genogram.entity.ProNewsFamilyRecord;
import com.genogram.entity.ProSysSite;
import com.genogram.entityvo.FamilyRecordVedioVo;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Administrator
 */
@Api(description = "省级家族动态")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/proNewsFamilyRecord")
public class ProNewsRecordController {
    @Autowired
    private IProNewsFamilyRecordService iProNewsFamilyRecordService;

    @Autowired
    private IProNewsFamilyRecordVedioService proNewsFamilyRecordVideoServices;

    @Autowired
    private IAllCheckOutService allCheckOutService;

    @Autowired
    private IProSysSiteService proSysSiteService;

    @Autowired
    private IAllUserVideosService allUserVideosService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    /**
     * 省级家族动态查询
     */
    @ApiOperation("省级家族动态查询")
    @RequestMapping(value = "proSelectRecortPage", method = RequestMethod.GET)
    public Response<ProNewsFamilyRecord> selectRecortPage(
            @RequestParam(value = "showId") Integer showId, // 显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            int status = 1;
            Page<ProFamilyRecordVo> proFamilyRecordPage = iProNewsFamilyRecordService.getProFamilyRecordPage(showId, status, pageNo, pageSize);
            if (proFamilyRecordPage == null) {
                //没有取到参数,返回空参
                Page<FamilyRecordVo> emptfamilyRecordVo = new Page<FamilyRecordVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
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
    @RequestMapping(value = "/getProFamilyRecord", method = RequestMethod.GET)
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
     * 省级记录家族文章进入修改页面抽取方法
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

            //增加查看数
            iProNewsFamilyRecordService.addVisitNum(id);
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

        if (set.size() >= 1) {
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
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
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
 /*   @ApiOperation("省级记录家族后台删除")
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
    }*/

    /**
     * 官方视频
     */
    @ApiOperation("官方视频")
    @RequestMapping(value = "selectRecortVedioPage", method = RequestMethod.GET)
    public Response<ProFamilyRecordVo> selectRecortVedioPage(
            @RequestParam(value = "showId") Integer showId, // 产业显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            int status = 1;
            Page<FamilyRecordVedioVo> familyRecordVedioVo = proNewsFamilyRecordVideoServices.getFamilyRecordVedioPage(showId, status, pageNo, pageSize);
            if (familyRecordVedioVo == null) {
                //没有取到参数,返回空参
                Page<FamilyRecordVo> emptfamilyRecordVo = new Page<FamilyRecordVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.success(familyRecordVedioVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 个人视频(根据用户)
     *
     * @param siteId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "个人视频", notes = "id-主键,userId-个人Id,status-状态(0-删除,1-正常),title-内容,videoPicUrl-视频封面URL,videoUrl-视频URL")
    @RequestMapping(value = "getAllUserVideosPage", method = RequestMethod.GET)
    public Response<AllUserVideos> getAllUserVideosPage(@ApiParam("主键") @RequestParam("siteId") Integer siteId,
                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        ProSysSite proSysSite = proSysSiteService.getProSysSite(siteId);

        if (StringUtils.isEmpty(proSysSite)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        List<AllUserLogin> loginList = allUserLoginService.getAllUserLoginByFamilyCode(proSysSite.getFamilyCode());

        if (loginList.size() == 0) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        List list = new ArrayList();
        loginList.forEach((AllUserLogin allUserLogin) -> {
            list.add(allUserLogin.getId());
        });

        Wrapper<AllUserVideos> wrapper = new EntityWrapper<>();

        wrapper.in("user_id", list);
        wrapper.eq("region_id", proSysSite.getRegionCode());
        wrapper.eq("status", 1);
        wrapper.orderBy("create_time", false);

        Page<AllUserVideos> page = new Page<>(pageNo, pageSize);

        Page<AllUserVideos> allUserVideosPage = allUserVideosService.getAllUserVideosList(page, wrapper);

        if (StringUtils.isEmpty(allUserVideosPage)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(allUserVideosPage);
    }
}
