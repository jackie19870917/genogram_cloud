package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.*;
import com.genogram.entityvo.FamilyRecordVedioVo;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.NewsDetailVo;
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

/**
 * <p>
 * 联谊会-记录家族-家族动态,家族通告文章表 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@Api(description = "家族动态")
@RequestMapping("/genogram/fanNewsFamilyRecord")
public class FanNewsFamilyRecordController {
    @Autowired
    private IFanNewsFamilyRecordService iFanNewsFamilyRecordService;

    @Autowired
    private IFanNewsFamilyRecordVedioService iFanNewsFamilyRecordVedioService;

    @Autowired
    private IFanSysSiteService fanSysSiteService;

    @Autowired
    private IAllUserVideosService allUserVideosService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    /**
     * 家族动态查询
     */
    @ResponseBody
    @ApiOperation(value = "家族动态", notes = "showId:显示位置,pageNo:当前页,pageSize:总页数")
    @RequestMapping(value = "selectRecortPage", method = RequestMethod.GET)
    public Response<FanNewsFamilyRecord> selectRecortPage(
            @RequestParam(value = "showId") Integer showId, // 产业显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            Page<FamilyRecordVo> familyRecordVo = iFanNewsFamilyRecordService.getFamilyRecordPage(showId, list, pageNo, pageSize);
            if (familyRecordVo == null) {
                //没有取到参数,返回空参
                Page<FamilyRecordVo> emptfamilyRecordVo = new Page<FamilyRecordVo>();
                return ResponseUtils.error(Constants.ERRO_CODE, "数据为空");
            }
            return ResponseUtils.success(familyRecordVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会家族动态详情查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "动态详情")
    @RequestMapping(value = "/getFamilyRecordDetail", method = RequestMethod.GET)
    public Response<FamilyRecordVo> getFamilyRecordDetail(
            @RequestParam(value = "id") Integer id // 家族文化文章ID
    ) {
        try {
            //返回空参
            FamilyRecordVo familyRecordVo = new FamilyRecordVo();
            if (id == null) {
                return ResponseUtils.error(Constants.IS_EMPTY, "数据为空");
            }
            NewsDetailVo newsDetailVo = iFanNewsFamilyRecordService.getFamilyRecordDetail(id);
            if (newsDetailVo == null) {
                return ResponseUtils.error(Constants.ERRO_CODE, "数据为空");
            }
            //增加查看数
            iFanNewsFamilyRecordService.addVisitNum(id);
            return ResponseUtils.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 视频查询
     *
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "视频查询")
    @RequestMapping(value = "selectVedioPage", method = RequestMethod.GET)
    public Response<FanNewsFamilyRecord> selectVedioPage(
            @RequestParam(value = "showId") Integer showId, // 产业显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            int status = 1;
            Page<FamilyRecordVedioVo> familyRecordVedioVo = iFanNewsFamilyRecordVedioService.getFamilyRecordVedioPage(showId, status, pageNo, pageSize);
            if (familyRecordVedioVo == null) {
                //没有取到参数,返回空参
                Page<FamilyRecordVedioVo> emptfamilyRecordVedioVo = new Page<FamilyRecordVedioVo>();
                return ResponseUtils.error(Constants.ERRO_CODE, "数据为空");
            }
            return ResponseUtils.success(familyRecordVedioVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会记录家族的详情
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:24
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "记录家族详情")
    @RequestMapping(value = "/getFamilyRecordVedio", method = RequestMethod.GET)
    public Response<FamilyRecordVedioVo> getFamilyRecordVedioDetail(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        return getNewsDetailVedioVoResponse(id);
    }

    /**
     * 联谊会记录家族视频进入修改
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:25
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "纪录家族视频修改")
    @RequestMapping(value = "/getFamilyRecordVedioAmend", method = RequestMethod.GET)
    public Response<FamilyRecordVedioVo> getFamilyRecordVedioAmend(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        return getNewsDetailVedioVoResponse(id);
    }

    /**
     * 联谊会记录家族视频文章进入修改页面抽取方法
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:24
     * @Param:
     * @return:
     * @Description:
     */
    private Response<FamilyRecordVedioVo> getNewsDetailVedioVoResponse(@RequestParam("id") Integer id) {
        try {
            NewsDetailVo newsDetailVo = iFanNewsFamilyRecordVedioService.getFamilyVedioRecord(id);
            return ResponseUtils.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会记录家族后台视频添加和修改 发表
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:24
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "视频添加修改")
    @RequestMapping(value = "/addOrUpdateVedioRecord", method = RequestMethod.POST)
    public Response<FanNewsFamilyRecordVedio> addOrUpdateVedioRecord(FanNewsFamilyRecordVedio fanNewsFamilyRecordVedio, String picfileName, String picPath, String vedioFileName, String vedioPath) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsFamilyRecordVedio.setStatus(1);
        return getFanNewsVedioRecordResponse(fanNewsFamilyRecordVedio, picfileName, picPath, vedioFileName, vedioPath);
    }

    /**
     * 联谊会记录家族后台视频添加和修改 抽取的方法
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 12:19
     * @Param:
     * @return:
     * @Description:
     */
    private Response<FanNewsFamilyRecordVedio> getFanNewsVedioRecordResponse(FanNewsFamilyRecordVedio fanNewsFamilyRecordVedio, String picfileName, String picPath, String vedioFileName, String vedioPath) {
        try {
            // 插入数据
            boolean b = iFanNewsFamilyRecordVedioService.addOrUpdateVedioRecord(fanNewsFamilyRecordVedio, picfileName, picPath, vedioFileName, vedioPath);
            return ResponseUtils.error(Constants.SUCCESSFUL_CODE, null);
            //插入图片
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /* */

    /**
     * 联谊会记录家族后台视频删除
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 12:22
     * @Param:
     * @return:
     * @Description:
     *//*
    @ApiOperation(value = "视频删除")
    @RequestMapping(value = "/deleteRecordById", method = RequestMethod.GET)
    public Response<FanNewsFamilyRecordVedio> deleteVedioRecordById(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        try {
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status = 0;
            Boolean aBoolean = iFanNewsFamilyRecordVedioService.deleteVedioRecordById(id, status);
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
     * 个人视频(根据用户去重)
     *
     * @param siteId
     * @param pageNo
     * @param pageSize
     * @return
     */
   /* @ApiOperation(value = "个人视频", notes = "id-主键,userId-个人Id,status-状态(0-删除,1-正常),title-内容,videoPicUrl-视频封面URL,videoUrl-视频URL")
    @RequestMapping(value = "getAllUserVideosPage", method = RequestMethod.GET)
    public Response<AllUserVideos> getAllUserVideosPage(@ApiParam("主键") @RequestParam("siteId") Integer siteId,
                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        FanSysSite fanSysSite = fanSysSiteService.getFanSysSite(siteId);

        if (StringUtils.isEmpty(fanSysSite)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        List<AllUserLogin> loginList = allUserLoginService.getAllUserLoginByFamilyCode(fanSysSite.getFamilyCode());

        if (loginList.size() == 0) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        Map map = new HashMap(16);
        map.put("user_id", loginList);
        map.put("region_id", fanSysSite.getRegionCode());
        map.put("status", 1);
        map.put("sys_status", 1);

        Page page = new Page();
        page.setCurrent(pageNo);
        page.setSize(pageSize);

        Page<AllUserVideos> mapPage = new Page<>(page.getCurrent(), page.getSize());

        Page<AllUserVideos> userVideosPage = allUserVideosService.getAllUserVideosList(mapPage, map);

        if (StringUtils.isEmpty(userVideosPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(userVideosPage);
    }*/

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
            return ResponseUtils.error(Constants.IS_EMPTY, null);
        }

        FanSysSite fanSysSite = fanSysSiteService.getFanSysSite(siteId);

        if (StringUtils.isEmpty(fanSysSite)) {
            return ResponseUtils.error(Constants.ERRO_CODE, null);
        }

        List<AllUserLogin> loginList = allUserLoginService.getAllUserLoginByFamilyCode(fanSysSite.getFamilyCode());

        if (loginList.size() == 0) {
            return ResponseUtils.error(Constants.ERRO_CODE, null);
        }

        List list = new ArrayList();
        loginList.forEach((AllUserLogin allUserLogin) -> {
            list.add(allUserLogin.getId());
        });

        Wrapper<AllUserVideos> wrapper = new EntityWrapper<>();

        wrapper.in("user_id", list);
        wrapper.eq("region_id", fanSysSite.getRegionCode());
        wrapper.eq("status", 1);
        wrapper.orderBy("create_time", false);

        Page<AllUserVideos> page = new Page<>(pageNo, pageSize);

        Page<AllUserVideos> allUserVideosPage = allUserVideosService.getAllUserVideosList(page, wrapper);

        if (StringUtils.isEmpty(allUserVideosPage)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtils.success(allUserVideosPage);
    }
}

