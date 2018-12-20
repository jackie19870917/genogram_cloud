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
 * 个人日志
 * </p>
 *
 * @author wangwei
 * @since 2018-12-20
 */
@TableName("all_user_news_info")
public class AllUserNewsInfo extends Model<AllUserNewsInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 个人id
     */
    @TableField("user_id")
    private Integer userId;
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
     * 省级编号
     */
    @TableField("pro_code")
    private Integer proCode;
    /**
     * 地区编号
     */
    @TableField("region_id")
    private Integer regionId;
    /**
     * 系统管理员操作状态(1-展示,2-不展示)
     */
    @TableField("sys_status")
    private Integer sysStatus;
    /**
     * 阅读量
     */
    @TableField("read_num")
    private Integer readNum;
    /**
     * 状态（0 删除 1 正常 2 草稿）
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

    public Integer getProCode() {
        return proCode;
    }

    public AllUserNewsInfo setProCode(Integer proCode) {
        this.proCode = proCode;
        return this;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public AllUserNewsInfo setRegionId(Integer regionId) {
        this.regionId = regionId;
        return this;
    }

    public Integer getSysStatus() {
        return sysStatus;
    }

    public AllUserNewsInfo setSysStatus(Integer sysStatus) {
        this.sysStatus = sysStatus;
        return this;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public AllUserNewsInfo setReadNum(Integer readNum) {
        this.readNum = readNum;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllUserNewsInfo setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public AllUserNewsInfo setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
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
                ", title=" + title +
                ", newsFaceUrl=" + newsFaceUrl +
                ", content=" + content +
                ", proCode=" + proCode +
                ", regionId=" + regionId +
                ", sysStatus=" + sysStatus +
                ", readNum=" + readNum +
                ", status=" + status +
                ", commentCount=" + commentCount +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
