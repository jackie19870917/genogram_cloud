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
 * 联谊会-家族慈善财务支出草稿表
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@TableName("fan_news_charity_out_drft")
public class FanNewsCharityOutDrft extends Model<FanNewsCharityOutDrft> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 文章id
     */
    @TableField("fan_news_charity_out_id")
    private Integer fanNewsCharityOutId;
    /**
     * 标题
     */
    @TableField("news_title")
    private String newsTitle;
    /**
     * 内容
     */
    @TableField("news_text")
    private String newsText;
    /**
     * 查看数
     */
    @TableField("visit_num")
    private Integer visitNum;
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

    public FanNewsCharityOutDrft setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFanNewsCharityOutId() {
        return fanNewsCharityOutId;
    }

    public FanNewsCharityOutDrft setFanNewsCharityOutId(Integer fanNewsCharityOutId) {
        this.fanNewsCharityOutId = fanNewsCharityOutId;
        return this;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public FanNewsCharityOutDrft setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
        return this;
    }

    public String getNewsText() {
        return newsText;
    }

    public FanNewsCharityOutDrft setNewsText(String newsText) {
        this.newsText = newsText;
        return this;
    }

    public Integer getVisitNum() {
        return visitNum;
    }

    public FanNewsCharityOutDrft setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanNewsCharityOutDrft setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanNewsCharityOutDrft setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanNewsCharityOutDrft setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanNewsCharityOutDrft setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanNewsCharityOutDrft{" +
        ", id=" + id +
        ", fanNewsCharityOutId=" + fanNewsCharityOutId +
        ", newsTitle=" + newsTitle +
        ", newsText=" + newsText +
        ", visitNum=" + visitNum +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
