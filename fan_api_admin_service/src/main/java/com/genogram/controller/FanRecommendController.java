package com.genogram.controller;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.CommonRecommendVo;
import com.genogram.service.IFanSysRecommendService;
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
 *联谊会推荐
 *@Author: Toxicant
 *@Date: 2018-11-09
 *@Time: 14:23
 *@Param:
 *@return:
 *@Description:
*/

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/fanRecommend")
@Api(description = "联谊会推荐")
public class FanRecommendController {

    @Autowired
    private IFanSysRecommendService fanSysRecommendService;

    /**
     *联谊会后台点击推荐
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 9:16
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "联谊会后台点击推荐", notes ="")
    @RequestMapping(value = "/addRecommendButton",method = RequestMethod.GET)
    public Response<FanSysRecommend> addRecommendButton(
            @ApiParam(value = "显示位置Id")@RequestParam(value = "showId") Integer showId,
            @ApiParam(value = "主键Id")@RequestParam(value = "id") Integer id //主键
    ) {
        try {
            if(showId==null || id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=1;
            //来源:(1县级,2省级)
            int newsSource=1;
            //是否自动推荐(0:否;1:是)
            int isAuto=0;
            //要插入的实体类
            FanSysRecommend fanSysRecommend=new FanSysRecommend();
            fanSysRecommend.setNewsId(id);
            fanSysRecommend.setShowId(showId);
            fanSysRecommend.setStatus(status);
            fanSysRecommend.setIsAuto(isAuto);
            fanSysRecommend.setNewsSource(newsSource);
            Boolean aBoolean = fanSysRecommendService.addRecommend(fanSysRecommend);
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
     *联谊会后台点击取消
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 10:04
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "联谊会后台点击取消", notes ="")
    @RequestMapping(value = "/deleteRecommendButton",method = RequestMethod.GET)
    public Response<FanSysRecommend> deleteRecommendButton(
            @ApiParam(value = "显示位置Id")@RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @ApiParam(value = "主键Id")@RequestParam(value = "id") Integer id //文章主键
    ) {
        try {
            if(showId==null || id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //来源:(1县级,2省级)
            int newsSource=1;
            //是否自动推荐(0:否;1:是)
            int isAuto=0;
            Wrapper<FanSysRecommend> entity = new EntityWrapper();
            entity.eq("show_id",showId);
            entity.eq("news_id",id);
            entity.eq("news_source",newsSource);
            entity.eq("is_auto",isAuto);
            Boolean aBoolean = fanSysRecommendService.recommendDelete(entity);
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
     *联谊会后台设置个人推荐取消展示
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 10:39
     *@Param: 
     *@return:
     *@Description:
    */
    @ApiOperation(value = "联谊会后台设置个人推荐取消展示", notes ="")
    @RequestMapping(value = "/deleteRecommend",method = RequestMethod.GET)
    public Response<FanSysRecommend> deleteRecommend(
            @ApiParam(value = "推荐表主键")@RequestParam(value = "recommendId") Integer recommendId
    ) {
        try {
            if(recommendId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=3;
            Wrapper<FanSysRecommend> entity = new EntityWrapper();
            entity.eq("id", recommendId);
            Boolean aBoolean = fanSysRecommendService.deleteRecommend(recommendId);
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
     *联谊会后台设置手动推荐查询
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 11:16
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "县级后台设置手动推荐查询" ,  notes="")
    @RequestMapping(value = "/getManualRecommend",method = RequestMethod.GET)
    public Response<FanSysRecommend> getManualRecommend(
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
            map.put("fanSiteId",siteId);
            map.put("status",status);
            map.put("newsSource",newsSource);
            map.put("isAuto",isAuto);
            List<CommonRecommendVo> commonRecommendVo=fanSysRecommendService.getManualRecommend(map);
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
     *县级后台设置手动推荐模糊查询
     *@Author: yuzhou
     *@Date: 2018-11-26
     *@Time: 18:15
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "县级后台设置手动推荐模糊查询" ,  notes="")
    @RequestMapping(value = "/getManualRecommendVague",method = RequestMethod.GET)
    public Response<FanSysRecommend> getManualRecommendVague(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId,
            @ApiParam(value = "标题模糊")@RequestParam(value = "title") Integer title
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
            map.put("fanSiteId",siteId);
            map.put("status",status);
            map.put("newsSource",newsSource);
            map.put("isAuto",isAuto);
            map.put("newsTitle",title);
            List<CommonRecommendVo> commonRecommendVo=fanSysRecommendService.getManualRecommend(map);
            if(commonRecommendVo==null){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.success(commonRecommendVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}
