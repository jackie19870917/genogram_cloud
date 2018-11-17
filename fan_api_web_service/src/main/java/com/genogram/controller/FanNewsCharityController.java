package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanIndexFund;
import com.genogram.entity.FanNewsCharityOut;
import com.genogram.entity.FanNewsCharityPayIn;
import com.genogram.entityvo.DonorVo;
import com.genogram.entityvo.NewsCharityOutVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IFanIndexFundService;
import com.genogram.service.IFanNewsCharityOutService;
import com.genogram.service.IFanNewsCharityPayInService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private IFanNewsCharityOutService fanNewsCharityOutService;

    @Autowired
    private IFanNewsCharityPayInService fanNewsCharityPayInService;

    @Autowired
    private IFanIndexFundService fanIndexFundService;

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

        if (siteId==null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        FanIndexFund fanIndexFund = fanIndexFundService.getFanIndexFund(siteId);

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

        if (showId==null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        list.add(status);

        Map map = new HashMap(16);
        map.put("showId", showId);
        map.put("status", list);

        Page page = new Page();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<FanNewsCharityPayIn> mapPage = new Page<>(page.getCurrent(), page.getSize());
        Page<DonorVo> donorVoPage = fanNewsCharityPayInService.getDonorVoPage(mapPage, map);

        return ResponseUtlis.success(donorVoPage);

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

        if (showId==null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        list.add(status);

        Page<DonorVo> donorVoPageByTime = fanNewsCharityPayInService.getDonorVoPageByTime(showId, list, pageNo, pageSize);

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
    public Response<NewsCharityOutVo> getFanNewsCharityOutVo(@RequestParam("showId") Integer showId,
                                                             @RequestParam(value = "newsType", defaultValue = "1") Integer newsType,
                                                             @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (showId==null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        list.add(status);

        Wrapper<FanNewsCharityOut> entity = new EntityWrapper<FanNewsCharityOut>();
        entity.eq("show_id", showId);
        entity.eq("news_type", newsType);
        entity.in("status", list);
        entity.orderBy("create_time", false);

        Page<NewsCharityOutVo> fanNewsCharityOutPage = fanNewsCharityOutService.getFanNewsCharityOutVoPage(entity, pageNo, pageSize);

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

        NewsDetailVo newsCharityOutDetail = fanNewsCharityOutService.getNewsCharityOutDetail(id);
        fanNewsCharityOutService.insertVisitNum(id);

        return ResponseUtlis.success(newsCharityOutDetail);
    }

    /**
     * 新增捐款记录
     * @param fanNewsCharityPayIn
     * @return
     */
    @RequestMapping(value = "insertFanNewsCharityPayIn", method = RequestMethod.POST)
    public Response<FanNewsCharityPayIn> insertFanNewsCharityPayIn(FanNewsCharityPayIn fanNewsCharityPayIn) {

        Boolean result = fanNewsCharityPayInService.insertFanNewsCharityPayIn(fanNewsCharityPayIn);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }
}

