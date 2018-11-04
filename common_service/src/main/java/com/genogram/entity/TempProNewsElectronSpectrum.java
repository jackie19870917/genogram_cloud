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
 * 省级电子谱
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@TableName("temp_pro_news_electron_spectrum")
public class TempProNewsElectronSpectrum extends Model<TempProNewsElectronSpectrum> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 网站id（sys_net_id）
     */
    @TableField("net_id")
    private Integer netId;
    /**
     * 显示位置(sys_show_position_id)
     */
    @TableField("show_position_id")
    private Integer showPositionId;
    /**
     * 字谱的名称
     */
    @TableField("spectrum_name")
    private String spectrumName;
    /**
     * 联系人姓名
     */
    @TableField("contact_name")
    private String contactName;
    /**
     * 省级
     */
    private String province;
    /**
     * 县级
     */
    private String city;
    /**
     * 区
     */
    private String area;
    /**
     * 密码
     */
    private String password;
    /**
     * 上传文件地址
     */
    @TableField("src_url")
    private String srcUrl;
    /**
     * 上传文件名
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 文件格式
     */
    @TableField("file_type")
    private String fileType;
    /**
     * 权限 
     */
    private Integer auth;
    /**
     * 是否删除
     */
    private Integer isDel;
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

    public TempProNewsElectronSpectrum setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getNetId() {
        return netId;
    }

    public TempProNewsElectronSpectrum setNetId(Integer netId) {
        this.netId = netId;
        return this;
    }

    public Integer getShowPositionId() {
        return showPositionId;
    }

    public TempProNewsElectronSpectrum setShowPositionId(Integer showPositionId) {
        this.showPositionId = showPositionId;
        return this;
    }

    public String getSpectrumName() {
        return spectrumName;
    }

    public TempProNewsElectronSpectrum setSpectrumName(String spectrumName) {
        this.spectrumName = spectrumName;
        return this;
    }

    public String getContactName() {
        return contactName;
    }

    public TempProNewsElectronSpectrum setContactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public TempProNewsElectronSpectrum setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCity() {
        return city;
    }

    public TempProNewsElectronSpectrum setCity(String city) {
        this.city = city;
        return this;
    }

    public String getArea() {
        return area;
    }

    public TempProNewsElectronSpectrum setArea(String area) {
        this.area = area;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public TempProNewsElectronSpectrum setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public TempProNewsElectronSpectrum setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public TempProNewsElectronSpectrum setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public TempProNewsElectronSpectrum setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public Integer getAuth() {
        return auth;
    }

    public TempProNewsElectronSpectrum setAuth(Integer auth) {
        this.auth = auth;
        return this;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public TempProNewsElectronSpectrum setIsDel(Integer isDel) {
        this.isDel = isDel;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TempProNewsElectronSpectrum setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public TempProNewsElectronSpectrum setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public TempProNewsElectronSpectrum setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public TempProNewsElectronSpectrum setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TempProNewsElectronSpectrum{" +
        ", id=" + id +
        ", netId=" + netId +
        ", showPositionId=" + showPositionId +
        ", spectrumName=" + spectrumName +
        ", contactName=" + contactName +
        ", province=" + province +
        ", city=" + city +
        ", area=" + area +
        ", password=" + password +
        ", srcUrl=" + srcUrl +
        ", fileName=" + fileName +
        ", fileType=" + fileType +
        ", auth=" + auth +
        ", isDel=" + isDel +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
