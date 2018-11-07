package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCharityOut;
import com.genogram.entityvo.CharityFundVo;
import com.genogram.entityvo.DonorVo;
import com.genogram.entityvo.FanNewsCharityOutVo;
import com.genogram.service.IFanNewsCharityOutService;
import com.genogram.service.IFanNewsCharityPayInService;
import com.genogram.service.IFanNewsCharityService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    Integer status = 1;
    /**
     *
     * @param siteId      慈善基金显示位置
     * @param pageNo      当前页
     * @param pageSize    每页记录数
     * @return
     */
    @RequestMapping(value = "getCharityFund",method = RequestMethod.GET)
    public Response<CharityFundVo> getCharityFundVo(@RequestParam(value = "siteId",defaultValue = "1") Integer siteId,
                                                    @RequestParam(value = "newsType",defaultValue = "0")Integer newsType,
                                                    @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                                    @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize) {

        CharityFundVo charity = iFanNewsCharityService.GetCharityFundVo(siteId,newsType, status, pageNo, pageSize);

        return ResponseUtlis.success(charity);
    }

    /**
     *
     * @param showId      捐款名录显示位置
     * @param pageNo      当前页
     * @param pageSize    每页记录数
     * @return
     */
    @RequestMapping(value = "getDonorPage",method = RequestMethod.GET)
    public Response<DonorVo> getDonorVoPage(@RequestParam(value = "showId",defaultValue = "1") Integer showId,
                                            @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                            @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize) {

        List<DonorVo> fanNewsCharityPayInPage = iFanNewsCharityPayInService.getDonorVoPage(showId, status, pageNo, pageSize);

        return ResponseUtlis.success(fanNewsCharityPayInPage);
    }

    /**
     *
     * @param showId         慈善收支显示位置
     * @param newsType       种类(0.支出用途.1.财政支出;2.财政收入)
     * @param pageNo         当前页
     * @param pageSize       每页记录数
     * @return
     */
    @RequestMapping(value = "getFanNewsCharityOutPage",method = RequestMethod.GET)
    public Response<FanNewsCharityOutVo> getFanNewsCharityOutVo(@RequestParam(value = "showId",defaultValue = "1") Integer showId,
                                                                @RequestParam(value = "newsType",defaultValue = "1")Integer newsType,
                                                                @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                                                @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize) {
        Page<FanNewsCharityOutVo> fanNewsCharityOutPage = iFanNewsCharityOutService.getFanNewsCharityOutVoPage(showId, newsType,status, pageNo, pageSize);

        return ResponseUtlis.success(fanNewsCharityOutPage);
    }
}

