package com.genogram.controller;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.config.Constants;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.CommonRecommendVo;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.IndustryDetailVo;
import com.genogram.service.IProSysRecommendService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import com.genogram.unit.StringsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *省级推荐
 *@Author: Toxicant
 *@Date: 2018-11-09
 *@Time: 14:23
 *@Param:
 *@return:
 *@Description:
*/
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/proRecommend")
@Api(description = "省级推荐")
public class ProRecommendController {

    @Autowired
    private IProSysRecommendService proSysRecommendService;

    @Autowired
    private IUserService userService;

    /**
     *省级后台点击推荐
     *@Author: yuzhou
     *@Date: 2018-11-13p
     *@Time: 9:16
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "省级后台点击推荐" ,  notes="根据showId,id查询")
    @RequestMapping(value = "/addRecommendButton",method = RequestMethod.GET)
    public Response<FanSysRecommend> addRecommendButton(
            @ApiParam(value = "显示位置Id")@RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @ApiParam(value = "主键Id")@RequestParam(value = "id") Integer id, //主键
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
        try {
            //判断token是否为空
            if(StringsUtils.isEmpty(token)){
                return ResponseUtlis.error(Constants.UNAUTHORIZED,"token不能为空");
            }
            //判断token是否正确
            if(StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))){
                return ResponseUtlis.error(Constants.FAILURE_CODE,"请输入正确的token");
            }
            //判断showId id 是否为空
            if(showId==null || id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=1;
            //来源:(1县级,2省级)
            int newsSource=2;
            //是否自动推荐(0:否;1:是)
            int isAuto=0;
            //要插入的实体类
            FanSysRecommend fanSysRecommend=new FanSysRecommend();
            fanSysRecommend.setNewsId(id);
            fanSysRecommend.setShowId(showId);
            fanSysRecommend.setStatus(status);
            fanSysRecommend.setIsAuto(isAuto);
            fanSysRecommend.setNewsSource(newsSource);
            Boolean aBoolean = proSysRecommendService.addRecommend(fanSysRecommend);
            if(!aBoolean){
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *省级后台点击取消
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 10:04
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "省级后台点击取消" ,  notes="根据showId,id查询")
    @RequestMapping(value = "/deleteRecommendButton",method = RequestMethod.GET)
    public Response<FanSysRecommend> deleteRecommendButton(
            @ApiParam(value = "显示位置Id")@RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @ApiParam(value = "主键Id")@RequestParam(value = "id") Integer id, //文章主键
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
        try {
            //判断token是否为空
            if(StringsUtils.isEmpty(token)){
                return ResponseUtlis.error(Constants.UNAUTHORIZED,"token不能为空");
            }
            //判断token是否正确
            if(StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))){
                return ResponseUtlis.error(Constants.FAILURE_CODE,"请输入正确的token");
            }
            //判断showId id 是否为空
            if(showId==null || id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=0;
            //是否自动推荐(0:否;1:是)
            int isAuto=0;
            //来源:(1县级,2省级)
            int newsSource=2;
            Wrapper<FanSysRecommend> entity = new EntityWrapper();
            entity.eq("show_id",showId);
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            entity.eq("status",1);
            entity.eq("news_id",id);
            entity.eq("is_auto",isAuto);
            entity.eq("news_source",newsSource);
            Boolean aBoolean = proSysRecommendService.deleteRecommend(entity,status);
            if(!aBoolean){
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *省级后台设置个人推荐取消展示
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 10:39
     *@Param: 
     *@return:
     *@Description:
    */
    @ApiOperation(value = "省级后台设置个人推荐取消展示" ,  notes="根据id删除")
    @RequestMapping(value = "/deleteRecommend",method = RequestMethod.GET)
    public Response<FanSysRecommend> deleteRecommend(
            @ApiParam(value = "推荐表主键Id")@RequestParam(value = "recommendId") Integer recommendId, //推荐主键
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
        //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
        int status=3;
        return getFanSysRecommendResponse(recommendId, status,token);
    }

