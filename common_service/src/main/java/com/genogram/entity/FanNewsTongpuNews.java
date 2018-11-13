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
 * 统谱资料-文章
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@TableName("fan_news_tongpu_news")
public class FanNewsTongpuNews extends Model<FanNewsTongpuNews> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    @TableField("show_id")
    private Integer showId;
    /**
     * 文章标题
     */
    @TableField("news_title")
    private String newsTitle;
    /**
     * 文章内容
     */
    @TableField("news_text")
    private String newsText;
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

    public FanNewsTongpuNews setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public FanNewsTongpuNews setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public FanNewsTongpuNews setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
        return this;
    }

    public String getNewsText() {
        return newsText;
    }

    public FanNewsTongpuNews setNewsText(String newsText) {
        this.newsText = newsText;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public FanNewsTongpuNews setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanNewsTongpuNews setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanNewsTongpuNews setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanNewsTongpuNews setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanNewsTongpuNews setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanNewsTongpuNews{" +
        ", id=" + id +
        ", showId=" + showId +
        ", newsTitle=" + newsTitle +
        ", newsText=" + newsText +
        ", status=" + status +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
