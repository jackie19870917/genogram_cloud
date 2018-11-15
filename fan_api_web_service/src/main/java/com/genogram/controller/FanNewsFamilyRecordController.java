package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.FanNewsFamilyRecordVedio;
import com.genogram.entityvo.FamilyRecordVedioVo;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IFanNewsFamilyRecordService;
import com.genogram.service.IFanNewsFamilyRecordVedioService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 联谊会-记录家族-家族动态,家族通告文章表 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/fanNewsFamilyRecord")
public class FanNewsFamilyRecordController {
    @Autowired
    private IFanNewsFamilyRecordService iFanNewsFamilyRecordService;

    @Autowired
    private IFanNewsFamilyRecordVedioService iFanNewsFamilyRecordVedioService;
    /**
     * 家族动态查询
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
     * 视频查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selectVedio",method = RequestMethod.GET)
    public  Response<FanNewsFamilyRecord> selectVedioPage(
            @RequestParam(value = "showId") Integer showId, // 产业显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            int status = 1;
            Page<FamilyRecordVedioVo> familyRecordVedioVo = iFanNewsFamilyRecordVedioService.getFamilyRecordVedioPage(showId, status, pageNo, pageSize);
            if(familyRecordVedioVo==null){
                //没有取到参数,返回空参
                Page<FamilyRecordVedioVo> emptfamilyRecordVedioVo = new Page<FamilyRecordVedioVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE,emptfamilyRecordVedioVo);
            }
            return ResponseUtlis.success(familyRecordVedioVo);
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
    @RequestMapping(value ="/getFamilyRecordVedio",method = RequestMethod.GET)
    public Response<FamilyRecordVedioVo> getFamilyRecordVedioDetail(
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        return getNewsDetailVedioVoResponse(id);
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
            NewsDetailVo newsDetailVo = iFanNewsFamilyRecordVedioService.getFamilyVedioRecord(id);
            return ResponseUtlis.success(newsDetailVo);
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
    @RequestMapping(value = "/addOrUpdateVedioRecord", method = RequestMethod.POST)
    public Response<FanNewsFamilyRecordVedio> addOrUpdateVedioRecord(FanNewsFamilyRecordVedio fanNewsFamilyRecordVedio, String picfileName, String picPath, String vedioFileName, String vedioPath) {
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
            boolean b = iFanNewsFamilyRecordVedioService.addOrUpdateVedioRecord(fanNewsFamilyRecordVedio,picfileName,picPath,vedioFileName,vedioPath);
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
    @RequestMapping(value ="/deleteRecordById",method = RequestMethod.GET)
    public Response<FanNewsFamilyRecordVedio> deleteVedioRecordById(
            @RequestParam(value = "id")Integer id // 家族文化详情显示位置
    ) {
        try {
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status=0;
            Boolean aBoolean = iFanNewsFamilyRecordVedioService.deleteVedioRecordById(id, status);
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

