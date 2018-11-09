package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsIndustry;
import com.genogram.entityvo.FamilyIndustryVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IFanNewsIndustryService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
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
            @RequestParam(value = "showId") Integer showId,
            @RequestParam(value = "type") Integer type,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        try {
            //判断showId是否有值
            if(showId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态 1 代表发表 2 代表草稿
            int status1=1;
            int status2=2;
            List statusList  = new ArrayList();
            statusList.add(status1);
            statusList.add(status2);
            Page<FamilyIndustryVo> familyCultureVo = iFanNewsIndustryService.getFamilyIndustryPage(showId, statusList, pageNo, pageSize, type);
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
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        return getNewsDetailVoResponse(showId, id);
    }

    //联谊会家族产业各个产业的详情
    @RequestMapping(value ="/getFamilyIndustryAmend",method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyIndustryAmend(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        return getNewsDetailVoResponse(showId, id);
    }

    //联谊会家族产业各个产业文章进入修改页面
    private Response<NewsDetailVo> getNewsDetailVoResponse(@RequestParam("showId") Integer showId, @RequestParam("id") Integer id) {
        try {
            //判断showId是否有值
            if (showId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            NewsDetailVo newsDetailVo = iFanNewsIndustryService.getFamilyIndustryDetail(showId, id);
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    // 联谊会家族产业后台添加和修改
    @RequestMapping(value = "/addNews", method = RequestMethod.POST)
    public Response<FanNewsIndustry> addNews(FanNewsIndustry fanNewsIndustry, List<MultipartFile> pictures) {
        try{
            // 插入数据
            boolean b = iFanNewsIndustryService.addNews(fanNewsIndustry,pictures);
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
            //插入图片
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }

    }
}
