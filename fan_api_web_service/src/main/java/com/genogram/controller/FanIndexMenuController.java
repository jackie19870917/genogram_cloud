package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.genogram.entity.FanSysWebNewsShow;
import com.genogram.entityvo.FanSysWebMenuVo;
import com.genogram.service.IFanSysWebNewsShowService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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
@Controller
@RequestMapping("/genogram/fanMenu")
@CrossOrigin(origins = "*")
public class FanIndexMenuController {
    @Value("${fan_api_web_service.ip}")
    private String hostIp;
    @Autowired
    private IFanSysWebNewsShowService iFanSysWebNewsShowService;

    @ResponseBody
    @RequestMapping(value = "/getMenuBySiteId" ,  method = RequestMethod.GET)
    public Response getMenuBySiteId(@RequestParam(name = "siteId") String siteId){
        EntityWrapper<FanSysWebNewsShow> entityWrapper = new EntityWrapper<FanSysWebNewsShow>();
        entityWrapper.eq("fan_sys_site_id",siteId);
        List<FanSysWebMenuVo> list = iFanSysWebNewsShowService.getMenu(hostIp,siteId,true,entityWrapper);

        List<FanSysWebMenuVo> indexMenus = iFanSysWebNewsShowService.getIndexMenu(siteId);
        Map indexMenusMap = new LinkedHashMap();
        indexMenus.forEach((index)->{
            indexMenusMap.put(index.getMenuType(),index);
        });

        Map map = new HashMap(16);
        map.put("index_show",indexMenusMap);
        map.put("menu_show",list);
        //单表查询list
        return ResponseUtlis.success(map);
    }
}

