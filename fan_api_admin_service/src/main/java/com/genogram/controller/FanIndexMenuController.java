package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entityvo.SysWebMenuVo;
import com.genogram.service.IFanSysWebNewsShowService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 菜单
 * @Author: wang,wei
 * @Date: 2018-11-05
 * @Time: 22:04
 * @return:
 * @Description:
 *
 */
@Api(description = "联谊会后台菜单接口")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanMenu")
public class FanIndexMenuController {
    @Autowired
    private IFanSysWebNewsShowService fanSysWebNewsShowService;

    @ApiOperation(value = "后台子栏目查询" ,  notes="siteId:网站id;menuId:主菜单id(2.家族文化,3.慈善公益,4.家族产业,5.家族名人,6.记录家族,7.组织架构,8.祖先分支,9.统谱编修)")
    @RequestMapping(value = "/getTitlesByMenuId", method = RequestMethod.GET)
    public Response getTitlesByMenuId(@RequestParam("siteId") int siteId, @RequestParam(name = "menuId") int menuId) {
        List<SysWebMenuVo> list = fanSysWebNewsShowService.getTitlesByMenuId(siteId, menuId);
        if (list.isEmpty()) {
            return ResponseUtlis.error(Constants.IS_EMPTY, "list为空");
        }
        return ResponseUtlis.success(list);
    }

    @ApiOperation(value = "后台子栏初始化" ,  notes="开通网站时候使用")
    @RequestMapping(value = "/initWebMenu", method = RequestMethod.POST)
    public Response initWebMenu(@RequestParam("siteId") int siteId) {
        fanSysWebNewsShowService.initWebMenu(siteId);
        return ResponseUtlis.success(true);
    }

    @ApiOperation(value = "后台子栏目修改" ,  notes="id:菜单id;menuName:菜单名称")
    @RequestMapping(value = "/updateTitlesById", method = RequestMethod.POST)
    public Response updateTitlesById(@RequestParam("id") int id, @RequestParam(name = "menuName") String menuName) {
        fanSysWebNewsShowService.updateTitlesById(id,menuName);
        return ResponseUtlis.success(true);
    }

    @ApiOperation(value = "后台子栏目删除" ,  notes="id:菜单id")
    @RequestMapping(value = "/delTitlesById", method = RequestMethod.GET)
    public Response delTitlesById(@RequestParam("id") int id) {
        fanSysWebNewsShowService.delTitlesById(id);
        return ResponseUtlis.success(true);
    }

    @ApiOperation(value = "后台子栏添加" ,  notes="siteId:网站id;menuName:菜单名")
    @RequestMapping(value = "/addTitles", method = RequestMethod.POST)
    public Response addTitles(@RequestParam("siteId") int siteId, @RequestParam(name = "menuName") String menuName, @RequestParam(name = "parentId") int parentId) {
        fanSysWebNewsShowService.addTitles(siteId,menuName,parentId);
        return ResponseUtlis.success(true);
    }
}