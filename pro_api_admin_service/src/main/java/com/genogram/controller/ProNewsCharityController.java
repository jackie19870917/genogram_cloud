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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(description = "慈善公益菜单(后台)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/proNewsCharity")
public class ProNewsCharityController {

    @Autowired
    private IProNewsCharityOutService proNewsCharityOutService;

    @Autowired
    private IProNewsCharityPayInService proNewsCharityPayInService;

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
     *                 // @param newsType 种类(1.财政支出;2.财政收入)
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */
    @ApiOperation(value = "慈善收支", notes = "id:主键,showId:显示位置,amount:支出金额,useFor:支出用途,newsTitle:标题,newsText:内容,visitNum:查看数,filePath:图片url,fileName:图片名称,picIndex,picIndex:是否封面")
    @RequestMapping(value = "index/getProNewsCharityOutPage", method = RequestMethod.GET)
    public Response<NewsCharityOutVo> getProNewsCharityOutPage(@ApiParam(value = "显示位置") @RequestParam Integer showId,
                                                               //  @RequestParam(value = "newsType", defaultValue = "1") Integer newsType,
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
        // entity.eq("news_type", newsType);
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
    @ApiOperation(value = "慈善收支(文章)详情", notes = "id:主键,showId:显示位置,amount:支出金额,useFor:支出用途,newsTitle:标题,newsText:内容,visitNum:查看数,filePath:图片url,fileName:图片名称,picIndex,picIndex:是否封面")
    @RequestMapping(value = "getNewsDetail", method = RequestMethod.GET)
    public Response<NewsDetailVo> getNewsDetail(@ApiParam(value = "主键") @RequestParam Integer id) {

        NewsDetailVo newsCharityOutDetail = proNewsCharityOutService.getNewsCharityOutDetail(id);

        return ResponseUtlis.success(newsCharityOutDetail);
    }

    /**
     * 新增/修改    慈善收支
     *
     * @param proNewsCharityOut
     * @param fileName
     * @param filePath
     * @return
     */
    @ApiOperation(value = "新增/修改  慈善收支(文章)", notes = "id:主键,showId:显示位置,amount:支出金额,useFor:支出用途,newsTitle:标题,newsText:内容,visitNum:查看数")
    @RequestMapping(value = "insertOrUpdateProNewsCharityOut", method = RequestMethod.POST)
    public Response<NewsCharityOutVo> insertOrUpdateProNewsCharityOut(ProNewsCharityOut proNewsCharityOut,
                                                                      @ApiParam(value = "图片名称") String fileName,
                                                                      @ApiParam(value = "图片url") String filePath) {

        //状态   (1:已发布;2:草稿)
        proNewsCharityOut.setStatus(1);
        Boolean result = proNewsCharityOutService.insertOrUpdateProNewsCharityOutVo(proNewsCharityOut, fileName, filePath);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 慈善收支草稿
     *
     * @param proNewsCharityOut
     * @param fileName
     * @param filePath
     * @return
     */
    @ApiOperation(value = "慈善收支(文章)草稿", notes = "id:主键,showId:显示位置,amount:支出金额,useFor:支出用途,newsTitle:标题,newsText:内容,visitNum:查看数")
    @RequestMapping(value = "insertOrUpdateProNewsCharityOutDeft", method = RequestMethod.POST)
    public Response<NewsCharityOutVo> insertOrUpdateProNewsCharityOutDeft(ProNewsCharityOut proNewsCharityOut,
                                                                          @ApiParam(value = "图片名称") String fileName,
                                                                          @ApiParam(value = "图片url") String filePath) {

        //状态   (1:已发布;2:草稿)
        proNewsCharityOut.setStatus(2);
        Boolean result = proNewsCharityOutService.insertOrUpdateProNewsCharityOutVo(proNewsCharityOut, fileName, filePath);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @ApiOperation("删除慈善收支(文章)")
    @RequestMapping(value = "deleteProNewsCharityOut", method = RequestMethod.GET)
    public Response<ProNewsCharityOut> deleteProNewsCharityOut(@ApiParam(value = "主键") @RequestParam Integer id) {

        Boolean result = proNewsCharityOutService.deleteProNewsCharityOut(id);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 新增线上提现
     *
     * @param proIndexFundDrowing
     * @return
     */
    @ApiOperation(value = "新增线上提现", notes = "id:主键,siteId:网站Id,drowAmount:提现金额,drowBank;提现银行,drowBankSub:支行名称,drowTime:提现时间,drowInAccountName:账户名,drowInAccountCard:账户")
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
     *
     * @param siteId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "线上提现记录", notes = "id:主键,siteId:网站Id,drowAmount:提现金额,drowBank;提现银行,drowBankSub:支行名称,drowTime:提现时间,drowInAccountName:账户名,drowInAccountCard:账户")
    @RequestMapping(value = "getFanIndexFundDrowing", method = RequestMethod.GET)
    public Response<IndexFundDrowingVo> getFanIndexFundDrowing(@ApiParam(value = "网站Id") @RequestParam Integer siteId,
                                                               @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }


        Page<IndexFundDrowingVo> indexFundDrowingVoPage = proIndexFundDrowingService.getIndexFundDrowingVoPage(siteId, pageNo, pageSize);

        return ResponseUtlis.success(indexFundDrowingVoPage);
    }

    /**
     * 新增线下捐款
     *
     * @param proNewsCharityPayIn
     * @return
     */
    @ApiOperation(value = "新增线下捐款", notes = "id:主键,showId:显示位置,payUsrId:捐款人,payAmount:捐款金额")
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

