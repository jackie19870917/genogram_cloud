package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entity.AllRegion;
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
 * 省级公共
 *
 * @author Toxicant
 * @date 2016/10/31
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/proPublic")
@Api(description = "公共方法")
public class CommonController {

    @Autowired
    private IAllRegionService allRegionService;

    /**
     *省级下属县级官网查询
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 18:47
     *@Param: 
     *@return:
     *@Description:
    */
    @ApiOperation(value = "省级下属县级官网查询" ,  notes="根据sizeId查询")
    @RequestMapping(value = "/provincialSubordinate", method = RequestMethod.GET)
    public Response<AllRegion> getProvincialSubordinate(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId")Integer siteId){
        try {
            if(siteId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            List<AllRegion> provincialSubordinate = allRegionService.getProvincialSubordinate(siteId);
            if(provincialSubordinate==null){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.success(provincialSubordinate);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}
