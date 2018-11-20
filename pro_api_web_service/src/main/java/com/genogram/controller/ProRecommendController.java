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
import io.swagger.annotations.*;
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
@Api(description = "省级下属联谊会推荐文章人物查询")
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
    @ApiOperation(value = "省级首页县级推荐文章查询" ,  notes="recommendId 推荐表主键/ sizeName 网站名称/ id 文章主键Id/ " +
            "showId 显示位置Id/ newsTitle 标题/ newsText 内容/ industryLocation 家族产业具体地址/ visitNum 查看数/ source 1代表家族文化 2 代表家族产业 3代表记录家族/ " +
            "status 状态(0:删除;1:已发布;2:草稿3:不显示)/ fanNewsUploadFileList 附件集合/ id 附件主键/ newsId 文章主键/ showId 显示位置/" +
            "fileName 图片名称/ filePath 图片路径/ picIndex 是否封面(0.否;1:是封面)/ status 状态1显示.0不显示   ")
    @RequestMapping(value = "/index/getRecommendArticle",method = RequestMethod.GET)
    public Response<IndustryDetailVo> getRecommendArticle(
            @ApiParam(value = "网站Id") @RequestParam(value = "siteId") Integer siteId
    ) {
        try {
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=2;
            //来源:(1县级,2省级)
            int newsSource=1;
            Map map=new HashMap(16);
            map.put("siteId",siteId);
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
     *省级首页县级推荐人物家族栋梁查询
     *@Author: yuzhou
     *@Date: 2018-11-16
     *@Time: 18:07
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "省级首页县级推荐人物家族栋梁查询" ,  notes="recommendId 推荐主键/ sizeName 网站名称/ id 家族长老表主键Id/ showId 显示位置/" +
            "personName 人名/ personSummary 人物简介/ picFileSrc 头像图片位置/ picFileName 头像名/ visitNum 查看数/  ")
    @RequestMapping(value = "/index/getRecommendFigureRooftree",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getRecommendFigureRooftree(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId
    ) {
        try {
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=1;
            //来源:(1县级,2省级)
            int newsSource=1;
            Map map=new HashMap(16);
            map.put("siteId",siteId);
            map.put("status",status);
            map.put("newsSource",newsSource);
            //家族栋梁
            map.put("showId","22");
            List<FamilyPersonVo> familyPersonVo=proSysRecommendService.getRecommendFigure(map);
            return ResponseUtlis.success(familyPersonVo);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }


    /**
     *省级首页县级推荐人物家族长老查询
     *@Author: yuzhou
     *@Date: 2018-11-19
     *@Time: 18:36
     *@Param:
     *@return:
     *@Description:
    */
    @ApiOperation(value = "省级首页县级推荐人物家族长老查询" ,  notes="recommendId 推荐主键/ sizeName 网站名称/ id 家族长老表主键Id/ showId 显示位置/" +
            "personName 人名/ personSummary 人物简介/ picFileSrc 头像图片位置/ picFileName 头像名/ visitNum 查看数/  ")
    @RequestMapping(value = "/index/getRecommendFigureElder",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getRecommendFigure(
            @ApiParam(value = "网站Id")@RequestParam(value = "siteId") Integer siteId
    ) {
        try {
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status=1;
            //来源:(1县级,2省级)
            int newsSource=1;
            Map map=new HashMap(16);
            map.put("siteId",siteId);
            map.put("status",status);
            map.put("newsSource",newsSource);
            //家族长老
            map.put("showId","21");
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
    @ApiOperation(value = "省级首页文章推荐详情查询" , notes="recommendId 推荐表主键/ sizeName 网站名称/ id 文章主键Id/ " +
            "showId 显示位置Id/ newsTitle 标题/ newsText 内容/ industryLocation 家族产业具体地址/ visitNum 查看数/ source 1代表家族文化 2 代表家族产业 3代表记录家族/ " +
            "status 状态(0:删除;1:已发布;2:草稿3:不显示)/ fanNewsUploadFileList 附件集合/ id 附件主键/ newsId 文章主键/ showId 显示位置/" +
            "fileName 图片名称/ filePath 图片路径/ picIndex 是否封面(0.否;1:是封面)/ status 状态1显示.0不显示   ")
    @RequestMapping(value = "/index/getRecommendDetails",method = RequestMethod.GET)
    public Response<Object> getRecommendParticulars(
            @ApiParam(value = "主键Id")@RequestParam(value = "id") Integer id,
            @ApiParam(value = "1代表家族文化 2 代表家族产业 3代表记录家族")@RequestParam(value = "source") Integer source
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
    @ApiOperation(value = "省级首页人物推荐详情查询" ,  notes="recommendId 推荐主键/ sizeName 网站名称/ id 家族长老表主键Id/ showId 显示位置/" +
            "personName 人名/ personSummary 人物简介/ picFileSrc 头像图片位置/ picFileName 头像名/ visitNum 查看数/  ")
    @RequestMapping(value = "/index/getRecommendFigureDetails",method = RequestMethod.GET)
    public Response<FamilyPersonVo> getRecommendFigureParticulars(
            @ApiParam(value = "家族名人主键Id")@RequestParam(value = "id") Integer id
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
