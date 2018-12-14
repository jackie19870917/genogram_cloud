package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ProNewsCharityPayIn;
import com.genogram.entity.ProNewsFamousAncestor;
import com.genogram.entityvo.AncestorsBranchVo;
import com.genogram.service.IAllCheckOutService;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IProNewsFamousAncestorService;
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

import java.util.*;

/**
 * 省级祖先分支
 *
 * @Author: yuzhou
 * @Date: 2018-11-20
 * @Time: 11:40
 * @Param:
 * @return:
 * @Description:
 */
@Api(description = "省级后台祖先分支")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/proNewsAncestor")
public class ProNewsAncestorController {

    @Autowired
    private IProNewsFamousAncestorService proNewsFamousAncestorService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllCheckOutService allCheckOutService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    /**
     * 角色权限 (0.不是管理员,1.县级管理员,2省级管理员,3.全国管理员,4县级副管理员,5省级副管理员,6全国副管理员,9.超级管理员)
     */
    Integer role02 = 2;
    Integer role05 = 5;
    Integer role09 = 9;

    /**
     * 省级祖先后台查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-20
     * @Time: 11:52
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级祖先查询", notes =
            "ancestorName 祖先名 --" +
                    "ancestorSummary 人物简介 --" +
                    "ancestorTitle 祖先头衔 --" +
                    "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键 --" +
                    "junwang 郡望 --" +
                    "parentId 父id --" +
                    "picFileName 头像名 --" +
                    "picFileSrc 头像图片位置 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:发布;3:不显示) --" +
                    "tanghao 堂号 --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "zipai 字派")
    @RequestMapping(value = "/getFamousAncestorPage", method = RequestMethod.GET)
    public Response<ProNewsFamousAncestor> getFamousAncestorPage(
            @ApiParam(value = "显示位置Id") @RequestParam(value = "showId") Integer showId, // 显示位置
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
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
            if (!allUserLogin.getRole().equals(role02) || !allUserLogin.getRole().equals(role05) || !allUserLogin.getRole().equals(role09)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
            }

            //判断显示位置Id是否为空
            if (showId == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            //parent_id 为0代表主数据
            Integer parentId = 0;
            //查询条件
            Wrapper<ProNewsFamousAncestor> entity = new EntityWrapper<>();
            entity.eq("show_id", showId);
            entity.eq("parent_id", parentId);
            entity.orderBy("update_time", false);
            Page<ProNewsFamousAncestor> proFamilyRecordPage = proNewsFamousAncestorService.getFamousAncestorPage(entity, pageNo, pageSize);
            if (proFamilyRecordPage == null) {
                //没有取到参数,返回空参
                Page<ProNewsFamousAncestor> proNewsFamousAncestor = new Page<ProNewsFamousAncestor>();
                return ResponseUtlis.error(Constants.ERRO_CODE, "proFamilyRecordPage为空");
            }
            return ResponseUtlis.success(proFamilyRecordPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级祖先人物详情查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-20
     * @Time: 14:22
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级祖先人物详情查询", notes =
            "ancestorName 祖先名 --" +
                    "ancestorSummary 人物简介 --" +
                    "ancestorTitle 祖先头衔 --" +
                    "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键 --" +
                    "junwang 郡望 --" +
                    "parentId 父id --" +
                    "picFileName 头像名 --" +
                    "picFileSrc 头像图片位置 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:发布;3:不显示) --" +
                    "tanghao 堂号 --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "zipai 字派 --" +
                    "proNewsFamousAncestorList 联谊会分支后裔集合")
    @RequestMapping(value = "/getFamousAncestorDetails", method = RequestMethod.GET)
    public Response<ProNewsFamousAncestor> getFamousAncestorDetails(
            @ApiParam(value = "主键Id") @RequestParam(value = "id") Integer id,// 显示位置
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
            if (!allUserLogin.getRole().equals(role02) || !allUserLogin.getRole().equals(role05) || !allUserLogin.getRole().equals(role09)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
            }

            //判断Id
            if (id == null) {
                return ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            AncestorsBranchVo ancestorsBranchVo = proNewsFamousAncestorService.getFamousAncestorDetails(id);
            if (ancestorsBranchVo == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.success(ancestorsBranchVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级祖先后台添加模糊查询
     *
     * @Author: yuzhou
     * @Date: 2018-11-20
     * @Time: 15:17
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级祖先后台添加模糊查询", notes =
            "source 1:县级 2省级 --" +
                    "ancestorName 祖先名 --" +
                    "ancestorSummary 人物简介 --" +
                    "ancestorTitle 祖先头衔 --" +
                    "createTime 创建时间 --" +
                    "createUser 创建人 --" +
                    "id 主键 --" +
                    "junwang 郡望 --" +
                    "parentId 父id --" +
                    "picFileName 头像名 --" +
                    "picFileSrc 头像图片位置 --" +
                    "showId 显示位置Id --" +
                    "status 状态(0:删除;1:发布;3:不显示) --" +
                    "tanghao 堂号 --" +
                    "updateTime 修改时间 --" +
                    "updateUser 修改人 --" +
                    "zipai 字派")
    @RequestMapping(value = "/getFamousAncestorVaguePage", method = RequestMethod.GET)
    public Response<ProNewsFamousAncestor> getFamousAncestorVaguePage(
            @ApiParam(value = "祖先名") @RequestParam(value = "ancestorName", required = false) String ancestorName,// 显示位置
            @ApiParam(value = "当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "每页显示的条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
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
            if (!allUserLogin.getRole().equals(role02) || !allUserLogin.getRole().equals(role05) || !allUserLogin.getRole().equals(role09)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
            }

            //分页
            Page<AncestorsBranchVo> mapPage = new Page<>(pageNo, pageSize);
            Map map = new HashMap(16);
            if (StringsUtils.isEmpty(ancestorName)) {
                map.put("ancestorName", ancestorName);
            }
            Page<AncestorsBranchVo> ancestorsBranchVo = proNewsFamousAncestorService.getFamousAncestorVaguePage(mapPage, map);
            if (ancestorsBranchVo == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE, null);
            }
            return ResponseUtlis.success(ancestorsBranchVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级祖先分支添加 修改
     *
     * @Author: yuzhou
     * @Date: 2018-11-20
     * @Time: 17:32
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级祖先后台添加 修改", notes = "ancestorName 祖先名 --" +
            "ancestorSummary 人物简介 --" +
            "ancestorTitle 祖先头衔 --" +
            "createTime 创建时间 --" +
            "createUser 创建人 --" +
            "id 主键 --" +
            "junwang 郡望 --" +
            "parentId 父id --" +
            "picFileName 头像名 --" +
            "picFileSrc 头像图片位置 --" +
            "showId 显示位置Id --" +
            "status 状态(0:删除;1:发布;3:不显示) --" +
            "tanghao 堂号 --" +
            "updateTime 修改时间 --" +
            "updateUser 修改人 --" +
            "zipai 字派")
    @RequestMapping(value = "/addFamousAncestor", method = RequestMethod.POST)
    public Response<ProNewsFamousAncestor> addFamousAncestor(
            @ApiParam(value = "省级主键Id") @RequestParam(value = "proIds") String proIds,// 显示位置
            @ApiParam(value = "县级主键Id") @RequestParam(value = "fanIds") String fanIds,// 显示位置
            @ApiParam(value = "祖先分支表") ProNewsFamousAncestor proNewsFamousAncestor,
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
            if (!allUserLogin.getRole().equals(role02) || !allUserLogin.getRole().equals(role05) || !allUserLogin.getRole().equals(role09)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
            }

            //省级主键Id
            List<String> proSplit = null;
            //县级主键Id
            List<String> fanSplit = null;
            if (StringsUtils.isNotEmpty(proIds)) {
                proSplit = Arrays.asList(proIds.split(","));

            }
            if (StringsUtils.isNotEmpty(fanIds)) {
                fanSplit = Arrays.asList(fanIds.split(","));
            }

            Set set = allCheckOutService.getSensitiveWord(proNewsFamousAncestor.getAncestorSummary());

            if (set != null && set.size() >= 1) {
                return ResponseUtlis.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set);
            }

            Boolean aBoolean = proNewsFamousAncestorService.addFamousAncestor(proNewsFamousAncestor, proSplit, fanSplit);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 省级祖先后台删除
     *
     * @Author: yuzhou
     * @Date: 2018-11-20
     * @Time: 19:21
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "省级祖先后台删除", notes = "")
    @RequestMapping(value = "/deleteFamousAncestor", method = RequestMethod.GET)
    public Response<ProNewsFamousAncestor> deleteFamousAncestor(
            @ApiParam(value = "主键Id") @RequestParam(value = "id") Integer id,
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
            if (!allUserLogin.getRole().equals(role02) || !allUserLogin.getRole().equals(role05) || !allUserLogin.getRole().equals(role09)) {
                return ResponseUtlis.error(Constants.UNAUTHORIZED, "您没有权限访问");
            }

            //判断主键是否为空
            if (id == null) {
                ResponseUtlis.error(Constants.IS_EMPTY, null);
            }
            Boolean aBoolean = proNewsFamousAncestorService.deleteFamousAncestor(id);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}

