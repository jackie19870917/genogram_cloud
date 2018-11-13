package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.genogram.config.Constants;
import com.genogram.entity.FanSysWebNewsShow;
import com.genogram.entityvo.SysWebMenuVo;
import com.genogram.service.IFanSysWebNewsShowService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanMenu")
public class FanIndexMenuController {
    @Value("${fan_api_admin_service.api_ip}")
    private String hostIp;

    @Autowired
    private IFanSysWebNewsShowService iFanSysWebNewsShowService;

    //http://localhost:8050/genogram/admin/fanMenu/getTitlesByMenuId?siteId=1&menuId=2
    @RequestMapping(value = "/getTitlesByMenuId", method = RequestMethod.GET)
    public Response getTitlesByMenuId(@RequestParam(name = "siteId") int siteId, @RequestParam(name = "menuId") int menuId) {
        List<SysWebMenuVo> list = iFanSysWebNewsShowService.getTitlesByMenuId(hostIp,siteId, menuId);
        if (list.isEmpty()) {
            return ResponseUtlis.error(Constants.IS_EMPTY, list);
        }
        return ResponseUtlis.success(list);
    }

    @RequestMapping(value = "/getMenuBySiteId", method = RequestMethod.GET)
    public Response getMenuBySiteId(@RequestParam(name = "siteId") String siteId) {
        EntityWrapper<FanSysWebNewsShow> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("fan_sys_site_id", siteId);
        List<SysWebMenuVo> list = iFanSysWebNewsShowService.getMenu(hostIp, siteId, false, entityWrapper);
        HashMap indexMap = new LinkedHashMap();
        list.forEach((fanSysWebMenuVo) -> {
            indexMap.put(fanSysWebMenuVo.getMenuCode(), fanSysWebMenuVo.getChild());
        });

        Map map = new HashMap(16);
        map.put("menu_show", indexMap);
        //单表查询list
        return ResponseUtlis.success(map);
    }
}