    /**
     *省级后台设置个人推荐展示
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 18:32
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "省级后台设置个人推荐展示" ,  notes="根据id")
    @RequestMapping(value = "/updateRecommend",method = RequestMethod.GET)
    public Response<FanSysRecommend> updateRecommend(
            @ApiParam(value = "主键Id")@RequestParam(value = "recommendId") Integer recommendId, //推荐主键
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
        //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
        int status=2;
        return getFanSysRecommendResponse(recommendId, status,token);
    }

    /**
     *省级后台设置个人推荐公共方法
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 18:32
     *@Param:
     *@return:
     *@Description:
    */
    private Response<FanSysRecommend> getFanSysRecommendResponse(@RequestParam("recommendId") Integer recommendId, int status,String token) {
        try {
            //判断token是否为空
            if(StringsUtils.isEmpty(token)){
                return ResponseUtlis.error(Constants.UNAUTHORIZED,"token不能为空");
            }
            //判断token是否正确
            if(StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))){
                return ResponseUtlis.error(Constants.FAILURE_CODE,"请输入正确的token");
            }
            //判断recommendId是否为空
            if(recommendId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            Wrapper<FanSysRecommend> entity = new EntityWrapper();
            entity.eq("id", recommendId);
            Boolean aBoolean = proSysRecommendService.deleteRecommend(entity, status);
            if (!aBoolean) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
    /**
     *省级后台设置手动推荐查询
     *@Author: yuzhou
     *@Date: 2018-11-19
     *@Time: 11:10
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "省级后台设置手动推荐查询" ,  notes=
            " 推荐表主键 recommendId --" +
            " 联谊会名称 sizeName --" +
            " 分类1代表家族文化 2代表记录家族 3代表家族产业 4代表家族名人 source --" +
            " 主键 id" +
            " 显示位置id(fan_sys_web_news_show_id) showId --" +
            " 标题 newsTitle --" +
            " 内容 newsText --" +
            " 查看数 visitNum --" +
            " 状态(0:删除;1:已发布;2:草稿3:不显示) status --" +
            " 创建时间 createTime --" +
            " 创建人 createUser --" +
            " 修改时间 updateTime --" +
            " 修改人 updateUser --" +
            " 家族产业具体地址 industryLocation --" +
            " 人名 personName --" +
            " 人物简介 personSummary --" +
            " 头像图片位置 picFileSrc --" +
            " 头像名 picFileName")
    @RequestMapping(value = "/getManualRecommend",method = RequestMethod.GET)
    public Response<CommonRecommendVo> getManualRecommend(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId,
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
        try{
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
            return ResponseUtlis.error(Constants.IS_EMPTY,"您好,请正确输入");
        }
        //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
        int status=1;
        //来源:(1县级,2省级)
        int newsSource=1;
        //是否自动推荐(0:否;1:是)
        int isAuto=0;
        Map map=new HashMap(16);
        map.put("siteId",siteId);
        map.put("status",status);
        map.put("newsSource",newsSource);
        map.put("isAuto",isAuto);
        List<CommonRecommendVo> commonRecommendVo=proSysRecommendService.getManualRecommend(map);
        if(commonRecommendVo==null){
            return ResponseUtlis.error(Constants.ERRO_CODE,"您好,没有您想要的数据");
        }
            return ResponseUtlis.success(commonRecommendVo);
    }catch (Exception e){
        e.printStackTrace();
        return  ResponseUtlis.error(Constants.FAILURE_CODE,"请联系客服");
    }
    }

    /**
     *省级后台设置手动推荐模糊查询
     *@Author: yuzhou
     *@Date: 2018-11-19
     *@Time: 11:10
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "省级后台设置手动推荐模糊查询" ,  notes=
            " 推荐表主键 recommendId --" +
            " 联谊会名称 sizeName --" +
            " 分类1代表家族文化 2代表记录家族 3代表家族产业 4代表家族名人 source --" +
            " 主键 id" +
            " 显示位置id(fan_sys_web_news_show_id) showId --" +
            " 标题 newsTitle --" +
            " 内容 newsText --" +
            " 查看数 visitNum --" +
            " 状态(0:删除;1:已发布;2:草稿3:不显示) status --" +
            " 创建时间 createTime --" +
            " 创建人 createUser --" +
            " 修改时间 updateTime --" +
            " 修改人 updateUser --" +
            " 家族产业具体地址 industryLocation --" +
            " 人名 personName --" +
            " 人物简介 personSummary --" +
            " 头像图片位置 picFileSrc --" +
            " 头像名 picFileName")
    @RequestMapping(value = "/getManualVagueRecommend",method = RequestMethod.GET)
    public Response<CommonRecommendVo> getManualVagueRecommend(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId,
            @ApiParam(value = "标题")@RequestParam(value = "newsTitle",required = false) String newsTitle,
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
        try{
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
                return ResponseUtlis.error(Constants.IS_EMPTY,"您好,请正确输入");
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=1;
            //来源:(1县级,2省级)
            int newsSource=1;
            //是否自动推荐(0:否;1:是)
            int isAuto=0;
            Map map=new HashMap(16);
            map.put("siteId",siteId);
            map.put("status",status);
            map.put("newsSource",newsSource);
            if(StringsUtils.isNotEmpty(newsTitle)){
                map.put("newsTitle",newsTitle);
            }
            map.put("isAuto",isAuto);
            List<CommonRecommendVo> commonRecommendVo=proSysRecommendService.getManualRecommend(map);
            if(commonRecommendVo==null){
                return ResponseUtlis.error(Constants.ERRO_CODE,"您好,没有您想要的数据");
            }
            return ResponseUtlis.success(commonRecommendVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,"请联系客服");
        }
    }

    /**
     *省级后台设置自动推荐文章查询
     *@Author: yuzhou
     *@Date: 2018-11-17
     *@Time: 17:55
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "省级后台设置自动推荐文章查询" ,  notes=
            " 推荐表主键 recommendId --" +
            " 联谊会名称 sizeName --" +
            " 分类1代表家族文化 2代表记录家族 3代表家族产业 4代表家族名人 source --" +
            " 主键 id" +
            " 显示位置id(fan_sys_web_news_show_id) showId --" +
            " 标题 newsTitle --" +
            " 内容 newsText --" +
            " 查看数 visitNum --" +
            " 状态(0:删除;1:已发布;2:草稿3:不显示) status --" +
            " 创建时间 createTime --" +
            " 创建人 createUser --" +
            " 修改时间 updateTime --" +
            " 修改人 updateUser --" +
            " 家族产业具体地址 industryLocation --" +
            " 人名 personName --" +
            " 人物简介 personSummary --" +
            " 头像图片位置 picFileSrc --" +
            " 头像名 picFileName")
    @RequestMapping(value = "/getAutomaticRecommendArticle",method = RequestMethod.GET)
    public Response<IndustryDetailVo> getAutomaticRecommendArticle(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId,
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
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
                return ResponseUtlis.error(Constants.IS_EMPTY,"您好,请正确输入");
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=2;
            //来源:(1县级,2省级)
            int newsSource=1;
            //是否自动推荐(0:否;1:是)
            int isAuto=1;
            Map map=new HashMap(16);
            map.put("siteId",siteId);
            map.put("status",status);
            map.put("newsSource",newsSource);
            map.put("isAuto",isAuto);
            List<IndustryDetailVo> industryDetailVo=proSysRecommendService.getRecommendArticle(map);
            if(industryDetailVo.size()==0){
                return ResponseUtlis.error(Constants.ERRO_CODE,"您好,没有您想要的数据");
            }
            return ResponseUtlis.success(industryDetailVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,"请联系客服");
        }
    }

    /**
     *省级后台设置自动推荐文章模糊查询
     *@Author: yuzhou
     *@Date: 2018-11-17
     *@Time: 17:55
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "省级后台设置自动推荐文章模糊查询" ,  notes=
            " 推荐表主键 recommendId --" +
            " 联谊会名称 sizeName --" +
            " 分类1代表家族文化 2代表记录家族 3代表家族产业 4代表家族名人 source --" +
            " 主键 id" +
            " 显示位置id(fan_sys_web_news_show_id) showId --" +
            " 标题 newsTitle --" +
            " 内容 newsText --" +
            " 查看数 visitNum --" +
            " 状态(0:删除;1:已发布;2:草稿3:不显示) status --" +
            " 创建时间 createTime --" +
            " 创建人 createUser --" +
            " 修改时间 updateTime --" +
            " 修改人 updateUser --" +
            " 家族产业具体地址 industryLocation --" +
            " 人名 personName --" +
            " 人物简介 personSummary --" +
            " 头像图片位置 picFileSrc --" +
            " 头像名 picFileName")
    @RequestMapping(value = "/getAutomaticRecommendVagueArticle",method = RequestMethod.GET)
    public Response<IndustryDetailVo> getAutomaticRecommendVagueArticle(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId,
            @ApiParam(value = "标题")@RequestParam(value = "newsTitle",required = false) String newsTitle,
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
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
                return ResponseUtlis.error(Constants.IS_EMPTY,"您好,请正确输入");
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=2;
            //来源:(1县级,2省级)
            int newsSource=1;
            //是否自动推荐(0:否;1:是)
            int isAuto=1;
            Map map=new HashMap(16);
            map.put("siteId",siteId);
            map.put("status",status);
            map.put("newsSource",newsSource);
            if(StringsUtils.isEmpty(newsTitle)){
                map.put("newsTitle",newsTitle);
            }
            map.put("isAuto",isAuto);
            List<IndustryDetailVo> industryDetailVo=proSysRecommendService.getRecommendArticle(map);
            if(industryDetailVo.size()==0){
                return ResponseUtlis.error(Constants.ERRO_CODE,"您好,没有您想要的数据");
            }
            return ResponseUtlis.success(industryDetailVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,"请联系客服");
}
    }

    /**
     *省级后台设置自动推荐人物查询
     *@Author: yuzhou
     *@Date: 2018-11-16
     *@Time: 18:07
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "省级后台设置自动推荐人物查询" ,  notes=
            "createTime 创建时间 --" +
            "createTimeLong 创建时间Long --" +
            "createUser 创建人Id" +
            "createUserName 创建人姓名名称 --" +
            "fanNewsUploadFileList 县级附件集合 --" +
            "id 主键 --" +
            "personName 人名 --" +
            "personSummary 人名简介 --" +
            "picFileName 头像名 --" +
            "picFileSrc 头像图片位置 --" +
            "recommendId 推荐表主键 --" +
            "showId 显示位置Id --" +
            "sizeName 网站名称 --" +
            "source 分类1代表家族文化 2代表记录家族 3代表家族产业 4代表家族名人 --" +
            "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
            "updateTime 修改时间 --" +
            "updateTimeLong 修改时间Long --" +
            "updateUser 修改人Id --" +
            "updateUserName 修改人姓名名称 --" +
            "visitNum 查看数")
    @RequestMapping(value = "/getRecommendFigure",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getRecommendFigure(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId,
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
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
                return ResponseUtlis.error(Constants.IS_EMPTY,"您好,请正确输入");
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=2;
            //来源:(1县级,2省级)
            int newsSource=1;
            //是否自动推荐(0:否;1:是)
            int isAuto=1;
            Map map=new HashMap(16);
            map.put("siteId",siteId);
            map.put("status",status);
            map.put("newsSource",newsSource);
            map.put("isAuto",isAuto);
            List<FamilyPersonVo> familyPersonVo=proSysRecommendService.getRecommendFigure(map);
            if(familyPersonVo.size()==0){
                return ResponseUtlis.error(Constants.ERRO_CODE,"您好,没有您想要的数据");
            }
            return ResponseUtlis.success(familyPersonVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,"请联系客服");
        }
    }

    /**
     *省级后台设置自动推荐人物模糊查询
     *@Author: yuzhou
     *@Date: 2018-11-16
     *@Time: 18:07
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "省级后台设置自动推荐人物模糊查询" ,  notes=
            "createTime 创建时间 --" +
            "createTimeLong 创建时间Long --" +
            "createUser 创建人Id" +
            "createUserName 创建人姓名名称 --" +
            "fanNewsUploadFileList 县级附件集合 --" +
            "id 主键 --" +
            "personName 人名 --" +
            "personSummary 人名简介 --" +
            "picFileName 头像名 --" +
            "picFileSrc 头像图片位置 --" +
            "recommendId 推荐表主键 --" +
            "showId 显示位置Id --" +
            "sizeName 网站名称 --" +
            "source 分类1代表家族文化 2代表记录家族 3代表家族产业 4代表家族名人 --" +
            "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
            "updateTime 修改时间 --" +
            "updateTimeLong 修改时间Long --" +
            "updateUser 修改人Id --" +
            "updateUserName 修改人姓名名称 --" +
            "visitNum 查看数")
    @RequestMapping(value = "/getRecommendVagueFigure",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getRecommendVagueFigure(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId,
            @ApiParam(value = "人物名称")@RequestParam(value = "personName",required = false) String personName,
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
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
                return ResponseUtlis.error(Constants.IS_EMPTY,"您好,请正确输入siteId");
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=2;
            //来源:(1县级,2省级)
            int newsSource=1;
            //是否自动推荐(0:否;1:是)
            int isAuto=1;
            Map map=new HashMap(16);
            map.put("siteId",siteId);
            map.put("status",status);
            map.put("newsSource",newsSource);
            if(StringsUtils.isEmpty(personName)){
                map.put("personName",personName);
            }
            map.put("isAuto",isAuto);
            List<FamilyPersonVo> familyPersonVo=proSysRecommendService.getRecommendFigure(map);
            if(familyPersonVo.size()==0){
                return ResponseUtlis.error(Constants.ERRO_CODE,"您好,没有您想要的数据");
            }
            return ResponseUtlis.success(familyPersonVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,"请联系客服");
        }
    }

    /**
     *省级后台设置手动推荐到全国
     *@Author: yuzhou
     *@Date: 2018-11-28
     *@Time: 15:14
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "省级后台设置手动推荐到全国" ,  notes="")
    @RequestMapping(value = "/getManuaRecommendNationwide",method = RequestMethod.GET)
    public Response<CommonRecommendVo> getManuaRecommendNationwide(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId,
            @ApiParam("token")@RequestParam(value = "token",required = false)String token
    ) {
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
                return ResponseUtlis.error(Constants.IS_EMPTY,"您好,请正确输入siteId");
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=1;
            //来源:(1县级,2省级)
            int newsSource=2;
            //是否自动推荐(0:否;1:是)
            int isAuto=0;
            Map map=new HashMap(16);
            map.put("siteId",siteId);
            map.put("status",status);
            map.put("newsSource",newsSource);
            map.put("isAuto",isAuto);
            List<CommonRecommendVo> commonRecommendVo=proSysRecommendService.getManuaRecommendNationwide(map);
            if(commonRecommendVo.size()==0){
                return ResponseUtlis.error(Constants.ERRO_CODE,"您好,没有您想要的数据");
            }
            return ResponseUtlis.success(commonRecommendVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,"请联系客服");
        }
    }
}
