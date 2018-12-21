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
 * 用户登录表
 * </p>
 *
 * @author wangwei
 * @since 2018-12-21
 */
@TableName("all_user_login")
public class AllUserLogin extends Model<AllUserLogin> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * openId(微信公众号)
     */
    private String openId;
    /**
     * 用户主键，user开头，日期年月日小时分秒+随机数组成
     */
    @TableField("user_id")
    private String userId;
    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;
    /**
     * 别名
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 手机号
     */
    @TableField("mobile_phone")
    private String mobilePhone;
    /**
     * 头像url
     */
    @TableField("pic_src")
    private String picSrc;
    /**
     * 密码
     */
    private String password;
    /**
     * 状态(0:删除;1:正常;2:异常)
     */
    private Integer status;
    /**
     * 网站id(省或者县的网站id)
     */
    @TableField("site_id")
    private Integer siteId;
    /**
     * 角色(0.不是管理员,1.县级管理员,2省级管理员,3.全国管理员,4县级副管理员,5省级副管理员,6全国副管理员,9.超级管理员)
     */
    private Integer role;
    /**
     * sys_family:id
     */
    @TableField("family_code")
    private Integer familyCode;
    @TableField("region_code")
    private Integer regionCode;
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

    public AllUserLogin setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getOpenId() {
        return openId;
    }

    public AllUserLogin setOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public AllUserLogin setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public AllUserLogin setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public AllUserLogin setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public AllUserLogin setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public AllUserLogin setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public String getPicSrc() {
        return picSrc;
    }

    public AllUserLogin setPicSrc(String picSrc) {
        this.picSrc = picSrc;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AllUserLogin setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllUserLogin setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public AllUserLogin setSiteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public Integer getRole() {
        return role;
    }

    public AllUserLogin setRole(Integer role) {
        this.role = role;
        return this;
    }

    public Integer getFamilyCode() {
        return familyCode;
    }

    public AllUserLogin setFamilyCode(Integer familyCode) {
        this.familyCode = familyCode;
        return this;
    }

    public Integer getRegionCode() {
        return regionCode;
    }

    public AllUserLogin setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AllUserLogin setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public AllUserLogin setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AllUserLogin setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public AllUserLogin setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllUserLogin{" +
        ", id=" + id +
        ", openId=" + openId +
        ", userId=" + userId +
        ", userName=" + userName +
        ", realName=" + realName +
        ", nickName=" + nickName +
        ", mobilePhone=" + mobilePhone +
        ", picSrc=" + picSrc +
        ", password=" + password +
        ", status=" + status +
        ", siteId=" + siteId +
        ", role=" + role +
        ", familyCode=" + familyCode +
        ", regionCode=" + regionCode +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
