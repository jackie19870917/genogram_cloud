package com.genogram.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yizx
 * @since 2019-01-18
 */
@TableName("pu_pepole_info")
public class PuPepoleInfo extends Model<PuPepoleInfo> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 第几代
     */
    @TableField("generate_num")
    private Integer generateNum;
    /**
     * 姓氏
     */
    private String surname;
    /**
     * 名字
     */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 父亲id
     */
    @TableField("father_id")
    private Integer fatherId;
    /**
     * 母亲id
     */
    @TableField("mother_id")
    private Integer motherId;
    /**
     * 兄或弟id
     */
    @TableField("brothers_id")
    private String brothersId;
    /**
     * 姐或妹id
     */
    @TableField("sisters_id")
    private String sistersId;
    /**
     * 出生日期
     */
    @TableField("birth_date")
    private Date birthDate;
    /**
     * 状态
     */
    @TableField("is_live")
    private Integer isLive;
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

    public PuPepoleInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getGenerateNum() {
        return generateNum;
    }

    public PuPepoleInfo setGenerateNum(Integer generateNum) {
        this.generateNum = generateNum;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public PuPepoleInfo setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getName() {
        return name;
    }

    public PuPepoleInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public PuPepoleInfo setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public PuPepoleInfo setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
        return this;
    }

    public Integer getMotherId() {
        return motherId;
    }

    public PuPepoleInfo setMotherId(Integer motherId) {
        this.motherId = motherId;
        return this;
    }

    public String getBrothersId() {
        return brothersId;
    }

    public PuPepoleInfo setBrothersId(String brothersId) {
        this.brothersId = brothersId;
        return this;
    }

    public String getSistersId() {
        return sistersId;
    }

    public PuPepoleInfo setSistersId(String sistersId) {
        this.sistersId = sistersId;
        return this;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public PuPepoleInfo setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Integer getIsLive() {
        return isLive;
    }

    public PuPepoleInfo setIsLive(Integer isLive) {
        this.isLive = isLive;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public PuPepoleInfo setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PuPepoleInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public PuPepoleInfo setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public PuPepoleInfo setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PuPepoleInfo{" +
        ", id=" + id +
        ", generateNum=" + generateNum +
        ", surname=" + surname +
        ", name=" + name +
        ", gender=" + gender +
        ", fatherId=" + fatherId +
        ", motherId=" + motherId +
        ", brothersId=" + brothersId +
        ", sistersId=" + sistersId +
        ", birthDate=" + birthDate +
        ", isLive=" + isLive +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
