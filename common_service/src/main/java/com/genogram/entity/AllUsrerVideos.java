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
 * @author yizx
 * @since 2018-12-04
 */
@TableName("all_usrer_videos")
public class AllUsrerVideos extends Model<AllUsrerVideos> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    @TableField("usr_id")
    private Integer usrId;
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

    public AllUsrerVideos setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUsrId() {
        return usrId;
    }

    public AllUsrerVideos setUsrId(Integer usrId) {
        this.usrId = usrId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllUsrerVideos setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AllUsrerVideos setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getVideoPicUrl() {
        return videoPicUrl;
    }

    public AllUsrerVideos setVideoPicUrl(String videoPicUrl) {
        this.videoPicUrl = videoPicUrl;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public AllUsrerVideos setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AllUsrerVideos setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public AllUsrerVideos setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AllUsrerVideos setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public AllUsrerVideos setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllUsrerVideos{" +
        ", id=" + id +
        ", usrId=" + usrId +
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
