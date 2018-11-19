package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entityvo.SysWebMenuVo;
import com.genogram.service.IProSysWebNewsShowService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(description = "省级菜单管理(前台)")
@RestController
@RequestMapping("/genogram/admin/proMenu")
@CrossOrigin(origins = "*")
public class ProIndexMenuController {
    @Autowired
    private IProSysWebNewsShowService proSysWebNewsShowService;

    @ApiOperation(value = "后台子栏目查询" ,  notes="siteId:网站id;menuId:主菜单id(2.家族文化,3.慈善公益,4.家族产业,5.家族名人,6.记录家族,7.组织架构,8.祖先分支,9.统谱编修)")
    @RequestMapping(value = "/getTitlesByMenuId", method = RequestMethod.GET)
    public Response getTitlesByMenuId(@RequestParam("siteId") int siteId, @RequestParam(name = "menuId") int menuId) {
        List<SysWebMenuVo> list = proSysWebNewsShowService.getTitlesByMenuId(siteId, menuId);
        if (list.isEmpty()) {
            return ResponseUtlis.error(Constants.IS_EMPTY, list);
        }
        return ResponseUtlis.success(list);
    }

    @ApiOperation(value = "后台子栏初始化" ,  notes="开通网站时候使用")
    @RequestMapping(value = "/initWebMenu", method = RequestMethod.POST)
    public Response initWebMenu(@RequestParam("siteId") int siteId) {
        proSysWebNewsShowService.initWebMenu(siteId);
        return ResponseUtlis.success(true);
    }

    @ApiOperation(value = "后台子栏目修改" ,  notes="id:菜单id;menuName:菜单名称")
    @RequestMapping(value = "/updateTitlesById", method = RequestMethod.POST)
    public Response updateTitlesById(@RequestParam("id") int id, @RequestParam(name = "menuName") String menuName) {
        proSysWebNewsShowService.updateTitlesById(id,menuName);
        return ResponseUtlis.success(true);
    }

    @ApiOperation(value = "后台子栏目删除" ,  notes="id:菜单id")
    @RequestMapping(value = "/delTitlesById", method = RequestMethod.GET)
    public Response delTitlesById(@RequestParam("id") int id) {
        proSysWebNewsShowService.delTitlesById(id);
        return ResponseUtlis.success(true);
    }

    @ApiOperation(value = "后台子栏添加" ,  notes="siteId:网站id;menuName:菜单名")
    @RequestMapping(value = "/addTitles", method = RequestMethod.POST)
    public Response addTitles(@RequestParam("siteId") int siteId, @RequestParam(name = "menuName") String menuName, @RequestParam(name = "parentId") int parentId) {
        proSysWebNewsShowService.addTitles(siteId,menuName,parentId);
        return ResponseUtlis.success(true);
    }
}

