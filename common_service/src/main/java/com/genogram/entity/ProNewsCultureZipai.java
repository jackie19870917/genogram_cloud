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
 * 省级-家族文化-字派表
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@TableName("pro_news_culture_zipai")
public class ProNewsCultureZipai extends Model<ProNewsCultureZipai> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键字派ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    @TableField("show_id")
    private Integer showId;
    /**
     * 字派具体地域
     */
    @TableField("ziapi_location")
    private String ziapiLocation;
    /**
     * 祖先名
     */
    @TableField("ancestors_name")
    private String ancestorsName;
    /**
     * 字派数组:数字和字的组合
     */
    @TableField("zipai_txt")
    private String zipaiTxt;
    /**
     * 访问数
     */
    @TableField("visit_num")
    private Integer visitNum;
    /**
     * 状态(0:删除;1:已发布;2:草稿3:不显示)
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

    public ProNewsCultureZipai setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public ProNewsCultureZipai setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public String getZiapiLocation() {
        return ziapiLocation;
    }

    public ProNewsCultureZipai setZiapiLocation(String ziapiLocation) {
        this.ziapiLocation = ziapiLocation;
        return this;
    }

    public String getAncestorsName() {
        return ancestorsName;
    }

    public ProNewsCultureZipai setAncestorsName(String ancestorsName) {
        this.ancestorsName = ancestorsName;
        return this;
    }

    public String getZipaiTxt() {
        return zipaiTxt;
    }

    public ProNewsCultureZipai setZipaiTxt(String zipaiTxt) {
        this.zipaiTxt = zipaiTxt;
        return this;
    }

    public Integer getVisitNum() {
        return visitNum;
    }

    public ProNewsCultureZipai setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ProNewsCultureZipai setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProNewsCultureZipai setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ProNewsCultureZipai setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ProNewsCultureZipai setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ProNewsCultureZipai setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProNewsCultureZipai{" +
                ", id=" + id +
                ", showId=" + showId +
                ", ziapiLocation=" + ziapiLocation +
                ", ancestorsName=" + ancestorsName +
                ", zipaiTxt=" + zipaiTxt +
                ", visitNum=" + visitNum +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
