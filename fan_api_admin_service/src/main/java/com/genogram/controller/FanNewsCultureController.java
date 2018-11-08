package com.genogram.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanNewsCulture/")
public class FanNewsCultureController {

    @Autowired
    private IFanNewsCultureZipaiService iFanNewsCultureZipaiService;

    @Autowired
    private IFanNewsCultureNewsService iFanNewsCultureNewsService;

    //联谊会家族字派查询
    @RequestMapping(value = "/getCommonalityPage",method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> getCommonalityPage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            //判断showId是否有值
            if(showId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态
            Integer status=1;
            Page<FanNewsCultureZipai> fanNewsCultureZipai = iFanNewsCultureZipaiService.commonality(showId, status, pageNo, pageSize);
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

    //联谊会家族字派新增
    @RequestMapping(value = "/addZiPai",method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> addZiPai(FanNewsCultureZipai fanNewsCultureZipai){
        try {
            Integer insert=iFanNewsCultureZipaiService.addZiPai(fanNewsCultureZipai);
            if(insert==null){
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
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try{
        //判断showId是否有值
        if(showId==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        //状态
        int status1=1; //  1代表发表
        int status2=2; //   2代表草稿
        List statusList  = new ArrayList();
        statusList.add(status1);
        statusList.add(status2);
        Page<FamilyCultureVo> familyCultureVoList = iFanNewsCultureNewsService.getFamilyCulturePage(showId, statusList, pageNo, pageSize);
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

    //联谊会家族文化详情查询
    @RequestMapping(value ="/getFamilyCultureDetail",method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyCultureDetail(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        try{
            //判断showId是否有值
            if(showId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            NewsDetailVo newsDetailVo= iFanNewsCultureNewsService.getFamilyCultureDetail(showId,id);
            return ResponseUtlis.success(newsDetailVo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    // 家族文化后台添加
    @RequestMapping(value = "/addNews", method = RequestMethod.POST)
    public Response<FanNewsCultureNews> addNews(FanNewsCultureNews fanNewsCultureNews, List<MultipartFile> pictures) {
        try{
            // 插入数据
            boolean b = iFanNewsCultureNewsService.addNews(fanNewsCultureNews,pictures);
                return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
            //插入图片
        } catch (Exception e) {
        e.printStackTrace();
        return ResponseUtlis.error(Constants.FAILURE_CODE,null);
    }

    }
}
