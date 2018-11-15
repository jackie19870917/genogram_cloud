package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.ProIndexFund;
import com.genogram.entity.ProIndexFundDrowing;
import com.genogram.entity.ProNewsCharityOut;
import com.genogram.entity.ProNewsCharityPayIn;
import com.genogram.entityvo.DonorVo;
import com.genogram.entityvo.IndexFundDrowingVo;
import com.genogram.entityvo.NewsCharityOutVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IProIndexFundDrowingService;
import com.genogram.service.IProIndexFundService;
import com.genogram.service.IProNewsCharityOutService;
import com.genogram.service.IProNewsCharityPayInService;
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
@RequestMapping("/genogram/admin/proNewsCharity")
public class ProNewsCharityController {

    @Autowired
    private IProNewsCharityOutService proNewsCharityOutService;

    @Autowired
    private IProNewsCharityPayInService proNewsCharityPayInService;

    @Autowired
    private IProIndexFundService proIndexFundService;

    @Autowired
    private IProIndexFundDrowingService proIndexFundDrowingService;

    /**
     * 状态(0:删除;1:已发布;2:草稿3:不显示)
     */
    Integer status = 1;

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
        //状态    1-正常   2-草稿
        list.add(1);
        list.add(2);

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
    @RequestMapping(value = "getNewsDetail", method = RequestMethod.GET)
    public Response<NewsDetailVo> getNewsDetail(@RequestParam(value = "id") Integer id) {

        NewsDetailVo newsCharityOutDetail = proNewsCharityOutService.getNewsCharityOutDetail(id);

        return ResponseUtlis.success(newsCharityOutDetail);
    }

    /**
     * 新增/修改    慈善收支
     * @param proNewsCharityOut
     * @param fileName
     * @param filePath
     * @return
     */
    @RequestMapping(value = "insertOrUpdateProNewsCharityOut", method = RequestMethod.POST)
    public Response<NewsCharityOutVo> insertOrUpdateProNewsCharityOut(ProNewsCharityOut proNewsCharityOut, String fileName,String filePath) {

        //状态   (1:已发布;2:草稿)
        proNewsCharityOut.setStatus(1);
        Boolean result = proNewsCharityOutService.insertOrUpdateProNewsCharityOutVo(proNewsCharityOut,fileName,filePath);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     *  慈善收支草稿
     * @param proNewsCharityOut
     * @param fileName
     * @param filePath
     * @return
     */
    @RequestMapping(value = "insertOrUpdateProNewsCharityOutDeft", method = RequestMethod.POST)
    public Response<NewsCharityOutVo> insertOrUpdateProNewsCharityOutDeft(ProNewsCharityOut proNewsCharityOut, String fileName,String filePath) {

        //状态   (1:已发布;2:草稿)
        proNewsCharityOut.setStatus(2);
        Boolean result = proNewsCharityOutService.insertOrUpdateProNewsCharityOutVo(proNewsCharityOut,fileName,filePath);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteProNewsCharityOut", method = RequestMethod.GET)
    public Response<ProNewsCharityOut> deleteProNewsCharityOut(Integer id) {

        Boolean result = proNewsCharityOutService.deleteProNewsCharityOut(id);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 新增线上提现
     * @param proIndexFundDrowing
     * @return
     */
    @RequestMapping(value = "insertProIndexFundDrowing", method = RequestMethod.POST)
    public Response<ProIndexFundDrowing> insertProIndexFundDrowing(ProIndexFundDrowing proIndexFundDrowing) {

        Boolean result = proIndexFundDrowingService.insertProIndexFundDrowing(proIndexFundDrowing);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 线上提现记录
     * @param siteId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "getFanIndexFundDrowing", method = RequestMethod.GET)
    public Response<IndexFundDrowingVo> getFanIndexFundDrowing(@RequestParam("siteId") Integer siteId,
                                                               @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (siteId==null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }


        Page<IndexFundDrowingVo> indexFundDrowingVoPage = proIndexFundDrowingService.getIndexFundDrowingVoPage(siteId, pageNo, pageSize);

        return ResponseUtlis.success(indexFundDrowingVoPage);
    }

    /**
     * 新增线下捐款
     * @param proNewsCharityPayIn
     * @return
     */
    @RequestMapping(value = "insertProNewsCharityPayIn", method = RequestMethod.POST)
    public Response<ProNewsCharityPayIn> insertProNewsCharityPayIn(ProNewsCharityPayIn proNewsCharityPayIn) {

        proNewsCharityPayIn.setType(2);
        Boolean result = proNewsCharityPayInService.insertProNewsCharityPayIn(proNewsCharityPayIn);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

}

