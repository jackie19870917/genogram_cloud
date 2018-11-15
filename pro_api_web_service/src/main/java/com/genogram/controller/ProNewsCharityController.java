package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.*;
import com.genogram.entityvo.DonorVo;
import com.genogram.entityvo.NewsCharityOutVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.*;
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
 * 省级-家族慈善财 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/proNewsCharity")
public class ProNewsCharityController {

    @Autowired
    private IProNewsCharityOutService proNewsCharityOutService;

    @Autowired
    private IProNewsCharityPayInService proNewsCharityPayInService;

    @Autowired
    private IProIndexFundService proIndexFundService;

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
    @RequestMapping(value = "index/getProIndexFund", method = RequestMethod.GET)
    public Response<ProIndexFund> getProIndexFund(@RequestParam("siteId") Integer siteId) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        ProIndexFund proIndexFund = proIndexFundService.getProIndexFund(siteId);

        return ResponseUtlis.success(proIndexFund);
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

        if (showId == null) {
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
        Page<ProNewsCharityPayIn> mapPage = new Page<>(page.getCurrent(), page.getSize());
        Page<DonorVo> donorVoPage = proNewsCharityPayInService.getDonorVoPage(mapPage, map);

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

        if (showId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        list.add(status);

        Page<DonorVo> donorVoPageByTime = proNewsCharityPayInService.getDonorVoPageByTime(showId, list, pageNo, pageSize);

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
    @RequestMapping(value = "index/getProNewsCharityOutPage", method = RequestMethod.GET)
    public Response<NewsCharityOutVo> getProNewsCharityOutPage(@RequestParam("showId") Integer showId,
                                                               @RequestParam(value = "newsType", defaultValue = "1") Integer newsType,
                                                               @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (showId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        list.add(status);

        Wrapper<ProNewsCharityOut> entity = new EntityWrapper<ProNewsCharityOut>();
        entity.eq("show_id", showId);
        entity.eq("news_type", newsType);
        entity.in("status", list);
        entity.orderBy("create_time", false);

        Page<NewsCharityOutVo> newsCharityOutVoPage = proNewsCharityOutService.getNewsCharityOutVoPage(entity, pageNo, pageSize);

        return ResponseUtlis.success(newsCharityOutVoPage);
    }

    /**
     * 慈善收支(文章)详情
     *
     * @param id 慈善收支详情显示位置
     * @return
     */
    @RequestMapping(value = "getFanNewsCharityDetail", method = RequestMethod.GET)
    public Response<NewsDetailVo> getFanNewsCharityDetail(@RequestParam(value = "id") Integer id) {

        NewsDetailVo newsCharityOutDetail = proNewsCharityOutService.getNewsCharityOutDetail(id);

        return ResponseUtlis.success(newsCharityOutDetail);
    }

    /**
     * 新增捐款记录
     *
     * @param proNewsCharityPayIn
     * @return
     */
    @RequestMapping(value = "insertProNewsCharityPayIn", method = RequestMethod.POST)
    public Response<ProNewsCharityPayIn> insertProNewsCharityPayIn(ProNewsCharityPayIn proNewsCharityPayIn) {

        Boolean result = proNewsCharityPayInService.insertProNewsCharityPayIn(proNewsCharityPayIn);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

}

