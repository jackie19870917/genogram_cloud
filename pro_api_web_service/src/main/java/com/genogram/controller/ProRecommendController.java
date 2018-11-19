package com.genogram.controller;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.IndustryDetailVo;
import com.genogram.service.IProSysRecommendService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
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
@RequestMapping("/genogram/admin/proRecommend")
public class ProRecommendController {

    @Autowired
    private IProSysRecommendService proSysRecommendService;

    /**
     *省级首页县级推荐文章查询
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 17:47
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/index/getRecommendArticle",method = RequestMethod.GET)
    public Response<IndustryDetailVo> getRecommendArticle(
            @RequestParam(value = "sizeId") Integer sizeId
    ) {
        try {
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=2;
            //来源:(1县级,2省级)
            int newsSource=1;
            Map map=new HashMap(16);
            map.put("sizeId",sizeId);
            map.put("status",status);
            map.put("newsSource",newsSource);
            List<IndustryDetailVo> industryDetailVo=proSysRecommendService.getRecommendArticle(map);
            return ResponseUtlis.success(industryDetailVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *省级首页县级推荐人物查询
     *@Author: yuzhou
     *@Date: 2018-11-16
     *@Time: 18:07
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/index/getRecommendFigure",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getRecommendFigure(
            @RequestParam(value = "sizeId") Integer sizeId
    ) {
        try {
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=2;
            //来源:(1县级,2省级)
            int newsSource=1;
            Map map=new HashMap(16);
            map.put("sizeId",sizeId);
            map.put("status",status);
            map.put("newsSource",newsSource);
            List<FamilyPersonVo> familyPersonVo=proSysRecommendService.getRecommendFigure(map);
            return ResponseUtlis.success(familyPersonVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *省级首页文章推荐详情查询
     *@Author: yuzhou
     *@Date: 2018-11-16
     *@Time: 19:08
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/index/getRecommendParticulars",method = RequestMethod.GET)
    public Response<Object> getRecommendParticulars(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "source") Integer source
    ) {
        try {
            //1代表家族文化 2 代表记录家族 3代表家族产业
            if(id==null && source==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            Object newsDetailVo=proSysRecommendService.getRecommendParticulars(id,source);
            if(newsDetailVo==null){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.success(newsDetailVo);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *省级首页人物推荐详情查询
     *@Author: yuzhou
     *@Date: 2018-11-17
     *@Time: 12:11
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/index/getRecommendFigureParticulars",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getRecommendFigureParticulars(
            @RequestParam(value = "id") Integer id
    ) {
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            FamilyPersonVo familyPersonVo=proSysRecommendService.getRecommendFigureParticulars(id);
            if(familyPersonVo==null){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.success(familyPersonVo);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}
