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
 * 记录家族视频上传-视频概要
 * </p>
 *
 * @author wangwei
 * @since 2018-12-20
 */
@TableName("fan_news_family_record_vedio")
public class FanNewsFamilyRecordVedio extends Model<FanNewsFamilyRecordVedio> {

    private static final long serialVersionUID = 1L;

    /**
     * 文件上传ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    @TableField("show_id")
    private Integer showId;
    /**
     * 视频种类1.个人;2官方
     */
    @TableField("vedio_type")
    private Integer vedioType;
    /**
     * 视频标题
     */
    private String title;
    /**
     * 视频作者
     */
    private String auth;
    /**
     * 点赞数
     */
    @TableField("praise_num")
    private Integer praiseNum;
    /**
     * 状态(0:删除;1:显示;2:不显示)
     */
    private Integer status;
    /**
     * 评论数
     */
    @TableField("comment_count")
    private Integer commentCount;
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

    public FanNewsFamilyRecordVedio setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public FanNewsFamilyRecordVedio setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public Integer getVedioType() {
        return vedioType;
    }

    public FanNewsFamilyRecordVedio setVedioType(Integer vedioType) {
        this.vedioType = vedioType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public FanNewsFamilyRecordVedio setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuth() {
        return auth;
    }

    public FanNewsFamilyRecordVedio setAuth(String auth) {
        this.auth = auth;
        return this;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public FanNewsFamilyRecordVedio setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public FanNewsFamilyRecordVedio setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public FanNewsFamilyRecordVedio setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanNewsFamilyRecordVedio setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanNewsFamilyRecordVedio setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanNewsFamilyRecordVedio setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanNewsFamilyRecordVedio setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanNewsFamilyRecordVedio{" +
        ", id=" + id +
        ", showId=" + showId +
        ", vedioType=" + vedioType +
        ", title=" + title +
        ", auth=" + auth +
        ", praiseNum=" + praiseNum +
        ", status=" + status +
        ", commentCount=" + commentCount +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
