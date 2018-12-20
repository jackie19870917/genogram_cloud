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
 * 个人说说
 * </p>
 *
 * @author wangwei
 * @since 2018-12-20
 */
@TableName("all_user_says")
public class AllUserSays extends Model<AllUserSays> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 个人id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 说说内容
     */
    private String content;
    /**
     * 状态（1 正常 0 删除）
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
     * 创建人id
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

    public AllUserSays setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public AllUserSays setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AllUserSays setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllUserSays setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public AllUserSays setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AllUserSays setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public AllUserSays setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AllUserSays setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public AllUserSays setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllUserSays{" +
                ", id=" + id +
                ", userId=" + userId +
                ", content=" + content +
                ", status=" + status +
                ", commentCount=" + commentCount +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
