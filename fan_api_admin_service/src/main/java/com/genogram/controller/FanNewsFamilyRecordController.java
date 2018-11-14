package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IFanNewsFamilyRecordService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanNewsFamilyRecord")
public class FanNewsFamilyRecordController {
    @Autowired
    private IFanNewsFamilyRecordService iFanNewsFamilyRecordService;

    /**
     * 后台家族动态查询
     */
    @ResponseBody
    @RequestMapping(value = "selectRecort",method = RequestMethod.GET)
    public Response<FanNewsFamilyRecord> selectRecort(
            @RequestParam(value = "showId") Integer showId, // 产业显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ){
        try {
            int status = 1;
            Page<FamilyRecordVo> familyRecordVo = iFanNewsFamilyRecordService.getFamilyRecordPage(showId, status, pageNo, pageSize);
            if(familyRecordVo==null){
                //没有取到参数,返回空参
                Page<FamilyRecordVo> emptfamilyRecordVo = new Page<FamilyRecordVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE,emptfamilyRecordVo);
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
            NewsDetailVo newsDetailVo = iFanNewsFamilyRecordService.getFamilyRecord(id);
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
    @RequestMapping(value = "/addOrUpdateRecord", method = RequestMethod.POST)
    public Response<FanNewsFamilyRecord> addOrUpdateRecord(FanNewsFamilyRecord fanNewsRecord, String fileNames) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsRecord.setStatus(1);
        return getFanNewsRecordResponse(fanNewsRecord, fileNames);
    }

    /**
     *联谊会家族产业后台添加和修改 草稿
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:10
     *@Param:
     *@return:
     *@Description:
     */
    @RequestMapping(value = "/addOrUpdateRecordDrft", method = RequestMethod.POST)
    public Response<FanNewsFamilyRecord> addOrUpdateRecordDrft(FanNewsFamilyRecord fanNewsRecord, String fileNames) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        fanNewsRecord.setStatus(2);
        return getFanNewsRecordResponse(fanNewsRecord, fileNames);
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
    private Response<FanNewsFamilyRecord> getFanNewsRecordResponse(FanNewsFamilyRecord fanNewsRecord, String fileNames) {
        try {
            // 插入数据
            boolean b = iFanNewsFamilyRecordService.addOrUpdateRecord(fanNewsRecord, fileNames);
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
    @RequestMapping(value ="/deleteRecordById",method = RequestMethod.GET)
    public Response<FanNewsFamilyRecord> deleteRecordById(
            @RequestParam(value = "id")Integer id // 家族文化详情显示位置
    ) {
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status=0;
            Boolean aBoolean = iFanNewsFamilyRecordService.deleteRecordById(id, status);
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
