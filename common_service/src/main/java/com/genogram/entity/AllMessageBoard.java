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
 * @author wangwei
 * @since 2018-11-23
 */
@TableName("all_message_board")
public class AllMessageBoard extends Model<AllMessageBoard> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名字
     */
    private String name;
    /**
     * 电话
     */
    private Integer phone;
    /**
     * 内容
     */
    private String content;
    /**
     * 1,联谊会2,省级3,全国
     */
    @TableField("source_type")
    private Integer sourceType;
    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 修改人
     */
    @TableField("update_user")
    private String updateUser;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public AllMessageBoard setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AllMessageBoard setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPhone() {
        return phone;
    }

    public AllMessageBoard setPhone(Integer phone) {
        this.phone = phone;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AllMessageBoard setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public AllMessageBoard setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
        return this;
    }

    public String getCreateUser() {
        return createUser;
    }

    public AllMessageBoard setCreateUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public AllMessageBoard setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AllMessageBoard setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AllMessageBoard setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllMessageBoard{" +
        ", id=" + id +
        ", name=" + name +
        ", phone=" + phone +
        ", content=" + content +
        ", sourceType=" + sourceType +
        ", createUser=" + createUser +
        ", updateUser=" + updateUser +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
