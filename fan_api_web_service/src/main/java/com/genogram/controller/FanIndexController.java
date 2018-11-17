package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanIndexFamilySummarys;
import com.genogram.entity.FanIndexMessage;
import com.genogram.entity.FanIndexSlidePic;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.service.*;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 联谊会首页-设置 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/fanIndex")
public class FanIndexController {

    @Autowired
    private IFanIndexMessageService fanIndexMessageService;

    @Autowired
    private IFanIndexFamilySummarysService fanIndexFamilySummarysService;

    @Autowired
    private IFanIndexSlidePicService fanIndexSlidePicService;

    @Autowired
    private IFanIndexInfoService fanIndexInfoService;

    /**
     * 状态
     */
    Integer status = 1;

    /**
     *      轮播图
     * @param siteId  网站ID
     * @return
     */
    @RequestMapping(value = "index/getFanIndexSlidePicList", method = RequestMethod.GET)
    public Response<FanIndexSlidePic> getFanIndexSlidePicList(@RequestParam(value = "siteId") Integer siteId) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        list.add(status);

        List<FanIndexSlidePic> fanIndexSlidePicList = fanIndexSlidePicService.getFanIndexSlidePicListBySiteId(siteId, list);

        return ResponseUtlis.success(fanIndexSlidePicList);
    }

    /**
     * 联谊堂
     *
     * @param siteId   网站ID
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */
    @RequestMapping(value = "index/getFanIndexFamilySummarysPage", method = RequestMethod.GET)
    public Response<FanIndexFamilySummarys> getFanIndexFamilySummarysPage(@RequestParam(value = "siteId") Integer siteId,
                                                                          @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                                          @RequestParam(value = "pageSize",defaultValue = "2333") Integer pageSize) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        list.add(status);

        Page<FanIndexFamilySummarys> fanIndexFamilySummarysPage = fanIndexFamilySummarysService.getFanIndexFamilySummarysPage(siteId, list, pageNo, pageSize);

        return ResponseUtlis.success(fanIndexFamilySummarysPage);
    }

    /**
     * 联谊会简介,宣言
     *
     * @param siteId 网站ID
     * @return
     */
    @RequestMapping(value = "index/getFanIndexInfo", method = RequestMethod.GET)
    public Response<IndexInfoVo> getFanIndexInfo(@RequestParam("siteId") Integer siteId) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        IndexInfoVo indexInfoVo = fanIndexInfoService.getFanIndexInfoVo(siteId);

        return ResponseUtlis.success(indexInfoVo);
    }

    /**
     *联谊会首页聊天记录
     *@Author: yuzhou
     *@Date: 2018-11-09
     *@Time: 19:26
     *@Param:
     *@return:
     *@Description:
    */
    @RequestMapping(value = "/index/getChatRecordList", method = RequestMethod.GET)
    public Response<FanIndexMessage> getChatRecordList(
            @RequestParam(value = "siteId") Integer siteId,
            @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize
    ) {
        try {
            //判断showId是否有值
            if (siteId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status = 1;
            Page<FanIndexMessage> fanIndexMessage = fanIndexMessageService.getChatRecordList(siteId, status, pageNo, pageSize);
            if (fanIndexMessage == null) {
                //没有取到参数,返回空参
                Page<FanIndexMessage> emptfamilyCultureVo = new Page<FanIndexMessage>();
                return ResponseUtlis.error(Constants.ERRO_CODE, emptfamilyCultureVo);
            }
            return ResponseUtlis.success(fanIndexMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

}
