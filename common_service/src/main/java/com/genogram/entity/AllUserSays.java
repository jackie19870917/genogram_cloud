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
 * @since 2018-12-04
 */
@TableName("all_user_says")
public class AllUserSays extends Model<AllUserSays> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 个人id
     */
    @TableField("usr_id")
    private Integer usrId;
    /**
     * 说说内容
     */
    private String content;
    /**
     * 状态（1 正常 0 删除）
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

    public AllUserSays setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUsrId() {
        return usrId;
    }

    public AllUserSays setUsrId(Integer usrId) {
        this.usrId = usrId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AllUserSays setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllUserSays setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AllUserSays setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public AllUserSays setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllUserSays{" +
        ", id=" + id +
        ", usrId=" + usrId +
        ", content=" + content +
        ", status=" + status +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        "}";
    }
}
