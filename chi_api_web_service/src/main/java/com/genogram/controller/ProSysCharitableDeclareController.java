package com.genogram.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanSysCharitableDeclare;
import com.genogram.service.IProFanSysCharitableDeclareService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiaohei
 * @since 2018-11-29
 */
@Api(description = "省级前台家族慈善申报")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/fanSysCharitableDeclare")
public class ProSysCharitableDeclareController {
    @Autowired
    private IProFanSysCharitableDeclareService fanSysCharitableDeclareService;

    @Autowired
    private IUserService userService;

    /**
     * 联谊会慈善帮扶申报详情页查询
     *
     * @Author: yuzhou
     * @Date: 2018-12-12
     * @Time: 15:32
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会慈善帮扶申报查询", notes =
            "abstractInfo 帮扶对象情况简介-- " +
                    "accountBank 申请人开户行-- " +
                    "accountName 申请人开户名-- " +
                    "accountNum 申请人开户行卡号-- " +
                    "applicantAdress 申请人地址-- " +
                    "applyMoney 申请金额-- " +
                    "applyName 申请人姓名-- " +
                    "applyNum 申请人身份证号码-- " +
                    "applyPhone 申请人电话-- " +
                    "approver 审批人-- " +
                    "approverOpinion 审批人意见-- " +
                    "createTime 创建时间-- " +
                    "createUser 创建人Id-- " +
                    "examinant 审查人-- " +
                    "examinantOpinion 审查人意见-- " +
                    "helpFeedback 帮助反馈-- " +
                    "helpFeedbackFile 上传帮助反馈的图片-- " +
                    "id 主键Id" +
                    "isApprover 审批人是否同意  1:同意 2:退回-- " +
                    "isExaminant 审查人是否同意  1:同意 2:退回-- " +
                    "isVerifier 审核人是否同意  1:同意 2:退回-- " +
                    "pictureAddress 联谊会添加的图片地址-- " +
                    "receivablesAddress 收款人地址-- " +
                    "receivablesBank 收款人开户行-- " +
                    "receivablesId 收款人身份证-- " +
                    "receivablesName 收款人姓名-- " +
                    "receivablesNum 收款人卡号-- " +
                    "receivablesPhone 收款人电话-- " +
                    "recommenderCompany 推荐人公司-- " +
                    "recommenderName 推荐人姓名-- " +
                    "recommenderOsition 推荐人职位-- " +
                    "recommenderPhone 推荐人电话-- " +
                    "responsiblePerson 经办人-- " +
                    "responsiblePersonMoney 经办人输入的金额-- " +
                    "showId 显示位置Id-- " +
                    "status 状态(0:审核通过;1:审核中;2:草稿3:审核不通过)-- " +
                    "title 标题-- " +
                    "updateTime 修改时间-- " +
                    "updateUser 修改人id-- " +
                    "verifier 审核人-- " +
                    "verifierOpinion 审核人意见-- ")
    @RequestMapping(value = "getSysCharitableDeclare", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> getFamilyStructureList(
            @ApiParam("显示位置Id") @RequestParam(value = "showId") Integer showId,
            @ApiParam("当前页") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam("每页信息条数") @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        try {
            //判断id是否为空
            if (showId == null) {
                return ResponseUtils.error(Constants.IS_EMPTY, "请输入showId");
            }
            //状态(0:审核通过;1:审核中;2:草稿3:审核不通过)
            List<Integer> list = new ArrayList<>();
            list.add(0);
            list.add(1);
            list.add(3);
            Wrapper<FanSysCharitableDeclare> entity = new EntityWrapper<FanSysCharitableDeclare>();
            entity.eq("show_id", showId);
            entity.in("status", list);
            Page<FanSysCharitableDeclare> fanSysCharitableDeclarePage = fanSysCharitableDeclareService.getCharitableDeclarePage(entity, pageNo, pageSize);
            if (fanSysCharitableDeclarePage == null) {
                return ResponseUtils.error(Constants.ERRO_CODE, "查询失败");
            }
            return ResponseUtils.success(fanSysCharitableDeclarePage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    /**
     * 联谊会慈善帮扶申报详情
     *
     * @Author: yuzhou
     * @Date: 2018-12-12
     * @Time: 16:13
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "联谊会慈善帮扶申报详情", notes =
            "abstractInfo 帮扶对象情况简介-- " +
                    "accountBank 申请人开户行-- " +
                    "accountName 申请人开户名-- " +
                    "accountNum 申请人开户行卡号-- " +
                    "applicantAdress 申请人地址-- " +
                    "applyMoney 申请金额-- " +
                    "applyName 申请人姓名-- " +
                    "applyNum 申请人身份证号码-- " +
                    "applyPhone 申请人电话-- " +
                    "approver 审批人-- " +
                    "approverOpinion 审批人意见-- " +
                    "createTime 创建时间-- " +
                    "createUser 创建人Id-- " +
                    "examinant 审查人-- " +
                    "examinantOpinion 审查人意见-- " +
                    "helpFeedback 帮助反馈-- " +
                    "helpFeedbackFile 上传帮助反馈的图片-- " +
                    "id 主键Id" +
                    "isApprover 审批人是否同意  1:同意 2:退回-- " +
                    "isExaminant 审查人是否同意  1:同意 2:退回-- " +
                    "isVerifier 审核人是否同意  1:同意 2:退回-- " +
                    "pictureAddress 联谊会添加的图片地址-- " +
                    "receivablesAddress 收款人地址-- " +
                    "receivablesBank 收款人开户行-- " +
                    "receivablesId 收款人身份证-- " +
                    "receivablesName 收款人姓名-- " +
                    "receivablesNum 收款人卡号-- " +
                    "receivablesPhone 收款人电话-- " +
                    "recommenderCompany 推荐人公司-- " +
                    "recommenderName 推荐人姓名-- " +
                    "recommenderOsition 推荐人职位-- " +
                    "recommenderPhone 推荐人电话-- " +
                    "responsiblePerson 经办人-- " +
                    "responsiblePersonMoney 经办人输入的金额-- " +
                    "showId 显示位置Id-- " +
                    "status 状态(0:审核通过;1:审核中;2:草稿3:审核不通过)-- " +
                    "title 标题-- " +
                    "updateTime 修改时间-- " +
                    "updateUser 修改人id-- " +
                    "verifier 审核人-- " +
                    "verifierOpinion 审核人意见-- ")
    @RequestMapping(value = "getFamilyStructureDetails", method = RequestMethod.GET)
    public Response<FanSysCharitableDeclare> getFamilyStructureDetails(
            @ApiParam("主键Id") @RequestParam(value = "id") Integer id
    ) {
        try {
            //判断id是否为空
            if (id == null) {
                return ResponseUtils.error(Constants.IS_EMPTY, "请输入Id");
            }
            FanSysCharitableDeclare fanSysCharitableDeclare = fanSysCharitableDeclareService.getFamilyStructureDetails(id);
            if (fanSysCharitableDeclare == null) {
                return ResponseUtils.error(Constants.ERRO_CODE, "查询失败");
            }
            return ResponseUtils.success(fanSysCharitableDeclare);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }
}

