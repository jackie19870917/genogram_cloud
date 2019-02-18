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
 * 省级-慈善公益-捐款人
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@TableName("chi_news_charity_pay_in")
public class ChiNewsCharityPayIn extends Model<ChiNewsCharityPayIn> {

    private static final long serialVersionUID = 1L;

    /**
     * 捐款ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    @TableField("show_id")
    private Integer showId;
    /**
     * 捐款人
     */
    @TableField("pay_usr_id")
    private Integer payUsrId;
    /**
     * 捐款方式(1-线上,2-线下)
     */
    private Integer type;
    /**
     * (支付宝)订单id
     */
    @TableField("order_id")
    private String orderId;
    /**
     * 捐款金额
     */
    @TableField("pay_amount")
    private BigDecimal payAmount;
    /**
     * 支付渠道
     */
    @TableField("pay_channel")
    private String payChannel;
    /**
     * 支付时间
     */
    @TableField("pay_time")
    private Date payTime;
    /**
     * 代付人账号
     */
    @TableField("agent_account")
    private String agentAccount;
    /**
     * 代付人用户ID
     */
    @TableField("agent_id")
    private String agentId;
    /**
     * 状态(0:删除;1:显示;2:不显示)
     */
    private Integer status;
    /**
     * 评论数
     */
    @TableField("comment_count")
    private Integer commentCount;
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

    public ChiNewsCharityPayIn setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public ChiNewsCharityPayIn setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public Integer getPayUsrId() {
        return payUsrId;
    }

    public ChiNewsCharityPayIn setPayUsrId(Integer payUsrId) {
        this.payUsrId = payUsrId;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public ChiNewsCharityPayIn setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public ChiNewsCharityPayIn setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public ChiNewsCharityPayIn setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
        return this;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public ChiNewsCharityPayIn setPayChannel(String payChannel) {
        this.payChannel = payChannel;
        return this;
    }

    public Date getPayTime() {
        return payTime;
    }

    public ChiNewsCharityPayIn setPayTime(Date payTime) {
        this.payTime = payTime;
        return this;
    }

    public String getAgentAccount() {
        return agentAccount;
    }

    public ChiNewsCharityPayIn setAgentAccount(String agentAccount) {
        this.agentAccount = agentAccount;
        return this;
    }

    public String getAgentId() {
        return agentId;
    }

    public ChiNewsCharityPayIn setAgentId(String agentId) {
        this.agentId = agentId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ChiNewsCharityPayIn setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public ChiNewsCharityPayIn setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChiNewsCharityPayIn setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ChiNewsCharityPayIn setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ChiNewsCharityPayIn setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ChiNewsCharityPayIn setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ChiNewsCharityPayIn{" +
                ", id=" + id +
                ", showId=" + showId +
                ", payUsrId=" + payUsrId +
                ", type=" + type +
                ", orderId=" + orderId +
                ", payAmount=" + payAmount +
                ", payChannel=" + payChannel +
                ", payTime=" + payTime +
                ", agentAccount=" + agentAccount +
                ", agentId=" + agentId +
                ", status=" + status +
                ", commentCount=" + commentCount +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
