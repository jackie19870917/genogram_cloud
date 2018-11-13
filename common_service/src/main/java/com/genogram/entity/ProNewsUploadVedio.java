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
 * @since 2018-11-13
 */
@TableName("pro_news_upload_vedio")
public class ProNewsUploadVedio extends Model<ProNewsUploadVedio> {

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

    public ProNewsUploadVedio setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public ProNewsUploadVedio setNewsId(Integer newsId) {
        this.newsId = newsId;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public ProNewsUploadVedio setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public ProNewsUploadVedio setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public ProNewsUploadVedio setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ProNewsUploadVedio setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProNewsUploadVedio setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ProNewsUploadVedio setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ProNewsUploadVedio setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ProNewsUploadVedio setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProNewsUploadVedio{" +
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
