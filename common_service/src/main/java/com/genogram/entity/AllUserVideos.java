package com.genogram.entity;

import java.util.Date;

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
 * @since 2018-12-05
 */
@TableName("all_user_videos")
public class AllUserVideos extends Model<AllUserVideos> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    @TableField("user_id")
    private Integer userId;
    private Integer status;
    private String title;
    @TableField("video_pic_url")
    private String videoPicUrl;
    @TableField("video_url")
    private String videoUrl;
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
                ", status=" + status +
                ", title=" + title +
                ", videoPicUrl=" + videoPicUrl +
                ", videoUrl=" + videoUrl +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
