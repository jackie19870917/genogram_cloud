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
    /**
     * 家族长老查询1
     */
    @ResponseBody
    @RequestMapping(value = "selectPerson",method = RequestMethod.GET)
    public Response<FanNewsFamousPerson> SelectPerson(
            @RequestParam(value = "showId") Integer showId, // 产业显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ){
        try {
            int status=1;
            Page<FamilyPersonVo> familyPersonVo = iFanNewsFamousPersonService.getFamilyPersionPage(showId, status, pageNo, pageSize);
            if(familyPersonVo==null){
                //没有取到参数,返回空参
                Page<FamilyPersonVo> emptfamilyCultureVo = new Page<FamilyPersonVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE,emptfamilyCultureVo);
            }
            return ResponseUtlis.success(familyPersonVo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
    /**
     * 组织架构
     */
    @ResponseBody
    @RequestMapping(value = "selectFramework",method = RequestMethod.GET)
    public Response<FanNewsFamousPerson> SelectFramework(
            @RequestParam(value = "showId") Integer showId, // 产业显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            int status = 1;
            Page<FamilyPersonVo> familyPersonVo = iFanNewsFamousPersonService.getFamilyPersionPage(showId, status, pageNo, pageSize);
            if (familyPersonVo == null) {
                //没有取到参数,返回空参
                Page<FamilyPersonVo> emptfamilyCultureVo = new Page<FamilyPersonVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, emptfamilyCultureVo);
            }
            return ResponseUtlis.success(familyPersonVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}

