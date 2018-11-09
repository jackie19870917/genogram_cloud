package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsIndustry;
import com.genogram.entityvo.FamilyIndustryVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IFanNewsIndustryService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import com.genogram.unit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanNewsIndustry")
public class FanNewsIndustryController {

    @Autowired
    private IFanNewsIndustryService iFanNewsIndustryService;

    //联谊会家族产业后台查询
    @RequestMapping(value ="/getFamilyIndustryPage",method = RequestMethod.GET)
    public Response<FamilyIndustryVo> getFamilyCulturePage(
            @RequestParam(value = "showId") String showId,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        try {
            //判断showId是否有值
            if(StringUtils.isEmpty(showId)){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态
            List statusList  = new ArrayList();
            statusList.add(1);
            statusList.add(2);
            //查询文章信息的条件
            Wrapper<FanNewsIndustry> entity = new EntityWrapper<FanNewsIndustry>();
            entity.eq("show_id", showId);
            if (statusList.size()!=0){
                entity.in("status", statusList);
            }
            if(StringUtils.isNotEmpty(type)){
                entity.eq("type",type);
            }
            entity.orderBy("create_time", false);
            Page<FamilyIndustryVo> familyCultureVo = iFanNewsIndustryService.getFamilyIndustryPage(entity, pageNo, pageSize);
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

    //联谊会家族产业各个产业的详情
    @RequestMapping(value ="/getFamilyIndustryDetail",method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyIndustryDetail(
            @RequestParam(value = "id") String id // 家族文化详情显示位置
    ) {
        return getNewsDetailVoResponse(id);
    }

    //联谊会家族产业各个产业的详情
    @RequestMapping(value ="/getFamilyIndustryAmend",method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyIndustryAmend(
            @RequestParam(value = "id") String id // 家族文化详情显示位置
    ) {
        return getNewsDetailVoResponse(id);
    }



    //联谊会家族产业各个产业文章进入修改页面抽取方法
    private Response<NewsDetailVo> getNewsDetailVoResponse( @RequestParam("id") String id) {
        try {
            NewsDetailVo newsDetailVo = iFanNewsIndustryService.getFamilyIndustryDetail(Integer.valueOf(id));
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    // 联谊会家族产业后台添加和修改
    @RequestMapping(value = "/addOrUpdateIndustry", method = RequestMethod.POST)
    public Response<FanNewsIndustry> addNews(FanNewsIndustry fanNewsIndustry, String urls) {
        try{
            // 插入数据
            boolean b = iFanNewsIndustryService.addNews(fanNewsIndustry,urls);
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
            //插入图片
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }

    }
}
