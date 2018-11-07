package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsFamousPerson;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.service.IFanNewsFamousPersonService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 联谊会-家族名人,家族长老,家族栋梁,组织架构 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/fanNewsFamousAncestor")
public class FanNewsFamousController {
    @Autowired
    private IFanNewsFamousPersonService iFanNewsFamousPersonService;

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
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Response<FanNewsFamousPerson> SelectPerson(
            @RequestParam(value = "showId") Integer showId, // 产业显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @RequestParam(value = "status", defaultValue = "1") Integer status
    ){
        try {
            Page<FamilyPersonVo> familyPersonVo = iFanNewsFamousPersonService.getFamilyPersionPage(showId, status, pageNo, pageSize);
            if(familyPersonVo==null){
                return ResponseUtlis.error(Constants.ERRO_CODE);
            }
            return ResponseUtlis.success(familyPersonVo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE);
        }
    }
}

