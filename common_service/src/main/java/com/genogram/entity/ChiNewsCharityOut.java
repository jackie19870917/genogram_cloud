package com.genogram.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 省级-家族慈善财务收入支出(文章)表
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@TableName("chi_news_charity_out")
public class ChiNewsCharityOut extends Model<ChiNewsCharityOut> {

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
     * 财务支出金额
     */
    private BigDecimal amount;
    /**
     * (财务支出)用途
     */
    @TableField("use_for")
    private String useFor;
    /**
     * 标题(财务支出文章)
     */
    @TableField("news_title")
    private String newsTitle;
    /**
     * 内容
     */
    @TableField("news_text")
    private String newsText;
    /**
     * 文章类别(1.财政支出;2.财政收入)
     */
    @TableField("news_type")
    private Integer newsType;
    /**
     * 查看数
     */
    @TableField("visit_num")
    private Integer visitNum;
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

    public ChiNewsCharityOut setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public ChiNewsCharityOut setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ChiNewsCharityOut setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getUseFor() {
        return useFor;
    }

    public ChiNewsCharityOut setUseFor(String useFor) {
        this.useFor = useFor;
        return this;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public ChiNewsCharityOut setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
        return this;
    }

    public String getNewsText() {
        return newsText;
    }

    public ChiNewsCharityOut setNewsText(String newsText) {
        this.newsText = newsText;
        return this;
    }

    public Integer getNewsType() {
        return newsType;
    }

    public ChiNewsCharityOut setNewsType(Integer newsType) {
        this.newsType = newsType;
        return this;
    }

    public Integer getVisitNum() {
        return visitNum;
    }

    public ChiNewsCharityOut setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ChiNewsCharityOut setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public ChiNewsCharityOut setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChiNewsCharityOut setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ChiNewsCharityOut setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ChiNewsCharityOut setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ChiNewsCharityOut setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ChiNewsCharityOut{" +
                ", id=" + id +
                ", showId=" + showId +
                ", amount=" + amount +
                ", useFor=" + useFor +
                ", newsTitle=" + newsTitle +
                ", newsText=" + newsText +
                ", newsType=" + newsType +
                ", visitNum=" + visitNum +
                ", status=" + status +
                ", commentCount=" + commentCount +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
