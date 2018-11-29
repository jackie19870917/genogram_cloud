package com.genogram.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.FanNewsFamousPerson;
import com.genogram.entity.FanSysCharitableDeclare;
import com.genogram.entity.FanSysWebNewsShow;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.ProFamilyPersonVo;
import com.genogram.service.IFanSysCharitableDeclareService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaohei
 * @since 2018-11-29
 */
@Api(description = "联谊会家族慈善")
@RestController
@RequestMapping("/genogram/admin/fanSysCharitableDeclare")
public class FanSysCharitableDeclareController {
    @Autowired
    private IFanSysCharitableDeclareService iFanSysCharitableDeclareService;

    @ApiOperation(value = "慈善查询分页", notes = "show_id:网站id")
    @RequestMapping(value = "getSysCharitableDeclare", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> getFamilyStructureList(
            @RequestParam(value = "show_id") Integer showId,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        try {
            Page<FanSysCharitableDeclare> fanSysCharitableDeclarePage = iFanSysCharitableDeclareService.getCharitableDeclarePage(showId, pageNo, pageSize);
            if(fanSysCharitableDeclarePage==null){
                //没有取到参数,返回空参
                Page<FanSysCharitableDeclare> emptfamilyRecordVo = new Page<FanSysCharitableDeclare>();
                return ResponseUtlis.error(Constants.ERRO_CODE,"fanSysCharitableDeclarePage");
            }
            return ResponseUtlis.success(fanSysCharitableDeclarePage);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
    @ApiOperation(value = "慈善帮扶新增或修改", notes = "实体类")
    @RequestMapping(value = "addSysCharitableDeclare", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> addOrUpdateCharitableDeclare(FanSysCharitableDeclare fanSysCharitableDeclare,String fileName,String filePath) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanSysCharitableDeclare.setStatus(1);
        return getCharitableDeclare(fanSysCharitableDeclare,fileName,filePath);
    }
    private Response<FanSysCharitableDeclare> getCharitableDeclare(FanSysCharitableDeclare fanSysCharitableDeclare,String fileName,String filePath) {
        try {
            // 插入数据
            boolean b = iFanSysCharitableDeclareService.addOrUpdateCharitableDeclare(fanSysCharitableDeclare,fileName,filePath);
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
    @ApiOperation(value = "慈善帮扶删除", notes = "")
    @RequestMapping(value = "deleteSysCharitableDeclare", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> deleteRecordById(
            @RequestParam(value = "id")Integer id // 详情显示位置
    ) {
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status=0;
            Boolean aBoolean = iFanSysCharitableDeclareService.deleteCharitableDeclareById(id, status);
            if (!aBoolean){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}

