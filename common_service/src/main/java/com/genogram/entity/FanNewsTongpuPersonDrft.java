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
 * 统谱资料-人物草稿
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@TableName("fan_news_tongpu_person_drft")
public class FanNewsTongpuPersonDrft extends Model<FanNewsTongpuPersonDrft> {

    private static final long serialVersionUID = 1L;

    /**
     * 文件上传ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 文章id
     */
    @TableField("fan_news_tongpu_person_id")
    private Integer fanNewsTongpuPersonId;
    /**
     * 人物名字
     */
    @TableField("person_name")
    private String personName;
    @TableField("person_comment")
    private String personComment;
    /**
     * 状态1显示.0不显示
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

    public FanNewsTongpuPersonDrft setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFanNewsTongpuPersonId() {
        return fanNewsTongpuPersonId;
    }

    public FanNewsTongpuPersonDrft setFanNewsTongpuPersonId(Integer fanNewsTongpuPersonId) {
        this.fanNewsTongpuPersonId = fanNewsTongpuPersonId;
        return this;
    }

    public String getPersonName() {
        return personName;
    }

    public FanNewsTongpuPersonDrft setPersonName(String personName) {
        this.personName = personName;
        return this;
    }

    public String getPersonComment() {
        return personComment;
    }

    public FanNewsTongpuPersonDrft setPersonComment(String personComment) {
        this.personComment = personComment;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public FanNewsTongpuPersonDrft setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanNewsTongpuPersonDrft setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanNewsTongpuPersonDrft setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanNewsTongpuPersonDrft setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanNewsTongpuPersonDrft setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanNewsTongpuPersonDrft{" +
        ", id=" + id +
        ", fanNewsTongpuPersonId=" + fanNewsTongpuPersonId +
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
