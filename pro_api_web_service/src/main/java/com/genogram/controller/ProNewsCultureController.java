package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsCultureZipai;
import com.genogram.entity.ProNewsCultureNews;
import com.genogram.entity.ProNewsCultureZipai;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsCultureZipaiVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.entityvo.ProNewsCultureZipaiVo;
import com.genogram.service.IProNewsCultureNewsService;
import com.genogram.service.IProNewsCultureZipaiService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 省级-家族文化前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/proNewsCulture")
public class ProNewsCultureController {

    @Autowired
    private IProNewsCultureZipaiService proNewsCultureZipaiService;

    @Autowired
    private IProNewsCultureNewsService proNewsCultureNewsService;

    /**
     * 省级家族字派详情页查询
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:20
     * @Param:
     * @return:
     * @Description:
     */
    @RequestMapping(value = "/getCommonalityPage", method = RequestMethod.GET)
    public Response<ProNewsCultureZipai> getCommonalityPage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            //判断showId是否有值
            if (showId==null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            List statusList = new ArrayList();
            statusList.add(1);
            //查询条件
            Wrapper<ProNewsCultureZipai> entity = new EntityWrapper<ProNewsCultureZipai>();
            entity.eq("show_id", showId);
            entity.in("status", statusList);
            entity.orderBy("create_time", false);
            Page<ProNewsCultureZipai> fanNewsCultureZipai = proNewsCultureZipaiService.commonality(entity, pageNo, pageSize);
            if (fanNewsCultureZipai == null) {
                //没有取到参数,返回空参
                Page<ProNewsCultureZipai> emptfamilyCultureVo = new Page<ProNewsCultureZipai>();
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.success(fanNewsCultureZipai);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     *省级家族字派模糊查询
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 10:06
     *@Param:
     *@return:
     *@Description:
     */
    @RequestMapping(value = "/getZipaiVaguePage",method = RequestMethod.GET)
    public Response<ProNewsCultureZipai> getZipaiVaguePage(
            @RequestParam(value = "showId") Integer showId, //
            @RequestParam(value = "zipaiTxt") String zipaiTxt, // 家族字派模糊查询参数
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try{
            //返回的空list集合结构
            List<ProNewsCultureZipai> list=new ArrayList<>();
            //判断showId是否有值
            if(showId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,list);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status=1;
            Page<ProNewsCultureZipaiVo> mapPage = new Page<ProNewsCultureZipaiVo>(pageNo,pageSize);

            Map map=new HashMap<>();
            map.put("showId",showId);
            map.put("zipaiTxt",zipaiTxt);
            map.put("status",status);
            Page<ProNewsCultureZipaiVo> zipaiVaguePage = proNewsCultureZipaiService.getZipaiVaguePage(mapPage,map);
            if(zipaiVaguePage==null){
                return ResponseUtlis.error(Constants.ERRO_CODE,list);
            }
            return ResponseUtlis.success(zipaiVaguePage);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *省级查出各个地区的字派
     *@Author: yuzhou
     *@Date: 2018-11-16
     *@Time: 9:53
     *@Param:
     *@return:
     *@Description:
     */
    @RequestMapping(value = "/getZipaiRegionPage",method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> getZipaiRegionPage(
            @RequestParam(value = "sizeId") Integer sizeId, // 家族字派显示位置
            @RequestParam(value = "code") Integer code, // 省级下属县级的地区编号
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        //判断sizeId是否有值
        if (sizeId==null && code==null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        int status=1;
        Page<FanNewsCultureZipai> mapPage = new Page<FanNewsCultureZipai>(pageNo,pageSize);

        Map map=new HashMap<>();
        map.put("code",code);
        map.put("status",1);

        Page<FanNewsCultureZipai> zipaiVaguePage =proNewsCultureZipaiService.getZipaiRegionPage(sizeId,mapPage,map);
        return ResponseUtlis.success(zipaiVaguePage);
    }

    /**
     * 省级家族文化查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    @RequestMapping(value = "/getFamilyCulturePage", method = RequestMethod.GET)
    public Response<FamilyCultureVo> getFamilyCulturePage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        //判断showId是否有值
        if (showId==null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }
        return getFamilyCultureVoResponse(showId, pageNo, pageSize);
    }

    /**
     * 省级首页家族文化查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    @RequestMapping(value = "/index/getFamilyIndexCulturePage", method = RequestMethod.GET)
    public Response<FamilyCultureVo> getFamilyIndexCulturePage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        //判断showId是否有值
        if (showId==null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }
        return getFamilyCultureVoResponse(showId, pageNo, pageSize);
    }

    /**
     * 抽取的家族文化方法查询方法
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    private Response<FamilyCultureVo> getFamilyCultureVoResponse(Integer showId, Integer pageNo, Integer pageSize) {
        try {
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            List statusList = new ArrayList();
            statusList.add(1);
            //查询文章信息的条件
            Wrapper<ProNewsCultureNews> entity = new EntityWrapper<ProNewsCultureNews>();
            entity.eq("show_id", showId);
            entity.in("status", statusList);
            entity.orderBy("create_time", false);
            Page<FamilyCultureVo> familyCultureVoList = proNewsCultureNewsService.getFamilyCulturePage(entity, pageNo, pageSize);
            if (familyCultureVoList == null) {
                //没有取到参数,返回空参
                Page<FamilyCultureVo> emptfamilyCultureVo = new Page<FamilyCultureVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, emptfamilyCultureVo);
            }
            return ResponseUtlis.success(familyCultureVoList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级家族文化详情查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    @RequestMapping(value = "/getFamilyCultureDetail", method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyCultureDetail(
            @RequestParam(value = "id") Integer id // 家族文化文章ID
    ) {
        try {
            //返回空参
            NewsDetailVo newsDetail = new NewsDetailVo();
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,newsDetail);
            }
            NewsDetailVo newsDetailVo = proNewsCultureNewsService.getFamilyCultureDetail(id);
            if (newsDetailVo == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, newsDetail);
            }
            //增加查看数
            proNewsCultureNewsService.addVisitNum(id);
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}

