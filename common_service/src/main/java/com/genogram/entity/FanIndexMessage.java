package com.genogram.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 联谊会在线聊天
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@TableName("fan_index_message")
public class FanIndexMessage extends Model<FanIndexMessage> {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 网站id(fan_sys_site_id)
     */
    @TableField("site_id")
    private Integer siteId;
    /**
     * 发送人ID
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 发送时间
     */
    @TableField("send_time")
    private Date sendTime;
    /**
     * 状态(0:删除;1:显示;2:不显示)
     */
    private Integer status;
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

    public FanIndexMessage setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public FanIndexMessage setSiteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public FanIndexMessage setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public FanIndexMessage setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public FanIndexMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public FanIndexMessage setSendTime(Date sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public FanIndexMessage setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanIndexMessage setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanIndexMessage setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanIndexMessage setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanIndexMessage setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanIndexMessage{" +
        ", id=" + id +
        ", siteId=" + siteId +
        ", userId=" + userId +
        ", nickname=" + nickname +
        ", message=" + message +
        ", sendTime=" + sendTime +
        ", status=" + status +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
