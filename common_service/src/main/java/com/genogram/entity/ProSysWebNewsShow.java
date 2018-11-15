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
 * 省级网站文章挂靠位置表
 * </p>
 *
 * @author wangwei
 * @since 2018-11-15
 */
@TableName("pro_sys_web_news_show")
public class ProSysWebNewsShow extends Model<ProSysWebNewsShow> {

    private static final long serialVersionUID = 1L;

    /**
     * 显示位置id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 文章show_id(site_id 和 menu_id 组合)
     */
    @TableField("show_id")
    private Integer showId;
    /**
     * 联谊网站id
     */
    @TableField("pro_sys_site_id")
    private Integer proSysSiteId;
    /**
     * 菜单id
     */
    @TableField("pro_sys_web_menu_id")
    private Integer proSysWebMenuId;
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

    public ProSysWebNewsShow setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public ProSysWebNewsShow setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public Integer getProSysSiteId() {
        return proSysSiteId;
    }

    public ProSysWebNewsShow setProSysSiteId(Integer proSysSiteId) {
        this.proSysSiteId = proSysSiteId;
        return this;
    }

    public Integer getProSysWebMenuId() {
        return proSysWebMenuId;
    }

    public ProSysWebNewsShow setProSysWebMenuId(Integer proSysWebMenuId) {
        this.proSysWebMenuId = proSysWebMenuId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProSysWebNewsShow setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ProSysWebNewsShow setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ProSysWebNewsShow setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ProSysWebNewsShow setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProSysWebNewsShow{" +
        ", id=" + id +
        ", showId=" + showId +
        ", proSysSiteId=" + proSysSiteId +
        ", proSysWebMenuId=" + proSysWebMenuId +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
