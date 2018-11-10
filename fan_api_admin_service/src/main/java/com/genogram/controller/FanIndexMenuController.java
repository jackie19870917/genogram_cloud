package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.genogram.entity.FanSysWebNewsShow;
import com.genogram.entityvo.FanSysWebMenuVo;
import com.genogram.service.IFanSysWebNewsShowService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanNewsCulture")
public class FanIndexMenuController {
    final static String hostIp="http://192.168.2.179:8050";

    @Autowired
    private IFanSysWebNewsShowService iFanSysWebNewsShowService;

    @RequestMapping(value = "/getMenuBySiteId" ,  method = RequestMethod.GET)
    public Response getMenuBySiteId(@RequestParam(name = "siteId") String siteId){
        EntityWrapper<FanSysWebNewsShow> entityWrapper = new EntityWrapper<FanSysWebNewsShow>();
        entityWrapper.eq("fan_sys_site_id",siteId);
        List<FanSysWebMenuVo> list = iFanSysWebNewsShowService.getMenu(hostIp,siteId,false,entityWrapper);
        HashMap indexMap = new LinkedHashMap();
        list.forEach((fanSysWebMenuVo)->{
            indexMap.put(fanSysWebMenuVo.getMenuCode(),fanSysWebMenuVo.getChild());
        });

        Map map = new HashMap(16);
        map.put("menu_show",indexMap);
        //单表查询list
        return ResponseUtlis.success(map);
    }
}
