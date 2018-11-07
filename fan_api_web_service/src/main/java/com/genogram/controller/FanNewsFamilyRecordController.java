package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entityvo.FamilyRecordVo;
import com.genogram.service.IFanNewsFamilyRecordService;
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

//    // 返回状态码 成功 200
//    private Integer SUCCESSFUL_CODE = ConstantClassField.SUCCESSFUL_CODE;
//    // 返回状态码 失败 500
//    private Integer FAILURE_CODE = ConstantClassField.FAILURE_CODE;
//    // 返回状态码 失败 400
//    private Integer ERRO_CODE = ConstantClassField.ERRO_CODE;
    /**
     * 家族动态查询
     */
    @ResponseBody
    @RequestMapping(value = "selectRecort",method = RequestMethod.POST)
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
}

