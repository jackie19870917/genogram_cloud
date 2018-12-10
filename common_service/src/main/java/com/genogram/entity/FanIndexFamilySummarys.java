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
 * 联谊会首页-联谊堂
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@TableName("fan_index_family_summarys")
public class FanIndexFamilySummarys extends Model<FanIndexFamilySummarys> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 网站id
     */
    @TableField("site_id")
    private Integer siteId;
    /**
     * 堂号
     */
    @TableField("root_group")
    private String rootGroup;
    /**
     * 始迁祖
     */
    @TableField("root_person")
    private String rootPerson;
    /**
     * 负责人
     */
    private String leader;
    /**
     * 负责人电话
     */
    @TableField("leader_phone")
    private String leaderPhone;
    /**
     * 膜拜数
     */
    @TableField("worship_num")
    private Integer worshipNum;
    /**
     * 点赞数
     */
    @TableField("praise_num")
    private Integer praiseNum;
    /**
     * 状态(0:删除;1:显示;2:草稿)
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
     * 修改时间
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

    public FanIndexFamilySummarys setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public FanIndexFamilySummarys setSiteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public String getRootGroup() {
        return rootGroup;
    }

    public FanIndexFamilySummarys setRootGroup(String rootGroup) {
        this.rootGroup = rootGroup;
        return this;
    }

    public String getRootPerson() {
        return rootPerson;
    }

    public FanIndexFamilySummarys setRootPerson(String rootPerson) {
        this.rootPerson = rootPerson;
        return this;
    }

    public String getLeader() {
        return leader;
    }

    public FanIndexFamilySummarys setLeader(String leader) {
        this.leader = leader;
        return this;
    }

    public String getLeaderPhone() {
        return leaderPhone;
    }

    public FanIndexFamilySummarys setLeaderPhone(String leaderPhone) {
        this.leaderPhone = leaderPhone;
        return this;
    }

    public Integer getWorshipNum() {
        return worshipNum;
    }

    public FanIndexFamilySummarys setWorshipNum(Integer worshipNum) {
        this.worshipNum = worshipNum;
        return this;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public FanIndexFamilySummarys setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public FanIndexFamilySummarys setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanIndexFamilySummarys setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanIndexFamilySummarys setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanIndexFamilySummarys setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanIndexFamilySummarys setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanIndexFamilySummarys{" +
                ", id=" + id +
                ", siteId=" + siteId +
                ", rootGroup=" + rootGroup +
                ", rootPerson=" + rootPerson +
                ", leader=" + leader +
                ", leaderPhone=" + leaderPhone +
                ", worshipNum=" + worshipNum +
                ", praiseNum=" + praiseNum +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
