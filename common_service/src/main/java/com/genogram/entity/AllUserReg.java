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
 * 用户注册表
 * </p>
 *
 * @author wangwei
 * @since 2018-11-07
 */
@TableName("all_user_reg")
public class AllUserReg extends Model<AllUserReg> {

    private static final long serialVersionUID = 1L;

    /**
     * 注册ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;
    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;
    /**
     * 外文名
     */
    @TableField("english_name")
    private String englishName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 国籍
     */
    private String nation;
    /**
     * 出生地
     */
    private String birthplace;
    /**
     * 职业
     */
    private String job;
    /**
     * 现居
     */
    @TableField("present_address")
    private String presentAddress;
    /**
     * 故居
     */
    @TableField("old_address")
    private String oldAddress;
    /**
     * 现居别称
     */
    private String alias;
    /**
     * 姓氏
     */
    private String surname;
    /**
     * 地区（国家country，省province，市city，县（区）county）
     */
    private String region;
    /**
     * 简介
     */
    private String summary;
    /**
     * 粉丝
     */
    private Integer fans;
    /**
     * 诚信值
     */
    private Integer honesty;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 状态(1-是,2-否)
     */
    private Integer status;
    /**
     * 个人头像地址
     */
    @TableField("pic_src")
    private String picSrc;
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

    public AllUserReg setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public AllUserReg setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public AllUserReg setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getEnglishName() {
        return englishName;
    }

    public AllUserReg setEnglishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public AllUserReg setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getNation() {
        return nation;
    }

    public AllUserReg setNation(String nation) {
        this.nation = nation;
        return this;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public AllUserReg setBirthplace(String birthplace) {
        this.birthplace = birthplace;
        return this;
    }

    public String getJob() {
        return job;
    }

    public AllUserReg setJob(String job) {
        this.job = job;
        return this;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public AllUserReg setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
        return this;
    }

    public String getOldAddress() {
        return oldAddress;
    }

    public AllUserReg setOldAddress(String oldAddress) {
        this.oldAddress = oldAddress;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public AllUserReg setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public AllUserReg setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public AllUserReg setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public AllUserReg setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public Integer getFans() {
        return fans;
    }

    public AllUserReg setFans(Integer fans) {
        this.fans = fans;
        return this;
    }

    public Integer getHonesty() {
        return honesty;
    }

    public AllUserReg setHonesty(Integer honesty) {
        this.honesty = honesty;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public AllUserReg setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllUserReg setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getPicSrc() {
        return picSrc;
    }

    public AllUserReg setPicSrc(String picSrc) {
        this.picSrc = picSrc;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AllUserReg setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public AllUserReg setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AllUserReg setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public AllUserReg setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllUserReg{" +
        ", id=" + id +
        ", userId=" + userId +
        ", realName=" + realName +
        ", englishName=" + englishName +
        ", phone=" + phone +
        ", nation=" + nation +
        ", birthplace=" + birthplace +
        ", job=" + job +
        ", presentAddress=" + presentAddress +
        ", oldAddress=" + oldAddress +
        ", alias=" + alias +
        ", surname=" + surname +
        ", region=" + region +
        ", summary=" + summary +
        ", fans=" + fans +
        ", honesty=" + honesty +
        ", creator=" + creator +
        ", status=" + status +
        ", picSrc=" + picSrc +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
