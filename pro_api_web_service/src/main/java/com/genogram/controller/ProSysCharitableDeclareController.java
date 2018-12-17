package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanSysCharitableDeclare;
import com.genogram.service.IProFanSysCharitableDeclareService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiaohei
 * @since 2018-11-29
 */
@Api(description = "省级前台家族慈善申报")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/fanSysCharitableDeclare")
public class ProSysCharitableDeclareController {
    @Autowired
    private IProFanSysCharitableDeclareService fanSysCharitableDeclareService;

    @Autowired
    private IUserService userService;

    /**
     * 联谊会慈善帮扶申报详情页查询
     *
     * @Author: yuzhou
     * @Date: 2018-12-12
     * @Time: 15:32
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会慈善帮扶申报查询", notes = "show_id:网站id")
    @RequestMapping(value = "getSysCharitableDeclare", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> getFamilyStructureList(
            @ApiParam("显示位置Id") @RequestParam(value = "showId") Integer showId,
            @ApiParam("当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam("每页信息条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize){
        try {
            //判断id是否为空
            if (showId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "请输入showId");
            }
            //状态(0:审核通过;1:审核中;2:草稿3:审核不通过)
            List<Integer> list = new ArrayList<>();
            list.add(0);
            list.add(1);
            list.add(3);
            Wrapper<FanSysCharitableDeclare> entity = new EntityWrapper<FanSysCharitableDeclare>();
            entity.eq("show_id", showId);
            entity.in("status", list);
            Page<FanSysCharitableDeclare> fanSysCharitableDeclarePage = fanSysCharitableDeclareService.getCharitableDeclarePage(entity, pageNo, pageSize);
            if (fanSysCharitableDeclarePage == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "查询失败");
            }
            return ResponseUtlis.success(fanSysCharitableDeclarePage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会慈善帮扶申报详情
     *
     * @Author: yuzhou
     * @Date: 2018-12-12
     * @Time: 16:13
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会慈善帮扶申报详情", notes = "")
    @RequestMapping(value = "getFamilyStructureDetails", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> getFamilyStructureDetails(
            @ApiParam("主键Id") @RequestParam(value = "id") Integer id
    ) {
        try {
            //判断id是否为空
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "请输入Id");
            }
            FanSysCharitableDeclare fanSysCharitableDeclare = fanSysCharitableDeclareService.getFamilyStructureDetails(id);
            if (fanSysCharitableDeclare == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "查询失败");
            }
            return ResponseUtlis.success(fanSysCharitableDeclare);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}

