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
    @RequestMapping(value ="/getFamilyIndustryPage",method = RequestMethod.GET)
    public Response<FamilyIndustryVo> getFamilyCulturePage(
            @RequestParam(value = "showId") Integer showId,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
            ) {
        try {
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
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
                return ResponseUtlis.error(Constants.ERRO_CODE,emptfamilyCultureVo);
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
    @RequestMapping(value ="/getFamilyIndustryDetail",method = RequestMethod.GET)
    public Response<IndustryDetailVo> getFamilyIndustryDetail(
            @RequestParam(value = "id") Integer id, // 家族文化详情显示位置
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }
        return getNewsDetailVoResponse(id);
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
    @RequestMapping(value ="/getFamilyIndustryAmend",method = RequestMethod.GET)
    public Response<IndustryDetailVo> getFamilyIndustryAmend(
            @RequestParam(value = "id") Integer id, // 家族文化详情显示位置
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }
        return getNewsDetailVoResponse(id);
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
    private Response<IndustryDetailVo> getNewsDetailVoResponse( @RequestParam("id") Integer id) {
        try {
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
    @RequestMapping(value = "/addOrUpdateIndustry", method = RequestMethod.POST)
    public Response<FanNewsIndustry> addOrUpdateIndustry(FanNewsIndustry fanNewsIndustry,
                                                         String fileName,String filePath,
                                                         @ApiParam("token")@RequestParam(value = "token",required = false)String token) {
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }
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
    @RequestMapping(value = "/addOrUpdateIndustryDrft", method = RequestMethod.POST)
    public Response<FanNewsIndustry> addOrUpdateIndustryDrft(FanNewsIndustry fanNewsIndustry,
                                                             String fileName,String filePath,
                                                             @ApiParam("token")@RequestParam(value = "token",required = false)String token) {
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }
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
            //获取用户对象
            AllUserLogin userLoginInfoByToken = userService.getUserLoginInfoByToken(token);
            if(fanNewsIndustry.getId()==null){
                //创建人
                fanNewsIndustry.setCreateUser(userLoginInfoByToken.getId());
                //修改人
                fanNewsIndustry.setUpdateUser(userLoginInfoByToken.getId());
            }else{
                //修改人
                fanNewsIndustry.setUpdateUser(userLoginInfoByToken.getId());
            }
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
            //判断id是否为空
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status=0;
            Boolean isDel = fanNewsIndustryService.deleteIndustryById(id, status);
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
