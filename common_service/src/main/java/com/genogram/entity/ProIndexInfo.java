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
 * 省级网站:图腾;简介;宣言
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@TableName("pro_index_info")
public class ProIndexInfo extends Model<ProIndexInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 网站id(fan_sys_site_id)
     */
    @TableField("site_id")
    private Integer siteId;
    /**
     * 图腾图片地址
     */
    @TableField("totem_pic_src")
    private String totemPicSrc;
    /**
     * 宣言
     */
    private String title;
    /**
     * 简介
     */
    private String description;
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

    public ProIndexInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public ProIndexInfo setSiteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public String getTotemPicSrc() {
        return totemPicSrc;
    }

    public ProIndexInfo setTotemPicSrc(String totemPicSrc) {
        this.totemPicSrc = totemPicSrc;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProIndexInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProIndexInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProIndexInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ProIndexInfo setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ProIndexInfo setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ProIndexInfo setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProIndexInfo{" +
        ", id=" + id +
        ", siteId=" + siteId +
        ", totemPicSrc=" + totemPicSrc +
        ", title=" + title +
        ", description=" + description +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
