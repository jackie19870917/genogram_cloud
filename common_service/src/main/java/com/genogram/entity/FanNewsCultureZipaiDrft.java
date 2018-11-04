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
 * 联谊会-家族文化-字派草稿表
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@TableName("fan_news_culture_zipai_drft")
public class FanNewsCultureZipaiDrft extends Model<FanNewsCultureZipaiDrft> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键字派ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 家族文化字派表id
     */
    @TableField("fan_news_culture_zipai_id")
    private Integer fanNewsCultureZipaiId;
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

    public FanNewsCultureZipaiDrft setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFanNewsCultureZipaiId() {
        return fanNewsCultureZipaiId;
    }

    public FanNewsCultureZipaiDrft setFanNewsCultureZipaiId(Integer fanNewsCultureZipaiId) {
        this.fanNewsCultureZipaiId = fanNewsCultureZipaiId;
        return this;
    }

    public String getZiapiLocation() {
        return ziapiLocation;
    }

    public FanNewsCultureZipaiDrft setZiapiLocation(String ziapiLocation) {
        this.ziapiLocation = ziapiLocation;
        return this;
    }

    public String getAncestorsName() {
        return ancestorsName;
    }

    public FanNewsCultureZipaiDrft setAncestorsName(String ancestorsName) {
        this.ancestorsName = ancestorsName;
        return this;
    }

    public String getZipaiTxt() {
        return zipaiTxt;
    }

    public FanNewsCultureZipaiDrft setZipaiTxt(String zipaiTxt) {
        this.zipaiTxt = zipaiTxt;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanNewsCultureZipaiDrft setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanNewsCultureZipaiDrft setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanNewsCultureZipaiDrft setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanNewsCultureZipaiDrft setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanNewsCultureZipaiDrft{" +
        ", id=" + id +
        ", fanNewsCultureZipaiId=" + fanNewsCultureZipaiId +
        ", ziapiLocation=" + ziapiLocation +
        ", ancestorsName=" + ancestorsName +
        ", zipaiTxt=" + zipaiTxt +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
