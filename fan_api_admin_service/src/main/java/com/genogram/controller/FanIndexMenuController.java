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
    private IFanSysWebNewsShowService fanSysWebfNewsShowService;

    @ApiOperation(value = "后台子栏目查询" ,  notes="siteId:网站id;menuId:主菜单id(2.家族文化,3.慈善公益,4.家族产业,5.家族名人,6.记录家族,7.组织架构,8.祖先分支,9.统谱编修)")
    @RequestMapping(value = "/getTitlesByMenuId", method = RequestMethod.GET)
    public Response getTitlesByMenuId(@RequestParam("siteId") int siteId, @RequestParam(name = "menuId") int menuId) {
        List<SysWebMenuVo> list = fanSysWebfNewsShowService.getTitlesByMenuId(siteId, menuId);
        if (list.isEmpty()) {
            return ResponseUtlis.error(Constants.IS_EMPTY, list);
        }
        return ResponseUtlis.success(list);
    }


    @ApiOperation(value = "后台子栏目修改" ,  notes="siteId:网站id;menuName:菜单名称")
    @RequestMapping(value = "/updateTitlesById", method = RequestMethod.POST)
    public Response updateTitlesById(@RequestParam("siteId") int siteId, @RequestParam(name = "menuName") String menuName) {
        fanSysWebfNewsShowService.updateTitlesById(siteId,menuName);
        return ResponseUtlis.success(true);
    }
}

