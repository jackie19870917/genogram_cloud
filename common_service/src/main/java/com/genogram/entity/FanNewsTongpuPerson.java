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
 * 统谱资料-人物
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@TableName("fan_news_tongpu_person")
public class FanNewsTongpuPerson extends Model<FanNewsTongpuPerson> {

    private static final long serialVersionUID = 1L;

    /**
     * 文件上传ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    @TableField("show_id")
    private Integer showId;
    /**
     * 人物名字
     */
    @TableField("person_name")
    private String personName;
    @TableField("person_comment")
    private String personComment;
    /**
     * 状态(0:删除;1:显示;2:不显示)
     */
    private Integer status;
    @TableField("create_time")
    private Date createTime;
    @TableField("create_user")
    private Integer createUser;
    @TableField("update_time")
    private Date updateTime;
    @TableField("update_user")
    private Integer updateUser;


    public Integer getId() {
        return id;
    }

    public FanNewsTongpuPerson setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getShowId() {
        return showId;
    }

    public FanNewsTongpuPerson setShowId(Integer showId) {
        this.showId = showId;
        return this;
    }

    public String getPersonName() {
        return personName;
    }

    public FanNewsTongpuPerson setPersonName(String personName) {
        this.personName = personName;
        return this;
    }

    public String getPersonComment() {
        return personComment;
    }

    public FanNewsTongpuPerson setPersonComment(String personComment) {
        this.personComment = personComment;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public FanNewsTongpuPerson setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanNewsTongpuPerson setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanNewsTongpuPerson setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanNewsTongpuPerson setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanNewsTongpuPerson setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanNewsTongpuPerson{" +
        ", id=" + id +
        ", showId=" + showId +
        ", personName=" + personName +
        ", personComment=" + personComment +
        ", status=" + status +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
