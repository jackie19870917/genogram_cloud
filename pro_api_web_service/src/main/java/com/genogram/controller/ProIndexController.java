package com.genogram.controller;

import com.genogram.config.Constants;
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

    @ApiOperation(value = "基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名称,regionCode;地区编号,totemPicSrc:图腾,title:宣言,description;简介")
    @RequestMapping(value = "index/getIndexInfo", method = RequestMethod.GET)
    public Response<IndexInfoVo> getIndexInfoVo(@ApiParam("网站Id") @RequestParam Integer siteId) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        IndexInfoVo indexInfoVo = proIndexInfoService.getFanIndexInfoVo(siteId);

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

        return ResponseUtlis.success(proIndexSlidePic);
    }
}
