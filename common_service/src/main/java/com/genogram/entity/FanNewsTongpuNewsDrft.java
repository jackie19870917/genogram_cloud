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
 * 统谱资料-文章草稿
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@TableName("fan_news_tongpu_news_drft")
public class FanNewsTongpuNewsDrft extends Model<FanNewsTongpuNewsDrft> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 文章id
     */
    @TableField("fan_news_tongpu_news_id")
    private Integer fanNewsTongpuNewsId;
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

    public FanNewsTongpuNewsDrft setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFanNewsTongpuNewsId() {
        return fanNewsTongpuNewsId;
    }

    public FanNewsTongpuNewsDrft setFanNewsTongpuNewsId(Integer fanNewsTongpuNewsId) {
        this.fanNewsTongpuNewsId = fanNewsTongpuNewsId;
        return this;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public FanNewsTongpuNewsDrft setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
        return this;
    }

    public String getNewsText() {
        return newsText;
    }

    public FanNewsTongpuNewsDrft setNewsText(String newsText) {
        this.newsText = newsText;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanNewsTongpuNewsDrft setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanNewsTongpuNewsDrft setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanNewsTongpuNewsDrft setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanNewsTongpuNewsDrft setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanNewsTongpuNewsDrft{" +
        ", id=" + id +
        ", fanNewsTongpuNewsId=" + fanNewsTongpuNewsId +
        ", newsTitle=" + newsTitle +
        ", newsText=" + newsText +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
