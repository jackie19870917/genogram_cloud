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
 * 联谊会-祖先分支
 * </p>
 *
 * @author wangwei
 * @since 2018-12-20
 */
@TableName("fan_news_famous_ancestor")
public class FanNewsFamousAncestor extends Model<FanNewsFamousAncestor> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    @TableField("show_id")
    private Integer showId;
    /**
     * 父id  0代表主数据
     */
    @TableField("parent_id")
    private Integer parentId;
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
     * 状态(0:删除;1:发布;3:不显示)
     */
    private Integer status;
    /**
     * 字派
     */
    private String zipai;
    /**
     * 郡望
     */
    private String junwang;
    /**
     * 堂号
     */
    private String tanghao;
    /**
     * 分支ID  (fan或者pro 的主键)
     */
    @TableField("branch_id")
    private Integer branchId;
    /**
     * 分类  1 代表县级2代表省级
     */
    @TableField("source_classify")
    private Integer sourceClassify;
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

    public FanNewsFamousAncestor setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public FanNewsFamousAncestor setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public FanNewsFamousAncestor setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getAncestorTitle() {
        return ancestorTitle;
    }

    public FanNewsFamousAncestor setAncestorTitle(String ancestorTitle) {
        this.ancestorTitle = ancestorTitle;
        return this;
    }

    public String getAncestorName() {
        return ancestorName;
    }

    public FanNewsFamousAncestor setAncestorName(String ancestorName) {
        this.ancestorName = ancestorName;
        return this;
    }

    public String getAncestorSummary() {
        return ancestorSummary;
    }

    public FanNewsFamousAncestor setAncestorSummary(String ancestorSummary) {
        this.ancestorSummary = ancestorSummary;
        return this;
    }

    public String getPicFileSrc() {
        return picFileSrc;
    }

    public FanNewsFamousAncestor setPicFileSrc(String picFileSrc) {
        this.picFileSrc = picFileSrc;
        return this;
    }

    public String getPicFileName() {
        return picFileName;
    }

    public FanNewsFamousAncestor setPicFileName(String picFileName) {
        this.picFileName = picFileName;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public FanNewsFamousAncestor setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getZipai() {
        return zipai;
    }

    public FanNewsFamousAncestor setZipai(String zipai) {
        this.zipai = zipai;
        return this;
    }

    public String getJunwang() {
        return junwang;
    }

    public FanNewsFamousAncestor setJunwang(String junwang) {
        this.junwang = junwang;
        return this;
    }

    public String getTanghao() {
        return tanghao;
    }

    public FanNewsFamousAncestor setTanghao(String tanghao) {
        this.tanghao = tanghao;
        return this;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public FanNewsFamousAncestor setBranchId(Integer branchId) {
        this.branchId = branchId;
        return this;
    }

    public Integer getSourceClassify() {
        return sourceClassify;
    }

    public FanNewsFamousAncestor setSourceClassify(Integer sourceClassify) {
        this.sourceClassify = sourceClassify;
        return this;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public FanNewsFamousAncestor setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanNewsFamousAncestor setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanNewsFamousAncestor setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanNewsFamousAncestor setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanNewsFamousAncestor setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanNewsFamousAncestor{" +
        ", id=" + id +
        ", showId=" + showId +
        ", parentId=" + parentId +
        ", ancestorTitle=" + ancestorTitle +
        ", ancestorName=" + ancestorName +
        ", ancestorSummary=" + ancestorSummary +
        ", picFileSrc=" + picFileSrc +
        ", picFileName=" + picFileName +
        ", status=" + status +
        ", zipai=" + zipai +
        ", junwang=" + junwang +
        ", tanghao=" + tanghao +
        ", branchId=" + branchId +
        ", sourceClassify=" + sourceClassify +
        ", commentCount=" + commentCount +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
