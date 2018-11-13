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
 * 联谊会文章推荐表
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@TableName("fan_sys_recommend")
public class FanSysRecommend extends Model<FanSysRecommend> {

    private static final long serialVersionUID = 1L;

    /**
     * 推荐主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 显示位置id
     */
    @TableField("show_id")
    private Integer showId;
    /**
     * 文章id
     */
    @TableField("news_id")
    private Integer newsId;
    /**
     * 是否省显示(0:否;1是)
     */
    private Integer isProvince;
    /**
     * 是否全国显示(0:否;1是)
     */
    private Integer isCountry;
    /**
     * 状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
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

    public FanSysRecommend setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public FanSysRecommend setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public FanSysRecommend setNewsId(Integer newsId) {
        this.newsId = newsId;
        return this;
    }

    public Integer getIsProvince() {
        return isProvince;
    }

    public FanSysRecommend setIsProvince(Integer isProvince) {
        this.isProvince = isProvince;
        return this;
    }

    public Integer getIsCountry() {
        return isCountry;
    }

    public FanSysRecommend setIsCountry(Integer isCountry) {
        this.isCountry = isCountry;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public FanSysRecommend setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanSysRecommend setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanSysRecommend setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanSysRecommend setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanSysRecommend setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanSysRecommend{" +
        ", id=" + id +
        ", showId=" + showId +
        ", newsId=" + newsId +
        ", isProvince=" + isProvince +
        ", isCountry=" + isCountry +
        ", status=" + status +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
