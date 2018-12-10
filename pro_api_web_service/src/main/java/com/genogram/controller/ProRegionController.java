package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanSysSite;
import com.genogram.service.IAllRegionService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 省级地图查询地区
 *
 * @Author: yuzhou
 * @Date: 2018-11-26
 * @Time: 11:13
 * @Param:
 * @return:
 * @Description:
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/proRegion")
@Api(description = "省级地图查询地区")
public class ProRegionController {

    @Autowired
    private IAllRegionService allRegionService;

    @ApiOperation(value = "省级下属地图联谊会查询", notes = "")
    @RequestMapping(value = "/index/getSodalityRegion", method = RequestMethod.GET)
    public Response<FanSysSite> getSodalityRegion(
            @ApiParam(value = "网站Id") @RequestParam(value = "siteId") Integer siteId,
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            if (siteId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            Page<FanSysSite> fanSysSite = allRegionService.getSodalityRegion(siteId, pageNo, pageSize);
            return ResponseUtlis.success(fanSysSite);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}
