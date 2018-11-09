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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.ValueExp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanNewsCulture")
public class FanNewsCultureController {

    @Autowired
    private IFanNewsCultureZipaiService iFanNewsCultureZipaiService;

    @Autowired
    private IFanNewsCultureNewsService iFanNewsCultureNewsService;

    //联谊会家族字派查询
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

    //联谊会家族字派进入修改
    @RequestMapping(value = "/getZiPaiDetail",method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> getZiPaiDetail(
            @RequestParam(value = "id") String id // 家族字派文章ID
    ){
        try {
            FanNewsCultureZipai fanNewsCultureZipai=iFanNewsCultureZipaiService.getZiPaiDetail(Integer.valueOf(id));
            return ResponseUtlis.success(fanNewsCultureZipai);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    //联谊会家族字派新增修改
    @RequestMapping(value = "/addOrUpdateZiPai",method = RequestMethod.POST)
    public Response<FanNewsCultureZipai> addZiPai(FanNewsCultureZipai fanNewsCultureZipai){
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


 /*   //联谊会家族字派查询
    @RequestMapping(value = "/getGrabblePage",method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> getGrabblePage(
            @RequestParam(value = "showId") Integer showId, // 家族字派显示位置
            @RequestParam(value = "zipaiTxt") Integer zipaiTxt, // 家族字派模糊查询参数
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        return null;
    }*/

    //联谊会家族文化查询
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

    //联谊会家族文化后台详情查询
    @RequestMapping(value ="/getFamilyCultureDetail",method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyCultureDetail(
            @RequestParam(value = "id") String id // 家族文化详情显示位置
    ) {
        return getNewsDetailVoResponse( id);
    }

    //家族文化后台进入修改页面
    @RequestMapping(value ="/getFamilyCultureAmend",method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyCultureAmend(
            @RequestParam(value = "id") String id // 家族文化详情显示位置
    ) {
        return getNewsDetailVoResponse(id);
    }

    //家族文化详情修改公共方法
    private Response<NewsDetailVo> getNewsDetailVoResponse(@RequestParam("id") String id) {
        try {
            NewsDetailVo newsDetailVo = iFanNewsCultureNewsService.getFamilyCultureDetail(Integer.valueOf(id));
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    // 家族文化后台添加和修改
    @RequestMapping(value = "/addOrUpdateCulture", method = RequestMethod.POST)
    public Response<FanNewsCultureNews> addNews(FanNewsCultureNews fanNewsCultureNews, String urs) {
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
}
