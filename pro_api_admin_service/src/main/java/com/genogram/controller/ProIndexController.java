package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entity.ProIndexInfo;
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
}
