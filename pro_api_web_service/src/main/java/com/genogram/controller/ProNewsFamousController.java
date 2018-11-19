package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.ProNewsFamousPerson;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.ProFamilyPersonVo;
import com.genogram.service.IProNewsFamilyPersionService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/proNewsFamous")
public class ProNewsFamousController {

    @Autowired
    private IProNewsFamilyPersionService iProNewsFamousPersonService;

    /**
     * 家族长老查询,组织架构
     */
    @ResponseBody
    @RequestMapping(value = "selectPersonPage",method = RequestMethod.GET)
    public Response<ProNewsFamousPerson> selectPersonPage(
            @RequestParam(value = "showId") Integer showId, // 产业显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ){
        try {
            int status=1;
            Page<ProFamilyPersonVo> familyPersonVo = iProNewsFamousPersonService.getFamilyPersionPage(showId, status, pageNo, pageSize);
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
     * 联谊会家族文化详情查询一条
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    @RequestMapping(value = "/getFamilyPersionDetail", method = RequestMethod.GET)
    public Response<ProFamilyPersonVo> getFamilyPersionDetail(
            @RequestParam(value = "id") Integer id // 家族文化文章ID
    ) {
        try {
            //返回空参
            ProFamilyPersonVo proFamilyPersonVo = new ProFamilyPersonVo();
            if(id==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,proFamilyPersonVo);
            }
            ProFamilyPersonVo newsDetailVo = iProNewsFamousPersonService.getFamilyFamilyDetail(id);
            if (newsDetailVo == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, proFamilyPersonVo);
            }
            //增加查看数
            iProNewsFamousPersonService.addVisitNum(id);
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}
