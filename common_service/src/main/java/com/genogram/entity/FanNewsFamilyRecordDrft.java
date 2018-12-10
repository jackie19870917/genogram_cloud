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
 * 联谊会-记录家族-家族动态,家族通告草稿表
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@TableName("fan_news_family_record_drft")
public class FanNewsFamilyRecordDrft extends Model<FanNewsFamilyRecordDrft> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 显示位置id
     */
    @TableField("fan_news_family_record_id")
    private Integer fanNewsFamilyRecordId;
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

    public FanNewsFamilyRecordDrft setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFanNewsFamilyRecordId() {
        return fanNewsFamilyRecordId;
    }

    public FanNewsFamilyRecordDrft setFanNewsFamilyRecordId(Integer fanNewsFamilyRecordId) {
        this.fanNewsFamilyRecordId = fanNewsFamilyRecordId;
        return this;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public FanNewsFamilyRecordDrft setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
        return this;
    }

    public String getNewsText() {
        return newsText;
    }

    public FanNewsFamilyRecordDrft setNewsText(String newsText) {
        this.newsText = newsText;
        return this;
    }

    public Integer getVisitNum() {
        return visitNum;
    }

    public FanNewsFamilyRecordDrft setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanNewsFamilyRecordDrft setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanNewsFamilyRecordDrft setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanNewsFamilyRecordDrft setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanNewsFamilyRecordDrft setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanNewsFamilyRecordDrft{" +
                ", id=" + id +
                ", fanNewsFamilyRecordId=" + fanNewsFamilyRecordId +
                ", newsTitle=" + newsTitle +
                ", newsText=" + newsText +
                ", visitNum=" + visitNum +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
