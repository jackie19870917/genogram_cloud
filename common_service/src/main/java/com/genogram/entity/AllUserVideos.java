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
 * 个人视频
 * </p>
 *
 * @author wangwei
 * @since 2018-12-20
 */
@TableName("all_user_videos")
public class AllUserVideos extends Model<AllUserVideos> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    /**
     * 系统管理员操作状态(1-展示,2-不展示)
     */
    @TableField("sys_status")
    private Integer sysStatus;
    private Integer status;
    private String title;
    /**
     * 用户所属地区
     */
    @TableField("region_id")
    private Integer regionId;
    @TableField("video_pic_url")
    private String videoPicUrl;
    @TableField("video_url")
    private String videoUrl;
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

    public AllUserVideos setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public AllUserVideos setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getSysStatus() {
        return sysStatus;
    }

    public AllUserVideos setSysStatus(Integer sysStatus) {
        this.sysStatus = sysStatus;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllUserVideos setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AllUserVideos setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public AllUserVideos setRegionId(Integer regionId) {
        this.regionId = regionId;
        return this;
    }

    public String getVideoPicUrl() {
        return videoPicUrl;
    }

    public AllUserVideos setVideoPicUrl(String videoPicUrl) {
        this.videoPicUrl = videoPicUrl;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public AllUserVideos setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public AllUserVideos setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AllUserVideos setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public AllUserVideos setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AllUserVideos setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public AllUserVideos setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllUserVideos{" +
        ", id=" + id +
        ", userId=" + userId +
        ", sysStatus=" + sysStatus +
        ", status=" + status +
        ", title=" + title +
        ", regionId=" + regionId +
        ", videoPicUrl=" + videoPicUrl +
        ", videoUrl=" + videoUrl +
        ", commentCount=" + commentCount +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
