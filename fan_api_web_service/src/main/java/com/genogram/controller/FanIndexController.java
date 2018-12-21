package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.*;
import com.genogram.entityvo.IndexInfoVo;
import com.genogram.entityvo.ProFamilyPersonVo;
import com.genogram.entityvo.SysWebMenuVo;
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
@CrossOrigin
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

    @Autowired
    private IAllUserNewsInfoService allUserNewsInfoService;

    @Autowired
    private IFanSysSiteService fanSysSiteService;

    @Autowired
    private ISysSiteService sysSiteService;

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

    @ApiOperation(value = "最新发布", notes = "id-主键,userId-个人Id,title-文章标题,newsFaceUrl-文章封面URL,content-文章内容,status-状态(0-删除,1-正常,2-草稿)")
    @RequestMapping(value = "getAllUserNewsInfoPage", method = RequestMethod.GET)
    public Response<AllUserNewsInfo> getAllUserNewsInfoPage(@ApiParam("主键") @RequestParam("siteId") Integer siteId,
                                                            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        FanSysSite fanSysSite = fanSysSiteService.getFanSysSite(siteId);

        if (StringUtils.isEmpty(fanSysSite)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        Map map = new HashMap(16);
        map.put("region_id", fanSysSite.getRegionCode());
        map.put("status", status);
        map.put("sys_status", status);

        Page page = new Page();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Page<AllUserNewsInfo> mapPage = new Page<>(page.getCurrent(), page.getSize());

        Page<AllUserNewsInfo> userNewsInfoPage = allUserNewsInfoService.getAllUserNewsInfoList(mapPage, map);

        if (StringUtils.isEmpty(userNewsInfoPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(userNewsInfoPage);
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
                List<FanNewsFamousPerson> listtemp = getPersonListByMenuCode(siteId, show.getMenuId());
                List list1 = new ArrayList();
                listtemp.forEach((FanNewsFamousPerson fanNewsFamousPerson) -> {
                    fanNewsFamousPerson.setPersonSummary(fanNewsFamousPerson.getPersonSummary().replaceAll("&nbsp;", ""));
                    list1.add(fanNewsFamousPerson);
                });
                zongmap.put(show.getMenuName(), list1);
            }

            return ResponseUtlis.success(zongmap);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    private List<FanNewsFamousPerson> getPersonListByMenuCode(int siteId, Integer menuId) {
        List<SysWebMenuVo> menuVoList = fanSysWebNewsShowService.getSysWebMenuVo(siteId, menuId);
        List<FanNewsFamousPerson> familyFrameList = fanNewsFamousPersonService.getFamilyFrameList(menuVoList.get(0).getShowId());
        return familyFrameList;
    }

    @ApiOperation(value = "最铁盟友 (省级)", notes = "id-主键,familyCode-姓氏编号,regionCode-地区编号,name-网站名称,parent-父网站对面的地区的编号")
    @RequestMapping(value = "getProSysSiteList", method = RequestMethod.GET)
    public Response<ProSysSite> getProSysSiteList() {

        List<ProSysSite> proSysSiteList = sysSiteService.getProSysSite();

        if (proSysSiteList.size() == 0) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        } else {
            return ResponseUtlis.success(proSysSiteList);
        }
    }

    @ApiOperation(value = "最铁盟友 (县级)", notes = "id-主键,familyCode-姓氏编号,regionCode-地区编号,name-网站名称,parent-父网站对面的地区的编号")
    @RequestMapping(value = "getFanSysSiteList", method = RequestMethod.GET)
    public Response<FanSysSite> getFanSysSiteList(@ApiParam("姓氏") @RequestParam Integer familyCode,
                                                  @ApiParam("省级地区编号") @RequestParam String parent,
                                                  @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(value = "pageSize", defaultValue = "9") Integer pageSize) {

        List<FanSysSite> fanSysSiteList = sysSiteService.getFanSysSite(familyCode, parent);

        Page<FanSysSite> page = new Page<>(pageNo, pageSize);
        page.setRecords(fanSysSiteList);
        page.setTotal(fanSysSiteList.size());

        if (StringUtils.isEmpty(page)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        } else {
            return ResponseUtlis.success(page);
        }
    }
}
