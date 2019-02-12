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
 * @since 2019-01-25
 */
@TableName("pro_news_upload_tree_file")
public class ProNewsUploadTreeFile extends Model<ProNewsUploadTreeFile> {

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
     * 评论数
     */
    @TableField("comment_count")
    private Integer commentCount;
    /**
     * 手动排序
     */
    private Integer order;
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
    /**
     * 电子谱文件预览地址
     */
    @TableField("tree_preview_path")
    private String treePreviewPath;
    /**
     * 上传文件名称
     */
    @TableField("upload_file_name")
    private String uploadFileName;


    public Integer getId() {
        return id;
    }

    public ProNewsUploadTreeFile setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public ProNewsUploadTreeFile setSiteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public ProNewsUploadTreeFile setRegionCode(String regionCode) {
        this.regionCode = regionCode;
        return this;
    }

    public Integer getIsFrom() {
        return isFrom;
    }

    public ProNewsUploadTreeFile setIsFrom(Integer isFrom) {
        this.isFrom = isFrom;
        return this;
    }

    public Integer getFamilyCode() {
        return familyCode;
    }

    public ProNewsUploadTreeFile setFamilyCode(Integer familyCode) {
        this.familyCode = familyCode;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public ProNewsUploadTreeFile setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public ProNewsUploadTreeFile setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getContactUser() {
        return contactUser;
    }

    public ProNewsUploadTreeFile setContactUser(String contactUser) {
        this.contactUser = contactUser;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ProNewsUploadTreeFile setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ProNewsUploadTreeFile setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getPreThirty() {
        return preThirty;
    }

    public ProNewsUploadTreeFile setPreThirty(Integer preThirty) {
        this.preThirty = preThirty;
        return this;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public ProNewsUploadTreeFile setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public Integer getOrder() {
        return order;
    }

    public ProNewsUploadTreeFile setOrder(Integer order) {
        this.order = order;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProNewsUploadTreeFile setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ProNewsUploadTreeFile setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ProNewsUploadTreeFile setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ProNewsUploadTreeFile setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public String getTreePreviewPath() {
        return treePreviewPath;
    }

    public ProNewsUploadTreeFile setTreePreviewPath(String treePreviewPath) {
        this.treePreviewPath = treePreviewPath;
        return this;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public ProNewsUploadTreeFile setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProNewsUploadTreeFile{" +
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
                ", commentCount=" + commentCount +
                ", order=" + order +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                ", treePreviewPath=" + treePreviewPath +
                ", uploadFileName=" + uploadFileName +
                "}";
    }
}
