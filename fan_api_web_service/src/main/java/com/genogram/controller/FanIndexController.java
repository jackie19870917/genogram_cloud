package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
    private IFanSysWebNewsShowService iFanSysWebNewsShowService;

    @Autowired
    private IFanNewsFamousPersonService iFanNewsFamousPersonService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    /**
     * 状态
     */
    Integer status = 1;

    @ApiOperation(value = "网站", notes = "id-主键,familyCode-姓氏,regionCode-地区,name-网站名,pic-图腾")
    @RequestMapping(value = "getIndexSysSite", method = RequestMethod.GET)
    public Response<ProSysSite> getIndexSysSite() {

        Wrapper<FanSysSite> fanSysSiteWrapper = new EntityWrapper<>();
        List<FanSysSite> fanSysSiteList = allUserLoginService.getFanSysSite(fanSysSiteWrapper);

        Wrapper<ProSysSite> proSysSiteWrapper = new EntityWrapper<>();
        List<ProSysSite> proSysSiteList = allUserLoginService.getProSysSite(proSysSiteWrapper);

        Map map = new HashMap(16);
        map.put("fan", fanSysSiteList);
        map.put("pro", proSysSiteList);

        if (StringUtils.isEmpty(map)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(map);
    }

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
            // List list = new ArrayList();
            //会长类
            // Map huizhangmap = new LinkedHashMap();
            // Map map = new LinkedHashMap();
            //拿到会长的showid
            FanSysWebNewsShow show = iFanSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(siteId, "persion_huizhang");
            List<FanNewsFamousPerson> familyFrameList = iFanNewsFamousPersonService.getFamilyFrameList(show.getShowId());
            zongmap.put(show.getMenuName(), familyFrameList);

            //拿到副会长的fushowid
            FanSysWebNewsShow fushow = iFanSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(siteId, "persion_fuhuizhang");
            List<FanNewsFamousPerson> fufamilyFrameList = iFanNewsFamousPersonService.getFamilyFrameList(fushow.getShowId());
            //familyFrameList.addAll(fufamilyFrameList);
            zongmap.put(fushow.getMenuName(), fufamilyFrameList);

            //族长类
            FanSysWebNewsShow zuzhangshow = iFanSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(siteId, "persion_zuzhang");
            List<FanNewsFamousPerson> zuzhangfamilyFrameList = iFanNewsFamousPersonService.getFamilyFrameList(zuzhangshow.getShowId());
            zongmap.put(zuzhangshow.getMenuName(), zuzhangfamilyFrameList);

            //官员类
            List guanyuan = new ArrayList<>();
            FanSysWebNewsShow guanyuanshow = iFanSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(siteId, "persion_guanyuan");
            List<FanNewsFamousPerson> guanyuanfamilyFrameList = iFanNewsFamousPersonService.getFamilyFrameList(guanyuanshow.getShowId());
            zongmap.put(guanyuanshow.getMenuName(), guanyuanfamilyFrameList);

            //企业家
            FanSysWebNewsShow qiyejiashow = iFanSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(siteId, "persion_qiyejia");
            List<FanNewsFamousPerson> qiyejiafamilyFrameList = iFanNewsFamousPersonService.getFamilyFrameList(qiyejiashow.getShowId());
            zongmap.put(qiyejiashow.getMenuName(), qiyejiafamilyFrameList);

            //店主
            FanSysWebNewsShow dianzhushow = iFanSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(siteId, "persion_dianzhu");
            List<FanNewsFamousPerson> dianzhufamilyFrameList = iFanNewsFamousPersonService.getFamilyFrameList(dianzhushow.getShowId());
            zongmap.put(dianzhushow.getMenuName(), dianzhufamilyFrameList);

//            huizhangmap.put(show.getMenuName(),familyFrameList);
//            list.add(huizhangmap);
//            list.add(zongmap);
            if (familyFrameList == null) {
                //没有取到参数,返回空参
                Page<ProFamilyPersonVo> emptfamilyCultureVo = new Page<ProFamilyPersonVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, "没有取到参数,返回空参");
            }
            return ResponseUtlis.success(zongmap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}
