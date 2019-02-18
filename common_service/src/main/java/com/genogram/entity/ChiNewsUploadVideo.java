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
 * 省级视频上传表(不包括视普通文件)
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@TableName("chi_news_upload_video")
public class ChiNewsUploadVideo extends Model<ChiNewsUploadVideo> {

    private static final long serialVersionUID = 1L;

    /**
     * 文件上传ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 文章id
     */
    @TableField("news_id")
    private Integer newsId;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    @TableField("show_id")
    private Integer showId;
    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;
    /**
     * 状态(0:删除;1:显示;2:不显示)
     */
    private Integer status;
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

    public ChiNewsUploadVideo setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public ChiNewsUploadVideo setNewsId(Integer newsId) {
        this.newsId = newsId;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public ChiNewsUploadVideo setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public ChiNewsUploadVideo setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public ChiNewsUploadVideo setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ChiNewsUploadVideo setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChiNewsUploadVideo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ChiNewsUploadVideo setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ChiNewsUploadVideo setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ChiNewsUploadVideo setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ChiNewsUploadVideo{" +
                ", id=" + id +
                ", newsId=" + newsId +
                ", showId=" + showId +
                ", fileName=" + fileName +
                ", filePath=" + filePath +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
