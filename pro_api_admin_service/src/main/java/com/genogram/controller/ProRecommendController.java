package com.genogram.controller;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.CommonRecommendVo;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.IndustryDetailVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IProSysRecommendService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
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
            @ApiParam(value = "主键Id")@RequestParam(value = "id") Integer id //主键
    ) {
        try {
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
            @ApiParam(value = "主键Id")@RequestParam(value = "id") Integer id //文章主键
    ) {
        try {
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
            @ApiParam(value = "推荐表主键Id")@RequestParam(value = "recommendId") Integer recommendId //推荐主键
    ) {
        if(recommendId==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
        int status=3;
        return getFanSysRecommendResponse(recommendId, status);
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
            @ApiParam(value = "主键Id")@RequestParam(value = "recommendId") Integer recommendId //推荐主键
    ) {
        if(recommendId==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
        int status=2;
        return getFanSysRecommendResponse(recommendId, status);
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
    private Response<FanSysRecommend> getFanSysRecommendResponse(@RequestParam("recommendId") Integer recommendId, int status) {
        try {

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
    @ApiOperation(value = "省级后台设置手动推荐查询" ,  notes="")
    @RequestMapping(value = "/getManualRecommend",method = RequestMethod.GET)
    public Response<CommonRecommendVo> getManualRecommend(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId
    ) {
        try{
        if(siteId==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
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
            return ResponseUtlis.error(Constants.ERRO_CODE,null);
        }
            return ResponseUtlis.success(commonRecommendVo);
    }catch (Exception e){
        e.printStackTrace();
        return  ResponseUtlis.error(Constants.FAILURE_CODE,null);
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
    @ApiOperation(value = "省级后台设置手动推荐模糊查询" ,  notes="")
    @RequestMapping(value = "/getManualVagueRecommend",method = RequestMethod.GET)
    public Response<CommonRecommendVo> getManualVagueRecommend(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId,
            @ApiParam(value = "标题")@RequestParam(value = "newsTitle") String newsTitle
    ) {
        try{
            if(siteId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
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
            map.put("newsTitle",newsTitle);
            map.put("isAuto",isAuto);
            List<CommonRecommendVo> commonRecommendVo=proSysRecommendService.getManualRecommend(map);
            if(commonRecommendVo==null){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.success(commonRecommendVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,null);
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
    @ApiOperation(value = "省级后台设置自动推荐文章查询" ,  notes="")
    @RequestMapping(value = "/getAutomaticRecommendArticle",method = RequestMethod.GET)
    public Response<IndustryDetailVo> getAutomaticRecommendArticle(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId
    ) {
        try {
            if(siteId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
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
            return ResponseUtlis.success(industryDetailVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,null);
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
    @ApiOperation(value = "省级后台设置自动推荐文章模糊查询" ,  notes="")
    @RequestMapping(value = "/getAutomaticRecommendVagueArticle",method = RequestMethod.GET)
    public Response<IndustryDetailVo> getAutomaticRecommendVagueArticle(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId,
            @ApiParam(value = "标题")@RequestParam(value = "newsTitle") String newsTitle
    ) {
        try {
            if(siteId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
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
            map.put("newsTitle",newsTitle);
            map.put("isAuto",isAuto);
            List<IndustryDetailVo> industryDetailVo=proSysRecommendService.getRecommendArticle(map);
            return ResponseUtlis.success(industryDetailVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,null);
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
    @ApiOperation(value = "省级后台设置自动推荐人物查询" ,  notes="")
    @RequestMapping(value = "/getRecommendFigure",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getRecommendFigure(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId
    ) {
        try {
            if(siteId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
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
            return ResponseUtlis.success(familyPersonVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,null);
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
    @ApiOperation(value = "省级后台设置自动推荐人物模糊查询" ,  notes="")
    @RequestMapping(value = "/getRecommendVagueFigure",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getRecommendVagueFigure(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId,
            @ApiParam(value = "人物名称")@RequestParam(value = "personName") String personName
    ) {
        try {
            if(siteId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
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
            map.put("personName",personName);
            map.put("isAuto",isAuto);
            List<FamilyPersonVo> familyPersonVo=proSysRecommendService.getRecommendFigure(map);
            return ResponseUtlis.success(familyPersonVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}
