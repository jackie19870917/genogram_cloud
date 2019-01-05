package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsCultureZipai;
import com.genogram.entity.ProNewsCultureNews;
import com.genogram.entity.ProNewsCultureZipai;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.entityvo.ProNewsCultureZipaiVo;
import com.genogram.service.IProNewsCultureNewsService;
import com.genogram.service.IProNewsCultureZipaiService;
import com.genogram.unit.Response;
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
 * 省级-家族文化前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/proNewsCulture")
@Api(description = "省级家族文化前台增删改查")
public class ProNewsCultureController {

    @Autowired
    private IProNewsCultureZipaiService proNewsCultureZipaiService;

    @Autowired
    private IProNewsCultureNewsService proNewsCultureNewsService;

    /**
     * 省级家族字派详情页查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:20
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级家族字派详情页查询", notes =
            "ancestorsName 祖先名 --" +
                    "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键id --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示)" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数" +
                    "ziapiLocation 字派具体地域 --" +
                    "zipaiTxt 字派数组:数字和字的组合 --")
    @RequestMapping(value = "/getCommonalityPage", method = RequestMethod.GET)
    public Response<ProNewsCultureZipai> getCommonalityPage(
            @ApiParam(value = "显示位置Id") @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            //判断showId是否有值
            if (showId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            List statusList = new ArrayList();
            statusList.add(1);
            //查询条件
            Wrapper<ProNewsCultureZipai> entity = new EntityWrapper<ProNewsCultureZipai>();
            entity.eq("show_id", showId);
            entity.in("status", statusList);
            entity.orderBy("create_time", false);
            Page<ProNewsCultureZipai> fanNewsCultureZipai = proNewsCultureZipaiService.commonality(entity, pageNo, pageSize);
            if (fanNewsCultureZipai == null) {
                //没有取到参数,返回空参
                Page<ProNewsCultureZipai> emptfamilyCultureVo = new Page<ProNewsCultureZipai>();
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.success(fanNewsCultureZipai);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级家族字派模糊查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 10:06
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级家族字派模糊查询", notes =
            "ancestorsName 祖先名 --" +
                    "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键id --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示)" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数" +
                    "ziapiLocation 字派具体地域 --" +
                    "zipaiTxt 字派数组:数字和字的组合 --")
    @RequestMapping(value = "/getZipaiVaguePage", method = RequestMethod.GET)
    public Response<ProNewsCultureZipai> getZipaiVaguePage(
            @ApiParam(value = "省级显示位置Id") @RequestParam(value = "showId") Integer showId,
            @ApiParam(value = "省级网站Id") @RequestParam(value = "siteId") Integer siteId,
            @ApiParam(value = "模糊查询的参数") @RequestParam(value = "zipaiTxt") String zipaiTxt, // 家族字派模糊查询参数
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            //返回的空list集合结构
            List<ProNewsCultureZipai> list = new ArrayList<>();
            //判断showId是否有值
            if (showId == null && siteId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "list为空");
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status = 1;
            Page<ProNewsCultureZipaiVo> mapPage = new Page<ProNewsCultureZipaiVo>(pageNo, pageSize);

            Map map = new HashMap<>(16);
            map.put("showId", showId);
            map.put("siteId", siteId);
            map.put("zipaiTxt", zipaiTxt);
            map.put("status", status);
            Page<ProNewsCultureZipaiVo> zipaiVaguePage = proNewsCultureZipaiService.getZipaiVaguePage(mapPage, map);
            if (zipaiVaguePage == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "zipaiVaguePage为空");
            }
            return ResponseUtlis.success(zipaiVaguePage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级查出各个地区的字派
     *
     * @Author: yuzhou
     * @Date: 2018-11-16
     * @Time: 9:53
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级查出各个地区的字派", notes =
            "ancestorsName 祖先名 --" +
                    "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键id --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示)" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数" +
                    "ziapiLocation 字派具体地域 --" +
                    "zipaiTxt 字派数组:数字和字的组合 --")
    @RequestMapping(value = "/getZipaiRegionPage", method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> getZipaiRegionPage(
            @ApiParam(value = "网站Id") @RequestParam(value = "sizeId") Integer sizeId, // 家族字派显示位置
            @ApiParam(value = "省级下属县级的地区编号") @RequestParam(value = "code") Integer code, // 省级下属县级的地区编号
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        //判断sizeId是否有值
        if (sizeId == null && code == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        int status = 1;
        Page<FanNewsCultureZipai> mapPage = new Page<FanNewsCultureZipai>(pageNo, pageSize);

        Map map = new HashMap<>(16);
        map.put("code", code);
        map.put("status", 1);

        Page<FanNewsCultureZipai> zipaiVaguePage = proNewsCultureZipaiService.getZipaiRegionPage(sizeId, mapPage, map);
        return ResponseUtlis.success(zipaiVaguePage);
    }

    /**
     * 省级家族文化详情页查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级家族文化详情页查询", notes =
            "createTime 创建时间 --" +
                    "createTimeLong 创建时间转换Long --" +
                    "createUser 创建人" +
                    "fanNewsUploadFileList 县级附件集合 --" +
                    "id 主键Id" +
                    "newsText 内容 --" +
                    "newsTitle 标题 --" +
                    "proNewsUploadFileList 省级附件集合 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateTimeLong 修改时间转换为Long --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数")
    @RequestMapping(value = "/getFamilyCulturePage", method = RequestMethod.GET)
    public Response<FamilyCultureVo> getFamilyCulturePage(
            @ApiParam(value = "显示位置Id") @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        //判断showId是否有值
        if (showId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }
        return getFamilyCultureVoResponse(showId, pageNo, pageSize);
    }

    /**
     * 省级首页家族文化查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级首页家族文化查询", notes =
            "createTime 创建时间 --" +
                    "createTimeLong 创建时间转换Long --" +
                    "createUser 创建人" +
                    "fanNewsUploadFileList 县级附件集合 --" +
                    "id 主键Id" +
                    "newsText 内容 --" +
                    "newsTitle 标题 --" +
                    "proNewsUploadFileList 省级附件集合 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateTimeLong 修改时间转换为Long --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数")
    @RequestMapping(value = "/index/getFamilyIndexCulturePage", method = RequestMethod.GET)
    public Response<FamilyCultureVo> getFamilyIndexCulturePage(
            @ApiParam(value = "显示位置Id") @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        //判断showId是否有值
        if (showId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }
        return getFamilyCultureVoResponse(showId, pageNo, pageSize);
    }

    /**
     * 抽取的家族文化方法查询方法
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    private Response<FamilyCultureVo> getFamilyCultureVoResponse(Integer showId, Integer pageNo, Integer pageSize) {
        try {
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            List statusList = new ArrayList();
            statusList.add(1);
            //查询文章信息的条件
            Wrapper<ProNewsCultureNews> entity = new EntityWrapper<ProNewsCultureNews>();
            entity.eq("show_id", showId);
            entity.in("status", statusList);
            entity.orderBy("create_time", false);
            Page<FamilyCultureVo> familyCultureVoList = proNewsCultureNewsService.getFamilyCulturePage(entity, pageNo, pageSize);
            if (familyCultureVoList == null) {
                //没有取到参数,返回空参
                Page<FamilyCultureVo> emptfamilyCultureVo = new Page<FamilyCultureVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, "familyCultureVoList为空");
            }
            return ResponseUtlis.success(familyCultureVoList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级家族文化详情查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级家族文化详情查询", notes =
            "createTime 创建时间 --" +
                    "createTimeLong 创建时间转换Long --" +
                    "createUser 创建人" +
                    "fanNewsUploadFileList 县级附件集合 --" +
                    "id 主键Id" +
                    "newsText 内容 --" +
                    "newsTitle 标题 --" +
                    "proNewsUploadFileList 省级附件集合 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateTimeLong 修改时间转换为Long --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数 --" +
                    "fileName 附件文件名 --" +
                    "filePath 附件文件路径 --" +
                    "picIndex 附件是否封面(0.否;1:是封面) --" +
                    "newsId 文章文化表的主键")
    @RequestMapping(value = "/getFamilyCultureDetail", method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyCultureDetail(
            @ApiParam(value = "主键") @RequestParam(value = "id") Integer id // 家族文化文章ID
    ) {
        try {
            //返回空参
            NewsDetailVo newsDetail = new NewsDetailVo();
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "id为空");
            }
            NewsDetailVo newsDetailVo = proNewsCultureNewsService.getFamilyCultureDetail(id);
            if (newsDetailVo == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "newsDetailVo为空");
            }
            //增加查看数
            proNewsCultureNewsService.addVisitNum(id);
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}

