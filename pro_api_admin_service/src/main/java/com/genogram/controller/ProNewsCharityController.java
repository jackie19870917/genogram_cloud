package com.genogram.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.*;
import com.genogram.entityvo.DonorVo;
import com.genogram.entityvo.IndexFundDrowingVo;
import com.genogram.entityvo.NewsCharityOutVo;
import com.genogram.entityvo.NewsDetailVo;
import com.genogram.service.*;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 省级-家族慈善财 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Api(description = "慈善公益菜单(后台)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/proNewsCharity")
public class ProNewsCharityController {

    @Autowired
    private IProIndexFundService proIndexFundService;

    @Autowired
    private IProNewsCharityOutService proNewsCharityOutService;

    @Autowired
    private IProNewsCharityPayInService proNewsCharityPayInService;

    @Autowired
    private IProIndexFundDrowingService proIndexFundDrowingService;

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
        list.add(2);
        list.add(5);
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
    @RequestMapping(value = "getProIndexFund", method = RequestMethod.POST)
    public Response<ProIndexFund> getProIndexFund(@ApiParam("网站id") @RequestParam Integer siteId,
                                                  @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        if (siteId == null) {
            return ResponseUtils.error(Constants.IS_EMPTY, null);
        }

        ProIndexFund proIndexFund = proIndexFundService.getProIndexFund(siteId);

        if (StringUtils.isEmpty(proIndexFund)) {
            return ResponseUtils.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtils.success(proIndexFund);
    }

    /**
     * 慈善收支
     *
     * @param showId   慈善收支显示位置
     *                 // @param newsType 种类(1.财政支出;2.财政收入)
     * @param pageNo   当前页
     * @param pageSize 每页记录数
     * @return
     */
    @ApiOperation(value = "慈善收支", notes = "id:主键,showId:显示位置,amount:支出金额,useFor:支出用途,newsTitle:标题,newsText:内容,visitNum:查看数,filePath:图片url,fileName:图片名称,picIndex,picIndex:是否封面")
    @RequestMapping(value = "index/getProNewsCharityOut", method = RequestMethod.GET)
    public Response<NewsCharityOutVo> getProNewsCharityOutPage(@ApiParam("显示位置") @RequestParam Integer showId,
                                                               @ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                               //  @RequestParam(value = "newsType", defaultValue = "1") Integer newsType,
                                                               @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        if (showId == null) {
            return ResponseUtils.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        //状态    1-正常   2-草稿
        list.add(1);
        list.add(2);

        Wrapper<ProNewsCharityOut> entity = new EntityWrapper<ProNewsCharityOut>();
        entity.eq("show_id", showId);
        entity.in("status", list);
        entity.orderBy("create_time", false);

        Page<NewsCharityOutVo> newsCharityOutVoPage = proNewsCharityOutService.getNewsCharityOutVoPage(entity, pageNo, pageSize);

        if (StringUtils.isEmpty(newsCharityOutVoPage)) {
            return ResponseUtils.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtils.success(newsCharityOutVoPage);
    }

    @ApiOperation(value = "捐款名录查询", notes = "id:主键,showId:显示位置,payUsrId:捐款人,userName:用户名,realName:真实名,nickName:昵称,payAmount:捐款金额")
    @RequestMapping(value = "index/getPayUser", method = RequestMethod.GET)
    public Response<DonorVo> getPayUser(@ApiParam(value = "显示位置") @RequestParam Integer showId,
                                        @ApiParam(value = "捐款人") @RequestParam(value = "nickName", required = false) String nickName,
                                        @ApiParam(value = "排序(time-时间,money-金额,null-缺省)") @RequestParam(value = "order", required = false) String order,
                                        @ApiParam(value = "升序-asc,降序-desc") @RequestParam(value = "label", required = false) String label,
                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                        @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {

        if (showId == null) {
            return ResponseUtils.error(Constants.IS_EMPTY, null);
        }

        List list = new ArrayList();
        list.add(1);

        Page<DonorVo> donorVoPage;
        String money = "money";
        if (money.equals(order)) {
            Map map = new HashMap(16);
            map.put("showId", showId);
            map.put("status", list);

            if (!StringUtils.isEmpty(nickName)) {
                map.put("nick_name", nickName);
            }

            map.put("label", label);

            Page page = new Page();
            page.setCurrent(pageNo);
            page.setSize(pageSize);
            Page<ProNewsCharityPayIn> mapPage = new Page<>(page.getCurrent(), page.getSize());

            donorVoPage = proNewsCharityPayInService.getDonorVoPage(mapPage, map);
        } else {
            donorVoPage = proNewsCharityPayInService.getDonorVoPageByTime(showId, list, nickName, pageNo, pageSize, order, label);
        }

        if (StringUtils.isEmpty(donorVoPage)) {
            return ResponseUtils.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtils.success(donorVoPage);

    }

    /**
     * 慈善收支(文章)详情
     *
     * @param id 慈善收支详情显示位置
     * @return
     */
    @ApiOperation(value = "慈善收支(文章)详情", notes = "id:主键,showId:显示位置,amount:支出金额,useFor:支出用途,newsTitle:标题,newsText:内容,visitNum:查看数,filePath:图片url,fileName:图片名称,picIndex,picIndex:是否封面")
    @RequestMapping(value = "getNewsDetail", method = RequestMethod.POST)
    public Response<NewsDetailVo> getNewsDetail(@ApiParam("主键") @RequestParam Integer id,
                                                @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        NewsDetailVo newsCharityOutDetail = proNewsCharityOutService.getNewsCharityOutDetail(id);

        return ResponseUtils.success(newsCharityOutDetail);
    }

    /**
     * 新增/修改    慈善收支
     *
     * @param proNewsCharityOut
     * @param fileName
     * @param filePath
     * @return
     */
    @ApiOperation(value = "新增/修改  慈善收支(文章)", notes = "id:主键,showId:显示位置,amount:支出金额,useFor:支出用途,newsTitle:标题,newsText:内容,visitNum:查看数")
    @RequestMapping(value = "insertOrUpdateProNewsCharityOut", method = RequestMethod.POST)
    public Response<NewsCharityOutVo> insertOrUpdateProNewsCharityOut(ProNewsCharityOut proNewsCharityOut,
                                                                      @ApiParam("图片名称") @RequestParam(value = "fileName", required = false) String fileName,
                                                                      @ApiParam("图片url") @RequestParam(value = "filePath", required = false) String filePath,
                                                                      @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        if (proNewsCharityOut.getId() == null) {
            proNewsCharityOut.setCreateUser(userLogin.getId());
        }

        proNewsCharityOut.setUpdateUser(userLogin.getId());

        Set set = allCheckOutService.getSensitiveWord(proNewsCharityOut.getNewsText());

        if (set != null && set.size() >= 1) {
            return ResponseUtils.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set);
        }

        //状态   (1:已发布;2:草稿)
        proNewsCharityOut.setStatus(1);
        Boolean result = proNewsCharityOutService.insertOrUpdateProNewsCharityOutVo(proNewsCharityOut, fileName, filePath);

        if (result) {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 慈善收支草稿
     *
     * @param proNewsCharityOut
     * @param fileName
     * @param filePath
     * @return
     */
    @ApiOperation(value = "慈善收支(文章)草稿", notes = "id:主键,showId:显示位置,amount:支出金额,useFor:支出用途,newsTitle:标题,newsText:内容,visitNum:查看数")
    @RequestMapping(value = "insertOrUpdateProNewsCharityOutDrft", method = RequestMethod.POST)
    public Response<NewsCharityOutVo> insertOrUpdateProNewsCharityOutDrft(ProNewsCharityOut proNewsCharityOut,
                                                                          @ApiParam("图片名称") @RequestParam(value = "fileName", required = false) String fileName,
                                                                          @ApiParam("图片url") @RequestParam(value = "filePath", required = false) String filePath,
                                                                          @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        if (proNewsCharityOut.getId() == null) {
            proNewsCharityOut.setCreateUser(userLogin.getId());
        }

        proNewsCharityOut.setUpdateUser(userLogin.getId());

        //状态   (1:已发布;2:草稿)
        proNewsCharityOut.setStatus(2);
        Boolean result = proNewsCharityOutService.insertOrUpdateProNewsCharityOutVo(proNewsCharityOut, fileName, filePath);

        if (result) {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @ApiOperation("删除慈善收支(文章)")
    @RequestMapping(value = "deleteProNewsCharityOut", method = RequestMethod.POST)
    public Response<ProNewsCharityOut> deleteProNewsCharityOut(@ApiParam("主键") @RequestParam Integer id,
                                                               @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        Boolean result = proNewsCharityOutService.deleteProNewsCharityOut(id, userLogin.getId());

        if (result) {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 新增线上提现
     *
     * @param proIndexFundDrowing
     * @return
     */
    @ApiOperation(value = "新增线上提现", notes = "id:主键,siteId:网站Id,drowAmount:提现金额,drowBank;提现银行,drowBankSub:支行名称,drowTime:提现时间,drowInAccountName:账户名,drowInAccountCard:账户")
    @RequestMapping(value = "insertProIndexFundDrowing", method = RequestMethod.POST)
    public Response<ProIndexFundDrowing> insertProIndexFundDrowing(ProIndexFundDrowing proIndexFundDrowing,
                                                                   @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        //  2-省级管理员
        Integer role02 = 2;
        if (!allUserLogin.getRole().equals(role02)) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        ProIndexFund proIndexFund = proIndexFundService.getProIndexFund(proIndexFundDrowing.getSiteId());

        if (proIndexFundDrowing.getDrowAmount().compareTo(new BigDecimal(0)) != -1) {
            return ResponseUtils.error(Constants.ERRO_CODE, "金额不能小于0");
        }

        if (proIndexFund.getRemain().subtract(proIndexFund.getUnuseAmount()).compareTo(proIndexFundDrowing.getDrowAmount()) == -1) {
            return ResponseUtils.error(Constants.ERRO_CODE, "金额不足");
        }

        proIndexFundDrowing.setCreateUser(userLogin.getId());

        Boolean result = proIndexFundDrowingService.insertProIndexFundDrowing(proIndexFundDrowing);

        proIndexFund.setUnuseAmount(proIndexFundDrowing.getDrowAmount());

        proIndexFundService.updateProIndexFund(proIndexFund);

        if (result) {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
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
    public Response<IndexFundDrowingVo> getProIndexFundDrowing(@ApiParam("网站Id") @RequestParam Integer siteId,
                                                               @ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                               @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        if (siteId == null) {
            return ResponseUtils.error(Constants.IS_EMPTY, null);
        }

        Page<IndexFundDrowingVo> indexFundDrowingVoPage = proIndexFundDrowingService.getIndexFundDrowingVoPage(siteId, pageNo, pageSize);

        if (StringUtils.isEmpty(indexFundDrowingVoPage)) {
            return ResponseUtils.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtils.success(indexFundDrowingVoPage);
    }

    /**
     * 新增线下捐款
     *
     * @param proNewsCharityPayIn
     * @return
     */
    @ApiOperation(value = "新增线下捐款", notes = "id:主键,showId:显示位置,payUsrId:捐款人,payAmount:捐款金额")
    @RequestMapping(value = "insertProNewsCharityPayIn", method = RequestMethod.POST)
    public Response<ProNewsCharityPayIn> insertProNewsCharityPayIn(ProNewsCharityPayIn proNewsCharityPayIn,
                                                                   @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        proNewsCharityPayIn.setType(2);
        proNewsCharityPayIn.setPayUsrId(userLogin.getId());

        Boolean result = proNewsCharityPayInService.insertProNewsCharityPayIn(proNewsCharityPayIn);

        if (result) {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

}

