package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entity.AllRegion;
import com.genogram.service.IAllRegionService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/proPublic")
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
    @RequestMapping(value = "/ProvincialSubordinate", method = RequestMethod.GET)
    public Response<AllRegion> getProvincialSubordinate(@RequestParam(value = "siteId")Integer siteId){
        if(siteId==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        allRegionService.getProvincialSubordinate(siteId);
     return null;
    }
}
