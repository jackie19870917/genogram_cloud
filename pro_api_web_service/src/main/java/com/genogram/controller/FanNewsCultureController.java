package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.service.IAllRegionService;
import com.genogram.unit.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 联谊会-家族文化前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/proNewsCulture")
public class FanNewsCultureController {

    @Autowired
    private IAllRegionService allRegionService;

    /**
     *省级家族字地区下拉框查询
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 19:14
     *@Param:
     *@return:
     *@Description:
    */
   @RequestMapping(value = "/getRegion",method = RequestMethod.GET)
    public Response getRegion(@RequestParam("parentCode") Integer parentCode) {
     /*  Wrapper<>
       allRegionService.getRegion();*/
        return null;
    }

}

