package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllRegion;
import com.genogram.entity.FanSysSite;
import com.genogram.service.IAllRegionService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import com.genogram.unit.StringsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/proRegion")
@Api(description = "省级地图查询地区")
public class ProRegionController {

    @Autowired
    private IAllRegionService allRegionService;

    @ApiOperation(value = "省级下属地图查询" ,  notes="")
    @RequestMapping(value = "/index/getRegion",method = RequestMethod.GET)
    public Response<AllRegion> getRegion(
            @ApiParam(value = "省级地区Id") @RequestParam(value = "parentCode") Integer parentCode
    ) {
        try {
            if(parentCode==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            Wrapper<AllRegion> entity=new EntityWrapper<>();
            entity.eq("parent_code",parentCode);
            List<AllRegion> allRegion= allRegionService.getRegion(entity);
            return ResponseUtlis.success(allRegion);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    @ApiOperation(value = "省级下属地图联谊会查询" ,  notes="")
    @RequestMapping(value = "/index/getSodalityRegion",method = RequestMethod.GET)
    public Response<FanSysSite> getSodalityRegion(
            @ApiParam(value = "县级地区Id集合") @RequestParam(value = "parentCode") String parentCode,
            @ApiParam(value = "网站Id") @RequestParam(value = "siteId") Integer siteId,
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize
    ) {
        try {
            if(StringsUtils.isEmpty(parentCode) || siteId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            Page<FanSysSite> fanSysSite=allRegionService.getSodalityRegion(parentCode,siteId,pageNo,pageSize);
            return ResponseUtlis.success(fanSysSite);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}
