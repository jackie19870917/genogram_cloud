package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.FanNewsFamilyRecordVedio;
import com.genogram.entityvo.FamilyRecordVedioVo;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IAllCheckOutService;
import com.genogram.service.IFanNewsFamilyRecordService;
import com.genogram.service.IFanNewsFamilyRecordVedioService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author xiaohei
 */
@Api(description = "家族动态(后台)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanNewsFamilyRecord")
public class FanNewsFamilyRecordController {
    @Autowired
    private IFanNewsFamilyRecordService fanNewsFamilyRecordService;

    @Autowired
    private IFanNewsFamilyRecordVedioService fanNewsFamilyRecordVedioService;

    @Autowired
    private IAllCheckOutService allCheckOutService;
    /**
     * 后台家族动态查询
     */
    @ResponseBody
    @ApiOperation(value = "后台家族动态查询", notes = "id  编号 __"+
                                               "show_id  显示位置id(fan_sys_web_news_show_id)"+
                                                "news_title  标题"+
                                                "news_text  内容"+
                                                "visit_num  查看数"+
                                                "status  状态(0:删除;1:已发布;2:草稿3:不显示)"+
                                                "is_top  是否置顶(0:不置顶;1.置顶;)"+
                                                "create_time  创建时间"+
                                                "create_user  创建人"+
                                                "update_time  修改时间"+
                                                "update_user  修改人"
                                                )
    @RequestMapping(value = "selectRecortPage",method = RequestMethod.GET)
    public Response<FanNewsFamilyRecord> selectRecortPage(
            @RequestParam(value = "showId") Integer showId, // 显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ){
        try {
            List<Integer> list =new ArrayList<>();
            list.add(1);
            list.add(2);
            Page<FamilyRecordVo> familyRecordVo = fanNewsFamilyRecordService.getFamilyRecordPage(showId,list, pageNo, pageSize);
            if(familyRecordVo==null){
                //没有取到参数,返回空参
                Page<FamilyRecordVo> emptfamilyRecordVo = new Page<FamilyRecordVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE,"familyRecordVo");
            }
            return ResponseUtlis.success(familyRecordVo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
    /**
     *联谊会记录家族的详情
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "联谊会记录家族的详情", notes = "id  编号 __"+
            "show_id  显示位置id(fan_sys_web_news_show_id)"+
            "news_title  标题"+
            "news_text  内容"+
            "visit_num  查看数"+
            "status  状态(0:删除;1:已发布;2:草稿3:不显示)"+
            "is_top  是否置顶(0:不置顶;1.置顶;)"+
            "create_time  创建时间"+
            "create_user  创建人"+
            "update_time  修改时间"+
            "update_user  修改人"
    )
    @RequestMapping(value ="/getFamilyRecord",method = RequestMethod.GET)
    public Response<FamilyRecordVo> getFamilyRecordDetail(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        return getNewsDetailVoResponse(id);
    }
    /**
     *联谊会记录家族进入修改
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:25
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "联谊会记录家族进入修改", notes = "id  编号 __"+
            "show_id  显示位置id(fan_sys_web_news_show_id)"+
            "news_title  标题"+
            "news_text  内容"+
            "visit_num  查看数"+
            "status  状态(0:删除;1:已发布;2:草稿3:不显示)"+
            "is_top  是否置顶(0:不置顶;1.置顶;)"+
            "create_time  创建时间"+
            "create_user  创建人"+
            "update_time  修改时间"+
            "update_user  修改人"
    )
    @RequestMapping(value ="/getFamilyRecordAmend",method = RequestMethod.GET)
    public Response<FamilyRecordVo> getFamilyRecordAmend(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        return getNewsDetailVoResponse(id);
    }
    /**
     *联谊会记录家族各个产业文章进入修改页面抽取方法
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
     */
    private Response<FamilyRecordVo> getNewsDetailVoResponse( @RequestParam("id") Integer id) {
        try {
            NewsDetailVo newsDetailVo = fanNewsFamilyRecordService.getFamilyRecord(id);
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
    /**
     *联谊会记录家族后台添加和修改 发表
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "记录家族后台添加和修改 发表", notes = "id  编号 __"+
            "show_id  显示位置id(fan_sys_web_news_show_id)"+
            "news_title  标题"+
            "news_text  内容"+
            "visit_num  查看数"+
            "status  状态(0:删除;1:已发布;2:草稿3:不显示)"+
            "is_top  是否置顶(0:不置顶;1.置顶;)"+
            "create_time  创建时间"+
            "create_user  创建人"+
            "update_time  修改时间"+
            "update_user  修改人"
    )
        @RequestMapping(value = "/addOrUpdateRecord", method = RequestMethod.POST)
        public Response<FanNewsFamilyRecord> addOrUpdateRecord(FanNewsFamilyRecord fanNewsRecord, String fileName,String filePath) {


        Set set = allCheckOutService.getSensitiveWord(fanNewsRecord.getNewsText());

        if (set.size() >= 1) {
            return ResponseUtlis.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set);
        }
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsRecord.setStatus(1);
        return getFanNewsRecordResponse(fanNewsRecord, fileName,filePath);
    }

    /**
     *联谊会记录家族后台添加和修改 草稿
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:10
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "记录家族后台添加和修改 草稿", notes = "id  编号 __"+
            "show_id  显示位置id(fan_sys_web_news_show_id)"+
            "news_title  标题"+
            "news_text  内容"+
            "visit_num  查看数"+
            "status  状态(0:删除;1:已发布;2:草稿3:不显示)"+
            "is_top  是否置顶(0:不置顶;1.置顶;)"+
            "create_time  创建时间"+
            "create_user  创建人"+
            "update_time  修改时间"+
            "update_user  修改人"
    )
    @RequestMapping(value = "/addOrUpdateRecordDrft", method = RequestMethod.POST)
    public Response<FanNewsFamilyRecord> addOrUpdateRecordDrft(FanNewsFamilyRecord fanNewsRecord,String fileName,String filePath) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsRecord.setStatus(2);
        return getFanNewsRecordResponse(fanNewsRecord, fileName,filePath);
    }
    /**
     *联谊会记录家族后台添加和修改 抽取的方法
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:19
     *@Param:
     *@return:
     *@Description:
     */
    private Response<FanNewsFamilyRecord> getFanNewsRecordResponse(FanNewsFamilyRecord fanNewsRecord, String fileName,String filePath) {
        try {
            // 插入数据
            boolean b = fanNewsFamilyRecordService.addOrUpdateRecord(fanNewsRecord, fileName,filePath);
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
            //插入图片
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
    /**
     *联谊会记录家族后台删除
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:22
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "录家族后台删除", notes = "id  编号 __"+
            "show_id  显示位置id(fan_sys_web_news_show_id)"+
            "news_title  标题"+
            "news_text  内容"+
            "visit_num  查看数"+
            "status  状态(0:删除;1:已发布;2:草稿3:不显示)"+
            "is_top  是否置顶(0:不置顶;1.置顶;)"+
            "create_time  创建时间"+
            "create_user  创建人"+
            "update_time  修改时间"+
            "update_user  修改人"
    )
    @RequestMapping(value ="/deleteRecordById",method = RequestMethod.GET)
    public Response<FanNewsFamilyRecord> deleteRecordById(
            @RequestParam(value = "id")Integer id // 详情显示位置
    ) {
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status=0;
            Boolean aBoolean = fanNewsFamilyRecordService.deleteRecordById(id, status);
            if (!aBoolean){
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE,null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    /**
     * 官方视频
     */
    @ResponseBody
    @ApiOperation(value = "官方视频", notes = "id  编号 __"+
            "show_id  显示位置id(fan_sys_web_news_show_id)"+
            "vedio_type  视频种类1.个人;2官方"+
            "title  视频标题"+
            "auth  视频作者"+
            "status  状态(0:删除;1:已发布;2:草稿3:不显示)"+
            "is_top  是否置顶(0:不置顶;1.置顶;)"+
            "create_time  创建时间"+
            "create_user  创建人"+
            "update_time  修改时间"+
            "update_user  修改人"
    )
    @RequestMapping(value = "selectRecortVedioPage",method = RequestMethod.GET)
    public Response<FanNewsFamilyRecordVedio> selectRecortVedioPage (
            @RequestParam(value = "showId") Integer showId, // 产业显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ){
        try {
            int status = 1;
            Page<FamilyRecordVedioVo> familyRecordVedioVo = fanNewsFamilyRecordVedioService.getFamilyRecordVedioPage(showId, status, pageNo, pageSize);
            if(familyRecordVedioVo==null){
                //没有取到参数,返回空参
                Page<FamilyRecordVo> emptfamilyRecordVo = new Page<FamilyRecordVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE,"familyRecordVedioVo为空");
            }
            return ResponseUtlis.success(familyRecordVedioVo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
    /**
     *联谊会记录家族视频详情
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "联谊会记录家族视频详情", notes = "id  编号 __"+
            "show_id  显示位置id(fan_sys_web_news_show_id)"+
            "vedio_type  视频种类1.个人;2官方"+
            "title  视频标题"+
            "auth  视频作者"+
            "status  状态(0:删除;1:已发布;2:草稿3:不显示)"+
            "is_top  是否置顶(0:不置顶;1.置顶;)"+
            "create_time  创建时间"+
            "create_user  创建人"+
            "update_time  修改时间"+
            "update_user  修改人"
    )
    @RequestMapping(value ="/getFamilyRecordVedioDetail",method = RequestMethod.GET)
    public Response<FamilyRecordVedioVo> getFamilyRecordVedioDetail(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        return getNewsDetailVedioVoDetailResponse(id);
    }
    /**
     *联谊会记录家族视频进入修改
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:25
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "联谊会记录家族视频进入修改", notes = "id  编号 __"+
            "show_id  显示位置id(fan_sys_web_news_show_id)"+
            "vedio_type  视频种类1.个人;2官方"+
            "title  视频标题"+
            "auth  视频作者"+
            "status  状态(0:删除;1:已发布;2:草稿3:不显示)"+
            "is_top  是否置顶(0:不置顶;1.置顶;)"+
            "create_time  创建时间"+
            "create_user  创建人"+
            "update_time  修改时间"+
            "update_user  修改人"
    )
    @RequestMapping(value ="/getFamilyRecordVedioAmend",method = RequestMethod.GET)
    public Response<FamilyRecordVedioVo> getFamilyRecordVedioAmend(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        return getNewsDetailVedioVoResponse(id);
    }
    /**
     *联谊会记录家族视频文章进入修改页面抽取方法
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
     */
    private Response<FamilyRecordVedioVo> getNewsDetailVedioVoResponse( @RequestParam("id") Integer id) {
        try {
            NewsDetailVo newsDetailVo = fanNewsFamilyRecordVedioService.getFamilyVedioRecord(id);
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    private Response<FamilyRecordVedioVo> getNewsDetailVedioVoDetailResponse( @RequestParam("id") Integer id) {
        try {
            FamilyRecordVedioVo familyRecordVedioVo = fanNewsFamilyRecordVedioService.getFamilyVedioDetilRecord(id);
            return ResponseUtlis.success(familyRecordVedioVo);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
    /**
     *联谊会记录家族后台视频添加和修改 发表
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 16:24
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "联谊会记录家族后台视频添加和修改 发表", notes = "id  编号 __"+
            "show_id  显示位置id(fan_sys_web_news_show_id)"+
            "vedio_type  视频种类1.个人;2官方"+
            "title  视频标题"+
            "auth  视频作者"+
            "status  状态(0:删除;1:已发布;2:草稿3:不显示)"+
            "is_top  是否置顶(0:不置顶;1.置顶;)"+
            "create_time  创建时间"+
            "create_user  创建人"+
            "update_time  修改时间"+
            "update_user  修改人"
    )
    @RequestMapping(value = "/addOrUpdateVedioRecord", method = RequestMethod.POST)
    public Response<FanNewsFamilyRecordVedio> addOrUpdateVedioRecord(FanNewsFamilyRecordVedio fanNewsFamilyRecordVedio, String picfileName,String picPath,String vedioFileName,String vedioPath) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsFamilyRecordVedio.setStatus(1);
        return getFanNewsVedioRecordResponse(fanNewsFamilyRecordVedio, picfileName,picPath,vedioFileName,vedioPath);
    }
    /**
     *联谊会记录家族后台视频添加和修改 抽取的方法
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:19
     *@Param:
     *@return:
     *@Description:
     */
    private Response<FanNewsFamilyRecordVedio> getFanNewsVedioRecordResponse(FanNewsFamilyRecordVedio fanNewsFamilyRecordVedio, String picfileName,String picPath,String vedioFileName,String vedioPath) {
        try {
            // 插入数据
            boolean b = fanNewsFamilyRecordVedioService.addOrUpdateVedioRecord(fanNewsFamilyRecordVedio,picfileName,picPath,vedioFileName,vedioPath);
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
            //插入图片
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
    /**
     *联谊会记录家族后台视频删除
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:22
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "联谊会记录家族后台视频删除", notes = "id  编号 __"+
            "show_id  显示位置id(fan_sys_web_news_show_id)"+
            "vedio_type  视频种类1.个人;2官方"+
            "title  视频标题"+
            "auth  视频作者"+
            "status  状态(0:删除;1:已发布;2:草稿3:不显示)"+
            "is_top  是否置顶(0:不置顶;1.置顶;)"+
            "create_time  创建时间"+
            "create_user  创建人"+
            "update_time  修改时间"+
            "update_user  修改人"
    )
    @RequestMapping(value ="/deleteRecordVedioById",method = RequestMethod.GET)
    public Response<FanNewsFamilyRecordVedio> deleteRecordVedioById(
            @RequestParam(value = "id")Integer id // 家族文化详情显示位置
    ) {
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status=0;
            Boolean aBoolean = fanNewsFamilyRecordVedioService.deleteVedioRecordById(id, status);
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
