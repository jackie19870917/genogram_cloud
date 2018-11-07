package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCharityOut;
import com.genogram.entity.FanNewsCharityPayIn;
import com.genogram.entityvo.ChiratyVo;
import com.genogram.entityvo.DonorVo;
import com.genogram.entityvo.FanNewsCharityOutVo;
import com.genogram.service.IFanNewsCharityOutService;
import com.genogram.service.IFanNewsCharityPayInService;
import com.genogram.service.IFanNewsCharityService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 联谊会-家族慈善财 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/fanNewsCharity")
public class FanNewsCharityController {

    @Autowired
    private IFanNewsCharityOutService iFanNewsCharityOutService;

    @Autowired
    private IFanNewsCharityPayInService iFanNewsCharityPayInService;

    @Autowired
    private IFanNewsCharityService iFanNewsCharityService;

    @RequestMapping("")
    public Response<FanNewsCharityOut> selectByCreateTime(@RequestParam(value = "showId",defaultValue = "1") Integer showId,
                                                          @RequestParam(value = "status",defaultValue = "1")Integer status,
                                                          @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                                          @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize) {

        Page<FanNewsCharityOut> fanNewsCharityOutPage = iFanNewsCharityOutService.selectPage(showId, status, pageNo, pageSize);

        return ResponseUtlis.success(fanNewsCharityOutPage);
    }

   /* @RequestMapping("/demo")
    public Response<FanNewsCharityOut> selectByCreateTimes(@RequestParam(value = "siteId",defaultValue = "1") Integer siteId,
                                                          @RequestParam(value = "status",defaultValue = "1")Integer status,
                                                          @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                                          @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize) {

        List<FanNewsCharityOut> fanNewsCharityOutPage = iFanNewsCharityOutService.selectPageList(siteId, status, pageNo, pageSize);

        return ResponseUtlis.success(fanNewsCharityOutPage);
    }*/

    @RequestMapping("chiratyVo")
    public Response<ChiratyVo> getchiratyVo(@RequestParam(value = "siteId",defaultValue = "1") Integer siteId,
                                            @RequestParam(value = "status",defaultValue = "1")Integer status,
                                            @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                            @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize) {

        ChiratyVo charity = iFanNewsCharityService.getAllFanNewsCharity(siteId, status, pageNo, pageSize);

        return ResponseUtlis.success(charity);
    }

    @RequestMapping("/FanNewsCharityDonor")
    public Response<DonorVo> selectByCreateTimess(@RequestParam(value = "showId",defaultValue = "1") Integer showId,
                                                  @RequestParam(value = "status",defaultValue = "1")Integer status,
                                                  @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                                  @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize) {

        List<DonorVo> fanNewsCharityPayInPage = iFanNewsCharityPayInService.queryMyItems(showId, status, pageNo, pageSize);

        return ResponseUtlis.success(fanNewsCharityPayInPage);
    }

    @RequestMapping("/FanNewsCharityPayOutPage")
    public Response<FanNewsCharityOutVo> getFanNewsCharityOutVo(@RequestParam(value = "siteId",defaultValue = "1") Integer siteId,
                                                                @RequestParam(value = "newsType",defaultValue = "1")Integer newsType,
                                                                @RequestParam(value = "status",defaultValue = "1")Integer status,
                                                                @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                                                @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize) {
        Page<FanNewsCharityOutVo> fanNewsCharityOutPage = iFanNewsCharityOutService.getFanNewsCharityOutPage(siteId, newsType,status, pageNo, pageSize);

        return ResponseUtlis.success(fanNewsCharityOutPage);
    }
}

