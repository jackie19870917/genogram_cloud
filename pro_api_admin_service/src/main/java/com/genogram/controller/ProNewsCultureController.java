package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ProNewsCultureNews;
import com.genogram.entity.ProNewsCultureZipai;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsCultureZipaiVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.IAllCheckOutService;
import com.genogram.service.IProNewsCultureNewsService;
import com.genogram.service.IProNewsCultureZipaiService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import com.genogram.unit.StringsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 省级-家族文化后台控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/proNewsCulture")
@Api(description = "省级家族文化后台增删改查")
public class ProNewsCultureController {

    @Autowired
    private IProNewsCultureZipaiService proNewsCultureZipaiService;

    @Autowired
    private IProNewsCultureNewsService proNewsCultureNewsService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllCheckOutService allCheckOutService;

    /**
     * 省级后台字派查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-14
     * @Time: 16:06
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级后台字派查询", notes =
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
            @ApiParam(value = "每页显示条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //判断token是否为空
            if (StringsUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //判断token是否正确
            if (StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "请输入正确的token");
            }
            //没有取到参数,返回空参
            Page<ProNewsCultureZipai> emptfanNewsCultureZipai = new Page<ProNewsCultureZipai>();
            //判断showId是否有值
            if (showId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, "showId 为空");
            }
            //状态
            List statusList = new ArrayList();
            statusList.add(1);
            //查询条件
            Wrapper<ProNewsCultureZipai> entity = new EntityWrapper<ProNewsCultureZipai>();
            entity.eq("show_id", Integer.valueOf(showId));
            if (statusList.size() != 0) {
                entity.in("status", statusList);
            }
            entity.orderBy("create_time", false);
            Page<ProNewsCultureZipai> commonality = proNewsCultureZipaiService.commonality(entity, pageNo, pageSize);
            if (commonality == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, "数据为空");
            }
            return ResponseUtlis.success(commonality);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级字派进入后台修改页面
     *
     * @Author: yuzhou
     * @Date: 2018-11-14
     * @Time: 16:15
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级字派进入后台修改页面", notes =
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
    @RequestMapping(value = "/getZiPaiDetail", method = RequestMethod.GET)
    public Response<ProNewsCultureZipai> getZiPaiDetail(
            @ApiParam(value = "主键Id") @RequestParam(value = "id") Integer id, // 家族字派文章ID
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //判断token是否为空
            if (StringsUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //判断token是否正确
            if (StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "请输入正确的token");
            }
            //判断id是否为空
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            ProNewsCultureZipai proNewsCultureZipai = proNewsCultureZipaiService.getZiPaiDetail(id);
            return ResponseUtlis.success(proNewsCultureZipai);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级家族字派后台新增修改 发表
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:19
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级家族字派后台新增修改 发表", notes =
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
    @RequestMapping(value = "/addOrUpdateZiPai", method = RequestMethod.POST)
    public Response<ProNewsCultureZipai> addOrUpdateZiPai(@ApiParam(value = "省级字派实体类") ProNewsCultureZipai proNewsCultureZipai,
                                                          @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

       /* Set set = allCheckOutService.getSensitiveWord(proNewsCultureZipai.getZipaiTxt());

        if (set.size() >= 1) {
            return ResponseUtlis.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set);
        }*/

