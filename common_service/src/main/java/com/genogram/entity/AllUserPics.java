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
 * 
 * </p>
 *
 * @author yizx
 * @since 2018-12-04
 */
@TableName("all_user_pics")
public class AllUserPics extends Model<AllUserPics> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 个人id
     */
    @TableField("usr_id")
    private Integer usrId;
    /**
     * 图片url
     */
    @TableField("pic_url")
    private String picUrl;
    /**
     * 状态（1正常 0 删除）
     */
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人id
     */
    @TableField("create_user")
    private Integer createUser;


    public Integer getId() {
        return id;
    }

    public AllUserPics setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUsrId() {
        return usrId;
    }

    public AllUserPics setUsrId(Integer usrId) {
        this.usrId = usrId;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public AllUserPics setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllUserPics setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AllUserPics setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public AllUserPics setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllUserPics{" +
        ", id=" + id +
        ", usrId=" + usrId +
        ", picUrl=" + picUrl +
        ", status=" + status +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        "}";
    }
}
