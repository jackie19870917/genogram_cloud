package com.genogram.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwei
 * @since 2018-12-07
 */
@TableName("all_user_praise")
public class AllUserPraise extends Model<AllUserPraise> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 被点赞主题id
     */
    @TableField("praise_topic_id")
    private Integer praiseTopicId;
    /**
     * service名
     */
    @TableField("entity_name")
    private String entityName;
    /**
     * 点赞人
     */
    @TableField("from_user_id")
    private Integer fromUserId;
    /**
     * 状态（0取消 1正常）预留
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
    @TableField("update_time")
    private Date updateTime;
    @TableField("update_user")
    private Integer updateUser;


    public Integer getId() {
        return id;
    }

    public AllUserPraise setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPraiseTopicId() {
        return praiseTopicId;
    }

    public AllUserPraise setPraiseTopicId(Integer praiseTopicId) {
        this.praiseTopicId = praiseTopicId;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public AllUserPraise setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public AllUserPraise setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllUserPraise setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AllUserPraise setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public AllUserPraise setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AllUserPraise setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public AllUserPraise setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllUserPraise{" +
                ", id=" + id +
                ", praiseTopicId=" + praiseTopicId +
                ", entityName=" + entityName +
                ", fromUserId=" + fromUserId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
