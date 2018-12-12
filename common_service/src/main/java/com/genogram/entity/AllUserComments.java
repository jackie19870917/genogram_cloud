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
 * 评论表
 * </p>
 *
 * @author wangwei
 * @since 2018-12-07
 */
@TableName("all_user_comments")
public class AllUserComments extends Model<AllUserComments> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 主题id
     */
    @TableField("topic_id")
    private Integer topicId;
    /**
     * service名（通过主题id和service名来确定对应评论记录）
     */
    @TableField("entity_name")
    private String entityName;
    /**
     * 评论人
     */
    @TableField("form_user_id")
    private Integer formUserId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 点赞数
     */
    @TableField("praise_num")
    private Integer praiseNum;
    /**
     * 状态(1-正常,0-删除)
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

    public AllUserComments setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public AllUserComments setTopicId(Integer topicId) {
        this.topicId = topicId;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public AllUserComments setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public Integer getFormUserId() {
        return formUserId;
    }

    public AllUserComments setFormUserId(Integer formUserId) {
        this.formUserId = formUserId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AllUserComments setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public AllUserComments setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllUserComments setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AllUserComments setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public AllUserComments setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AllUserComments setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public AllUserComments setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllUserComments{" +
        ", id=" + id +
        ", topicId=" + topicId +
        ", entityName=" + entityName +
        ", formUserId=" + formUserId +
        ", content=" + content +
        ", praiseNum=" + praiseNum +
        ", status=" + status +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
