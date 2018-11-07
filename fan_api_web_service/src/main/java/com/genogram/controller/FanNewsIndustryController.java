package com.genogram.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
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
    /**
     * 联谊会家族产业  详情页查询
     * @Author: wang,wei
     * @Date: 2018-11-06
     * @Time: 23:02
     * @param showId 家族产业显示位置
     * @param type 种类(1:家族产业;2:个人产业)
     * @param pageNo 当前页
     * @param pageSize 每页记录数
     * @return:
     * @Description:
     *
     */
    @RequestMapping(value ="/getFamilyIndustryPage",method = RequestMethod.GET)
    public Response<FamilyIndustryVo> getFamilyCulturePage(
            @RequestParam(value = "showId") Integer showId,
            @RequestParam(value = "type") Integer type,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        //判断showId是否有值
        if(showId==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        //状态
        int status =1;
        return getFamilyIndustryVoResponse(showId, type, pageNo, pageSize, status);
    }

    /**
     * 联谊会家族产业 首页查询
     * @Author: wang,wei
     * @Date: 2018-11-06
     * @Time: 23:02
     * @param showId 家族产业显示位置
     * @param type 种类(1:家族产业;2:个人产业)
     * @return:
     * @Description:
     *
     */
    @RequestMapping(value ="/index/getFamilyIndexIndustryList",method = RequestMethod.GET)
    public Response<FamilyIndustryVo> getFamilyIndexIndustryList(
            @RequestParam(value = "showId") Integer showId,
            @RequestParam(value = "type") Integer type,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        //判断showId是否有值
        if(showId==null){
            return ResponseUtlis.error(Constants.IS_EMPTY,null);
        }
        //状态
        int status =1;
        return getFamilyIndustryVoResponse(showId, type, pageNo, pageSize, status);
    }

    /**
     * 抽取的公共方法
     * getFamilyIndustryVoResponse
     * @Author: wang,wei
     * @Date: 2018-11-06
     * @Time: 23:08
     * @param showId 家族文化显示位置
     * @param type 家族文化显示位置
     * @param pageNo 当前页
     * @param pageSize 每页记录数
     * @return:
     * @Description:
     *
     */
    private Response<FamilyIndustryVo> getFamilyIndustryVoResponse(@RequestParam("showId") Integer showId, @RequestParam(value = "type", defaultValue = "1") Integer type, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize, @RequestParam(value = "status", defaultValue = "1") Integer status) {
        try {
            Page<FamilyIndustryVo> familyCultureVo = iFanNewsIndustryService.getFamilyIndustryPage(showId, status, pageNo, pageSize, type);
            if (familyCultureVo == null) {
                //没有取到参数,返回空参
                Page<FamilyIndustryVo> emptfamilyCultureVo = new Page<FamilyIndustryVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE,emptfamilyCultureVo);
            }
            return ResponseUtlis.success(familyCultureVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}

