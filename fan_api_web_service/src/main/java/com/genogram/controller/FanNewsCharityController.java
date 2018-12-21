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
import com.genogram.service.*;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
@Api(description = "慈善公益菜单(前台)")
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
    @ApiOperation(value = "基金查询", notes = "id:主键,siteId:网站Id,remian:基金总额,payNum:捐款人数,payOnline:线上捐款,payUnderline:线下捐款,payGenogram:网络修普金额")
    @RequestMapping(value = "index/getFanIndexFund", method = RequestMethod.GET)
    public Response<FanIndexFund> getFanIndexFund(@ApiParam(value = "网站id") @RequestParam Integer siteId) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        FanIndexFund fanIndexFund = fanIndexFundService.getFanIndexFund(siteId);

        if (StringUtils.isEmpty(fanIndexFund)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(fanIndexFund);
    }

    /**
     * 捐款名录
     *
     * @param showId   捐款名录显示位置
     * @param nickName 别名
     * @param order    排序
     * @param label    升序,降序
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */
    @ApiOperation(value = "捐款名录查询", notes = "id:主键,showId:显示位置,payUsrId:捐款人,userName:用户名,realName:真实名,nickName:昵称,payAmount:捐款金额")
    @RequestMapping(value = "index/getPayUser", method = RequestMethod.GET)
    public Response<DonorVo> getPayUser(@ApiParam(value = "显示位置") @RequestParam Integer showId,
                                        @ApiParam(value = "捐款人") @RequestParam(value = "nickName", required = false) String nickName,
                                        @ApiParam(value = "排序(time-时间,money-金额,null-缺省)") @RequestParam(value = "order", required = false) String order,
                                        @ApiParam(value = "升序-asc,降序-desc") @RequestParam(value = "label", required = false) String label,
                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                        @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {

        if (showId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        list.add(status);

        Page<DonorVo> donorVoPage = null;
        String money = "money";
        if (money.equals(order)) {
            Map map = new HashMap(16);
            map.put("showId", showId);
            map.put("status", list);

            if (!StringUtils.isEmpty(nickName)) {
                map.put("nick_name", nickName);
            }

            map.put("label", label);

            Page page = new Page();
            page.setCurrent(pageNo);
            page.setSize(pageSize);
            Page<FanNewsCharityPayIn> mapPage = new Page<>(page.getCurrent(), page.getSize());
            donorVoPage = fanNewsCharityPayInService.getDonorVoPage(mapPage, map);
        } else {
            donorVoPage = fanNewsCharityPayInService.getDonorVoPageByTime(showId, list, nickName, pageNo, pageSize, order, label);
        }

        if (StringUtils.isEmpty(donorVoPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(donorVoPage);

    }

    /**
     * 慈善收支
     *
     * @param showId   慈善收支显示位置
     *                 //@param newsType 种类(1.财政支出;2.财政收入)
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */
    @ApiOperation(value = "慈善收支", notes = "id:主键,showId:显示位置,amount:支出金额,useFor:支出用途,newsTitle:标题,newsText:内容,visitNum:查看数,filePath:图片url,fileName:图片名称,picIndex,picIndex:是否封面")
    @RequestMapping(value = "index/getFanNewsCharityOutPage", method = RequestMethod.GET)
    public Response<NewsCharityOutVo> getFanNewsCharityOutVo(@ApiParam(value = "显示位置") @RequestParam Integer showId,
                                                             //  @RequestParam(value = "newsType", defaultValue = "1") Integer newsType,
                                                             @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (showId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        list.add(status);

        Wrapper<FanNewsCharityOut> entity = new EntityWrapper<FanNewsCharityOut>();
        entity.eq("show_id", showId);
        entity.in("status", list);
        entity.orderBy("create_time", false);

        Page<NewsCharityOutVo> fanNewsCharityOutPage = fanNewsCharityOutService.getFanNewsCharityOutVoPage(entity, pageNo, pageSize);

        if (StringUtils.isEmpty(fanNewsCharityOutPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(fanNewsCharityOutPage);
    }

    /**
     * 慈善收支详情
     *
     * @param id 慈善收支详情显示位置
     * @return
     */
    @ApiOperation(value = "慈善收支(文章)详情", notes = "id:主键,showId:显示位置,amount:支出金额,useFor:支出用途,newsTitle:标题,newsText:内容,visitNum:查看数,filePath:图片url,fileName:图片名称,picIndex,picIndex:是否封面")
    @RequestMapping(value = "getFanNewsCharityDetail", method = RequestMethod.GET)
    public Response<NewsDetailVo> getFanNewsCharityDetail(@ApiParam(value = "主键") @RequestParam Integer id) {

        NewsDetailVo newsCharityOutDetail = fanNewsCharityOutService.getNewsCharityOutDetail(id);
        fanNewsCharityOutService.insertVisitNum(id);

        return ResponseUtlis.success(newsCharityOutDetail);
    }

}

