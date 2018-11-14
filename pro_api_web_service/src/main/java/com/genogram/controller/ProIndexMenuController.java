package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entityvo.SysWebMenuVo;
import com.genogram.service.IProSysWebNewsShowService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
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
@RestController
@RequestMapping("/genogram/proMenu")
@CrossOrigin(origins = "*")
public class ProIndexMenuController {
    @Value("${pro_api_web_service.ip}")
    private String hostIp;
    @Autowired
    private IProSysWebNewsShowService proSysWebNewsShowService;

    //localhost:8050/genogram/proMenu/getTitlesByMenuId?siteId=1&menuId=2
    //第一级菜单查询
    @RequestMapping(value = "/getTitlesByMenuId", method = RequestMethod.GET)
    public Response getTitlesByMenuId(@RequestParam(name = "siteId") int siteId, @RequestParam(name = "menuId") int menuId) {
        List<SysWebMenuVo> list = proSysWebNewsShowService.getTitlesByMenuId(hostIp,true,siteId, menuId);
        if (list.isEmpty()) {
            return ResponseUtlis.error(Constants.IS_EMPTY, list);
        }
        return ResponseUtlis.success(list);
    }

    //初始化数据 临时运用
    @RequestMapping(value = "/initWebNewsShow", method = RequestMethod.GET)
    public Response initWebNewsShow(@RequestParam(name = "siteId") int siteId) {
//        List<SysWebMenuVo> list = proSysWebNewsShowService.getTitlesByMenuId(hostIp,true,siteId);
//        if (list.isEmpty()) {
//            return ResponseUtlis.error(Constants.IS_EMPTY, list);
//        }
        return null;
    }

}

