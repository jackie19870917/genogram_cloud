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
 *
 * </p>
 *
 * @author wangwei
 * @since 2019-01-22
 */
@TableName("pu_base_info")
public class PuBaseInfo extends Model<PuBaseInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 族谱名称
     */
    private String name;
    /**
     * 姓氏
     */
    @TableField("sur_name")
    private String surName;
    /**
     * 地区
     */
    private Integer region;
    /**
     * 详细地址
     */
    @TableField("deatail_adr")
    private String deatailAdr;
    /**
     * 族谱来源
     */
    @TableField("is_from")
    private String isFrom;
    /**
     * 族谱描述
     */
    private String describe;
    /**
     * 封面图片
     */
    @TableField("face_url")
    private String faceUrl;
    /**
     * 状态(0:删除;1:已完成;2:完善中3:不显示)
     */
    private Integer status;
    /**
     * 谱文id
     */
    @TableField("content_id")
    private Integer contentId;
    /**
     * 根人物Id
     */
    @TableField("pep_id")
    private String pepId;
    /**
     * 谱文路径
     */
    @TableField("file_url")
    private String fileUrl;
    @TableField("create_user")
    private Integer createUser;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_user")
    private Integer updateUser;
    @TableField("update_time")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public PuBaseInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PuBaseInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurName() {
        return surName;
    }

    public PuBaseInfo setSurName(String surName) {
        this.surName = surName;
        return this;
    }

    public Integer getRegion() {
        return region;
    }

    public PuBaseInfo setRegion(Integer region) {
        this.region = region;
        return this;
    }

    public String getDeatailAdr() {
        return deatailAdr;
    }

    public PuBaseInfo setDeatailAdr(String deatailAdr) {
        this.deatailAdr = deatailAdr;
        return this;
    }

    public String getIsFrom() {
        return isFrom;
    }

    public PuBaseInfo setIsFrom(String isFrom) {
        this.isFrom = isFrom;
        return this;
    }

    public String getDescribe() {
        return describe;
    }

    public PuBaseInfo setDescribe(String describe) {
        this.describe = describe;
        return this;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public PuBaseInfo setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public PuBaseInfo setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getContentId() {
        return contentId;
    }

    public PuBaseInfo setContentId(Integer contentId) {
        this.contentId = contentId;
        return this;
    }

    public String getPepId() {
        return pepId;
    }

    public PuBaseInfo setPepId(String pepId) {
        this.pepId = pepId;
        return this;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public PuBaseInfo setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public PuBaseInfo setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PuBaseInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public PuBaseInfo setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public PuBaseInfo setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PuBaseInfo{" +
                ", id=" + id +
                ", name=" + name +
                ", surName=" + surName +
                ", region=" + region +
                ", deatailAdr=" + deatailAdr +
                ", isFrom=" + isFrom +
                ", describe=" + describe +
                ", faceUrl=" + faceUrl +
                ", status=" + status +
                ", contentId=" + contentId +
                ", pepId=" + pepId +
                ", fileUrl=" + fileUrl +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", updateUser=" + updateUser +
                ", updateTime=" + updateTime +
                "}";
    }
}
