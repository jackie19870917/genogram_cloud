package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsCultureZipai;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.service.IFanNewsCultureZipaiService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/backstage/fanNewsCulture/")
public class FanNewsCultureController {

    @Autowired
    private IFanNewsCultureZipaiService iFanNewsCultureZipaiService;

    //联谊会家族字派查询
    @RequestMapping(value = "/getCommonalityPage",method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> getCommonalityPage(
            @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            //判断showId是否有值
            if(showId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态
            Integer status=1;
            Page<FanNewsCultureZipai> fanNewsCultureZipai = iFanNewsCultureZipaiService.commonality(showId, status, pageNo, pageSize);
            if(fanNewsCultureZipai==null){
                //没有取到参数,返回空参
                Page<FamilyCultureVo> emptfamilyCultureVo = new Page<FamilyCultureVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE,null);
            }
            return ResponseUtlis.success(fanNewsCultureZipai);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

    //联谊会家族字派查询
    @RequestMapping(value = "/getGrabblePage",method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> getGrabblePage(
            @RequestParam(value = "showId") Integer showId, // 家族字派显示位置
            @RequestParam(value = "zipaiTxt") Integer zipaiTxt, // 家族字派模糊查询参数
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        return null;
    }
}
