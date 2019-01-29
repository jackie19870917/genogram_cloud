package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.AllUserNewsInfo;
import com.genogram.entity.ProIndexSlidePic;
import com.genogram.entity.ProSysSite;
import com.genogram.entityvo.IndexInfoVo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 省级首页-设置 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Api(description = "省级菜单首页(前台)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/proIndex")
public class ProIndexController {

    @Autowired
    private IProIndexInfoService proIndexInfoService;

    @Autowired
    private IProIndexSlidePicService proIndexSlidePicService;

    @Autowired
    private IProSysSiteService proSysSiteService;

    @Autowired
    private IAllUserNewsInfoService allUserNewsInfoService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @ApiOperation(value = "基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名称,regionCode;地区编号,totemPicSrc:图腾,title:宣言,description;简介")
    @RequestMapping(value = "index/getIndexInfo", method = RequestMethod.GET)
    public Response<IndexInfoVo> getIndexInfoVo(@ApiParam("网站Id") @RequestParam Integer siteId) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        IndexInfoVo indexInfoVo = proIndexInfoService.getFanIndexInfoVo(siteId);

        if (StringUtils.isEmpty(indexInfoVo)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(indexInfoVo);
    }

    @ApiOperation(value = "轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片url,sort:排序")
    @RequestMapping(value = "index/getProIndexSlidePic", method = RequestMethod.GET)
    public Response<ProIndexSlidePic> getProIndexSlidePic(@ApiParam("网站Id") @RequestParam Integer siteId) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        //状态   1-前后台显示   2-前台不显示      0-前后台都不显示
        list.add(1);

        List<ProIndexSlidePic> proIndexSlidePic = proIndexSlidePicService.getProIndexSlidePic(siteId, list);

        if (StringUtils.isEmpty(proIndexSlidePic)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(proIndexSlidePic);
    }

    /*@ApiOperation(value = "最新发布", notes = "id-主键,userId-个人Id,title-文章标题,newsFaceUrl-文章封面URL,content-文章内容,status-状态(0-删除,1-正常,2-草稿)")
    @RequestMapping(value = "getAllUserNewsInfoPage", method = RequestMethod.GET)
    public Response<AllUserNewsInfo> getAllUserNewsInfoPage(@ApiParam("主键") @RequestParam("siteId") Integer siteId,
                                                            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        ProSysSite proSysSite = proSysSiteService.getProSysSite(siteId);

        if (StringUtils.isEmpty(proSysSite)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        Map map = new HashMap(16);
        map.put("pro_code", proSysSite.getRegionCode());
        map.put("status", 1);
        map.put("sys_status", 1);

        Page page = new Page();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<AllUserNewsInfo> mapPage = new Page<>(page.getCurrent(), page.getSize());

        Page<AllUserNewsInfo> userNewsInfoPage = allUserNewsInfoService.getAllUserNewsInfoList(mapPage, map);

        if (StringUtils.isEmpty(userNewsInfoPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(userNewsInfoPage);
    }*/

    /**
     * 最新发布(根据用户)
     *
     * @param siteId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "最新发布", notes = "id-主键,userId-个人Id,title-文章标题,newsFaceUrl-文章封面URL,content-文章内容,status-状态(0-删除,1-正常,2-草稿)")
    @RequestMapping(value = "getAllUserNewsInfoPage", method = RequestMethod.GET)
    public Response<AllUserNewsInfo> getAllUserNewsInfoPage(@ApiParam("主键") @RequestParam("siteId") Integer siteId,
                                                            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        ProSysSite proSysSite = proSysSiteService.getProSysSite(siteId);

        if (StringUtils.isEmpty(proSysSite)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        List<AllUserLogin> loginList = allUserLoginService.getAllUserLoginByFamilyCode(proSysSite.getFamilyCode());

        if (loginList.size() == 0) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        List list = new ArrayList();
        loginList.forEach((AllUserLogin allUserLogin) -> {
            list.add(allUserLogin.getId());
        });

        Wrapper<AllUserNewsInfo> wrapper = new EntityWrapper<>();

        wrapper.in("user_id", list);
        wrapper.eq("region_id", proSysSite.getRegionCode());
        wrapper.eq("status", 1);
        wrapper.orderBy("create_time", false);

        Page<AllUserNewsInfo> page = new Page<>(pageNo, pageSize);

        Page<AllUserNewsInfo> allUserNewsInfoPage = allUserNewsInfoService.getAllUserNewsInfoList(page, wrapper);

        if (StringUtils.isEmpty(allUserNewsInfoPage)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(allUserNewsInfoPage);
    }
}
