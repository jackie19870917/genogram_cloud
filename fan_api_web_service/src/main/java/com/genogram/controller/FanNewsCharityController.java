package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexFund;
import com.genogram.entityvo.DonorVo;
import com.genogram.entityvo.FanNewsCharityOutVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IFanIndexFundService;
import com.genogram.service.IFanNewsCharityOutService;
import com.genogram.service.IFanNewsCharityPayInService;
import com.genogram.service.IFanNewsCharityService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private IFanIndexFundService iFanIndexFundService;

    /**
     * 状态(0:删除;1:已发布;2:草稿3:不显示)
     */
    Integer status = 1;


    /**
     * 慈善基金
     *
     * @param siteId 慈善基金ID
     * @return
     */
    @RequestMapping(value = "index/getFanIndexFund", method = RequestMethod.GET)
    public Response<FanIndexFund> getFanIndexFund(@RequestParam("siteId") Integer siteId) {

        FanIndexFund fanIndexFund = iFanIndexFundService.getFanIndexFund(siteId);

        return ResponseUtlis.success(fanIndexFund);
    }


    /**
     * 捐款名录         (个人总金额)
     *
     * @param showId   捐款名录显示位置
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */

    @RequestMapping(value = "index/getDonorPage", method = RequestMethod.GET)
    public Response<DonorVo> getDonorVoPageBySum(@RequestParam("showId") Integer showId,
                                                 @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        List list = new ArrayList();
        list.add(status);

        List<DonorVo> fanNewsCharityPayInPage = iFanNewsCharityPayInService.getDonorVoPage(showId, list, pageNo, pageSize);

        return ResponseUtlis.success(fanNewsCharityPayInPage);
    }

    /**
     * 捐款名录          (最新捐款记录)
     *
     * @param showId   捐款名录显示位置
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */

    @RequestMapping(value = "index/getDonorVoPageByCreateTime", method = RequestMethod.GET)
    public Response<DonorVo> getDonorVoPageByCreateTime(@RequestParam("showId") Integer showId,
                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {

        List list = new ArrayList();
        list.add(status);

        Page<DonorVo> donorVoPageByTime = iFanNewsCharityPayInService.getDonorVoPageByTime(showId, list, pageNo, pageSize);

        return ResponseUtlis.success(donorVoPageByTime);
    }


    /**
     * 慈善收支
     *
     * @param showId   慈善收支显示位置
     * @param newsType 种类(1.财政支出;2.财政收入)
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */
    @RequestMapping(value = "index/getFanNewsCharityOutPage", method = RequestMethod.GET)
    public Response<FanNewsCharityOutVo> getFanNewsCharityOutVo(@RequestParam("showId") Integer showId,
                                                                @RequestParam(value = "newsType", defaultValue = "1") Integer newsType,
                                                                @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List list = new ArrayList();
        list.add(status);
        Page<FanNewsCharityOutVo> fanNewsCharityOutPage = iFanNewsCharityOutService.getFanNewsCharityOutVoPage(showId, newsType, list, pageNo, pageSize);

        return ResponseUtlis.success(fanNewsCharityOutPage);
    }

    /**
     * 慈善收支详情
     *
     * @param id     慈善收支详情显示位置
     * @return
     */
    @RequestMapping(value = "getFanNewsCharityDetail", method = RequestMethod.GET)
    public Response<NewsDetailVo> getFanNewsCharityDetail(@RequestParam(value = "id") Integer id) {

        NewsDetailVo newsCharityOutDetail = iFanNewsCharityOutService.getNewsCharityOutDetail(id);

        return ResponseUtlis.success(newsCharityOutDetail);
    }
}

