package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.*;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.entityvo.ProFamilyPersonVo;
import com.genogram.service.*;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 联谊会首页-设置 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Api(description = "联谊会首页(前台)")
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

    @Autowired
    private IFanSysWebNewsShowService fanSysWebNewsShowService;

    @Autowired
    private IFanNewsFamousPersonService fanNewsFamousPersonService;

    /**
     * 状态
     */
    Integer status = 1;

    /**
     * 轮播图
     *
     * @param siteId 网站ID
     * @return
     */
    @ApiOperation(value = "轮播图", notes = "id:主键,siteId:网站Id,picUrl:图片url,sort:排序")
    @RequestMapping(value = "index/getFanIndexSlidePicList", method = RequestMethod.GET)
    public Response<FanIndexSlidePic> getFanIndexSlidePicList(@ApiParam("网站ID") @RequestParam Integer siteId) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        list.add(status);

        List<FanIndexSlidePic> fanIndexSlidePicList = fanIndexSlidePicService.getFanIndexSlidePicListBySiteId(siteId, list);

        if (StringUtils.isEmpty(fanIndexSlidePicList)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(fanIndexSlidePicList);
    }

    /**
     * 联谊堂
     *
     * @param siteId   网站IDid
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */
    @ApiOperation(value = "联谊堂", notes = "id:主键,siteId:网站Id,rootGroup:堂号,rootPerson:始迁祖,leader:负责人,leaderPhone:负责人电话,worshipNum:膜拜,praiseNum:赞")
    @RequestMapping(value = "index/getFanIndexFamilySummarysPage", method = RequestMethod.GET)
    public Response<FanIndexFamilySummarys> getFanIndexFamilySummarysPage(@ApiParam("网站ID") @RequestParam Integer siteId,
                                                                          @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                                          @RequestParam(value = "pageSize", defaultValue = "2333") Integer pageSize) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        list.add(status);

        Page<FanIndexFamilySummarys> fanIndexFamilySummarysPage = fanIndexFamilySummarysService.getFanIndexFamilySummarysPage(siteId, list, pageNo, pageSize);

        if (StringUtils.isEmpty(fanIndexFamilySummarysPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(fanIndexFamilySummarysPage);
    }

    /**
     * 联谊会简介,宣言
     *
     * @param siteId 网站ID
     * @return
     */
    @ApiOperation(value = "基本信息", notes = "id:主键,siteId:网站Id,siteName:网站名称,regionCode;地区编号,totemPicSrc:图腾,title:宣言,description;简介")
    @RequestMapping(value = "index/getFanIndexInfo", method = RequestMethod.GET)
    public Response<IndexInfoVo> getFanIndexInfo(@ApiParam("网站ID") @RequestParam Integer siteId) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        IndexInfoVo indexInfoVo = fanIndexInfoService.getFanIndexInfoVo(siteId);

        if (StringUtils.isEmpty(indexInfoVo)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(indexInfoVo);
    }

    /**
     * 联谊会首页聊天记录
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 19:26
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation("联谊会首页聊天记录")
    @RequestMapping(value = "/index/getChatRecordList", method = RequestMethod.GET)
    public Response<FanIndexMessage> getChatRecordList(
            @ApiParam("网站ID") @RequestParam Integer siteId,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
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
                return ResponseUtlis.error(Constants.ERRO_CODE, "没有取到参数,返回空参");
            }
            return ResponseUtlis.success(fanIndexMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 组织架构查询会长副会长
     *
     * @param siteId
     * @return
     */
    @ApiOperation(value = "组织架构查询会长副会长", notes = "siteId:网站id")
    @RequestMapping(value = "/getFamilyStructureList", method = RequestMethod.GET)
    public Response<ProFamilyPersonVo> getFamilyStructureList(
            @RequestParam(value = "siteId") Integer siteId) {
        try {
            //最大的map
            Map zongmap = new LinkedHashMap();
            List<FanSysWebNewsShow> list = fanSysWebNewsShowService.getMenuCodeByParentId(siteId, 7);

            for (FanSysWebNewsShow show : list) {
                List listtemp = getPersonListByMenuCode(siteId, show.getMenuCode());
                zongmap.put(show.getMenuName(), listtemp);
            }

            return ResponseUtlis.success(zongmap);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    private List getPersonListByMenuCode(int siteId, String menuCode) {
        FanSysWebNewsShow show = fanSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(siteId, menuCode);
        List<FanNewsFamousPerson> familyFrameList = fanNewsFamousPersonService.getFamilyFrameList(show.getShowId());
        return familyFrameList;
    }
}
