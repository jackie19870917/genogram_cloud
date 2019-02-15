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
 * 省级网站表
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@TableName("chi_sys_site")
public class ChiSysSite extends Model<ChiSysSite> {

    private static final long serialVersionUID = 1L;

    /**
     * 网站ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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

    public ChiSysSite setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFamilyCode() {
        return familyCode;
    }

    public ChiSysSite setFamilyCode(Integer familyCode) {
        this.familyCode = familyCode;
        return this;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public ChiSysSite setRegionCode(String regionCode) {
        this.regionCode = regionCode;
        return this;
    }

    public String getName() {
        return name;
    }

    public ChiSysSite setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAdmin() {
        return admin;
    }

    public ChiSysSite setAdmin(Integer admin) {
        this.admin = admin;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ChiSysSite setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getParent() {
        return parent;
    }

    public ChiSysSite setParent(Integer parent) {
        this.parent = parent;
        return this;
    }

    public String getOneUrl() {
        return oneUrl;
    }

    public ChiSysSite setOneUrl(String oneUrl) {
        this.oneUrl = oneUrl;
        return this;
    }

    public String getTwoUrl() {
        return twoUrl;
    }

    public ChiSysSite setTwoUrl(String twoUrl) {
        this.twoUrl = twoUrl;
        return this;
    }

    public String getAdminEnable() {
        return adminEnable;
    }

    public ChiSysSite setAdminEnable(String adminEnable) {
        this.adminEnable = adminEnable;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChiSysSite setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ChiSysSite setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ChiSysSite setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ChiSysSite setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ChiSysSite{" +
        ", id=" + id +
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
