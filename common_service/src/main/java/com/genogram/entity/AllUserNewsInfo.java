package com.genogram.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 个人日志
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
@TableName("all_user_news_info")
public class AllUserNewsInfo extends Model<AllUserNewsInfo> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 个人id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 状态（0 删除 1 正常 2 草稿）
     */
    private Integer status;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章封面url
     */
    @TableField("news_face_url")
    private String newsFaceUrl;
    /**
     * 文章内容
     */
    private String content;
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
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 更新人
     */
    @TableField("update_user")
    private Integer updateUser;


    public Integer getId() {
        return id;
    }

    public AllUserNewsInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public AllUserNewsInfo setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllUserNewsInfo setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AllUserNewsInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getNewsFaceUrl() {
        return newsFaceUrl;
    }

    public AllUserNewsInfo setNewsFaceUrl(String newsFaceUrl) {
        this.newsFaceUrl = newsFaceUrl;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AllUserNewsInfo setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AllUserNewsInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public AllUserNewsInfo setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AllUserNewsInfo setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public AllUserNewsInfo setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllUserNewsInfo{" +
                ", id=" + id +
                ", userId=" + userId +
                ", status=" + status +
                ", title=" + title +
                ", newsFaceUrl=" + newsFaceUrl +
                ", content=" + content +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
