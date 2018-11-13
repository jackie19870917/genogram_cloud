package com.genogram.controller;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.RecommendVo;
import com.genogram.service.IFanSysRecommendService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



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
@RequestMapping("genogram/admin/recommend")
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
            Boolean aBoolean = fanSysRecommendService.addRecommend(showId,id,status);
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
            int status=0;
            Wrapper<FanSysRecommend> entity = new EntityWrapper();
            entity.eq("show_id",showId);
            entity.eq("news_id",id);
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
     *联谊会后台设置个人推荐删除
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 10:39
     *@Param: 
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/deleteRecommend",method = RequestMethod.GET)
    public Response<FanSysRecommend> deleteRecommend(
            @RequestParam(value = "id") Integer id //主键
    ) {try {
        if(id==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
        int status=0;
        Wrapper<FanSysRecommend> entity = new EntityWrapper();
        entity.eq("id",id);
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
     *联谊会后台设置推荐查询
     *@Author: yuzhou
     *@Date: 2018-11-13
     *@Time: 11:16
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/getRecommendPage",method = RequestMethod.GET)
    public Response<FanSysRecommend> getRecommendPage(
            @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize
    ) {
        try {
            Wrapper<FanSysRecommend> entity = new EntityWrapper();
            entity.orderBy("create_time", false);
            Page<RecommendVo> recommendPage = fanSysRecommendService.getRecommendPage(entity, pageNo, pageSize);
            if(recommendPage==null){
                //没有取到参数,返回空参
                Page<RecommendVo> emptfamilyCultureVo = new Page<RecommendVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE,emptfamilyCultureVo);
            }
            return ResponseUtlis.success(recommendPage);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

}