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
 * 省级-首页-基金-前台后台
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@TableName("chi_index_fund")
public class ChiIndexFund extends Model<ChiIndexFund> {

    private static final long serialVersionUID = 1L;

    /**
     * 表ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 网站id(fan_sys_site_id)
     */
    @TableField("site_id")
    private Integer siteId;
    /**
     * 基金余额
     */
    private BigDecimal remain;
    /**
     * 捐款人数
     */
    @TableField("pay_num")
    private Integer payNum;
    /**
     * 不可用金额(冻结金额)
     */
    @TableField("unuse_amount")
    private BigDecimal unuseAmount;
    /**
     * 线上捐款金额
     */
    @TableField("pay_online")
    private BigDecimal payOnline;
    /**
     * 线下捐款金额
     */
    @TableField("pay_underline")
    private BigDecimal payUnderline;
    /**
     * 网络修普金额
     */
    @TableField("pay_genogram")
    private BigDecimal payGenogram;
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

    public ChiIndexFund setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public ChiIndexFund setSiteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public BigDecimal getRemain() {
        return remain;
    }

    public ChiIndexFund setRemain(BigDecimal remain) {
        this.remain = remain;
        return this;
    }

    public Integer getPayNum() {
        return payNum;
    }

    public ChiIndexFund setPayNum(Integer payNum) {
        this.payNum = payNum;
        return this;
    }

    public BigDecimal getUnuseAmount() {
        return unuseAmount;
    }

    public ChiIndexFund setUnuseAmount(BigDecimal unuseAmount) {
        this.unuseAmount = unuseAmount;
        return this;
    }

    public BigDecimal getPayOnline() {
        return payOnline;
    }

    public ChiIndexFund setPayOnline(BigDecimal payOnline) {
        this.payOnline = payOnline;
        return this;
    }

    public BigDecimal getPayUnderline() {
        return payUnderline;
    }

    public ChiIndexFund setPayUnderline(BigDecimal payUnderline) {
        this.payUnderline = payUnderline;
        return this;
    }

    public BigDecimal getPayGenogram() {
        return payGenogram;
    }

    public ChiIndexFund setPayGenogram(BigDecimal payGenogram) {
        this.payGenogram = payGenogram;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChiIndexFund setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ChiIndexFund setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ChiIndexFund setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ChiIndexFund setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ChiIndexFund{" +
        ", id=" + id +
        ", siteId=" + siteId +
        ", remain=" + remain +
        ", payNum=" + payNum +
        ", unuseAmount=" + unuseAmount +
        ", payOnline=" + payOnline +
        ", payUnderline=" + payUnderline +
        ", payGenogram=" + payGenogram +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
