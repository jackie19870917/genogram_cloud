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
 * 联谊会-家族文化-字派表
 * </p>
 *
 * @author wangwei
 * @since 2018-12-20
 */
@TableName("fan_news_culture_zipai")
public class FanNewsCultureZipai extends Model<FanNewsCultureZipai> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键字派ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    @TableField("show_id")
    private Integer showId;
    /**
     * 字派具体地域
     */
    @TableField("ziapi_location")
    private String ziapiLocation;
    /**
     * 祖先名
     */
    @TableField("ancestors_name")
    private String ancestorsName;
    /**
     * 字派数组:数字和字的组合
     */
    @TableField("zipai_txt")
    private String zipaiTxt;
    /**
     * 访问数
     */
    @TableField("visit_num")
    private Integer visitNum;
    /**
     * 点赞数
     */
    @TableField("praise_num")
    private Integer praiseNum;
    /**
     * 状态(0:删除;1:已发布;2:草稿3:不显示)
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

    public FanNewsCultureZipai setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public FanNewsCultureZipai setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public String getZiapiLocation() {
        return ziapiLocation;
    }

    public FanNewsCultureZipai setZiapiLocation(String ziapiLocation) {
        this.ziapiLocation = ziapiLocation;
        return this;
    }

    public String getAncestorsName() {
        return ancestorsName;
    }

    public FanNewsCultureZipai setAncestorsName(String ancestorsName) {
        this.ancestorsName = ancestorsName;
        return this;
    }

    public String getZipaiTxt() {
        return zipaiTxt;
    }

    public FanNewsCultureZipai setZipaiTxt(String zipaiTxt) {
        this.zipaiTxt = zipaiTxt;
        return this;
    }

    public Integer getVisitNum() {
        return visitNum;
    }

    public FanNewsCultureZipai setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
        return this;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public FanNewsCultureZipai setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public FanNewsCultureZipai setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public FanNewsCultureZipai setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanNewsCultureZipai setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanNewsCultureZipai setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanNewsCultureZipai setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanNewsCultureZipai setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanNewsCultureZipai{" +
                ", id=" + id +
                ", showId=" + showId +
                ", ziapiLocation=" + ziapiLocation +
                ", ancestorsName=" + ancestorsName +
                ", zipaiTxt=" + zipaiTxt +
                ", visitNum=" + visitNum +
                ", praiseNum=" + praiseNum +
                ", status=" + status +
                ", commentCount=" + commentCount +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
