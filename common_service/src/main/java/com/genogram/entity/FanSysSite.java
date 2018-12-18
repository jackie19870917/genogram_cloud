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
 * 开通联谊会表
 * </p>
 *
 * @author wangwei
 * @since 2018-12-18
 */
@TableName("fan_sys_site")
public class FanSysSite extends Model<FanSysSite> {

    private static final long serialVersionUID = 1L;

    /**
     * 网站ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 访问编号
     */
    @TableField("fan_url_code")
    private String fanUrlCode;
    /**
     * sys_family:id
     */
    @TableField("family_code")
    private Integer familyCode;
    /**
     * sys_region:code
     */
    @TableField("region_code")
    private String regionCode;
    /**
     * 网站名称
     */
    private String name;
    /**
     * 管理员id
     */
    private Integer admin;
    /**
     * 状态(0:删除1:已开通;2:未开通;3:待开通)
     */
    private Integer status;
    /**
     * 父网站id
     */
    private Integer parent;
    /**
     * 一级域名
     */
    @TableField("one_url")
    private String oneUrl;
    /**
     * 2域名
     */
    @TableField("two_url")
    private String twoUrl;
    /**
     * 管理员是否可以操作:1:可用;0禁用;
     */
    @TableField("admin_enable")
    private String adminEnable;
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

    public FanSysSite setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getFanUrlCode() {
        return fanUrlCode;
    }

    public FanSysSite setFanUrlCode(String fanUrlCode) {
        this.fanUrlCode = fanUrlCode;
        return this;
    }

    public Integer getFamilyCode() {
        return familyCode;
    }

    public FanSysSite setFamilyCode(Integer familyCode) {
        this.familyCode = familyCode;
        return this;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public FanSysSite setRegionCode(String regionCode) {
        this.regionCode = regionCode;
        return this;
    }

    public String getName() {
        return name;
    }

    public FanSysSite setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAdmin() {
        return admin;
    }

    public FanSysSite setAdmin(Integer admin) {
        this.admin = admin;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public FanSysSite setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getParent() {
        return parent;
    }

    public FanSysSite setParent(Integer parent) {
        this.parent = parent;
        return this;
    }

    public String getOneUrl() {
        return oneUrl;
    }

    public FanSysSite setOneUrl(String oneUrl) {
        this.oneUrl = oneUrl;
        return this;
    }

    public String getTwoUrl() {
        return twoUrl;
    }

    public FanSysSite setTwoUrl(String twoUrl) {
        this.twoUrl = twoUrl;
        return this;
    }

    public String getAdminEnable() {
        return adminEnable;
    }

    public FanSysSite setAdminEnable(String adminEnable) {
        this.adminEnable = adminEnable;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanSysSite setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanSysSite setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanSysSite setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanSysSite setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanSysSite{" +
                ", id=" + id +
                ", fanUrlCode=" + fanUrlCode +
                ", familyCode=" + familyCode +
                ", regionCode=" + regionCode +
                ", name=" + name +
                ", admin=" + admin +
                ", status=" + status +
                ", parent=" + parent +
                ", oneUrl=" + oneUrl +
                ", twoUrl=" + twoUrl +
                ", adminEnable=" + adminEnable +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
