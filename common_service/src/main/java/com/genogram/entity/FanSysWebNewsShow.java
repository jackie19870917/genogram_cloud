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
 * 联谊网站文章挂靠位置表
 * </p>
 *
 * @author wangwei
 * @since 2018-11-15
 */
@TableName("fan_sys_web_news_show")
public class FanSysWebNewsShow extends Model<FanSysWebNewsShow> {

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
    @TableField("fan_sys_site_id")
    private Integer fanSysSiteId;
    /**
     * 菜单id
     */
    @TableField("fan_sys_web_menu_id")
    private Integer fanSysWebMenuId;
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

    public FanSysWebNewsShow setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public FanSysWebNewsShow setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public Integer getFanSysSiteId() {
        return fanSysSiteId;
    }

    public FanSysWebNewsShow setFanSysSiteId(Integer fanSysSiteId) {
        this.fanSysSiteId = fanSysSiteId;
        return this;
    }

    public Integer getFanSysWebMenuId() {
        return fanSysWebMenuId;
    }

    public FanSysWebNewsShow setFanSysWebMenuId(Integer fanSysWebMenuId) {
        this.fanSysWebMenuId = fanSysWebMenuId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanSysWebNewsShow setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanSysWebNewsShow setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanSysWebNewsShow setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanSysWebNewsShow setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanSysWebNewsShow{" +
        ", id=" + id +
        ", showId=" + showId +
        ", fanSysSiteId=" + fanSysSiteId +
        ", fanSysWebMenuId=" + fanSysWebMenuId +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
