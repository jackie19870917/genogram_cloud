package com.genogram.controller;


import com.genogram.entity.FanNewsCharityOut;
import com.genogram.service.CommonIProNewsCharityOutService;
import com.genogram.service.IProNewsCharityOutService;
import com.genogram.service.IProSysWebNewsShowService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 联谊会首页-设置 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Api(description = "测试Swagger接口")
@RestController
@RequestMapping("/swagger")
@CrossOrigin(origins = "*")
public class SwaggerController {
    @Autowired
    private IProSysWebNewsShowService proSysWebNewsShowService;

    @Autowired
    private IProNewsCharityOutService proNewsCharityOutService;

    /**
     * 第一级菜单查询
     *
     * @param fan
     * @return
     */
    @ApiOperation(value = "一级菜单查询", notes = "查询")
    @RequestMapping(value = "/getTitlesByMenuId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response getTitlesByMenuId(FanNewsCharityOut fan) {
        System.out.println(fan);
        return ResponseUtlis.success(fan);
    }


    /**
     * 第二级菜单查询
     *
     * @param fan
     * @return
     */
    @ApiOperation(value = "二级级菜单查询", notes = "查询")
    @RequestMapping(value = "/getTitlesByMenuId2", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response getTitlesByMenuId2(@RequestBody FanNewsCharityOut fan) {
        System.out.println(fan);
        return ResponseUtlis.success(fan);
    }

}

