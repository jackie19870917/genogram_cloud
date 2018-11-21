package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsFamousAncestor;
import com.genogram.entity.ProNewsFamousAncestor;
import com.genogram.entityvo.AncestorsBranchVo;
import com.genogram.service.IFanNewsFamousAncestorService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *省级祖先分支
 *@Author: yuzhou
 *@Date: 2018-11-20
 *@Time: 11:40
 *@Param:
 *@return:
 *@Description:
*/
@Api(description = "联谊会前台祖先分支")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/proNewsAncestor")
public class FanNewsAncestorController {

    @Autowired
    private IFanNewsFamousAncestorService fanNewsFamousAncestorService;

    /**
     *省级祖先查询
     *@Author: yuzhou
     *@Date: 2018-11-20
     *@Time: 11:52
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "联谊会祖先查询", notes = "")
    @RequestMapping(value = "/getFamousAncestorPage",method = RequestMethod.GET)
    public Response<FanNewsFamousAncestor> getFamousAncestorPage(
            @ApiParam(value = "显示位置Id") @RequestParam(value = "showId") Integer showId, // 显示位置
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "14") Integer pageSize
    ){
        try {
            if(showId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //查询条件
            Wrapper<FanNewsFamousAncestor> entity=new EntityWrapper<>();
            entity.eq("show_id",showId);
            entity.orderBy("update_time", false);
            Page<FanNewsFamousAncestor> proFamilyRecordPage = fanNewsFamousAncestorService.getFamousAncestorPage(entity, pageNo, pageSize);
            if(proFamilyRecordPage==null){
                //没有取到参数,返回空参
                Page<FanNewsFamousAncestor> proNewsFamousAncestor = new Page<FanNewsFamousAncestor>();
                return ResponseUtlis.error(Constants.ERRO_CODE,proNewsFamousAncestor);
            }
            return ResponseUtlis.success(proFamilyRecordPage);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *省级祖先人物详情查询
     *@Author: yuzhou
     *@Date: 2018-11-20
     *@Time: 14:22
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "联谊会祖先人物详情查询", notes = "")
    @RequestMapping(value = "/getFamousAncestorDetails",method = RequestMethod.GET)
    public Response<FanNewsFamousAncestor> getFamousAncestorDetails(
            @ApiParam(value = "主键Id")@RequestParam(value = "id") Integer id// 显示位置
    ){
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            AncestorsBranchVo ancestorsBranchVo = fanNewsFamousAncestorService.getFamousAncestorDetails(id);
            if(ancestorsBranchVo==null){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.success(ancestorsBranchVo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}

