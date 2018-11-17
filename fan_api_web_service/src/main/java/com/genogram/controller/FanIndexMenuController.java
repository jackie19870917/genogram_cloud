package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entityvo.SysWebMenuVo;
import com.genogram.service.IFanSysWebNewsShowService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联谊会首页-设置 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Api(description = "联谊会前台首页菜单接口")
@RestController
@RequestMapping("/genogram/fanMenu")
@CrossOrigin(origins = "*")
public class FanIndexMenuController {
    @Value("${fan_api_web_service.ip}")
    private String hostIp;
    @Autowired
    private IFanSysWebNewsShowService fanSysWebNewsShowService;

    @ApiOperation(value = "前台首页静态菜单" ,  notes="siteId:网站id")
    @RequestMapping(value = "/getIndexMenuBySiteId" ,  method = RequestMethod.GET)
    public Response getIndexMenuBySiteId(@RequestParam(name = "siteId") String siteId){

        List<SysWebMenuVo> indexMenus = fanSysWebNewsShowService.getIndexMenu(siteId);
        Map indexMenusMap = new LinkedHashMap();
        indexMenus.forEach((index)->{
            indexMenusMap.put(index.getMenuCode(),index);
        });

        Map map = new HashMap(16);
        map.put("index_show",indexMenusMap);
        //单表查询list
        return ResponseUtlis.success(map);
    }

    @ApiOperation(value = "前台子栏目查询" ,  notes="siteId:网站id;menuId:主菜单id(1.首页,2.家族文化,3.慈善公益,4.家族产业,5.家族名人,6.记录家族,7.组织架构,8.祖先分支,9.统谱编修)")
    @RequestMapping(value = "/getTitlesByMenuId", method = RequestMethod.GET)
    public Response getTitlesByMenuId(@RequestParam("siteId") int siteId, @RequestParam(name = "menuId") int menuId) {
        List<SysWebMenuVo> list = fanSysWebNewsShowService.getTitlesByMenuId(siteId, menuId);
        if (list.isEmpty()) {
            return ResponseUtlis.error(Constants.IS_EMPTY, list);
        }
        return ResponseUtlis.success(list);
    }
}

