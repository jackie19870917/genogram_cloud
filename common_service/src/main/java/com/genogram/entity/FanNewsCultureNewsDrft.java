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
 * 联谊会-家族文化文章草稿表
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@TableName("fan_news_culture_news_drft")
public class FanNewsCultureNewsDrft extends Model<FanNewsCultureNewsDrft> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 家族文化-文章id
     */
    @TableField("fan_news_culture_news_id")
    private Integer fanNewsCultureNewsId;
    /**
     * 标题
     */
    @TableField("news_title")
    private String newsTitle;
    /**
     * 内容
     */
    @TableField("news_text")
    private String newsText;
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

    public FanNewsCultureNewsDrft setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFanNewsCultureNewsId() {
        return fanNewsCultureNewsId;
    }

    public FanNewsCultureNewsDrft setFanNewsCultureNewsId(Integer fanNewsCultureNewsId) {
        this.fanNewsCultureNewsId = fanNewsCultureNewsId;
        return this;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public FanNewsCultureNewsDrft setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
        return this;
    }

    public String getNewsText() {
        return newsText;
    }

    public FanNewsCultureNewsDrft setNewsText(String newsText) {
        this.newsText = newsText;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanNewsCultureNewsDrft setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanNewsCultureNewsDrft setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanNewsCultureNewsDrft setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanNewsCultureNewsDrft setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanNewsCultureNewsDrft{" +
        ", id=" + id +
        ", fanNewsCultureNewsId=" + fanNewsCultureNewsId +
        ", newsTitle=" + newsTitle +
        ", newsText=" + newsText +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