        //状态(0:删除;1:已发布;2:草稿3:不显示)
        proNewsCultureZipai.setStatus(1);
        return getFanNewsCultureZipaiResponse(proNewsCultureZipai, token);
    }

    /**
     * 省级家族字派后台新增修改 草稿
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 11:16
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级家族字派后台新增修改 草稿", notes =
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
    @RequestMapping(value = "/addOrUpdateZiPaiDrft", method = RequestMethod.POST)
    public Response<ProNewsCultureZipai> addOrUpdateZiPaiDrft(@ApiParam(value = "省级字派实体类") ProNewsCultureZipai proNewsCultureZipai,
                                                              @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //状态(0:删除;1:已发布;2:草稿3:不显示)
        proNewsCultureZipai.setStatus(2);
        return getFanNewsCultureZipaiResponse(proNewsCultureZipai, token);
    }

    /**
     * 省级家族字派后台新增修改 抽取的方法
     *
     * @Author: yuzhou
     * @Date: 2018-11-100
     * @Time: 12:19
     * @Param:
     * @return:
     * @Description:
     */
    private Response<ProNewsCultureZipai> getFanNewsCultureZipaiResponse(ProNewsCultureZipai proNewsCultureZipai, String token) {
        try {
            //判断token是否为空
            if (StringsUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //判断token是否正确
            if (StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "请输入正确的token");
            }
            boolean result = proNewsCultureZipaiService.addOrUpdateZiPai(proNewsCultureZipai);
            if (!result) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级家族字派后台删除
     *
     * @Author: yuzhou
     * @Date: 2018-11-14
     * @Time: 16:41
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级家族字派后台删除", notes = "根据id删除")
    @RequestMapping(value = "/deleteZipaiById", method = RequestMethod.GET)
    public Response<ProNewsCultureZipai> deleteZipaiById(
            @ApiParam(value = "主键Id") @RequestParam(value = "id") Integer id, // 家族文化详情显示位置
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //判断token是否为空
            if (StringsUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //判断token是否正确
            if (StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "请输入正确的token");
            }
            //判断id是否为空
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status = 0;
            Boolean aBoolean = proNewsCultureZipaiService.deleteZipaiById(id, status);
            if (!aBoolean) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级家族文化查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-14
     * @Time: 17:02
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级家族文化查询", notes =
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
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //判断token是否为空
            if (StringsUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //判断token是否正确
            if (StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "请输入正确的token");
            }
            //判断showId是否有值
            if (showId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            List statusList = new ArrayList();
            statusList.add(1);
            statusList.add(2);
            //查询文章信息的条件
            Wrapper<ProNewsCultureNews> entity = new EntityWrapper<ProNewsCultureNews>();
            entity.eq("show_id", Integer.valueOf(showId));
            if (statusList.size() != 0) {
                entity.in("status", statusList);
            }
            entity.orderBy("create_time", false);
            Page<FamilyCultureVo> familyCultureVoList = proNewsCultureNewsService.getFamilyCulturePage(entity, pageNo, pageSize);
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
     * 省级家族文化详情 进入修改页面
     *
     * @Author: yuzhou
     * @Date: 2018-11-14
     * @Time: 17:04
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级家族文化详情 进入修改页面", notes =
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
            @ApiParam(value = "主键Id") @RequestParam(value = "id") Integer id, // 家族文化详情显示位置
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //判断token是否为空
            if (StringsUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //判断token是否正确
            if (StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "请输入正确的token");
            }
            //判断Id
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            NewsDetailVo newsDetailVo = proNewsCultureNewsService.getFamilyCultureDetail(id);
            return ResponseUtlis.success(newsDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级家族文化后台添加和修改 发表
     *
     * @Author: yuzhou
     * @Date: 2018-11-09
     * @Time: 16:20
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级家族文化后台添加和修改 发表", notes =
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
    @RequestMapping(value = "/addOrUpdateCulture", method = RequestMethod.POST)
    public Response<ProNewsCultureNews> addOrUpdateCulture(@ApiParam(value = "家族文化表") ProNewsCultureNews proNewsCultureNews,
                                                           @ApiParam(value = "上传文件名称") @RequestParam(value = "fileName") String fileName,
                                                           @ApiParam(value = "上传文件地址") @RequestParam(value = "filePath") String filePath,
                                                           @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        /*Set set = allCheckOutService.getSensitiveWord(proNewsCultureNews.getNewsText());

        if (set.size() >= 1) {
            return ResponseUtlis.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set);
        }*/

        //状态(0:删除;1:已发布;2:草稿3:不显示)
        proNewsCultureNews.setStatus(1);
        return getFanNewsCultureNewsResponse(proNewsCultureNews, fileName, filePath, token);
    }

    /**
     * 省级家族文化后台添加和修改 草稿
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 12:18
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级家族文化后台添加和修改 草稿", notes =
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
    @RequestMapping(value = "/addOrUpdateCultureDrft", method = RequestMethod.POST)
    public Response<ProNewsCultureNews> addOrUpdateCultureDrft(@ApiParam(value = "家族文化表") ProNewsCultureNews proNewsCultureNews,
                                                               @ApiParam(value = "上传文件名称") @RequestParam(value = "fileName") String fileName,
                                                               @ApiParam(value = "上传文件地址") @RequestParam(value = "filePath") String filePath,
                                                               @ApiParam("token") @RequestParam(value = "token", required = false) String token) {
        //状态(0:删除;1:已发布;2:草稿3:不显示)
        proNewsCultureNews.setStatus(2);
        return getFanNewsCultureNewsResponse(proNewsCultureNews, fileName, filePath, token);
    }

    /**
     * 省级家族文化后台添加和修改 抽取的方法
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 12:18
     * @Param:
     * @return:
     * @Description:
     */
    private Response<ProNewsCultureNews> getFanNewsCultureNewsResponse(ProNewsCultureNews proNewsCultureNews, String fileName, String filePath, String token) {
        try {
            //判断token是否为空
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //获取用户对象
            AllUserLogin userLoginInfoByToken = userService.getUserLoginInfoByToken(token);
            //判断token是否正确
            if (StringsUtils.isEmpty(userLoginInfoByToken)) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "请输入正确的token");
            }
            // 插入数据
            boolean insert = proNewsCultureNewsService.addOrUpdateCulture(proNewsCultureNews, fileName, filePath);
            if (!insert) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
            //插入图片
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级家族文化后台删除
     *
     * @Author: yuzhou
     * @Date: 2018-11-14
     * @Time: 17:20
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级家族文化后台删除", notes = "根据id删除")
    @RequestMapping(value = "/deleteCulturById", method = RequestMethod.GET)
    public Response<ProNewsCultureNews> deleteCulturById(
            @ApiParam(value = "主键Id") @RequestParam(value = "id") Integer id, // 家族文化详情显示位置
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //判断token是否为空
            if (StringsUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
            }
            //判断token是否正确
            if (StringsUtils.isEmpty(userService.getUserLoginInfoByToken(token))) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "请输入正确的token");
            }
            //判断Id
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;1:已发布;2:草稿3:不显示)
            int status = 0;
            Boolean aBoolean = proNewsCultureNewsService.deleteCulturById(id, status);
            if (!aBoolean) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}

