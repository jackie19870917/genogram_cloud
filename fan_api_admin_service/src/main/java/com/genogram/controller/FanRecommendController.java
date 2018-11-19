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
    @RequestMapping(value = "/addRecommendButton",method = RequestMethod.GET)
    public Response<FanSysRecommend> addRecommendButton(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "id") Integer id //主键
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
            fanSysRecommend.setStatus(isAuto);
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
    @RequestMapping(value = "/deleteRecommendButton",method = RequestMethod.GET)
    public Response<FanSysRecommend> deleteRecommendButton(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "id") Integer id //文章主键
    ) {
        try {
            if(showId==null || id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=3;
            //是否自动推荐(0:否;1:是)
            int isAuto=0;
            Wrapper<FanSysRecommend> entity = new EntityWrapper();
            entity.eq("show_id",showId);
            entity.eq("news_id",id);
            entity.eq("is_auto",isAuto);
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            entity.eq("status",1);
            Boolean aBoolean = fanSysRecommendService.deleteRecommend(entity,status);
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
    @RequestMapping(value = "/deleteRecommend",method = RequestMethod.GET)
    public Response<FanSysRecommend> deleteRecommend(
            @RequestParam(value = "recommendId") Integer recommendId //推荐主键
    ) {
        if(recommendId==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
        int status=3;
        return getFanSysRecommendResponse(recommendId, status);
    }

    /**
     *联谊会后台设置个人推荐展示
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 18:32
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/updateRecommend",method = RequestMethod.GET)
    public Response<FanSysRecommend> updateRecommend(
            @RequestParam(value = "recommendId") Integer recommendId //推荐主键
    ) {
        if(recommendId==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
        int status=2;
        return getFanSysRecommendResponse(recommendId, status);
    }

    /**
     *联谊会后台设置个人推荐公共方法
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
            Boolean aBoolean = fanSysRecommendService.deleteRecommend(entity, status);
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
     *联谊会后台设置推荐查询
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
            @ApiParam(value = "网站Id")@RequestParam(value = "sizeId") Integer sizeId
    ) {
        try{
            if(sizeId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=1;
            //来源:(1县级,2省级)
            int newsSource=1;
            //是否自动推荐(0:否;1:是)
            int isAuto=0;
            Map map=new HashMap();
            map.put("sizeId",sizeId);
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

}
