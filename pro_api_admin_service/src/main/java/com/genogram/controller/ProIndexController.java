package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entity.ProIndexInfo;
import com.genogram.entity.ProIndexSlidePic;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.service.IProIndexInfoService;
import com.genogram.service.IProIndexSlidePicService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 省级首页-设置 后端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Api(description = "省级首页(后台)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/proIndex")
public class ProIndexController {

    @Autowired
    private IProIndexInfoService proIndexInfoService;

    @Autowired
    private IProIndexSlidePicService proIndexSlidePicService;

    /**
     * 省级网站信息
     * @param siteId 网站ID
     * @return
     */
    @ApiOperation("基本信息")
    @RequestMapping(value = "getIndexInfo", method = RequestMethod.GET)
    public Response<IndexInfoVo> getIndexInfoVo(@ApiParam("网站Id") @RequestParam Integer siteId) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        IndexInfoVo indexInfoVo = proIndexInfoService.getFanIndexInfoVo(siteId);

        return ResponseUtlis.success(indexInfoVo);
    }

    @ApiOperation(value = "新增/修改基本信息",notes="id:主键,siteId:网站Id,siteName:网站名字,totemPicSrc:图腾,title:宣言,description:简介")
    @RequestMapping(value = "insertOrUpdateIndexInfo", method = RequestMethod.POST)
    public Response<IndexInfoVo> insertOrUpdateIndexInfo(IndexInfoVo indexInfoVo) {

        Boolean result = proIndexInfoService.insertOrUpdateIndexInfoVo(indexInfoVo);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 删除   省级基本信息
     * @param proIndexInfo
     * @return
     */
    @ApiOperation("删除基本信息")
    @RequestMapping(value = "deleteProIndexInfo", method = RequestMethod.POST)
    public Response<ProIndexInfo> deleteFanIndexInfo(ProIndexInfo proIndexInfo) {

        Boolean result = proIndexInfoService.deleteProIndexInfo(proIndexInfo);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    @ApiOperation("轮播图")
    @RequestMapping(value = "index/getProIndexSlidePic", method = RequestMethod.GET)
    public Response<ProIndexSlidePic> getProIndexSlidePic(@ApiParam("网站Id") @RequestParam Integer siteId) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();

        //状态   1-前后台显示    2-前台不显示      0-前后台都不显示
        list.add(1);
        list.add(2);

        List<ProIndexSlidePic> proIndexSlidePic = proIndexSlidePicService.getProIndexSlidePic(siteId,list);

        return ResponseUtlis.success(proIndexSlidePic);
    }

    /**
     * 新增/修改  轮播图
     * @param proIndexSlidePic
     * @return
     */
    @ApiOperation(value = "新增/修改 轮播图",notes = "id:主键,siteId:网站Id,picUrl:图片,sort:排序")
    @RequestMapping(value = "insertOrUpdateProIndexSlidePic", method = RequestMethod.POST)
    public Response<ProIndexSlidePic> insertOrUpdateProIndexSlidePic(ProIndexSlidePic proIndexSlidePic) {

        Boolean result = proIndexSlidePicService.insertOrUpdateProIndexSlidePic(proIndexSlidePic);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 逻辑删除  轮播图
     * @param id
     * @return
     */
    @ApiOperation("删除轮播图")
    @RequestMapping(value = "deleteProIndexSlidePic", method = RequestMethod.GET)
    public Response<ProIndexSlidePic> deleteProIndexSlidePic(@ApiParam("主键") @RequestParam Integer id) {

        Boolean result = proIndexSlidePicService.deleteProIndexSlidePic(id);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

}
