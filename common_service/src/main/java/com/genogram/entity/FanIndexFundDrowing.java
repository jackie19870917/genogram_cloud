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
 * 线上提现
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@TableName("fan_index_fund_drowing")
public class FanIndexFundDrowing extends Model<FanIndexFundDrowing> {

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
     * 提现状态(1:成功;2:失败)
     */
    @TableField("drow_status")
    private Integer drowStatus;
    /**
     * 审核状态(1:通过;2:不通过)
     */
    @TableField("approve_status")
    private String approveStatus;
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

    public FanIndexFundDrowing setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public FanIndexFundDrowing setSiteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public BigDecimal getDrowAmount() {
        return drowAmount;
    }

    public FanIndexFundDrowing setDrowAmount(BigDecimal drowAmount) {
        this.drowAmount = drowAmount;
        return this;
    }

    public String getDrowBank() {
        return drowBank;
    }

    public FanIndexFundDrowing setDrowBank(String drowBank) {
        this.drowBank = drowBank;
        return this;
    }

    public String getDrowBankSub() {
        return drowBankSub;
    }

    public FanIndexFundDrowing setDrowBankSub(String drowBankSub) {
        this.drowBankSub = drowBankSub;
        return this;
    }

    public Date getDrowTime() {
        return drowTime;
    }

    public FanIndexFundDrowing setDrowTime(Date drowTime) {
        this.drowTime = drowTime;
        return this;
    }

    public String getDrowInAccountName() {
        return drowInAccountName;
    }

    public FanIndexFundDrowing setDrowInAccountName(String drowInAccountName) {
        this.drowInAccountName = drowInAccountName;
        return this;
    }

    public String getDrowInAccountCard() {
        return drowInAccountCard;
    }

    public FanIndexFundDrowing setDrowInAccountCard(String drowInAccountCard) {
        this.drowInAccountCard = drowInAccountCard;
        return this;
    }

    public Integer getDrowStatus() {
        return drowStatus;
    }

    public FanIndexFundDrowing setDrowStatus(Integer drowStatus) {
        this.drowStatus = drowStatus;
        return this;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public FanIndexFundDrowing setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanIndexFundDrowing setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanIndexFundDrowing setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanIndexFundDrowing setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanIndexFundDrowing setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanIndexFundDrowing{" +
        ", id=" + id +
        ", siteId=" + siteId +
        ", drowAmount=" + drowAmount +
        ", drowBank=" + drowBank +
        ", drowBankSub=" + drowBankSub +
        ", drowTime=" + drowTime +
        ", drowInAccountName=" + drowInAccountName +
        ", drowInAccountCard=" + drowInAccountCard +
        ", drowStatus=" + drowStatus +
        ", approveStatus=" + approveStatus +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
