package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsCultureZipai;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.service.IFanNewsCultureNewsService;
import com.genogram.service.IFanNewsCultureZipaiService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    // 返回状态码 成功 200
    private Integer SUCCESSFUL_CODE = Constants.SUCCESSFUL_CODE;
    // 返回状态码 失败 500
    private Integer FAILURE_CODE = Constants.FAILURE_CODE;
    // 返回状态码 失败 400
    private Integer ERRO_CODE = Constants.ERRO_CODE;

    //联谊会家族字派
    @RequestMapping(value = "/getCommonalityPage",method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> getCommonalityPage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @RequestParam(value = "status", defaultValue = "1") Integer status) {
        try {
            Page<FanNewsCultureZipai> fanNewsCultureZipai = iFanNewsCultureZipaiService.commonality(showId, status, pageNo, pageSize);
            if(fanNewsCultureZipai==null){
                return ResponseUtlis.error(ERRO_CODE);
            }
            return ResponseUtlis.success(fanNewsCultureZipai);
        }catch (Exception e) {
                e.printStackTrace();
                return ResponseUtlis.error(FAILURE_CODE);
            }
    }

    //联谊会首页家族字派
    @RequestMapping(value = "/getCommonalityIndexPage",method = RequestMethod.GET)
    public Response<StringBuffer> getCommonalityIndexPage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "status", defaultValue = "1") Integer status) {
        try {
            StringBuffer stringBuffer = iFanNewsCultureZipaiService.CommonalityIndex(showId, status);
            //判断该stringBuffer是否返回为null
            if(stringBuffer==null){
                return ResponseUtlis.error(ERRO_CODE);
            }
            return ResponseUtlis.success(stringBuffer);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(FAILURE_CODE);
        }
    }

    //联谊会家族文化查询
    @RequestMapping(value ="/getFamilyCulturePage",method = RequestMethod.GET)
    public Response<FamilyCultureVo> getFamilyCulturePage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @RequestParam(value = "status", defaultValue = "1") Integer status) {
        return getFamilyCultureVoResponse(showId, pageNo, pageSize, status);
    }

    //联谊会首页家族文化查询
    @RequestMapping(value ="/getFamilyIndexCulturePage",method = RequestMethod.GET)
    public Response<FamilyCultureVo> getFamilyIndexCulturePage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @RequestParam(value = "status", defaultValue = "1") Integer status) {
        return getFamilyCultureVoResponse(showId, pageNo, pageSize, status);
    }

    //抽取的家族文化方法
    private Response<FamilyCultureVo> getFamilyCultureVoResponse(@RequestParam("showId") Integer showId, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize, @RequestParam(value = "status", defaultValue = "1") Integer status) {
        try {
            Page<FamilyCultureVo> familyCultureVo = iFanNewsCultureNewsService.getFamilyCulturePage(showId, status, pageNo, pageSize);
            if (familyCultureVo == null) {
                return ResponseUtlis.error(ERRO_CODE);
            }
            return ResponseUtlis.success(familyCultureVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(FAILURE_CODE);
        }
    }
}

