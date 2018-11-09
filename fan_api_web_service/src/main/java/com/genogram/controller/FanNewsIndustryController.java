package com.genogram.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entityvo.FamilyIndustryVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IFanNewsIndustryService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        return getFamilyIndustryVoResponse(showId, type, pageNo, pageSize);
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
        return getFamilyIndustryVoResponse(showId, type, pageNo, pageSize);
    }

    /**
     * 抽取的公共方法
     * getFamilyIndustryVoResponse
     * @Author: wang,wei
     * @Date: 2018-11-06
     * @Time: 23:08
     * @param showId 家族产业显示位置
     * @param type 家族产业显示位置
     * @param pageNo 当前页
     * @param pageSize 每页记录数
     * @return:
     * @Description:
     *
     */
    private Response<FamilyIndustryVo> getFamilyIndustryVoResponse(Integer showId, Integer type,  Integer pageNo, Integer pageSize) {
        try {
            //状态
            int status=1;
            List statusList  = new ArrayList();
            statusList.add(status);
            Page<FamilyIndustryVo> familyCultureVo = iFanNewsIndustryService.getFamilyIndustryPage(showId, statusList, pageNo, pageSize, type);
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
    
    /**
     *
     *@Author: Toxicant
     *@Date: 2018-11-09
     *@Time: 10:34
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value ="/getFamilyIndustryDetail",method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyIndustryDetail(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "id") Integer id // 家族文化详情显示位置
    ) {
        try{
            //判断showId是否有值
            if(showId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            NewsDetailVo newsDetailVo= iFanNewsIndustryService.getFamilyIndustryDetail(showId,id);
            return ResponseUtlis.success(newsDetailVo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }
}

