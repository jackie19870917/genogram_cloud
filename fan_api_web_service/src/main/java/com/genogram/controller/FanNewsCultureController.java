package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanNewsCultureNews;
import com.genogram.entity.FanNewsCultureZipai;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsCultureZipaiVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IFanNewsCultureNewsService;
import com.genogram.service.IFanNewsCultureZipaiService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import com.genogram.unit.StringsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 联谊会-家族文化前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "http://47.105.177.1/fanApiWebService")
@RequestMapping("/genogram/fanNewsCulture")
@Api(description = "联谊会家族文化前台增删改查")
public class FanNewsCultureController {

    @Autowired
    private IFanNewsCultureZipaiService fanNewsCultureZipaiService;
    @Autowired
    private IFanNewsCultureNewsService fanNewsCultureNewsService;

    /**
     * 联谊会家族字派查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:20
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会家族字派查询", notes =
            "ancestorsName 祖先名 --" +
                    "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数 --" +
                    "ziapiLocation 字派具体地域 --" +
                    "zipaiTxt 字派数组:数字和字的组合")
    @RequestMapping(value = "/getCommonalityPage", method = RequestMethod.GET)
    public Response<FanNewsCultureZipai> getCommonalityPage(
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
            Wrapper<FanNewsCultureZipai> entity = new EntityWrapper<FanNewsCultureZipai>();
            entity.eq("show_id", showId);
            entity.in("status", statusList);
            entity.orderBy("update_time", false);
            Page<NewsCultureZipaiVo> fanNewsCultureZipai = fanNewsCultureZipaiService.commonality(entity, pageNo, pageSize);
            if (fanNewsCultureZipai == null) {
                //没有取到参数,返回空参
                Page<FamilyCultureVo> emptfamilyCultureVo = new Page<FamilyCultureVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.success(fanNewsCultureZipai);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会首页家族字派
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:20
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会首页家族字派", notes =
            "ancestorsName 祖先名 --" +
                    "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数 --" +
                    "ziapiLocation 字派具体地域 --" +
                    "zipaiTxt 字派数组:数字和字的组合")
    @RequestMapping(value = "/index/getCommonalityIndexPage", method = RequestMethod.GET)
    public Response<StringBuffer> getCommonalityIndexPage(
            @ApiParam(value = "显示位置Id") @RequestParam(value = "showId") Integer showId // 家族文化显示位置
    ) {
        try {
            //判断showId是否有值
            if (showId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            List statusList = new ArrayList();
            statusList.add(1);
            Wrapper<FanNewsCultureZipai> entity = new EntityWrapper<FanNewsCultureZipai>();
            entity.eq("show_id", showId);
            entity.in("status", statusList);
            entity.orderBy("update_time", false);
            StringBuffer stringBuffer = fanNewsCultureZipaiService.commonalityIndex(entity);
            //判断该stringBuffer是否返回为null
            if (stringBuffer == null) {
                //没有取到参数,返回空参
                Page<FamilyCultureVo> emptfamilyCultureVo = new Page<FamilyCultureVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, "没有取到参数,返回空参");
            }
            return ResponseUtlis.success(stringBuffer);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会家族字派模糊查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 10:06
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会家族字派模糊查询", notes =
            "ancestorsName 祖先名 --" +
                    "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数 --" +
                    "ziapiLocation 字派具体地域 --" +
                    "zipaiTxt 字派数组:数字和字的组合")
    @RequestMapping(value = "/getZipaiVaguePage", method = RequestMethod.POST)
    public Response<FanNewsCultureZipai> getGrabblePage(
            @ApiParam(value = "显示位置Id") @RequestParam(value = "showId") Integer showId,
            @ApiParam(value = "家族字派模糊查询参数") @RequestParam(value = "zipaiTxt", required = false) String zipaiTxt,
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            //返回的空list集合结构
            List<FanNewsCultureZipai> list = new ArrayList<>();
            //判断showId是否有值
            if (showId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "showId 为空");
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            List statusList = new ArrayList();
            statusList.add(1);
            Wrapper<FanNewsCultureZipai> entity = new EntityWrapper<FanNewsCultureZipai>();
            entity.eq("show_id", showId);
            entity.eq("status", statusList);
            if (StringsUtils.isEmpty(zipaiTxt)) {
                entity.like("zipai_txt", zipaiTxt);
            }
            Page<NewsCultureZipaiVo> fanNewsCultureZipaiPage = fanNewsCultureZipaiService.commonality(entity, pageNo, pageSize);
            if (fanNewsCultureZipaiPage == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "数据为空");
            }
            return ResponseUtlis.success(fanNewsCultureZipaiPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }


    /**
     * 联谊会家族文化查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会家族文化查询", notes =
            "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键Id --" +
                    "newsText 内容 --" +
                    "newsTitle 标题 --" +
                    "showId 显示位置ID --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数")
    @RequestMapping(value = "/getFamilyCulturePage", method = RequestMethod.GET)
    public Response<FamilyCultureVo> getFamilyCulturePage(
            @ApiParam(value = "显示位置Id") @RequestParam(value = "showId") Integer showId,
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
            //查询文章信息的条件
            Wrapper<FanNewsCultureNews> entity = new EntityWrapper<FanNewsCultureNews>();
            entity.eq("show_id", showId);
            entity.in("status", statusList);
            entity.orderBy("create_time", false);
            Page<FamilyCultureVo> familyCultureVoList = fanNewsCultureNewsService.getFamilyCulturePage(entity, pageNo, pageSize);
            if (familyCultureVoList == null) {
                //没有取到参数,返回空参
                Page<FamilyCultureVo> emptfamilyCultureVo = new Page<FamilyCultureVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, "数据为空");
            }
            return ResponseUtlis.success(familyCultureVoList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会首页家族文化查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会首页家族文化查询", notes =
            "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键Id --" +
                    "newsText 内容 --" +
                    "newsTitle 标题 --" +
                    "showId 显示位置ID --" +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "visitNum 查看数")
    @RequestMapping(value = "/index/getFamilyIndexCulturePage", method = RequestMethod.GET)
    public Response<FamilyCultureVo> getFamilyIndexCulturePage(
            @ApiParam(value = "显示位置Id") @RequestParam(value = "siteId") Integer siteId,
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        try {
            //判断showId是否有值
            if (siteId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            Page<FamilyCultureVo> familyCultureVo = fanNewsCultureNewsService.getFamilyIndexCulturePage(siteId, pageNo, pageSize);
            if (StringsUtils.isEmpty(familyCultureVo)) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.success(familyCultureVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
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
    /*private Response<FamilyCultureVo> getFamilyCultureVoResponse(Integer showId, Integer pageNo, Integer pageSize) {
        try {
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            List statusList = new ArrayList();
            statusList.add(1);
            //查询文章信息的条件
            Wrapper<FanNewsCultureNews> entity = new EntityWrapper<FanNewsCultureNews>();
                entity.eq("show_id", showId);
                entity.in("status", statusList);
                entity.orderBy("create_time", false);
            Page<FamilyCultureVo> familyCultureVoList = fanNewsCultureNewsService.getFamilyCulturePage(entity, pageNo, pageSize);
            if (familyCultureVoList == null) {
                //没有取到参数,返回空参
                Page<FamilyCultureVo> emptfamilyCultureVo = new Page<FamilyCultureVo>();
                return ResponseUtlis.error(Constants.ERRO_CODE, "数据为空");
            }
            return ResponseUtlis.success(familyCultureVoList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }*/

