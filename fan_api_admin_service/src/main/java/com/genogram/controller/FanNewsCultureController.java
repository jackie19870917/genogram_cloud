package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsCultureNews;
import com.genogram.entity.FanNewsCultureZipai;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IFanNewsCultureNewsService;
import com.genogram.service.IFanNewsCultureZipaiService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import com.genogram.unit.StringUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.ValueExp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *联谊会家族文化增删改查
 *@Author: yuzhou
 *@Date: 2018-11-09
 *@Time: 16:00
 *@Param:
 *@return:
 *@Description:
*/
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanNewsCulture")
public class FanNewsCultureController {

    @Autowired
    private IFanNewsCultureZipaiService iFanNewsCultureZipaiService;

    @Autowired
    private IFanNewsCultureNewsService iFanNewsCultureNewsService;

    /**
     *联谊会家族字派后台查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:18
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/getCommonalityPage",method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> getCommonalityPage(
            @RequestParam(value = "showId") String showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            //判断showId是否有值
            if(StringUtils.isEmpty(showId)){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态
            List statusList  = new ArrayList();
            statusList.add(1);
            //查询条件
            Wrapper<FanNewsCultureZipai> entity = new EntityWrapper<FanNewsCultureZipai>();
                entity.eq("show_id", Integer.valueOf(showId));
            if(statusList.size()!=0){
                entity.in("status", statusList);
            }
            entity.orderBy("create_time", false);
            Page<FanNewsCultureZipai> fanNewsCultureZipai = iFanNewsCultureZipaiService.commonality(entity, pageNo, pageSize);
            if(fanNewsCultureZipai==null){
                //没有取到参数,返回空参
                Page<FanNewsCultureZipai> emptfanNewsCultureZipai = new Page<FanNewsCultureZipai>();
                return ResponseUtlis.error(Constants.ERRO_CODE,emptfanNewsCultureZipai);
            }
            return ResponseUtlis.success(fanNewsCultureZipai);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *联谊会家族字派后台进入修改
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:18
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/getZiPaiDetail",method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> getZiPaiDetail(
            @RequestParam(value = "id") String id // 家族字派文章ID
    ){
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            FanNewsCultureZipai fanNewsCultureZipai=iFanNewsCultureZipaiService.getZiPaiDetail(Integer.valueOf(id));
            return ResponseUtlis.success(fanNewsCultureZipai);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *联谊会家族字派后台新增修改 发表
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:19
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/addOrUpdateZiPai",method = RequestMethod.POST)
    public Response<FanNewsCultureZipai> addOrUpdateZiPai(FanNewsCultureZipai fanNewsCultureZipai){
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsCultureZipai.setStatus(1);
        return getFanNewsCultureZipaiResponse(fanNewsCultureZipai);
    }

    /**
     *联谊会家族字派后台新增修改 草稿
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 11:16
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/addOrUpdateZiPaiDrft",method = RequestMethod.POST)
    public Response<FanNewsCultureZipai> addOrUpdateZiPaiDrft(FanNewsCultureZipai fanNewsCultureZipai){
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsCultureZipai.setStatus(2);
        return getFanNewsCultureZipaiResponse(fanNewsCultureZipai);
    }

    /**
     *联谊会家族字派后台新增修改 抽取的方法
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:19
     *@Param:
     *@return:
     *@Description:
    */
    private Response<FanNewsCultureZipai> getFanNewsCultureZipaiResponse(FanNewsCultureZipai fanNewsCultureZipai) {
        try {
            boolean result=iFanNewsCultureZipaiService.addOrUpdateZiPai(fanNewsCultureZipai);
            if( ! result){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

 /**
  *联谊会家族字派后台删除
  *@Author: yuzhou
  *@Date: 2018-11-10
  *@Time: 10:05
  *@Param:
  *@return:
  *@Description:
 */
    @RequestMapping(value ="/deleteByIdZipai",method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> deleteByIdZipai(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status=2;
            Boolean aBoolean = iFanNewsCultureZipaiService.deleteByIdZipai(id, status);
            if(!aBoolean){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *联谊会家族文化后台查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:19
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value ="/getFamilyCulturePage",method = RequestMethod.GET)
    public Response<FamilyCultureVo> getFamilyCulturePage(
            @RequestParam(value = "showId") String showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try{
        //判断showId是否有值
        if(StringUtils.isEmpty(showId)){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        //状态
        List statusList  = new ArrayList();
        statusList.add(1);
        statusList.add(2);
        //查询文章信息的条件
        Wrapper<FanNewsCultureNews> entity = new EntityWrapper<FanNewsCultureNews>();
        entity.eq("show_id", Integer.valueOf(showId));
        if (statusList.size()!=0){
            entity.in("status", statusList);
        }
        entity.orderBy("create_time", false);
        Page<FamilyCultureVo> familyCultureVoList = iFanNewsCultureNewsService.getFamilyCulturePage(entity, pageNo, pageSize);
        if (familyCultureVoList == null) {
            //没有取到参数,返回空参
            Page<FamilyCultureVo> emptfamilyCultureVo = new Page<FamilyCultureVo>();
            return ResponseUtlis.error(Constants.ERRO_CODE,emptfamilyCultureVo);
        }
        return ResponseUtlis.success(familyCultureVoList);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseUtlis.error(Constants.FAILURE_CODE,null);
    }
    }

    /**
     *联谊会家族文化后台详情查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:19
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value ="/getFamilyCultureDetail",method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyCultureDetail(
            @RequestParam(value = "id") String id // 家族文化详情显示位置
    ) {
        if(id==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        return getNewsDetailVoResponse( id);
    }

    /**
     *家族文化后台进入修改页面
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:19
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value ="/getFamilyCultureAmend",method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyCultureAmend(
            @RequestParam(value = "id") String id // 家族文化详情显示位置
    ) {
        if(id==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        return getNewsDetailVoResponse(id);
    }

    /**
     *家族文化详情修改公共方法
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:20
     *@Param:
     *@return:
     *@Description:
    */
    private Response<NewsDetailVo> getNewsDetailVoResponse(@RequestParam("id") String id) {
        try {
            NewsDetailVo newsDetailVo = iFanNewsCultureNewsService.getFamilyCultureDetail(Integer.valueOf(id));
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     *家族文化后台添加和修改 发表
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:20
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/addOrUpdateCulture", method = RequestMethod.POST)
    public Response<FanNewsCultureNews> addOrUpdateCulture(FanNewsCultureNews fanNewsCultureNews, String urs) {
        return getFanNewsCultureNewsResponse(fanNewsCultureNews, urs);
    }

    /**
     *家族文化后台添加和修改 草稿
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:18
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/addOrUpdateCultureDrft", method = RequestMethod.POST)
    public Response<FanNewsCultureNews> addOrUpdateCultureDrft(FanNewsCultureNews fanNewsCultureNews, String urs) {
        return getFanNewsCultureNewsResponse(fanNewsCultureNews, urs);
    }

    /**
     *家族文化后台添加和修改 抽取的方法
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:18
     *@Param:
     *@return:
     *@Description:
    */
    private Response<FanNewsCultureNews> getFanNewsCultureNewsResponse(FanNewsCultureNews fanNewsCultureNews, String urs) {
        try{
            // 插入数据
            boolean insert = iFanNewsCultureNewsService.addNews(fanNewsCultureNews,urs);
            if( ! insert){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
            //插入图片
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }


    /**
     *联谊会家族文化后台删除
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 9:43
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value ="/deleteByIdCultur",method = RequestMethod.GET)
    public Response<FanNewsCultureNews> deleteByIdCultur(
            @RequestParam(value = "id")Integer id // 家族文化详情显示位置
    ) {
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态
            int status=2;
            Boolean aBoolean = iFanNewsCultureNewsService.deleteByIdCultur(id, status);
            if (!aBoolean){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}
