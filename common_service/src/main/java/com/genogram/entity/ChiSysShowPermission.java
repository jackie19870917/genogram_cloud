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
 * 省级权限表
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@TableName("chi_sys_show_permission")
public class ChiSysShowPermission extends Model<ChiSysShowPermission> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;
    /**
     * 关联菜单id
     */
    @TableField("pro_show_id")
    private Integer proShowId;
    /**
     * 状态（1 启用 2禁用）
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

    public ChiSysShowPermission setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public ChiSysShowPermission setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Integer getProShowId() {
        return proShowId;
    }

    public ChiSysShowPermission setProShowId(Integer proShowId) {
        this.proShowId = proShowId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ChiSysShowPermission setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChiSysShowPermission setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ChiSysShowPermission setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ChiSysShowPermission setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ChiSysShowPermission setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ChiSysShowPermission{" +
        ", id=" + id +
        ", userId=" + userId +
        ", proShowId=" + proShowId +
        ", status=" + status +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
