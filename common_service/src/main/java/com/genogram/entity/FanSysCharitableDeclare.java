package com.genogram.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 慈善帮扶
 * </p>
 *
 * @author wangwei
 * @since 2018-12-20
 */
@TableName("fan_sys_charitable_declare")
public class FanSysCharitableDeclare extends Model<FanSysCharitableDeclare> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 状态(0:审核通过;1:审核中;2:草稿3:审核不通过)
     */
    private Integer status;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    @TableField("show_id")
    private Integer showId;
    /**
     * 标题
     */
    private String title;
    /**
     * 图片地址
     */
    @TableField("picture_address")
    private String pictureAddress;
    /**
     * 申请人姓名
     */
    @TableField("apply_name")
    private String applyName;
    /**
     * 申请人电话
     */
    @TableField("apply_phone")
    private String applyPhone;
    /**
     * 申请金额
     */
    @TableField("apply_money")
    private BigDecimal applyMoney;
    /**
     * 申请人身份证号码
     */
    @TableField("apply_num")
    private String applyNum;
    /**
     * 申请人开户行
     */
    @TableField("account_bank")
    private String accountBank;
    /**
     * 申请人开户名
     */
    @TableField("account_name")
    private String accountName;
    /**
     * 申请人开户行卡号
     */
    @TableField("account_num")
    private String accountNum;
    /**
     * 申请人地址
     */
    @TableField("applicant_adress")
    private String applicantAdress;
    /**
     * 收款人姓名
     */
    @TableField("receivables_name")
    private String receivablesName;
    /**
     * 收款人电话
     */
    @TableField("receivables_phone")
    private String receivablesPhone;
    /**
     * 收款人身份证
     */
    @TableField("receivables_id")
    private String receivablesId;
    /**
     * 收款人地址
     */
    @TableField("receivables_address")
    private String receivablesAddress;
    /**
     * 收款人开户行
     */
    @TableField("receivables_bank")
    private String receivablesBank;
    /**
     * 收款人卡号
     */
    @TableField("receivables_num")
    private String receivablesNum;
    /**
     * 帮扶对象情况简介
     */
    @TableField("abstract_info")
    private String abstractInfo;
    /**
     * 推荐人姓名
     */
    @TableField("recommender_name")
    private String recommenderName;
    /**
     * 推荐人电话
     */
    @TableField("recommender_phone")
    private String recommenderPhone;
    /**
     * 推荐人公司
     */
    @TableField("recommender_company")
    private String recommenderCompany;
    /**
     * 推荐人职位
     */
    @TableField("recommender_osition")
    private String recommenderOsition;
    /**
     * 审查人
     */
    private String examinant;
    /**
     * 审查人意见
     */
    @TableField("examinant_opinion")
    private String examinantOpinion;
    /**
     * 审查人是否同意  1:同意 2:退回
     */
    private Integer isExaminant;
    /**
     * 审核人
     */
    private String verifier;
    /**
     * 审核人意见
     */
    @TableField("verifier_opinion")
    private String verifierOpinion;
    /**
     * 审核人是否同意  1:同意 2:退回
     */
    private Integer isVerifier;
    /**
     * 审批人
     */
    private String approver;
    /**
     * 审批人意见
     */
    @TableField("approver_opinion")
    private String approverOpinion;
    /**
     * 审批人是否同意  1:同意 2:退回
     */
    private Integer isApprover;
    /**
     * 帮助反馈
     */
    @TableField("help_feedback")
    private String helpFeedback;
    /**
     * 上传帮助反馈的图片
     */
    @TableField("help_feedback_file")
    private String helpFeedbackFile;
    /**
     * 经办人
     */
    @TableField("responsible_person")
    private String responsiblePerson;
    /**
     * 经办人输入的金额
     */
    @TableField("responsible_person_money")
    private BigDecimal responsiblePersonMoney;
    /**
     * 评论数
     */
    @TableField("comment_count")
    private Integer commentCount;
    /**
     * 创建人
     */
    @TableField("create_user")
    private Integer createUser;
    /**
     * 修改人
     */
    @TableField("update_user")
    private Integer updateUser;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public FanSysCharitableDeclare setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public FanSysCharitableDeclare setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public FanSysCharitableDeclare setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public FanSysCharitableDeclare setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPictureAddress() {
        return pictureAddress;
    }

    public FanSysCharitableDeclare setPictureAddress(String pictureAddress) {
        this.pictureAddress = pictureAddress;
        return this;
    }

    public String getApplyName() {
        return applyName;
    }

    public FanSysCharitableDeclare setApplyName(String applyName) {
        this.applyName = applyName;
        return this;
    }

    public String getApplyPhone() {
        return applyPhone;
    }

    public FanSysCharitableDeclare setApplyPhone(String applyPhone) {
        this.applyPhone = applyPhone;
        return this;
    }

    public BigDecimal getApplyMoney() {
        return applyMoney;
    }

    public FanSysCharitableDeclare setApplyMoney(BigDecimal applyMoney) {
        this.applyMoney = applyMoney;
        return this;
    }

    public String getApplyNum() {
        return applyNum;
    }

    public FanSysCharitableDeclare setApplyNum(String applyNum) {
        this.applyNum = applyNum;
        return this;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public FanSysCharitableDeclare setAccountBank(String accountBank) {
        this.accountBank = accountBank;
        return this;
    }

    public String getAccountName() {
        return accountName;
    }

    public FanSysCharitableDeclare setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public FanSysCharitableDeclare setAccountNum(String accountNum) {
        this.accountNum = accountNum;
        return this;
    }

    public String getApplicantAdress() {
        return applicantAdress;
    }

    public FanSysCharitableDeclare setApplicantAdress(String applicantAdress) {
        this.applicantAdress = applicantAdress;
        return this;
    }

    public String getReceivablesName() {
        return receivablesName;
    }

    public FanSysCharitableDeclare setReceivablesName(String receivablesName) {
        this.receivablesName = receivablesName;
        return this;
    }

    public String getReceivablesPhone() {
        return receivablesPhone;
    }

    public FanSysCharitableDeclare setReceivablesPhone(String receivablesPhone) {
        this.receivablesPhone = receivablesPhone;
        return this;
    }

    public String getReceivablesId() {
        return receivablesId;
    }

    public FanSysCharitableDeclare setReceivablesId(String receivablesId) {
        this.receivablesId = receivablesId;
        return this;
    }

    public String getReceivablesAddress() {
        return receivablesAddress;
    }

    public FanSysCharitableDeclare setReceivablesAddress(String receivablesAddress) {
        this.receivablesAddress = receivablesAddress;
        return this;
    }

    public String getReceivablesBank() {
        return receivablesBank;
    }

    public FanSysCharitableDeclare setReceivablesBank(String receivablesBank) {
        this.receivablesBank = receivablesBank;
        return this;
    }

    public String getReceivablesNum() {
        return receivablesNum;
    }

    public FanSysCharitableDeclare setReceivablesNum(String receivablesNum) {
        this.receivablesNum = receivablesNum;
        return this;
    }

    public String getAbstractInfo() {
        return abstractInfo;
    }

    public FanSysCharitableDeclare setAbstractInfo(String abstractInfo) {
        this.abstractInfo = abstractInfo;
        return this;
    }

    public String getRecommenderName() {
        return recommenderName;
    }

    public FanSysCharitableDeclare setRecommenderName(String recommenderName) {
        this.recommenderName = recommenderName;
        return this;
    }

    public String getRecommenderPhone() {
        return recommenderPhone;
    }

    public FanSysCharitableDeclare setRecommenderPhone(String recommenderPhone) {
        this.recommenderPhone = recommenderPhone;
        return this;
    }

    public String getRecommenderCompany() {
        return recommenderCompany;
    }

    public FanSysCharitableDeclare setRecommenderCompany(String recommenderCompany) {
        this.recommenderCompany = recommenderCompany;
        return this;
    }

    public String getRecommenderOsition() {
        return recommenderOsition;
    }

    public FanSysCharitableDeclare setRecommenderOsition(String recommenderOsition) {
        this.recommenderOsition = recommenderOsition;
        return this;
    }

    public String getExaminant() {
        return examinant;
    }

    public FanSysCharitableDeclare setExaminant(String examinant) {
        this.examinant = examinant;
        return this;
    }

    public String getExaminantOpinion() {
        return examinantOpinion;
    }

    public FanSysCharitableDeclare setExaminantOpinion(String examinantOpinion) {
        this.examinantOpinion = examinantOpinion;
        return this;
    }

    public Integer getIsExaminant() {
        return isExaminant;
    }

    public FanSysCharitableDeclare setIsExaminant(Integer isExaminant) {
        this.isExaminant = isExaminant;
        return this;
    }

    public String getVerifier() {
        return verifier;
    }

    public FanSysCharitableDeclare setVerifier(String verifier) {
        this.verifier = verifier;
        return this;
    }

    public String getVerifierOpinion() {
        return verifierOpinion;
    }

    public FanSysCharitableDeclare setVerifierOpinion(String verifierOpinion) {
        this.verifierOpinion = verifierOpinion;
        return this;
    }

    public Integer getIsVerifier() {
        return isVerifier;
    }

    public FanSysCharitableDeclare setIsVerifier(Integer isVerifier) {
        this.isVerifier = isVerifier;
        return this;
    }

    public String getApprover() {
        return approver;
    }

    public FanSysCharitableDeclare setApprover(String approver) {
        this.approver = approver;
        return this;
    }

    public String getApproverOpinion() {
        return approverOpinion;
    }

    public FanSysCharitableDeclare setApproverOpinion(String approverOpinion) {
        this.approverOpinion = approverOpinion;
        return this;
    }

    public Integer getIsApprover() {
        return isApprover;
    }

    public FanSysCharitableDeclare setIsApprover(Integer isApprover) {
        this.isApprover = isApprover;
        return this;
    }

    public String getHelpFeedback() {
        return helpFeedback;
    }

    public FanSysCharitableDeclare setHelpFeedback(String helpFeedback) {
        this.helpFeedback = helpFeedback;
        return this;
    }

    public String getHelpFeedbackFile() {
        return helpFeedbackFile;
    }

    public FanSysCharitableDeclare setHelpFeedbackFile(String helpFeedbackFile) {
        this.helpFeedbackFile = helpFeedbackFile;
        return this;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public FanSysCharitableDeclare setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
        return this;
    }

    public BigDecimal getResponsiblePersonMoney() {
        return responsiblePersonMoney;
    }

    public FanSysCharitableDeclare setResponsiblePersonMoney(BigDecimal responsiblePersonMoney) {
        this.responsiblePersonMoney = responsiblePersonMoney;
        return this;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public FanSysCharitableDeclare setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanSysCharitableDeclare setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanSysCharitableDeclare setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanSysCharitableDeclare setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanSysCharitableDeclare setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanSysCharitableDeclare{" +
                ", id=" + id +
                ", status=" + status +
                ", showId=" + showId +
                ", title=" + title +
                ", pictureAddress=" + pictureAddress +
                ", applyName=" + applyName +
                ", applyPhone=" + applyPhone +
                ", applyMoney=" + applyMoney +
                ", applyNum=" + applyNum +
                ", accountBank=" + accountBank +
                ", accountName=" + accountName +
                ", accountNum=" + accountNum +
                ", applicantAdress=" + applicantAdress +
                ", receivablesName=" + receivablesName +
                ", receivablesPhone=" + receivablesPhone +
                ", receivablesId=" + receivablesId +
                ", receivablesAddress=" + receivablesAddress +
                ", receivablesBank=" + receivablesBank +
                ", receivablesNum=" + receivablesNum +
                ", abstractInfo=" + abstractInfo +
                ", recommenderName=" + recommenderName +
                ", recommenderPhone=" + recommenderPhone +
                ", recommenderCompany=" + recommenderCompany +
                ", recommenderOsition=" + recommenderOsition +
                ", examinant=" + examinant +
                ", examinantOpinion=" + examinantOpinion +
                ", isExaminant=" + isExaminant +
                ", verifier=" + verifier +
                ", verifierOpinion=" + verifierOpinion +
                ", isVerifier=" + isVerifier +
                ", approver=" + approver +
                ", approverOpinion=" + approverOpinion +
                ", isApprover=" + isApprover +
                ", helpFeedback=" + helpFeedback +
                ", helpFeedbackFile=" + helpFeedbackFile +
                ", responsiblePerson=" + responsiblePerson +
                ", responsiblePersonMoney=" + responsiblePersonMoney +
                ", commentCount=" + commentCount +
                ", createUser=" + createUser +
                ", updateUser=" + updateUser +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
