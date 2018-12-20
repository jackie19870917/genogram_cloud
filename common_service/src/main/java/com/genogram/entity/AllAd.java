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
 * 广告
 * </p>
 *
 * @author wangwei
 * @since 2018-12-20
 */
@TableName("all_ad")
public class AllAd extends Model<AllAd> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 广告图路径
     */
    @TableField("ad_url")
    private String adUrl;
    /**
     * 外链路径
     */
    private String url;
    /**
     * 状态 (1-显示,2-不显示,0-删除)
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

    public AllAd setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public AllAd setAdUrl(String adUrl) {
        this.adUrl = adUrl;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public AllAd setUrl(String url) {
        this.url = url;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllAd setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AllAd setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public AllAd setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AllAd setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public AllAd setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllAd{" +
                ", id=" + id +
                ", adUrl=" + adUrl +
                ", url=" + url +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
