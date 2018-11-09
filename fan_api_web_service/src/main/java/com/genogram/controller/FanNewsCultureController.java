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

    /**
     *联谊会家族字派查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:20
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
                Page<FamilyCultureVo> emptfamilyCultureVo = new Page<FamilyCultureVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.success(fanNewsCultureZipai);
        }catch (Exception e) {
                e.printStackTrace();
                return ResponseUtlis.error(Constants.FAILURE_CODE,null);
            }
    }

    /**
     *联谊会首页家族字派
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:20
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/index/getCommonalityIndexPage",method = RequestMethod.GET)
    public Response<StringBuffer> getCommonalityIndexPage(
            @RequestParam(value = "showId") String
                                                                                                                                                                      showId // 家族文化显示位置
           ) {
        try {
            //判断showId是否有值
            if(StringUtils.isEmpty(showId)){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态
            List statusList  = new ArrayList();
            statusList.add(1);
            Wrapper<FanNewsCultureZipai> entity = new EntityWrapper<FanNewsCultureZipai>();
            entity.eq("show_id", Integer.valueOf(showId));
            if(statusList.size()!=0){
                entity.in("status", statusList);
            }
            entity.orderBy("create_time", false);
            StringBuffer stringBuffer = iFanNewsCultureZipaiService.commonalityIndex(entity);
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

    /**
     *联谊会家族文化查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:21
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
        //判断showId是否有值
        if(StringUtils.isEmpty(showId)){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        return getFamilyCultureVoResponse(showId, pageNo, pageSize);
    }

    /**
     *联谊会首页家族文化查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:21
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value ="/index/getFamilyIndexCulturePage",method = RequestMethod.GET)
    public Response<FamilyCultureVo> getFamilyIndexCulturePage(
            @RequestParam(value = "showId") String showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
            ) {
        //判断showId是否有值
        if(StringUtils.isEmpty(showId)){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        return getFamilyCultureVoResponse(showId, pageNo, pageSize);
    }

    /**
     *联谊会家族文化详情查询
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:21
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value ="/getFamilyCultureDetail",method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyCultureDetail(
            @RequestParam(value = "id") String id // 家族文化文章ID
    ) {
        try{
            NewsDetailVo newsDetailVo= iFanNewsCultureNewsService.getFamilyCultureDetail(Integer.valueOf(id));
            return ResponseUtlis.success(newsDetailVo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *抽取的家族文化方法
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:21
     *@Param:
     *@return:
     *@Description:
    */
    private Response<FamilyCultureVo> getFamilyCultureVoResponse(String showId, Integer pageNo, Integer pageSize) {
        try {
            //状态
            List statusList  = new ArrayList();
            statusList.add(1);
            //查询文章信息的条件
            Wrapper<FanNewsCultureNews> entity = new EntityWrapper<FanNewsCultureNews>();
            entity.eq("show_id", Integer.valueOf(showId));
            if(statusList.size()!=0){
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



}

