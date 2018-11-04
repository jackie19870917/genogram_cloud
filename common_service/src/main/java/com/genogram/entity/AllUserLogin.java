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
 * @since 2018-11-05
 */
@TableName("all_user_login")
public class AllUserLogin extends Model<AllUserLogin> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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
     * 手机号
     */
    @TableField("mobile_phone")
    private String mobilePhone;
    /**
     * 省份
     */
    @TableField("province_code")
    private Integer provinceCode;
    /**
     * 市
     */
    @TableField("city_code")
    private Integer cityCode;
    /**
     * 县
     */
    @TableField("county_code")
    private Integer countyCode;
    /**
     * 密码
     */
    private String password;
    /**
     * 国家
     */
    private Integer country;
    /**
     * 状态(0:删除;1:正常;2:异常)
     */
    private Integer status;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public AllUserLogin setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public Integer getProvinceCode() {
        return provinceCode;
    }

    public AllUserLogin setProvinceCode(Integer provinceCode) {
        this.provinceCode = provinceCode;
        return this;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public AllUserLogin setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public Integer getCountyCode() {
        return countyCode;
    }

    public AllUserLogin setCountyCode(Integer countyCode) {
        this.countyCode = countyCode;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AllUserLogin setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getCountry() {
        return country;
    }

    public AllUserLogin setCountry(Integer country) {
        this.country = country;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllUserLogin setStatus(Integer status) {
        this.status = status;
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
        ", userId=" + userId +
        ", userName=" + userName +
        ", mobilePhone=" + mobilePhone +
        ", provinceCode=" + provinceCode +
        ", cityCode=" + cityCode +
        ", countyCode=" + countyCode +
        ", password=" + password +
        ", country=" + country +
        ", status=" + status +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
