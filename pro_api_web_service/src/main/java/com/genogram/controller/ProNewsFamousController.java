package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.ProNewsFamousPerson;
import com.genogram.entity.ProSysWebNewsShow;
import com.genogram.entityvo.FamilyPersonVo;
import com.genogram.entityvo.ProFamilyPersonVo;
import com.genogram.service.IProNewsFamilyPersonService;
import com.genogram.service.IProSysWebNewsShowService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Api(description = "省级家族长老，组织架构")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/proNewsFamous")
public class ProNewsFamousController {

    @Autowired
    private IProNewsFamilyPersonService iProNewsFamousPersonService;

    @Autowired
    private IProSysWebNewsShowService iProSysWebNewsShowService;

    /**
     * 家族长老查询,组织架构
     */
    @ApiOperation(value = "家族长老，组织架构", notes = "showId:显示位置,pageNo:页数,pageSize:总页数")
    @ResponseBody
    @RequestMapping(value = "selectPersonPage", method = RequestMethod.GET)
    public Response<ProNewsFamousPerson> selectPersonPage(
            @RequestParam(value = "showId") Integer showId, // 产业显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            int status = 1;
            Page<ProFamilyPersonVo> familyPersonVo = iProNewsFamousPersonService.getFamilyPersonPage(showId, status, pageNo, pageSize);
            if (familyPersonVo == null) {
                //没有取到参数,返回空参
                Page<FamilyPersonVo> emptfamilyCultureVo = new Page<FamilyPersonVo>();
                return ResponseUtils.error(Constants.ERRO_CODE, "familyPersonVo为空");
            }
            return ResponseUtils.success(familyPersonVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会家族名人后台查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:24
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation("查询家族名人")
    @RequestMapping(value = "/getFamilyPersonPage", method = RequestMethod.GET)
    public Response<ProFamilyPersonVo> getFamilyPersonPage(
            @RequestParam(value = "showId") Integer showId,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        try {
            //判断showId是否有值
            if (showId == null) {
                return ResponseUtils.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            List statusList = new ArrayList();
            statusList.add(1);
            statusList.add(2);
            //查询文章信息的条件
            Wrapper<ProNewsFamousPerson> entity = new EntityWrapper<ProNewsFamousPerson>();
            entity.eq("show_id", showId);
            if (statusList.size() != 0) {
                entity.in("status", statusList);
            }
            entity.orderBy("create_time", false);
            Page<ProFamilyPersonVo> familyPersonVoPage = iProNewsFamousPersonService.getFamilyPersonPages(entity, pageNo, pageSize);
            if (familyPersonVoPage == null) {
                //没有取到参数,返回空参
                Page<FamilyPersonVo> emptfamilyCultureVo = new Page<FamilyPersonVo>();
                return ResponseUtils.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtils.success(familyPersonVoPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会家族文化详情查询一条
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "家族长老，组织架构", notes = "showId:显示位置,pageNo:页数,pageSize:总页数")
    @RequestMapping(value = "/getFamilyPersionDetail", method = RequestMethod.GET)
    public Response<ProFamilyPersonVo> getFamilyPersionDetail(
            @RequestParam(value = "id") Integer id // 家族文化文章ID
    ) {
        try {
            //返回空参
            ProFamilyPersonVo proFamilyPersonVo = new ProFamilyPersonVo();
            if (id == null) {
                return ResponseUtils.error(Constants.IS_EMPTY, "id为空");
            }
            ProFamilyPersonVo newsDetailVo = iProNewsFamousPersonService.getFamilyFamilyDetail(id);
            if (newsDetailVo == null) {
                return ResponseUtils.error(Constants.ERRO_CODE, "newsDetailVo为空");
            }
            //增加查看数
            iProNewsFamousPersonService.addVisitNum(id);
            return ResponseUtils.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 组织架构
     */
    @ApiOperation(value = "组织架构", notes = "siteId:网站id")
    @RequestMapping(value = "/getFamilyStructureList", method = RequestMethod.GET)
    public Response<ProFamilyPersonVo> getFamilyStructureList(
            @RequestParam(value = "siteId") Integer siteId
    ) {
        try {
            Map map = new LinkedHashMap();
            //拿到会长的showid
            ProSysWebNewsShow show = iProSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(siteId, "persion_huizhang");
            ProNewsFamousPerson familyFrameList = iProNewsFamousPersonService.getFamilyFrameList(show.getShowId());
            map.put(show.getMenuName(), familyFrameList);

            //拿到执行会长的showid
            ProSysWebNewsShow zhixinshow = iProSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(siteId, "persion_zhixin_huizhang");
            ProNewsFamousPerson zhixinfamilyFrameList = iProNewsFamousPersonService.getFamilyFrameList(zhixinshow.getShowId());
            map.put(zhixinshow.getMenuName(), zhixinfamilyFrameList);

            //拿到名誉会长的showid
            ProSysWebNewsShow mingyushow = iProSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(siteId, "persion_mingyu_huizhang");
            ProNewsFamousPerson mingyufamilyFrameList = iProNewsFamousPersonService.getFamilyFrameList(mingyushow.getShowId());
            map.put(mingyushow.getMenuName(), mingyufamilyFrameList);

            //拿到副会长的showid
            ProSysWebNewsShow fushow = iProSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(siteId, "persion_fuhuizhang");
            ProNewsFamousPerson fufamilyFrameList = iProNewsFamousPersonService.getFamilyFrameList(fushow.getShowId());
            map.put(fushow.getMenuName(), fufamilyFrameList);

            if (familyFrameList == null) {
                //没有取到参数,返回空参
                Page<ProFamilyPersonVo> emptfamilyCultureVo = new Page<ProFamilyPersonVo>();
                return ResponseUtils.error(Constants.ERRO_CODE, "familyFrameList为空");
            }
            return ResponseUtils.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }
}
