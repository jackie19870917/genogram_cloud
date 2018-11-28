package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsFamousAncestor;
import com.genogram.entityvo.AncestorsBranchVo;
import com.genogram.service.IFanNewsFamousAncestorService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import com.genogram.unit.StringsUtils;
import io.swagger.annotations.*;
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

    @Autowired
    private IUserService userService;

    /**
     *联谊会祖先查询
     *@Author: yuzhou
     *@Date: 2018-11-20
     *@Time: 11:52
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "联谊会后台祖先查询", notes =
                    "ancestorName 祖先名 --" +
                    "ancestorSummary 人物简介 --" +
                    "ancestorTitle 祖先头衔 --" +
                    "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键 --" +
                    "junwang 郡望 --" +
                    "parentId 父id --" +
                    "picFileName 头像名 --" +
                    "picFileSrc 头像图片位置 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:发布;3:不显示) --" +
                    "tanghao 堂号 --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "zipai 字派")
    @RequestMapping(value = "/getFamousAncestorPage",method = RequestMethod.GET)
    public Response<FanNewsFamousAncestor> getFamousAncestorPage(
            @ApiParam(value = "网站Id") @RequestParam(value = "siteId") Integer siteId, // 显示位置
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "14") Integer pageSize,
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ){
        try {
            //判断token是否为空
            if(StringsUtils.isEmpty(token)){
                return ResponseUtlis.error(Constants.UNAUTHORIZED,"token不能为空");
            }
            //判断token是否正确
            if(StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))){
                return ResponseUtlis.error(Constants.FAILURE_CODE,"请输入正确的token");
            }
            //判断siteId是否为空
            if(siteId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            Page<FanNewsFamousAncestor> proFamilyRecordPage = fanNewsFamousAncestorService.getFamousAncestorPage(siteId, pageNo, pageSize);
            if(proFamilyRecordPage==null){
                //没有取到参数,返回空参
                Page<FanNewsFamousAncestor> proNewsFamousAncestor = new Page<FanNewsFamousAncestor>();
                return ResponseUtlis.error(Constants.ERRO_CODE,"proFamilyRecordPage为空");
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
    @ApiOperation(value = "联谊会后台祖先人物详情查询", notes =
                    "ancestorName 祖先名 --" +
                    "ancestorSummary 人物简介 --" +
                    "ancestorTitle 祖先头衔 --" +
                    "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键 --" +
                    "junwang 郡望 --" +
                    "parentId 父id --" +
                    "picFileName 头像名 --" +
                    "picFileSrc 头像图片位置 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:发布;3:不显示) --" +
                    "tanghao 堂号 --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "zipai 字派 --"+
                    "fanNewsFamousAncestorList 联谊会分支后裔集合")
    @RequestMapping(value = "/getFamousAncestorDetails",method = RequestMethod.GET)
    public Response<FanNewsFamousAncestor> getFamousAncestorDetails(
            @ApiParam(value = "主键Id")@RequestParam(value = "id") Integer id,// 显示位置
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ){
        try {
            //判断token是否为空
            if(StringsUtils.isEmpty(token)){
                return ResponseUtlis.error(Constants.UNAUTHORIZED,"token不能为空");
            }
            //判断token是否正确
            if(StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))){
                return ResponseUtlis.error(Constants.FAILURE_CODE,"请输入正确的token");
            }
            //判断主键是否为空
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
    @ApiOperation(value = "联谊会祖先后台添加模糊查询", notes =
            "source 1:县级 2省级 --" +
            "ancestorName 祖先名 --" +
            "ancestorSummary 人物简介 --" +
            "ancestorTitle 祖先头衔 --" +
            "createTime 创建时间 --" +
            "createUser 创建人 --" +
            "id 主键 --" +
            "junwang 郡望 --" +
            "parentId 父id --" +
            "picFileName 头像名 --" +
            "picFileSrc 头像图片位置 --" +
            "showId 显示位置Id --" +
            "status 状态(0:删除;1:发布;3:不显示) --" +
            "tanghao 堂号 --" +
            "updateTime 修改时间 --" +
            "updateUser 修改人 --" +
            "zipai 字派")
    @RequestMapping(value = "/getFamousAncestorVaguePage",method = RequestMethod.GET)
    public Response<FanNewsFamousAncestor> getFamousAncestorVaguePage(
            @ApiParam(value = "祖先名")@RequestParam(value = "ancestorName") String ancestorName,// 显示位置
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ){
        try {
            //判断token是否为空
            if(StringsUtils.isEmpty(token)){
                return ResponseUtlis.error(Constants.UNAUTHORIZED,"token不能为空");
            }
            //判断token是否正确
            if(StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))){
                return ResponseUtlis.error(Constants.FAILURE_CODE,"请输入正确的token");
            }
            //分页条件
            Page<AncestorsBranchVo> mapPage = new Page<>(pageNo, pageSize);
            Map map=new HashMap(16);
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
    @ApiOperation(value = "联谊会祖先后台添加 修改", notes = "ancestorName 祖先名 --" +
            "ancestorSummary 人物简介 --" +
            "ancestorTitle 祖先头衔 --" +
            "createTime 创建时间 --" +
            "createUser 创建人 --" +
            "id 主键 --" +
            "junwang 郡望 --" +
            "parentId 父id --" +
            "picFileName 头像名 --" +
            "picFileSrc 头像图片位置 --" +
            "showId 显示位置Id --" +
            "status 状态(0:删除;1:发布;3:不显示) --" +
            "tanghao 堂号 --" +
            "updateTime 修改时间 --" +
            "updateUser 修改人 --" +
            "zipai 字派")
    @RequestMapping(value = "/addFamousAncestor",method = RequestMethod.POST)
    public Response<FanNewsFamousAncestor> addFamousAncestor(
            @ApiParam(value = "省级主键Id")@RequestParam(value = "proIds",required = false) String proIds,// 显示位置
            @ApiParam(value = "县级主键Id")@RequestParam(value = "fanIds",required = false) String fanIds,// 显示位置
            @ApiParam(value = "祖先分支表")FanNewsFamousAncestor fanNewsFamousAncestor,
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ){
        try {
            //判断token是否为空
            if(StringsUtils.isEmpty(token)){
                return ResponseUtlis.error(Constants.UNAUTHORIZED,"token不能为空");
            }
            //获取用户对象
            AllUserLogin userLoginInfoByToken = userService.getUserLoginInfoByToken(token);
            //判断token是否正确
            if(StringsUtils.isEmpty(userLoginInfoByToken)){
                return ResponseUtlis.error(Constants.FAILURE_CODE,"请输入正确的token");
            }
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
            Boolean aBoolean = fanNewsFamousAncestorService.addFamousAncestor(fanNewsFamousAncestor, proSplit, fanSplit,userLoginInfoByToken);
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
            @ApiParam(value = "主键Id")@RequestParam(value = "id") Integer id,
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ){
        try {
            //判断token是否为空
            if(StringsUtils.isEmpty(token)){
                return ResponseUtlis.error(Constants.UNAUTHORIZED,"token不能为空");
            }
            //判断token是否正确
            if(StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))){
                return ResponseUtlis.error(Constants.FAILURE_CODE,"请输入正确的token");
            }
            //判断主键是否为空
            if(id==null){
              return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            Boolean aBoolean =fanNewsFamousAncestorService.deleteFamousAncestor(id);
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}

