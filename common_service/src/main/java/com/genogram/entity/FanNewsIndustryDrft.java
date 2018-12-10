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
 * 联谊会-家族产业草稿
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@TableName("fan_news_industry_drft")
public class FanNewsIndustryDrft extends Model<FanNewsIndustryDrft> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 家族产业文章id
     */
    @TableField("fan_news_industry_id")
    private Integer fanNewsIndustryId;
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
     * 家族产业具体地址
     */
    @TableField("industry_location")
    private String industryLocation;
    /**
     * 种类(1:家族产业;2:个人产业)
     */
    private String type;
    /**
     * 查看数
     */
    @TableField("visit_num")
    private Integer visitNum;
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

    public FanNewsIndustryDrft setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFanNewsIndustryId() {
        return fanNewsIndustryId;
    }

    public FanNewsIndustryDrft setFanNewsIndustryId(Integer fanNewsIndustryId) {
        this.fanNewsIndustryId = fanNewsIndustryId;
        return this;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public FanNewsIndustryDrft setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
        return this;
    }

    public String getNewsText() {
        return newsText;
    }

    public FanNewsIndustryDrft setNewsText(String newsText) {
        this.newsText = newsText;
        return this;
    }

    public String getIndustryLocation() {
        return industryLocation;
    }

    public FanNewsIndustryDrft setIndustryLocation(String industryLocation) {
        this.industryLocation = industryLocation;
        return this;
    }

    public String getType() {
        return type;
    }

    public FanNewsIndustryDrft setType(String type) {
        this.type = type;
        return this;
    }

    public Integer getVisitNum() {
        return visitNum;
    }

    public FanNewsIndustryDrft setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanNewsIndustryDrft setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanNewsIndustryDrft setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanNewsIndustryDrft setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanNewsIndustryDrft setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanNewsIndustryDrft{" +
                ", id=" + id +
                ", fanNewsIndustryId=" + fanNewsIndustryId +
                ", newsTitle=" + newsTitle +
                ", newsText=" + newsText +
                ", industryLocation=" + industryLocation +
                ", type=" + type +
                ", visitNum=" + visitNum +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
