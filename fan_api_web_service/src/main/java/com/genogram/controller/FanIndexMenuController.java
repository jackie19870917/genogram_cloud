package com.genogram.controller;

import com.genogram.entityvo.FanSysWebMenuVo;
import com.genogram.entityvo.TestVo;
import com.genogram.service.IFanSysWebNewsShowService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private IFanSysWebNewsShowService iFanSysWebNewsShowService;

    @ResponseBody
    @RequestMapping(value = "/getMenuBySiteId" ,  method = RequestMethod.GET)
    public Response<TestVo> test1(@RequestParam(name = "siteId") String siteId){
        List<FanSysWebMenuVo> list = iFanSysWebNewsShowService.getMenu(siteId);

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

