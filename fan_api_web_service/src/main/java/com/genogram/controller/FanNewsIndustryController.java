package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsIndustry;
import com.genogram.entityvo.FamilyIndustryVo;
import com.genogram.entityvo.IndustryDetailVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IFanNewsIndustryService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 联谊会-家族产业 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/fanNewsIndustry")
@Api(description = "联谊会家族产业前台增删改查")
public class FanNewsIndustryController {

    @Autowired
    private IFanNewsIndustryService fanNewsIndustryService;

    /**
     * 联谊会家族产业  详情页查询
     *
     * @param showId   家族产业显示位置
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @Author: wang, wei
     * @Date: 2018-11-06
     * @Time: 23:02
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会家族产业  详情页查询", notes =
            "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键Id --" +
                    "industryLocation 家族产业具体地址 --" +
                    "newsText 内容 --" +
                    "newsTitle 标题 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数")
    @RequestMapping(value = "/getFamilyIndustryPage", method = RequestMethod.GET)
    public Response<FamilyIndustryVo> getFamilyCulturePage(
            @ApiParam(value = "显示位置Id") @RequestParam(value = "showId") Integer showId,
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        //判断showId是否有值
        if (showId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }
        return getFamilyIndustryVoResponse(showId, pageNo, pageSize);
    }

    /**
     * 联谊会家族产业 首页查询
     *
     * @param showId 家族产业显示位置
     * @Author: wang, wei
     * @Date: 2018-11-06
     * @Time: 23:02
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会家族产业 首页查询", notes =
            "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键Id --" +
                    "industryLocation 家族产业具体地址 --" +
                    "newsText 内容 --" +
                    "newsTitle 标题 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数")
    @RequestMapping(value = "/index/getFamilyIndexIndustryList", method = RequestMethod.GET)
    public Response<FamilyIndustryVo> getFamilyIndexIndustryList(
            @ApiParam(value = "显示位置Id") @RequestParam(value = "showId") Integer showId,
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        //判断showId是否有值
        if (showId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }
        return getFamilyIndustryVoResponse(showId, pageNo, pageSize);
    }

    /**
     * 抽取的公共方法
     * getFamilyIndustryVoResponse
     *
     * @param showId   家族产业显示位置
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @Author: wang, wei
     * @Date: 2018-11-06
     * @Time: 23:08
     * @return:
     * @Description:
     */
    private Response<FamilyIndustryVo> getFamilyIndustryVoResponse(Integer showId, Integer pageNo, Integer pageSize) {
        try {
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            List statusList = new ArrayList();
            statusList.add(1);
            //查询文章信息的条件
            Wrapper<FanNewsIndustry> entity = new EntityWrapper<FanNewsIndustry>();
            entity.eq("show_id", Integer.valueOf(showId));
            entity.in("status", statusList);
            entity.orderBy("create_time", false);
            Page<FamilyIndustryVo> familyCultureVo = fanNewsIndustryService.getFamilyIndustryPage(entity, pageNo, pageSize);
            if (familyCultureVo == null) {
                //没有取到参数,返回空参
                Page<FamilyIndustryVo> emptfamilyCultureVo = new Page<FamilyIndustryVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, "数据为空");
            }
            return ResponseUtlis.success(familyCultureVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会家族产业各个产业的详情
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:25
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会家族产业各个产业的详情", notes =
            "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键Id --" +
                    "industryLocation 家族产业具体地址 --" +
                    "newsText 内容 --" +
                    "newsTitle 标题 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数")
    @RequestMapping(value = "/getFamilyIndustryDetail", method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyIndustryDetail(
            @ApiParam(value = "主键Id") @RequestParam(value = "id") Integer id
    ) {
        try {
            NewsDetailVo newsDetailEmpty = new NewsDetailVo();
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "数据为空");
            }
            IndustryDetailVo familyIndustryDetail = fanNewsIndustryService.getFamilyIndustryDetail(id);
            //判断是否返回为空
            if (familyIndustryDetail == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "数据为空");
            }
            //增加查看数
            fanNewsIndustryService.addVisitNum(id);
            return ResponseUtlis.success(familyIndustryDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}

