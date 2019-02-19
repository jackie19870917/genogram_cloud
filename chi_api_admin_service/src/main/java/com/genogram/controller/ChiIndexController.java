package com.genogram.controller;


import com.genogram.config.Constants;
import com.genogram.entity.ChiIndexSlidePic;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.service.IChiIndexInfoService;
import com.genogram.service.IChiIndexSlidePicService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 全国-首页--后台 后端控制器
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@Api(description = "全国首页（后台）")
@RestController
@CrossOrigin
@RequestMapping("/genogram/admin/chiIndex")
public class ChiIndexController {

    @Autowired
    private IChiIndexSlidePicService chiIndexSlidePicService;

    @Autowired
    private IChiIndexInfoService chiIndexInfoService;

    /**
     * 轮播图
     * @param siteId
     * @return
     */
    @ApiOperation(value = "轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片url,sort:排序")
    @RequestMapping(value = "index/getChiIndexSlidePic", method = RequestMethod.GET)
    public Response<ChiIndexSlidePic> getChiIndexSlidePic(@ApiParam("网站ID") @RequestParam Integer siteId) {

        if (siteId == null) {
            return ResponseUtils.error(Constants.IS_EMPTY, null);
        }

        List<ChiIndexSlidePic> chiIndexSlidePicList = chiIndexSlidePicService.getChiIndexSlidePicBySiteId(siteId, 1);

        if (StringUtils.isEmpty(chiIndexSlidePicList)) {
            return ResponseUtils.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtils.success(chiIndexSlidePicList);
    }

    /**
     * 全国基本信息
     * @param siteId
     * @return
     */
    @ApiOperation(value = "基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名称,regionCode;地区编号,totemPicSrc:图腾,title:宣言,description;简介")
    @RequestMapping(value = "index/getChiIndexInfo", method = RequestMethod.GET)
    public Response<IndexInfoVo> getChiIndexInfo(@ApiParam("网站ID") @RequestParam Integer siteId) {

        if (siteId == null) {
            return ResponseUtils.error(Constants.IS_EMPTY, null);
        }

        IndexInfoVo indexInfoVo = chiIndexInfoService.getIndexInfoVoBySiteId(siteId);

        if (StringUtils.isEmpty(indexInfoVo)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtils.success(indexInfoVo);
    }
}

