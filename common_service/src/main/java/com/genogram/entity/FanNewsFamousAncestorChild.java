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
 * 联谊会-祖先分支后裔
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@TableName("fan_news_famous_ancestor_child")
public class FanNewsFamousAncestorChild extends Model<FanNewsFamousAncestorChild> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 祖先id
     */
    @TableField("fan_news_famous_ancestor_id")
    private Integer fanNewsFamousAncestorId;
    /**
     * 祖先头衔
     */
    @TableField("ancestor_title")
    private String ancestorTitle;
    /**
     * 祖先名
     */
    @TableField("ancestor_name")
    private String ancestorName;
    /**
     * 人物简介
     */
    @TableField("ancestor_summary")
    private String ancestorSummary;
    /**
     * 头像图片位置
     */
    @TableField("pic_file_src")
    private String picFileSrc;
    /**
     * 头像名
     */
    @TableField("pic_file_name")
    private String picFileName;
    /**
     * 查看数
     */
    @TableField("visit_num")
    private Integer visitNum;
    /**
     * 状态(0:删除;1:发布;3:不显示)
     */
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人
     */
    @TableField("create_user")
    private Integer createUser;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 修改人
     */
    @TableField("update_user")
    private Integer updateUser;


    public Integer getId() {
        return id;
    }

    public FanNewsFamousAncestorChild setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFanNewsFamousAncestorId() {
        return fanNewsFamousAncestorId;
    }

    public FanNewsFamousAncestorChild setFanNewsFamousAncestorId(Integer fanNewsFamousAncestorId) {
        this.fanNewsFamousAncestorId = fanNewsFamousAncestorId;
        return this;
    }

    public String getAncestorTitle() {
        return ancestorTitle;
    }

    public FanNewsFamousAncestorChild setAncestorTitle(String ancestorTitle) {
        this.ancestorTitle = ancestorTitle;
        return this;
    }

    public String getAncestorName() {
        return ancestorName;
    }

    public FanNewsFamousAncestorChild setAncestorName(String ancestorName) {
        this.ancestorName = ancestorName;
        return this;
    }

    public String getAncestorSummary() {
        return ancestorSummary;
    }

    public FanNewsFamousAncestorChild setAncestorSummary(String ancestorSummary) {
        this.ancestorSummary = ancestorSummary;
        return this;
    }

    public String getPicFileSrc() {
        return picFileSrc;
    }

    public FanNewsFamousAncestorChild setPicFileSrc(String picFileSrc) {
        this.picFileSrc = picFileSrc;
        return this;
    }

    public String getPicFileName() {
        return picFileName;
    }

    public FanNewsFamousAncestorChild setPicFileName(String picFileName) {
        this.picFileName = picFileName;
        return this;
    }

    public Integer getVisitNum() {
        return visitNum;
    }

    public FanNewsFamousAncestorChild setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public FanNewsFamousAncestorChild setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanNewsFamousAncestorChild setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanNewsFamousAncestorChild setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanNewsFamousAncestorChild setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanNewsFamousAncestorChild setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanNewsFamousAncestorChild{" +
                ", id=" + id +
                ", fanNewsFamousAncestorId=" + fanNewsFamousAncestorId +
                ", ancestorTitle=" + ancestorTitle +
                ", ancestorName=" + ancestorName +
                ", ancestorSummary=" + ancestorSummary +
                ", picFileSrc=" + picFileSrc +
                ", picFileName=" + picFileName +
                ", visitNum=" + visitNum +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