    /**
     * 联谊会家族文化详情查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:21
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会家族文化详情查询", notes =
            "createTime 创建时间 --" +
                    "createTimeLong 创建时间Long --" +
                    "createUser 创建人ID --" +
                    "createUserName 创建人姓名 --" +
                    "fanNewsUploadFileList 县级附件 --" +
                    "id 主键ID" +
                    "newsText 内容 --" +
                    "newsTitle 标题 --" +
                    "proNewsUploadFileList 省级附件 --" +
                    "showId 显示位置Id" +
                    "source " +
                    "status 状态(0:删除;1:已发布;2:草稿3:不显示) --" +
                    "updateTime 修改时间 --" +
                    "updateTimeLong 修改时间Long --" +
                    "updateUser 修改人Id --" +
                    "updateUserName 修改人名称" +
                    "visitNum 查看数")
    @RequestMapping(value = "/getFamilyCultureDetail", method = RequestMethod.GET)
    public Response<NewsDetailVo> getFamilyCultureDetail(
            @ApiParam(value = "主键Id") @RequestParam(value = "id") Integer id // 家族文化文章ID
    ) {
        try {
            //返回空参
            NewsDetailVo newsDetail = new NewsDetailVo();
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "数据为空");
            }
            NewsDetailVo newsDetailVo = fanNewsCultureNewsService.getFamilyCultureDetail(id);
            if (newsDetailVo == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "数据为空");
            }
            //增加查看数
            fanNewsCultureNewsService.addVisitNum(id);
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

}

