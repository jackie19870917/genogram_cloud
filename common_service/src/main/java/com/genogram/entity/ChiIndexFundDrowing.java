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
 * 省级线上提现
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@TableName("chi_index_fund_drowing")
public class ChiIndexFundDrowing extends Model<ChiIndexFundDrowing> {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 网站id(fan_sys_site_id)
     */
    @TableField("site_id")
    private Integer siteId;
    /**
     * 提现金额
     */
    @TableField("drow_amount")
    private BigDecimal drowAmount;
    /**
     * 到账银行
     */
    @TableField("drow_bank")
    private String drowBank;
    /**
     * 到账银行支行名称
     */
    @TableField("drow_bank_sub")
    private String drowBankSub;
    /**
     * 提现时间
     */
    @TableField("drow_time")
    private Date drowTime;
    /**
     * 账户名
     */
    @TableField("drow_in_account_name")
    private String drowInAccountName;
    /**
     * 入账卡号
     */
    @TableField("drow_in_account_card")
    private String drowInAccountCard;
    /**
     * 备注
     */
    @TableField("drow_comments")
    private String drowComments;
    /**
     * 审核状态(1:审核中;2:打款中;3:成功4:拒绝)
     */
    @TableField("approve_status")
    private Integer approveStatus;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人
     */
    @TableField("create_user")
    private Integer createUser;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 修改人
     */
    @TableField("update_user")
    private Integer updateUser;


    public Integer getId() {
        return id;
    }

    public ChiIndexFundDrowing setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public ChiIndexFundDrowing setSiteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public BigDecimal getDrowAmount() {
        return drowAmount;
    }

    public ChiIndexFundDrowing setDrowAmount(BigDecimal drowAmount) {
        this.drowAmount = drowAmount;
        return this;
    }

    public String getDrowBank() {
        return drowBank;
    }

    public ChiIndexFundDrowing setDrowBank(String drowBank) {
        this.drowBank = drowBank;
        return this;
    }

    public String getDrowBankSub() {
        return drowBankSub;
    }

    public ChiIndexFundDrowing setDrowBankSub(String drowBankSub) {
        this.drowBankSub = drowBankSub;
        return this;
    }

    public Date getDrowTime() {
        return drowTime;
    }

    public ChiIndexFundDrowing setDrowTime(Date drowTime) {
        this.drowTime = drowTime;
        return this;
    }

    public String getDrowInAccountName() {
        return drowInAccountName;
    }

    public ChiIndexFundDrowing setDrowInAccountName(String drowInAccountName) {
        this.drowInAccountName = drowInAccountName;
        return this;
    }

    public String getDrowInAccountCard() {
        return drowInAccountCard;
    }

    public ChiIndexFundDrowing setDrowInAccountCard(String drowInAccountCard) {
        this.drowInAccountCard = drowInAccountCard;
        return this;
    }

    public String getDrowComments() {
        return drowComments;
    }

    public ChiIndexFundDrowing setDrowComments(String drowComments) {
        this.drowComments = drowComments;
        return this;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public ChiIndexFundDrowing setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChiIndexFundDrowing setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ChiIndexFundDrowing setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ChiIndexFundDrowing setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ChiIndexFundDrowing setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ChiIndexFundDrowing{" +
                ", id=" + id +
                ", siteId=" + siteId +
                ", drowAmount=" + drowAmount +
                ", drowBank=" + drowBank +
                ", drowBankSub=" + drowBankSub +
                ", drowTime=" + drowTime +
                ", drowInAccountName=" + drowInAccountName +
                ", drowInAccountCard=" + drowInAccountCard +
                ", drowComments=" + drowComments +
                ", approveStatus=" + approveStatus +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
