package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.ProNewsCultureNews;
import com.genogram.entity.ProNewsCultureZipai;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsCultureZipaiVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IProNewsCultureNewsService;
import com.genogram.service.IProNewsCultureZipaiService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 省级-家族文化后台控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/proNewsCulture")
public class ProNewsCultureController {

    @Autowired
    private IProNewsCultureZipaiService proNewsCultureZipaiService;

    /**
     *省级后台字派查询
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 16:06
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/getCommonalityPage",method = RequestMethod.GET)
    public Response<ProNewsCultureZipai> getCommonalityPage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            //没有取到参数,返回空参
            Page<ProNewsCultureZipai> emptfanNewsCultureZipai = new Page<ProNewsCultureZipai>();
            //判断showId是否有值
            if(showId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,emptfanNewsCultureZipai);
            }
            //状态
            List statusList  = new ArrayList();
            statusList.add(1);
            statusList.add(2);
            //查询条件
            Wrapper<ProNewsCultureZipai> entity = new EntityWrapper<ProNewsCultureZipai>();
            entity.eq("show_id", Integer.valueOf(showId));
            if(statusList.size()!=0){
                entity.in("status", statusList);
            }
            entity.orderBy("create_time", false);
            Page<ProNewsCultureZipai> commonality = proNewsCultureZipaiService.commonality(entity, pageNo, pageSize);
            if(commonality==null){
                return ResponseUtlis.error(Constants.ERRO_CODE,emptfanNewsCultureZipai);
            }
            return ResponseUtlis.success(commonality);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *省级字派进入后台页面
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 16:15
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/getZiPaiDetail",method = RequestMethod.GET)
    public Response<ProNewsCultureZipai> getZiPaiDetail(
            @RequestParam(value = "id") Integer id // 家族字派文章ID
    ){
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            ProNewsCultureZipai proNewsCultureZipai=proNewsCultureZipaiService.getZiPaiDetail(id);
            return ResponseUtlis.success(proNewsCultureZipai);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     *省级家族字派后台新增修改 发表
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:19
     *@Param:
     *@return:
     *@Description:
     */
    @RequestMapping(value = "/addOrUpdateZiPai",method = RequestMethod.POST)
    public Response<ProNewsCultureZipai> addOrUpdateZiPai(ProNewsCultureZipai proNewsCultureZipai){
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        proNewsCultureZipai.setStatus(1);
        return getFanNewsCultureZipaiResponse(proNewsCultureZipai);
    }

    /**
     *省级家族字派后台新增修改 草稿
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 11:16
     *@Param:
     *@return:
     *@Description:
     */
    @RequestMapping(value = "/addOrUpdateZiPaiDrft",method = RequestMethod.POST)
    public Response<ProNewsCultureZipai> addOrUpdateZiPaiDrft(ProNewsCultureZipai proNewsCultureZipai){
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        proNewsCultureZipai.setStatus(2);
        return getFanNewsCultureZipaiResponse(proNewsCultureZipai);
    }

    /**
     *省级家族字派后台新增修改 抽取的方法
     *@Author: yuzhou
     *@Date: 2018-11-100
     *@Time: 12:19
     *@Param:
     *@return:
     *@Description:
     */
    private Response<ProNewsCultureZipai> getFanNewsCultureZipaiResponse(ProNewsCultureZipai proNewsCultureZipai) {
        try {
            boolean result=proNewsCultureZipaiService.addOrUpdateZiPai(proNewsCultureZipai);
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
     *省级家族字派后台删除
     *@Author: yuzhou
     *@Date: 2018-11-14
     *@Time: 16:41
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value ="/deleteZipaiById",method = RequestMethod.GET)
    public Response<ProNewsCultureZipai> deleteZipaiById(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status=0;
            Boolean aBoolean = proNewsCultureZipaiService.deleteZipaiById(id, status);
            if(!aBoolean){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}

