package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsIndustry;
import com.genogram.entityvo.FamilyIndustryVo;
import com.genogram.entityvo.IndustryDetailVo;
import com.genogram.service.IFanNewsIndustryService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import com.genogram.unit.StringsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

/**
 *家族产业增删改查
 *@Author: yuzhou
 *@Date: 2018-11-09
 *@Time: 16:06
 *@Param:
 *@return:
 *@Description:
*/
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanNewsIndustry")
@Api(description = "联谊会家族产业增删改查")
public class FanNewsIndustryController {

    @Autowired
    private IFanNewsIndustryService fanNewsIndustryService;

    @Autowired
    private IUserService userService;

    /**
     *联谊会家族产业后台查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "联谊会家族产业后台查询", notes =
            "createTime 创建时间 --" +
            "createUser 创建人 --" +
            "id 主键Id --" +
            "industryLocation 家族产业具体地址 --" +
            "newsText 内容 --" +
            "newsTitle 标题 --" +
            "showId 显示位置Id --" +
            "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
            "updateTime 修改时间 --" +
            "updateUser 修改人 --" +
            "visitNum 查看数")
    @RequestMapping(value ="/getFamilyIndustryPage",method = RequestMethod.GET)
    public Response<FamilyIndustryVo> getFamilyCulturePage(
            @ApiParam(value = "显示位置Id")@RequestParam(value = "showId") Integer showId,
            @ApiParam(value = "当前页")@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数")@RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
            ) {
        try {
            //判断token是否为空
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //判断token是否正确
            if(StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))){
                return ResponseUtlis.error(Constants.FAILURE_CODE,"请输入正确的token");
            }
            //判断showId是否有值
            if(showId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            List statusList  = new ArrayList();
            statusList.add(1);
            statusList.add(2);
            //查询文章信息的条件
            Wrapper<FanNewsIndustry> entity = new EntityWrapper<FanNewsIndustry>();
            entity.eq("show_id", showId);
            if (statusList.size()!=0){
                entity.in("status", statusList);
            }
            entity.orderBy("create_time", false);
            Page<FamilyIndustryVo> familyCultureVo = fanNewsIndustryService.getFamilyIndustryPage(entity, pageNo, pageSize);
            if (familyCultureVo == null) {
                //没有取到参数,返回空参
                Page<FamilyIndustryVo> emptfamilyCultureVo = new Page<FamilyIndustryVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE,"familyCultureVo为空");
            }
            return ResponseUtlis.success(familyCultureVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *联谊会家族产业各个产业的详情
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "联谊会家族产业各个产业的详情", notes =
            "createTime 创建时间 --" +
            "createUser 创建人 --" +
            "id 主键Id --" +
            "industryLocation 家族产业具体地址 --" +
            "newsText 内容 --" +
            "newsTitle 标题 --" +
            "showId 显示位置Id --" +
            "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
            "updateTime 修改时间 --" +
            "updateUser 修改人 --" +
            "visitNum 查看数")
    @RequestMapping(value ="/getFamilyIndustryDetail",method = RequestMethod.GET)
    public Response<IndustryDetailVo> getFamilyIndustryDetail(
            @ApiParam(value = "主键Id")@RequestParam(value = "id") Integer id, // 家族文化详情显示位置
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
        return getNewsDetailVoResponse(id,token);
    }

    /**
     *联谊会家族产业各个产业进入修改
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:25
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "联谊会家族产业各个产业进入修改", notes =
            "createTime 创建时间 --" +
            "createUser 创建人 --" +
            "id 主键Id --" +
            "industryLocation 家族产业具体地址 --" +
            "newsText 内容 --" +
            "newsTitle 标题 --" +
            "showId 显示位置Id --" +
            "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
            "updateTime 修改时间 --" +
            "updateUser 修改人 --" +
            "visitNum 查看数")
    @RequestMapping(value ="/getFamilyIndustryAmend",method = RequestMethod.GET)
    public Response<IndustryDetailVo> getFamilyIndustryAmend(
            @ApiParam(value = "主键Id")@RequestParam(value = "id") Integer id, // 家族文化详情显示位置
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
        return getNewsDetailVoResponse(id,token);
    }

    /**
     *联谊会家族产业各个产业文章进入修改页面抽取方法
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
    */
    private Response<IndustryDetailVo> getNewsDetailVoResponse( @RequestParam("id") Integer id,String token) {
        try {
            //判断token是否为空
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //判断token是否正确
            if(StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))){
                return ResponseUtlis.error(Constants.FAILURE_CODE,"请输入正确的token");
            }
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            IndustryDetailVo industryDetailVo = fanNewsIndustryService.getFamilyIndustryDetail(id);
            return ResponseUtlis.success(industryDetailVo);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     *联谊会家族产业后台添加和修改 发表
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "联谊会家族产业后台添加和修改 发表", notes =
            "createTime 创建时间 --" +
            "createUser 创建人 --" +
            "id 主键Id --" +
            "industryLocation 家族产业具体地址 --" +
            "newsText 内容 --" +
            "newsTitle 标题 --" +
            "showId 显示位置Id --" +
            "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
            "updateTime 修改时间 --" +
            "updateUser 修改人 --" +
            "visitNum 查看数")
    @RequestMapping(value = "/addOrUpdateIndustry", method = RequestMethod.POST)
    public Response<FanNewsIndustry> addOrUpdateIndustry(
                                                         @ApiParam(value = "县级家族产业表")FanNewsIndustry fanNewsIndustry,
                                                         @ApiParam(value = "上传文件名称")@RequestParam(value = "fileName") String fileName,
                                                         @ApiParam(value = "上传文件地址")@RequestParam(value = "filePath") String filePath,
                                                         @ApiParam("token")@RequestParam(value = "token",required = false)String token) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsIndustry.setStatus(1);
        return getFanNewsIndustryResponse(fanNewsIndustry, fileName,filePath,token);
    }

    /**
     *联谊会家族产业后台添加和修改 草稿
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:10
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "联谊会家族产业后台添加和修改 草稿", notes =
            "createTime 创建时间 --" +
            "createUser 创建人 --" +
            "id 主键Id --" +
            "industryLocation 家族产业具体地址 --" +
            "newsText 内容 --" +
            "newsTitle 标题 --" +
            "showId 显示位置Id --" +
            "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
            "updateTime 修改时间 --" +
            "updateUser 修改人 --" +
            "visitNum 查看数")
    @RequestMapping(value = "/addOrUpdateIndustryDrft", method = RequestMethod.POST)
    public Response<FanNewsIndustry> addOrUpdateIndustryDrft(@ApiParam(value = "县级家族产业表")FanNewsIndustry fanNewsIndustry,
                                                             @ApiParam(value = "上传文件名称")@RequestParam(value = "fileName") String fileName,
                                                             @ApiParam(value = "上传文件地址")@RequestParam(value = "filePath") String filePath,
                                                             @ApiParam("token")@RequestParam(value = "token",required = false)String token) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsIndustry.setStatus(2);
        return getFanNewsIndustryResponse(fanNewsIndustry, fileName,filePath,token);
    }

    /**
     *联谊会家族产业后台添加和修改 抽取的方法
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:19
     *@Param:
     *@return:
     *@Description:
    */
    private Response<FanNewsIndustry> getFanNewsIndustryResponse(FanNewsIndustry fanNewsIndustry,
                                                                 String fileName,String filePath,
                                                                 @ApiParam("token")@RequestParam(value = "token",required = false)String token) {
        try {
            //判断token不能为空
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //获取用户对象
            AllUserLogin userLoginInfoByToken = userService.getUserLoginInfoByToken(token);
            //判断token是否正确
            if(StringsUtils.isEmpty(userLoginInfoByToken)){
                return ResponseUtlis.error(Constants.FAILURE_CODE,"请输入正确的token");
            }
            if(fanNewsIndustry.getId()==null) {
                //创建人
                fanNewsIndustry.setCreateUser(userLoginInfoByToken.getId());
            }
                //修改人
                fanNewsIndustry.setUpdateUser(userLoginInfoByToken.getId());
            // 插入数据
            fanNewsIndustryService.addOrUpdateIndustry(fanNewsIndustry, fileName,filePath);
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
            //插入图片
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     *联谊会家族产业后台删除
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:22
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value ="/deleteIndustryById",method = RequestMethod.GET)
    public Response<FanNewsIndustry> deleteIndustryById(
            @RequestParam(value = "id")Integer id, // 家族文化详情显示位置
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
        try {
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //获取用户对象
            AllUserLogin userLoginInfoByToken = userService.getUserLoginInfoByToken(token);
            //判断token是否正确
            if(StringsUtils.isEmpty(userLoginInfoByToken)){
                return ResponseUtlis.error(Constants.FAILURE_CODE,"请输入正确的token");
            }
            //判断id是否为空
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status=0;
            Boolean isDel = fanNewsIndustryService.deleteIndustryById(id, status,userLoginInfoByToken);
            if (!isDel){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}
