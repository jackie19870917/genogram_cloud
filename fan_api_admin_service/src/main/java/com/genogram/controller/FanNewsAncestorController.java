package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsFamousAncestor;
import com.genogram.entityvo.AncestorsBranchVo;
import com.genogram.service.IFanNewsFamousAncestorService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import com.genogram.unit.StringsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *联谊会祖先分支
 *@Author: yuzhou
 *@Date: 2018-11-20
 *@Time: 11:40
 *@Param:
 *@return:
 *@Description:
*/
@Api(description = "联谊会后台祖先分支")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/proNewsAncestor")
public class FanNewsAncestorController {

    @Autowired
    private IFanNewsFamousAncestorService fanNewsFamousAncestorService;

    /**
     *联谊会祖先查询
     *@Author: yuzhou
     *@Date: 2018-11-20
     *@Time: 11:52
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "联谊会后台祖先查询", notes = "")
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
     *联谊会祖先人物详情查询
     *@Author: yuzhou
     *@Date: 2018-11-20
     *@Time: 14:22
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "联谊会后台祖先人物详情查询", notes = "")
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

    /**
     *联谊会祖先后台添加模糊查询
     *@Author: yuzhou
     *@Date: 2018-11-20
     *@Time: 15:17
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "联谊会祖先后台添加模糊查询", notes = "")
    @RequestMapping(value = "/getFamousAncestorVaguePage",method = RequestMethod.GET)
    public Response<FanNewsFamousAncestor> getFamousAncestorVaguePage(
            @ApiParam(value = "祖先名")@RequestParam(value = "ancestorName") String ancestorName,// 显示位置
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ){
        try {
            Page<AncestorsBranchVo> mapPage = new Page<>(pageNo, pageSize);
            Map map=new HashMap();
            map.put("ancestorName",ancestorName);
            Page<AncestorsBranchVo> ancestorsBranchVo = fanNewsFamousAncestorService.getFamousAncestorVaguePage(mapPage,map);
            if(ancestorsBranchVo==null){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.success(ancestorsBranchVo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *联谊会祖先分支添加 修改
     *@Author: yuzhou
     *@Date: 2018-11-20
     *@Time: 17:32
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "联谊会祖先后台添加 修改", notes = "")
    @RequestMapping(value = "/addFamousAncestor",method = RequestMethod.GET)
    public Response<FanNewsFamousAncestor> addFamousAncestor(
            @ApiParam(value = "省级主键Id")@RequestParam(value = "proIds") String proIds,// 显示位置
            @ApiParam(value = "县级主键Id")@RequestParam(value = "fanIds") String fanIds,// 显示位置
            @ApiParam(value = "祖先分支表")FanNewsFamousAncestor fanNewsFamousAncestor
    ){
        try {
            //省级主键Id
            List<String> proSplit=null;
            //县级主键Id
            List<String> fanSplit=null;
            if (StringsUtils.isNotEmpty(proIds)){
                proSplit = Arrays.asList(proIds.split(","));

            }
            if (StringsUtils.isNotEmpty(fanIds)){
                fanSplit = Arrays.asList(fanIds.split(","));
            }
            Boolean aBoolean = fanNewsFamousAncestorService.addFamousAncestor(fanNewsFamousAncestor, proSplit, fanSplit);
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *联谊会祖先后台删除
     *@Author: yuzhou
     *@Date: 2018-11-20
     *@Time: 19:21
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "联谊会祖先后台删除", notes = "")
    @RequestMapping(value = "/deleteFamousAncestor",method = RequestMethod.GET)
    public Response<FanNewsFamousAncestor> deleteFamousAncestor(
            @ApiParam(value = "主键Id")@RequestParam(value = "id") Integer id
    ){
        try {
            if(id==null){
                ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            Boolean aBoolean =fanNewsFamousAncestorService.deleteFamousAncestor(id);
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}

