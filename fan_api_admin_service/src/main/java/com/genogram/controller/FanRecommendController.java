package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanSysRecommend;
import com.genogram.entityvo.CommonRecommendVo;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IFanSysRecommendService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 联谊会推荐
 *
 * @Author: Toxicant
 * @Date: 2018-11-09
 * @Time: 14:23
 * @Param:
 * @return:
 * @Description:
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/fanRecommend")
@Api(description = "联谊会推荐")
public class FanRecommendController {

    @Autowired
    private IFanSysRecommendService fanSysRecommendService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    private List getList() {

        List list = new ArrayList();
        /**
         * 角色权限 (0.不是管理员,1.县级管理员,2省级管理员,3.全国管理员,4县级副管理员,5省级副管理员,6全国副管理员,9.超级管理员)
         */
        list.add(1);
        list.add(4);
        list.add(9);

        return list;
    }

    /**
     * 联谊会后台点击推荐
     *
     * @Author: yuzhou
     * @Date: 2018-11-13
     * @Time: 9:16
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会后台点击推荐", notes = "")
    @RequestMapping(value = "/addRecommendButton", method = RequestMethod.GET)
    public Response<FanSysRecommend> addRecommendButton(
            @ApiParam(value = "显示位置Id") @RequestParam(value = "showId") Integer showId,
            @ApiParam(value = "主键Id") @RequestParam(value = "id") Integer id, //主键
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //  判断是否登陆
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
            }

            AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

            if (StringUtils.isEmpty(userLogin)) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
            }

            AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

            //  判断是否有权限访问
            if (!this.getList().contains(allUserLogin.getRole())) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
            }
            if (showId == null || id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status = 1;
            //来源:(1县级,2省级)
            int newsSource = 1;
            //是否自动推荐(0:否;1:是)
            int isAuto = 0;
            //要插入的实体类
            FanSysRecommend fanSysRecommend = new FanSysRecommend();
            fanSysRecommend.setNewsId(id);
            fanSysRecommend.setShowId(showId);
            fanSysRecommend.setStatus(status);
            fanSysRecommend.setIsAuto(isAuto);
            fanSysRecommend.setNewsSource(newsSource);
            Boolean aBoolean = fanSysRecommendService.addRecommend(fanSysRecommend);
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
     * 联谊会后台点击取消
     *
     * @Author: yuzhou
     * @Date: 2018-11-13
     * @Time: 10:04
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会后台点击取消", notes = "")
    @RequestMapping(value = "/deleteRecommendButton", method = RequestMethod.GET)
    public Response<FanSysRecommend> deleteRecommendButton(
            @ApiParam(value = "显示位置Id") @RequestParam(value = "showId") Integer showId, // 家族文化显示位置
            @ApiParam(value = "主键Id") @RequestParam(value = "id") Integer id, //文章主键
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //  判断是否登陆
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
            }

            AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

            if (StringUtils.isEmpty(userLogin)) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
            }

            AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

            //  判断是否有权限访问
            if (!this.getList().contains(allUserLogin.getRole())) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
            }
            if (showId == null || id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status = 1;
            //来源:(1县级,2省级)
            int newsSource = 1;
            //是否自动推荐(0:否;1:是)
            int isAuto = 0;
            Wrapper<FanSysRecommend> entity = new EntityWrapper();
            entity.eq("show_id", showId);
            entity.eq("news_id", id);
            entity.eq("news_source", newsSource);
            entity.eq("is_auto", isAuto);
            entity.eq("status", status);
            Boolean aBoolean = fanSysRecommendService.recommendDelete(entity);
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
     * 联谊会后台设置个人推荐取消展示
     *
     * @Author: yuzhou
     * @Date: 2018-11-13
     * @Time: 10:39
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会后台设置个人推荐取消展示", notes = "")
    @RequestMapping(value = "/deleteRecommend", method = RequestMethod.GET)
    public Response<FanSysRecommend> deleteRecommend(
            @ApiParam(value = "推荐表主键") @RequestParam(value = "recommendId") Integer recommendId,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //  判断是否登陆
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
            }

            AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

            if (StringUtils.isEmpty(userLogin)) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
            }

            AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

            //  判断是否有权限访问
            if (!this.getList().contains(allUserLogin.getRole())) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status = 3;
            Wrapper<FanSysRecommend> entity = new EntityWrapper();
            entity.eq("id", recommendId);
            Boolean aBoolean = fanSysRecommendService.deleteRecommend(recommendId);
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
     * 联谊会后台设置手动推荐查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-13
     * @Time: 11:16
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "县级后台设置手动推荐查询", notes =
            " 推荐表主键 recommendId --" +
                    " 联谊会名称 sizeName --" +
                    " 分类1代表家族文化 2代表记录家族 3代表家族产业 4代表家族名人 source --" +
                    " 主键 id" +
                    " 显示位置id(fan_sys_web_news_show_id) showId --" +
                    " 标题 newsTitle --" +
                    " 内容 newsText --" +
                    " 查看数 visitNum --" +
                    " 状态(0:删除;1:已发布;2:草稿3:不显示) status --" +
                    " 创建时间 createTime --" +
                    " 创建人 createUser --" +
                    " 修改时间 updateTime --" +
                    " 修改人 updateUser --" +
                    " 家族产业具体地址 industryLocation --" +
                    " 人名 personName --" +
                    " 人物简介 personSummary --" +
                    " 头像图片位置 picFileSrc --" +
                    " 头像名 picFileName")
    @RequestMapping(value = "/getManualRecommend", method = RequestMethod.GET)
    public Response<CommonRecommendVo> getManualRecommend(
            @ApiParam(value = "网站Id") @RequestParam(value = "siteId") Integer siteId,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //  判断是否登陆
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
            }

            AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

            if (StringUtils.isEmpty(userLogin)) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
            }

            AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

            //  判断是否有权限访问
            if (!this.getList().contains(allUserLogin.getRole())) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
            }
            //判断siteId是否为空
            if (siteId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status = 1;
            //来源:(1县级,2省级)
            int newsSource = 1;
            //是否自动推荐(0:否;1:是)
            int isAuto = 0;
            Map map = new HashMap(16);
            map.put("fanSiteId", siteId);
            map.put("status", status);
            map.put("newsSource", newsSource);
            map.put("isAuto", isAuto);
            List<CommonRecommendVo> commonRecommendVo = fanSysRecommendService.getManualRecommend(map);
            if (commonRecommendVo == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.success(commonRecommendVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 县级后台设置手动推荐模糊查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-26
     * @Time: 18:15
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "县级后台设置手动推荐模糊查询", notes =
            " 推荐表主键 recommendId --" +
                    " 联谊会名称 sizeName --" +
                    " 分类1代表家族文化 2代表记录家族 3代表家族产业 4代表家族名人 source --" +
                    " 主键 id" +
                    " 显示位置id(fan_sys_web_news_show_id) showId --" +
                    " 标题 newsTitle --" +
                    " 内容 newsText --" +
                    " 查看数 visitNum --" +
                    " 状态(0:删除;1:已发布;2:草稿3:不显示) status --" +
                    " 创建时间 createTime --" +
                    " 创建人 createUser --" +
                    " 修改时间 updateTime --" +
                    " 修改人 updateUser --" +
                    " 家族产业具体地址 industryLocation --" +
                    " 人名 personName --" +
                    " 人物简介 personSummary --" +
                    " 头像图片位置 picFileSrc --" +
                    " 头像名 picFileName")
    @RequestMapping(value = "/getManualRecommendVague", method = RequestMethod.GET)
    public Response<CommonRecommendVo> getManualRecommendVague(
            @ApiParam(value = "网站Id") @RequestParam(value = "siteId") Integer siteId,
            @ApiParam(value = "标题模糊") @RequestParam(value = "title", required = false) String title,
            @ApiParam("token") @RequestParam(value = "token", required = false) String token
    ) {
        try {
            //  判断是否登陆
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
            }

            AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

            if (StringUtils.isEmpty(userLogin)) {
                return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
            }

            AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

            //  判断是否有权限访问
            if (!this.getList().contains(allUserLogin.getRole())) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
            }
            //判断siteId是否为空
            if (siteId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
            int status = 1;
            //来源:(1县级,2省级)
            int newsSource = 1;
            //是否自动推荐(0:否;1:是)
            int isAuto = 0;
            Map map = new HashMap(16);
            map.put("fanSiteId", siteId);
            map.put("status", status);
            map.put("newsSource", newsSource);
            map.put("isAuto", isAuto);
            if (StringsUtils.isNotEmpty(title)) {
                map.put("newsTitle", title);
            }
            List<CommonRecommendVo> commonRecommendVo = fanSysRecommendService.getManualRecommend(map);
            if (commonRecommendVo == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.success(commonRecommendVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}
