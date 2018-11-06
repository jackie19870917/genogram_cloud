package com.genogram.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.ConstantClassField;
import com.genogram.entityvo.FamilyIndustryVo;
import com.genogram.service.IFanNewsIndustryService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 联谊会-家族产业 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/fanNewsIndustry")
public class FanNewsIndustryController {

    @Autowired
    private IFanNewsIndustryService iFanNewsIndustryService;

    // 返回状态码 成功 200
    private Integer SUCCESSFUL_CODE = ConstantClassField.SUCCESSFUL_CODE;
    // 返回状态码 失败 500
    private Integer FAILURE_CODE = ConstantClassField.FAILURE_CODE;
    // 返回状态码 失败 400
    private Integer ERRO_CODE = ConstantClassField.ERRO_CODE;

    //联谊会家族文化查询
    @RequestMapping(value ="/getFamilyIndustryPage",method = RequestMethod.GET)
    public Response<FamilyIndustryVo> getFamilyCulturePage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "type",defaultValue = "1") Integer type,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @RequestParam(value = "status", defaultValue = "1") Integer status) {
        try {
            Page<FamilyIndustryVo> familyCultureVo = iFanNewsIndustryService.getFamilyIndustryPage(showId, status, pageNo, pageSize,type);
            if(familyCultureVo==null){
                return ResponseUtlis.error(ERRO_CODE);
            }
            return ResponseUtlis.success(familyCultureVo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(FAILURE_CODE);
        }
    }

}

