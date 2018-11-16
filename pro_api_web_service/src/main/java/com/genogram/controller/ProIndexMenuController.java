package com.genogram.controller;


import com.genogram.config.Constants;
import com.genogram.entity.FanNewsCharityOut;
import com.genogram.entityvo.SysWebMenuVo;
import com.genogram.service.IProSysWebNewsShowService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 联谊会首页-设置 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Api(description = "首页菜单接口")
@RestController
@RequestMapping("/genogram/proMenu")
@CrossOrigin(origins = "*")
public class ProIndexMenuController {
    @Autowired
    private IProSysWebNewsShowService proSysWebNewsShowService;

    @ApiOperation(value = "首页静态菜单" ,  notes="siteId:网站id")
    @RequestMapping(value = "/getIndexMenuBySiteId", method = RequestMethod.GET)
    public Response getIndexMenuBySiteId(@RequestParam(value="siteId") int siteId) {
        List<SysWebMenuVo> list = proSysWebNewsShowService.getIndexMenuBySiteId(siteId);
        if (list.isEmpty()) {
            return ResponseUtlis.error(Constants.IS_EMPTY, list);
        }
        return ResponseUtlis.success(list);
    }

    @ApiOperation(value = "一级菜单查询" ,  notes="查询")
    @RequestMapping(value = "/getTitlesByMenuId", method = RequestMethod.GET)
    public Response getTitlesByMenuId(@RequestParam(name = "siteId") int siteId, @RequestParam(name = "menuId") int menuId) {
        List<SysWebMenuVo> list = proSysWebNewsShowService.getTitlesByMenuId(siteId, menuId);
        if (list.isEmpty()) {
            return ResponseUtlis.error(Constants.IS_EMPTY, list);
        }
        return ResponseUtlis.success(list);
    }

    @ApiOperation(value = "初始化数据 " ,  notes="临时运用")
    @RequestMapping(value = "/initWebNewsShow", method = RequestMethod.GET)
    public Response initWebNewsShow(@RequestParam(name = "siteId") int siteId) {
        boolean result = proSysWebNewsShowService.initWebNewsShow(siteId);
        return ResponseUtlis.success(result);
    }

}

