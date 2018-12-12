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
@TableName("all_user_reply")
public class AllUserReply extends Model<AllUserReply> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 评论表id
     */
    @TableField("comment_id")
    private Integer commentId;
    /**
     * 回复目标id
     */
    @TableField("reply_id")
    private Integer replyId;
    /**
     * 1 评论的回复 2回复的回复
     */
    @TableField("reply_type")
    private Integer replyType;
    /**
     * 回复内容
     */
    private String content;
    /**
     * 点赞数
     */
    @TableField("praise_count")
    private Integer praiseCount;
    /**
     * 0删除 1正常
     */
    private Integer status;
    /**
     * 回复用户id
     */
    @TableField("from_user_id")
    private Integer fromUserId;
    /**
     * 目标用户id
     */
    @TableField("to_user_id")
    private Integer toUserId;
    @TableField("create_time")
    private Date createTime;
    @TableField("create_user")
    private Integer createUser;
    @TableField("update_time")
    private Date updateTime;
    @TableField("update_user")
    private Integer updateUser;


    public Integer getId() {
        return id;
    }

    public AllUserReply setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public AllUserReply setCommentId(Integer commentId) {
        this.commentId = commentId;
        return this;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public AllUserReply setReplyId(Integer replyId) {
        this.replyId = replyId;
        return this;
    }

    public Integer getReplyType() {
        return replyType;
    }

    public AllUserReply setReplyType(Integer replyType) {
        this.replyType = replyType;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AllUserReply setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getPraiseCount() {
        return praiseCount;
    }

    public AllUserReply setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllUserReply setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public AllUserReply setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
        return this;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public AllUserReply setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AllUserReply setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public AllUserReply setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AllUserReply setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public AllUserReply setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllUserReply{" +
        ", id=" + id +
        ", commentId=" + commentId +
        ", replyId=" + replyId +
        ", replyType=" + replyType +
        ", content=" + content +
        ", praiseCount=" + praiseCount +
        ", status=" + status +
        ", fromUserId=" + fromUserId +
        ", toUserId=" + toUserId +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
