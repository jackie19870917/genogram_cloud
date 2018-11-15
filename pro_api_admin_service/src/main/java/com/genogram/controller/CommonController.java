package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entity.AllRegion;
import com.genogram.service.IAllRegionService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RequestMapping(value = "/provincialSubordinate", method = RequestMethod.GET)
    public Response<AllRegion> getProvincialSubordinate(@RequestParam(value = "siteId")Integer siteId){
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
