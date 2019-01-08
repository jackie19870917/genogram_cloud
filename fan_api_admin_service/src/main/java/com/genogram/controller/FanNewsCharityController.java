package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.*;
import com.genogram.entityvo.IndexFundDrowingVo;
import com.genogram.entityvo.NewsCharityOutVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.*;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 联谊会-家族慈善财 后台控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Api(description = "慈善公益菜单(后台)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/fanNewsCharity/")
public class FanNewsCharityController {

    @Autowired
    private IFanNewsCharityOutService fanNewsCharityOutService;

    @Autowired
    private IFanIndexFundDrowingService fanIndexFundDrowingService;

    @Autowired
    private IFanNewsCharityPayInService fanNewsCharityPayInService;

    @Autowired
    private IFanIndexFundService fanIndexFundService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllCheckOutService allCheckOutService;


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
     * 慈善基金
     *
     * @param siteId 慈善基金ID
     * @return
     */
    @ApiOperation(value = "基金查询", notes = "id:主键,siteId:网站Id,remain:基金总额,payNum:捐款人数,payOnline:线上捐款,payUnderline:线下捐款,payGenogram:网络修普金额")
    @RequestMapping(value = "getFanIndexFund", method = RequestMethod.POST)
    public Response<FanIndexFund> getFanIndexFund(@ApiParam("网站id") @RequestParam Integer siteId,
                                                  @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

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

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        FanIndexFund fanIndexFund = fanIndexFundService.getFanIndexFund(siteId);

        if (StringUtils.isEmpty(fanIndexFund)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(fanIndexFund);
    }

    /**
     * 慈善收支
     *
     * @param showId   慈善收支显示位置
     *                 // @param newsType 种类
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */
    @ApiOperation(value = "慈善收支", notes = "id:主键,showId:显示位置,amount:支出金额,useFor:支出用途,newsTitle:标题,newsText:内容,visitNum:查看数,filePath:图片url,fileName:图片名称,picIndex,picIndex:是否封面")
    @RequestMapping(value = "getFanNewsCharityOutPage", method = RequestMethod.GET)
    public Response<NewsCharityOutVo> getFanNewsCharityOutVo(@ApiParam("显示位置") @RequestParam Integer showId,
                                                             @ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                             @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

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

        if (showId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        //状态    1-正常   2-草稿
        list.add(1);
        list.add(2);

        Wrapper<FanNewsCharityOut> entity = new EntityWrapper<FanNewsCharityOut>();
        entity.eq("show_id", showId);
        entity.in("status", list);
        entity.orderBy("create_time", false);

        Page<NewsCharityOutVo> fanNewsCharityOutPage = fanNewsCharityOutService.getFanNewsCharityOutVoPage(entity, pageNo, pageSize);

        if (StringUtils.isEmpty(fanNewsCharityOutPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(fanNewsCharityOutPage);
    }

    /**
     * 慈善收支详情
     *
     * @param id 慈善收支详情显示位置
     * @return
     */
    @ApiOperation(value = "慈善收支(文章)详情", notes = "id:主键,showId:显示位置,amount:支出金额,useFor:支出用途,newsTitle:标题,newsText:内容,visitNum:查看数,filePath:图片url,fileName:图片名称,picIndex,picIndex:是否封面")
    @RequestMapping(value = "getFanNewsCharityDetail", method = RequestMethod.POST)
    public Response<NewsDetailVo> getFanNewsCharityDetail(@ApiParam("主键") @RequestParam Integer id,
                                                          @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

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

        NewsDetailVo newsCharityOutDetail = fanNewsCharityOutService.getNewsCharityOutDetail(id);

        return ResponseUtlis.success(newsCharityOutDetail);
    }

    /**
     * 新增/修改    慈善收支
     *
     * @param fanNewsCharityOut
     * @param fileName
     * @param filePath
     * @return
     */
    @ApiOperation(value = "新增/修改  慈善收支(文章)", notes = "id:主键,showId:显示位置,amount:支出金额,useFor:支出用途,newsTitle:标题,newsText:内容,visitNum:查看数")
    @RequestMapping(value = "insertOrUpdateFanNewsCharityOut", method = RequestMethod.POST)
    public Response<NewsCharityOutVo> insertOrUpdateFanNewsCharityOut(FanNewsCharityOut fanNewsCharityOut,
                                                                      @ApiParam("图片名称") @RequestParam(value = "fileName", required = false) String fileName,
                                                                      @ApiParam("图片url") @RequestParam(value = "filePath", required = false) String filePath,
                                                                      @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

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

        if (fanNewsCharityOut.getId() == null) {
            fanNewsCharityOut.setCreateUser(userLogin.getId());
        }

        Set set = allCheckOutService.getSensitiveWord(fanNewsCharityOut.getNewsText());

        if (set != null && set.size() >= 1) {
            return ResponseUtlis.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set);
        }

        fanNewsCharityOut.setUpdateUser(userLogin.getId());

        //状态   (1:已发布;2:草稿)
        fanNewsCharityOut.setStatus(1);
        Boolean result = fanNewsCharityOutService.insertOrUpdateFanNewsCharityOutVo(fanNewsCharityOut, fileName, filePath);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 慈善收支草稿
     *
     * @param fanNewsCharityOut
     * @param fileName
     * @param filePath
     * @return
     */
    @ApiOperation(value = "慈善收支(文章)草稿", notes = "id:主键,showId:显示位置,amount:支出金额,useFor:支出用途,newsTitle:标题,newsText:内容,visitNum:查看数")
    @RequestMapping(value = "insertOrUpdateFanNewsCharityOutDrft", method = RequestMethod.POST)
    public Response<NewsCharityOutVo> insertOrUpdateFanNewsCharityOutDrft(FanNewsCharityOut fanNewsCharityOut,
                                                                          @ApiParam("图片名称") @RequestParam(value = "fileName", required = false) String fileName,
                                                                          @ApiParam("图片url") @RequestParam(value = "filePath", required = false) String filePath,
                                                                          @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

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

        if (fanNewsCharityOut.getId() == null) {
            fanNewsCharityOut.setCreateUser(userLogin.getId());
        }

        fanNewsCharityOut.setUpdateUser(userLogin.getId());

        //状态   (1:已发布;2:草稿)
        fanNewsCharityOut.setStatus(2);
        Boolean result = fanNewsCharityOutService.insertOrUpdateFanNewsCharityOutVo(fanNewsCharityOut, fileName, filePath);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @ApiOperation("删除慈善收支(文章)")
    @RequestMapping(value = "deleteFanNewsCharityOut", method = RequestMethod.POST)
    public Response<FanNewsCharityOut> deleteFanNewsCharityOut(@ApiParam("主键") @RequestParam Integer id,
                                                               @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

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

        Boolean result = fanNewsCharityOutService.deleteFanNewsCharityOut(id, userLogin.getId());

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 新增线上提现
     *
     * @param fanIndexFundDrowing
     * @return
     */
    @ApiOperation(value = "新增线上提现", notes = "id:主键,siteId:网站Id,drowAmount:提现金额,drowBank;提现银行,drowBankSub:支行名称,drowTime:提现时间,drowInAccountName:账户名,drowInAccountCard:账户")
    @RequestMapping(value = "insertFanIndexFundDrowing", method = RequestMethod.POST)
    public Response<FanIndexFundDrowing> insertFanIndexFundDrowing(FanIndexFundDrowing fanIndexFundDrowing,
                                                                   @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

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
        if (allUserLogin.getRole() != 1) {
            return ResponseUtlis.error(Constants.ERRO_CODE, "您没有权限");
        }

        FanIndexFund fanIndexFund = fanIndexFundService.getFanIndexFund(fanIndexFundDrowing.getSiteId());

        if (fanIndexFundDrowing.getDrowAmount().compareTo(new BigDecimal(0)) != -1) {
            return ResponseUtlis.error(Constants.ERRO_CODE, "金额不能小于0");
        }

        if (fanIndexFund.getRemain().subtract(fanIndexFund.getUnuseAmount()).compareTo(fanIndexFundDrowing.getDrowAmount()) == -1) {
            return ResponseUtlis.error(Constants.ERRO_CODE, "金额不足");
        }

        fanIndexFundDrowing.setCreateUser(userLogin.getId());

        Boolean result = fanIndexFundDrowingService.insertFanIndexFundDrowing(fanIndexFundDrowing);

        fanIndexFund.setUpdateUser(userLogin.getId());
        fanIndexFund.setUnuseAmount(fanIndexFundDrowing.getDrowAmount());

        fanIndexFundService.updateFanIndexFund(fanIndexFund);
        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 线上提现记录
     *
     * @param siteId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "线上提现记录", notes = "id:主键,siteId:网站Id,drowAmount:提现金额,drowBank;提现银行,drowBankSub:支行名称,drowTime:提现时间,drowInAccountName:账户名,drowInAccountCard:账户")
    @RequestMapping(value = "getFanIndexFundDrowing", method = RequestMethod.POST)
    public Response<IndexFundDrowingVo> getFanIndexFundDrowing(@ApiParam("网站Id") @RequestParam Integer siteId,
                                                               @ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                               @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

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

        if (siteId == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }

        Page<IndexFundDrowingVo> indexFundDrowingVoPage = fanIndexFundDrowingService.getIndexFundDrowingVoPage(siteId, pageNo, pageSize);

        if (StringUtils.isEmpty(indexFundDrowingVoPage)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(indexFundDrowingVoPage);
    }

    /**
     * 新增线下捐款
     *
     * @param fanNewsCharityPayIn
     * @return
     */
    @ApiOperation(value = "新增线下捐款", notes = "id:主键,showId:显示位置,payUsrId:捐款人,payAmount:捐款金额")
    @RequestMapping(value = "insertFanNewsCharityPayIn", method = RequestMethod.POST)
    public Response<FanNewsCharityPayIn> insertFanNewsCharityPayIn(FanNewsCharityPayIn fanNewsCharityPayIn,
                                                                   @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

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

        fanNewsCharityPayIn.setType(2);
        fanNewsCharityPayIn.setPayUsrId(userLogin.getId());

        Boolean result = fanNewsCharityPayInService.insertFanNewsCharityPayIn(fanNewsCharityPayIn);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}
