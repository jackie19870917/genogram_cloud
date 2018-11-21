package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.FanNewsFamilyRecordVedio;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.FamilyRecordVedioVo;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IFanNewsFamilyRecordService;
import com.genogram.service.IFanNewsFamilyRecordVedioService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(description = "家族动态")
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
    @ApiOperation(value = "家族动态", notes = "showId:显示位置,pageNo:当前页,pageSize:总页数")
    @RequestMapping(value = "selectRecortPage",method = RequestMethod.GET)
    public Response<FanNewsFamilyRecord> selectRecortPage(
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
     * 联谊会家族动态详情查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "动态详情")
    @RequestMapping(value = "/getFamilyRecordDetail", method = RequestMethod.GET)
    public Response<FamilyRecordVo> getFamilyRecordDetail(
            @RequestParam(value = "id") Integer id // 家族文化文章ID
    ) {
        try {
            //返回空参
            FamilyRecordVo familyRecordVo = new FamilyRecordVo();
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,familyRecordVo);
            }
            FamilyRecordVo newsDetailVo = iFanNewsFamilyRecordService.getFamilyRecordDetail(id);
            if (newsDetailVo == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, familyRecordVo);
            }
            //增加查看数
            iFanNewsFamilyRecordService.addVisitNum(id);
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 视频查询
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "视频查询")
    @RequestMapping(value = "selectVedioPage",method = RequestMethod.GET)
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
    @ApiOperation(value = "记录家族详情")
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
    @ApiOperation(value = "纪录家族视频修改")
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
    @ApiOperation(value = "视频添加修改")
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
    @ApiOperation(value = "视频删除")
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

