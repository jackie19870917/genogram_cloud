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
 * 省级网文章附件上传表(不包括视频文件)
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@TableName("chi_news_upload_file")
public class ChiNewsUploadFile extends Model<ChiNewsUploadFile> {

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
     * 是否封面(0.否;1:是封面)
     */
    @TableField("pic_index")
    private Integer picIndex;
    /**
     * 状态1显示.0不显示
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

    public ChiNewsUploadFile setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public ChiNewsUploadFile setNewsId(Integer newsId) {
        this.newsId = newsId;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public ChiNewsUploadFile setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public ChiNewsUploadFile setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public ChiNewsUploadFile setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public Integer getPicIndex() {
        return picIndex;
    }

    public ChiNewsUploadFile setPicIndex(Integer picIndex) {
        this.picIndex = picIndex;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ChiNewsUploadFile setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChiNewsUploadFile setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ChiNewsUploadFile setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ChiNewsUploadFile setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ChiNewsUploadFile setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ChiNewsUploadFile{" +
                ", id=" + id +
                ", newsId=" + newsId +
                ", showId=" + showId +
                ", fileName=" + fileName +
                ", filePath=" + filePath +
                ", picIndex=" + picIndex +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
