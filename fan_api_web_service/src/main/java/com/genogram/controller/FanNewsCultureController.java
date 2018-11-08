package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsCultureZipai;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IFanNewsCultureNewsService;
import com.genogram.service.IFanNewsCultureZipaiService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 联谊会-家族文化前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/fanNewsCulture")
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
            int status=1;
            Page<FanNewsCultureZipai> fanNewsCultureZipai = iFanNewsCultureZipaiService.commonality(showId, status, pageNo, pageSize);
            if(fanNewsCultureZipai==null){
                //没有取到参数,返回空参
                Page<FamilyCultureVo> emptfamilyCultureVo = new Page<FamilyCultureVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.success(fanNewsCultureZipai);
        }catch (Exception e) {
                e.printStackTrace();
                return ResponseUtlis.error(Constants.FAILURE_CODE,null);
            }
    }

    //联谊会首页家族字派
    @RequestMapping(value = "/index/getCommonalityIndexPage",method = RequestMethod.GET)
    public Response<StringBuffer> getCommonalityIndexPage(
            @RequestParam(value = "showId") Integer showId // 家族文化显示位置
           ) {
        try {
            //判断showId是否有值
            if(showId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态
            int status=1;
            StringBuffer stringBuffer = iFanNewsCultureZipaiService.CommonalityIndex(showId, status);
            //判断该stringBuffer是否返回为null
            if(stringBuffer==null){
                //没有取到参数,返回空参
                Page<FamilyCultureVo> emptfamilyCultureVo = new Page<FamilyCultureVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE,emptfamilyCultureVo);
            }
            return ResponseUtlis.success(stringBuffer);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    //联谊会家族文化查询
    @RequestMapping(value ="/getFamilyCulturePage",method = RequestMethod.GET)
    public Response<FamilyCultureVo> getFamilyCulturePage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
            ) {
        //判断showId是否有值
        if(showId==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        return getFamilyCultureVoResponse(showId, pageNo, pageSize);
    }

    //联谊会首页家族文化查询
    @RequestMapping(value ="/index/getFamilyIndexCulturePage",method = RequestMethod.GET)
    public Response<FamilyCultureVo> getFamilyIndexCulturePage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
            ) {
        //判断showId是否有值
        if(showId==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        return getFamilyCultureVoResponse(showId, pageNo, pageSize);
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


    //抽取的家族文化方法
    private Response<FamilyCultureVo> getFamilyCultureVoResponse(Integer showId, Integer pageNo, Integer pageSize) {
        try {
            //状态
            int status=1;
            List statusList  = new ArrayList();
            statusList.add(status);
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



}

