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
 * @since 2018-12-14
 */
@TableName("fan_news_upload_tree_file")
public class FanNewsUploadTreeFile extends Model<FanNewsUploadTreeFile> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 网站id
     */
    @TableField("site_id")
    private Integer siteId;
    /**
     * 地区
     */
    @TableField("region_code")
    private String regionCode;
    /**
     * 来源(0-省级,1-县级)
     */
    @TableField("is_from")
    private Integer isFrom;
    /**
     * 姓氏
     */
    @TableField("family_code")
    private Integer familyCode;
    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;
    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 联系人
     */
    @TableField("contact_user")
    private String contactUser;
    /**
     * 状态(1-公开,2-密码访问,3-私密,0-删除)
     */
    private Integer status;
    /**
     * 访问密码(公开状态,密码为null)
     */
    private String password;
    /**
     * 前三十页(1-显示,2-不显示)
     */
    @TableField("pre_thirty")
    private Integer preThirty;
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

    public FanNewsUploadTreeFile setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public FanNewsUploadTreeFile setSiteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public FanNewsUploadTreeFile setRegionCode(String regionCode) {
        this.regionCode = regionCode;
        return this;
    }

    public Integer getIsFrom() {
        return isFrom;
    }

    public FanNewsUploadTreeFile setIsFrom(Integer isFrom) {
        this.isFrom = isFrom;
        return this;
    }

    public Integer getFamilyCode() {
        return familyCode;
    }

    public FanNewsUploadTreeFile setFamilyCode(Integer familyCode) {
        this.familyCode = familyCode;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public FanNewsUploadTreeFile setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public FanNewsUploadTreeFile setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getContactUser() {
        return contactUser;
    }

    public FanNewsUploadTreeFile setContactUser(String contactUser) {
        this.contactUser = contactUser;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public FanNewsUploadTreeFile setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public FanNewsUploadTreeFile setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getPreThirty() {
        return preThirty;
    }

    public FanNewsUploadTreeFile setPreThirty(Integer preThirty) {
        this.preThirty = preThirty;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FanNewsUploadTreeFile setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public FanNewsUploadTreeFile setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public FanNewsUploadTreeFile setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public FanNewsUploadTreeFile setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FanNewsUploadTreeFile{" +
        ", id=" + id +
        ", siteId=" + siteId +
        ", regionCode=" + regionCode +
        ", isFrom=" + isFrom +
        ", familyCode=" + familyCode +
        ", filePath=" + filePath +
        ", fileName=" + fileName +
        ", contactUser=" + contactUser +
        ", status=" + status +
        ", password=" + password +
        ", preThirty=" + preThirty +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
