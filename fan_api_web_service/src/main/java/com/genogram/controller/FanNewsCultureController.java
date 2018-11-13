package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsCultureNews;
import com.genogram.entity.FanNewsCultureZipai;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsCultureZipaiVo;
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
    private IFanNewsCultureZipaiService fanNewsCultureZipaiService;
    @Autowired
    private IFanNewsCultureNewsService fanNewsCultureNewsService;

    /**
     * 联谊会家族字派查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:20
     * @Param:
     * @return:
     * @Description:
     */
    @RequestMapping(value = "/getCommonalityPage", method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> getCommonalityPage(
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
            Wrapper<FanNewsCultureZipai> entity = new EntityWrapper<FanNewsCultureZipai>();
            entity.eq("show_id", showId);
            entity.in("status", statusList);
            entity.orderBy("create_time", false);
            Page<NewsCultureZipaiVo> fanNewsCultureZipai = fanNewsCultureZipaiService.commonality(entity, pageNo, pageSize);
            if (fanNewsCultureZipai == null) {
                //没有取到参数,返回空参
                Page<FamilyCultureVo> emptfamilyCultureVo = new Page<FamilyCultureVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.success(fanNewsCultureZipai);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会首页家族字派
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:20
     * @Param:
     * @return:
     * @Description:
     */
    @RequestMapping(value = "/index/getCommonalityIndexPage", method = RequestMethod.GET)
    public Response<StringBuffer> getCommonalityIndexPage(
            @RequestParam(value = "showId") Integer
                    showId // 家族文化显示位置
    ) {
        try {
            //判断showId是否有值
            if (showId==null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            List statusList = new ArrayList();
            statusList.add(1);
            Wrapper<FanNewsCultureZipai> entity = new EntityWrapper<FanNewsCultureZipai>();
            entity.eq("show_id", showId);
            entity.in("status", statusList);
            entity.orderBy("create_time", false);
            StringBuffer stringBuffer = fanNewsCultureZipaiService.commonalityIndex(entity);
            //判断该stringBuffer是否返回为null
            if (stringBuffer == null) {
                //没有取到参数,返回空参
                Page<FamilyCultureVo> emptfamilyCultureVo = new Page<FamilyCultureVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, emptfamilyCultureVo);
            }
            return ResponseUtlis.success(stringBuffer);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     *联谊会家族字派模糊查询
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 10:06
     *@Param:
     *@return:
     *@Description:
     */
    @RequestMapping(value = "/getZipaiVaguePage",method = RequestMethod.POST)
    public Response<FanNewsCultureZipai> getGrabblePage(
            @RequestParam(value = "showId") Integer showId, // 家族字派显示位置
            @RequestParam(value = "zipaiTxt") String zipaiTxt, // 家族字派模糊查询参数
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try{
            //返回的空list集合结构
            List<FanNewsCultureZipai> list=new ArrayList<>();
            //判断showId是否有值
            if(showId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,list);
            }
            Wrapper<FanNewsCultureZipai> entity = new EntityWrapper<FanNewsCultureZipai>();
            entity.eq("show_id",showId);
            entity.like("zipai_txt",zipaiTxt);
            Page<NewsCultureZipaiVo> fanNewsCultureZipaiPage = fanNewsCultureZipaiService.commonality(entity,pageNo, pageSize);
            if(fanNewsCultureZipaiPage==null){
                return ResponseUtlis.error(Constants.ERRO_CODE,list);
            }
            return ResponseUtlis.success(fanNewsCultureZipaiPage);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }


    /**
     * 联谊会家族文化查询
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
     * 联谊会首页家族文化查询
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
            Wrapper<FanNewsCultureNews> entity = new EntityWrapper<FanNewsCultureNews>();
                entity.eq("show_id", showId);
                entity.in("status", statusList);
                entity.orderBy("create_time", false);
            Page<FamilyCultureVo> familyCultureVoList = fanNewsCultureNewsService.getFamilyCulturePage(entity, pageNo, pageSize);
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
     * 联谊会家族文化详情查询
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
            NewsDetailVo newsDetailVo = fanNewsCultureNewsService.getFamilyCultureDetail(id);
            if (newsDetailVo == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, newsDetail);
            }
            //增加查看数
            fanNewsCultureNewsService.addVisitNum(id);
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

}

