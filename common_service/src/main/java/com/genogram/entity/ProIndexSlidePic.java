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
 * 省级网站轮播图
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@TableName("pro_index_slide_pic")
public class ProIndexSlidePic extends Model<ProIndexSlidePic> {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 网站id(fan_sys_site_id)
     */
    @TableField("site_id")
    private Integer siteId;
    /**
     * 图片路径
     */
    @TableField("pic_url")
    private String picUrl;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态(0:删除;1:显示;2:不显示)
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

    public ProIndexSlidePic setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public ProIndexSlidePic setSiteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public ProIndexSlidePic setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public ProIndexSlidePic setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ProIndexSlidePic setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProIndexSlidePic setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ProIndexSlidePic setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ProIndexSlidePic setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ProIndexSlidePic setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProIndexSlidePic{" +
        ", id=" + id +
        ", siteId=" + siteId +
        ", picUrl=" + picUrl +
        ", sort=" + sort +
        ", status=" + status +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
