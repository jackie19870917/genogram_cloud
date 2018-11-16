package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanIndexFamilySummarys;
import com.genogram.entity.FanIndexInfo;
import com.genogram.entity.FanIndexSlidePic;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.service.IFanIndexFamilySummarysService;
import com.genogram.service.IFanIndexInfoService;
import com.genogram.service.IFanIndexSlidePicService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 *联谊会后台
 *@Author: Toxicant
 *@Date: 2018-11-09
 *@Time: 14:23
 *@Param:
 *@return:
 *@Description:
*/
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/fanIndex")
public class FanIndexController {

    @Autowired
    private IFanIndexInfoService fanIndexInfoService;

    @Autowired
    private IFanIndexFamilySummarysService fanIndexFamilySummarysService;

    @Autowired
    private IFanIndexSlidePicService fanIndexSlidePicService;

    /**
     * 轮播图
     * @param siteId  网站ID
     * @return
     */
    @RequestMapping(value = "getFanIndexSlidePicList", method = RequestMethod.GET)
    public Response<FanIndexSlidePic> getFanIndexSlidePicList(@RequestParam(value = "siteId") Integer siteId) {


        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        //状态   1-前后台显示    2-前台不显示      0-前后台都不显示
        list.add(1);
        list.add(2);

        List<FanIndexSlidePic> fanIndexSlidePicList = fanIndexSlidePicService.getFanIndexSlidePicListBySiteId(siteId, list);

        return ResponseUtlis.success(fanIndexSlidePicList);
    }

    /**
     * 新增/修改  轮播图
     * @param fanIndexSlidePic
     * @return
     */
    @RequestMapping(value = "insertOrUpdateFanIndexSlidePic", method = RequestMethod.POST)
    public Response<FanIndexSlidePic> insertOrUpdateFanIndexSlidePic(FanIndexSlidePic fanIndexSlidePic) {

        Boolean result = fanIndexSlidePicService.insertOrUpdateFanIndexSlidePic(fanIndexSlidePic);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 逻辑删除  轮播图
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteFanIndexSlidePic", method = RequestMethod.GET)
    public Response<FanIndexSlidePic> deleteFanIndexSlidePic(Integer id) {

        Boolean result = fanIndexSlidePicService.deleteFanIndexSlidePic(id);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 联谊会简介,宣言
     * @param siteId   网站ID
     * @return
     */
    @RequestMapping(value = "getFanIndexInfo",method = RequestMethod.GET)
    public Response<IndexInfoVo> getFanIndexInfo(@RequestParam("siteId") Integer siteId) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        IndexInfoVo indexInfoVo = fanIndexInfoService.getFanIndexInfoVo(siteId);

        return ResponseUtlis.success(indexInfoVo);
    }

    /**
     * 新增或修改    联谊会简介,宣言
     * @param indexInfoVo   实体类
     * @return
     */
    @RequestMapping(value = "insertOrUpdateFanIndexInfo", method = RequestMethod.POST)
    public Response<FanIndexInfo> insertOrUpdateFanIndexInfo(IndexInfoVo indexInfoVo) {

        Boolean result = fanIndexInfoService.insertOrUpdateIndexInfoVo(indexInfoVo);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 删除   联谊会简介,宣言
     * @param fanIndexInfo
     * @return
     */
    @RequestMapping(value = "deleteFanIndexInfo", method = RequestMethod.POST)
    public Response<FanIndexInfo> deleteFanIndexInfo(FanIndexInfo fanIndexInfo) {

        Boolean result = fanIndexInfoService.deleteFanIndexInfo(fanIndexInfo);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 联谊堂
     * @param siteId    网站ID
     * @param pageNo    当前页
     * @param pageSize  每页记录数
     * @return
     */
    @RequestMapping(value = "getFanIndexFamilySummarysPage", method = RequestMethod.GET)
    public Response<FanIndexFamilySummarys> getFanIndexFamilySummarysPage(@RequestParam(value = "siteId") Integer siteId,
                                                                          @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        //  1-正常    2-草稿
        list.add(1);
        list.add(2);
        Page<FanIndexFamilySummarys> fanIndexFamilySummarysPage = fanIndexFamilySummarysService.getFanIndexFamilySummarysPage(siteId, list, pageNo, pageSize);

        return ResponseUtlis.success(fanIndexFamilySummarysPage);
    }

    /**
     * 联谊堂详情
     * @param id  主键
     * @return
     */
    @RequestMapping(value = "getFanIndexFamilySummarys", method = RequestMethod.GET)
    public Response<FanIndexFamilySummarys> getFanIndexFamilySummarys(@RequestParam(value = "id") Integer id) {

        FanIndexFamilySummarys fanIndexFamilySummarys = fanIndexFamilySummarysService.getFanIndexFamilySummarys(id);

        return ResponseUtlis.success(fanIndexFamilySummarys);
    }

    /**
     * 增或修改    联谊堂
     * @param fanIndexFamilySummarys
     * @return
     */
    @RequestMapping(value = "insertOrUpdateFanIndexFamilySummarys", method = RequestMethod.POST)
    public Response<FanIndexFamilySummarys> insertOrUpdateFanIndexFamilySummarys(FanIndexFamilySummarys fanIndexFamilySummarys) {

        //状态   1-正常  2-草稿
        fanIndexFamilySummarys.setStatus(1);
        Boolean result = fanIndexFamilySummarysService.insertOrUpdateFanIndexFamilySummarys(fanIndexFamilySummarys);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    /**
     * 联谊堂 草稿
     * @param fanIndexFamilySummarys
     * @return
     */
    @RequestMapping(value = "insertOrUpdateFanIndexFamilySummarysDrft", method = RequestMethod.POST)
    public Response<FanIndexFamilySummarys> insertOrUpdateFanIndexFamilySummarysDrft(FanIndexFamilySummarys fanIndexFamilySummarys) {

        //状态   1-正常  2-草稿
        fanIndexFamilySummarys.setStatus(2);
        Boolean result = fanIndexFamilySummarysService.insertOrUpdateFanIndexFamilySummarys(fanIndexFamilySummarys);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }
    /**
     * 逻辑删除 联谊堂
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteFanIndexFamilySummarys", method = RequestMethod.GET)
    public Response<FanIndexFamilySummarys> deleteFanIndexFamilySummarys(Integer id) {

        Boolean result = fanIndexFamilySummarysService.deleteFanIndexFamilySummarys(id);

        if (result) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

}
