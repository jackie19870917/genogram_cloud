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
 * @author wangwei
 * @since 2019-01-22
 */
@TableName("pu_pepole_info")
public class PuPepoleInfo extends Model<PuPepoleInfo> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 第几代
     */
    @TableField("generate_num")
    private Integer generateNum;
    /**
     * 姓氏
     */
    private String surname;
    /**
     * 名字
     */
    private String name;
    /**
     * 1 : 已故 ; 2 : 健在
     */
    @TableField("is_alive")
    private Integer isAlive;
    /**
     * 性别
     */
    private String gender;
    /**
     * 字派
     */
    private String zipai;
    /**
     * 父亲id
     */
    @TableField("father_id")
    private Integer fatherId;
    /**
     * 母亲id
     */
    @TableField("mother_id")
    private Integer motherId;
    /**
     * 兄或弟或姐或妹id
     */
    @TableField("brothers_sisters_id")
    private String brothersSistersId;
    /**
     * 女儿
     */
    @TableField("daughter_id")
    private String daughterId;
    /**
     * 儿子
     */
    @TableField("son_id")
    private String sonId;
    /**
     * 配偶
     */
    @TableField("spouse_id")
    private String spouseId;
    /**
     * 本人详情
     */
    @TableField("self_details")
    private String selfDetails;
    /**
     * 配偶详情
     */
    @TableField("spouse_details")
    private String spouseDetails;
    /**
     * 其他名字
     */
    @TableField("else_name")
    private String elseName;
    /**
     * 号
     */
    private String hao;
    /**
     * 谥号
     */
    @TableField("posthumous_title")
    private String posthumousTitle;
    /**
     * 讳
     */
    private String hui;
    /**
     * 行号描述
     */
    @TableField("linenum_describe")
    private String linenumDescribe;
    /**
     * 职务名称
     */
    @TableField("position_title")
    private String positionTitle;
    /**
     * 出生日期
     */
    @TableField("birth_date")
    private Date birthDate;
    /**
     * 去世日期
     */
    @TableField("away_date")
    private Date awayDate;
    /**
     * 葬地与朝向
     */
    private String orientation;
    /**
     * 卒世描述
     */
    @TableField("obiit_")
    private String obiit;
    /**
     * 居地古称
     */
    @TableField("ancient_site")
    private String ancientSite;
    /**
     * 尾注
     */
    private String endnote;
    /**
     * 状态(0:删除;1:显示;)
     */
    @TableField("is_live")
    private Integer isLive;
    @TableField("create_user")
    private Integer createUser;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_user")
    private Integer updateUser;
    @TableField("update_time")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public PuPepoleInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getGenerateNum() {
        return generateNum;
    }

    public PuPepoleInfo setGenerateNum(Integer generateNum) {
        this.generateNum = generateNum;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public PuPepoleInfo setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getName() {
        return name;
    }

    public PuPepoleInfo setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getIsAlive() {
        return isAlive;
    }

    public PuPepoleInfo setIsAlive(Integer isAlive) {
        this.isAlive = isAlive;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public PuPepoleInfo setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getZipai() {
        return zipai;
    }

    public PuPepoleInfo setZipai(String zipai) {
        this.zipai = zipai;
        return this;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public PuPepoleInfo setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
        return this;
    }

    public Integer getMotherId() {
        return motherId;
    }

    public PuPepoleInfo setMotherId(Integer motherId) {
        this.motherId = motherId;
        return this;
    }

    public String getBrothersSistersId() {
        return brothersSistersId;
    }

    public PuPepoleInfo setBrothersSistersId(String brothersSistersId) {
        this.brothersSistersId = brothersSistersId;
        return this;
    }

    public String getDaughterId() {
        return daughterId;
    }

    public PuPepoleInfo setDaughterId(String daughterId) {
        this.daughterId = daughterId;
        return this;
    }

    public String getSonId() {
        return sonId;
    }

    public PuPepoleInfo setSonId(String sonId) {
        this.sonId = sonId;
        return this;
    }

    public String getSpouseId() {
        return spouseId;
    }

    public PuPepoleInfo setSpouseId(String spouseId) {
        this.spouseId = spouseId;
        return this;
    }

    public String getSelfDetails() {
        return selfDetails;
    }

    public PuPepoleInfo setSelfDetails(String selfDetails) {
        this.selfDetails = selfDetails;
        return this;
    }

    public String getSpouseDetails() {
        return spouseDetails;
    }

    public PuPepoleInfo setSpouseDetails(String spouseDetails) {
        this.spouseDetails = spouseDetails;
        return this;
    }

    public String getElseName() {
        return elseName;
    }

    public PuPepoleInfo setElseName(String elseName) {
        this.elseName = elseName;
        return this;
    }

    public String getHao() {
        return hao;
    }

    public PuPepoleInfo setHao(String hao) {
        this.hao = hao;
        return this;
    }

    public String getPosthumousTitle() {
        return posthumousTitle;
    }

    public PuPepoleInfo setPosthumousTitle(String posthumousTitle) {
        this.posthumousTitle = posthumousTitle;
        return this;
    }

    public String getHui() {
        return hui;
    }

    public PuPepoleInfo setHui(String hui) {
        this.hui = hui;
        return this;
    }

    public String getLinenumDescribe() {
        return linenumDescribe;
    }

    public PuPepoleInfo setLinenumDescribe(String linenumDescribe) {
        this.linenumDescribe = linenumDescribe;
        return this;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public PuPepoleInfo setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
        return this;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public PuPepoleInfo setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Date getAwayDate() {
        return awayDate;
    }

    public PuPepoleInfo setAwayDate(Date awayDate) {
        this.awayDate = awayDate;
        return this;
    }

    public String getOrientation() {
        return orientation;
    }

    public PuPepoleInfo setOrientation(String orientation) {
        this.orientation = orientation;
        return this;
    }

    public String getObiit() {
        return obiit;
    }

    public PuPepoleInfo setObiit(String obiit) {
        this.obiit = obiit;
        return this;
    }

    public String getAncientSite() {
        return ancientSite;
    }

    public PuPepoleInfo setAncientSite(String ancientSite) {
        this.ancientSite = ancientSite;
        return this;
    }

    public String getEndnote() {
        return endnote;
    }

    public PuPepoleInfo setEndnote(String endnote) {
        this.endnote = endnote;
        return this;
    }

    public Integer getIsLive() {
        return isLive;
    }

    public PuPepoleInfo setIsLive(Integer isLive) {
        this.isLive = isLive;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public PuPepoleInfo setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PuPepoleInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public PuPepoleInfo setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public PuPepoleInfo setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PuPepoleInfo{" +
        ", id=" + id +
        ", generateNum=" + generateNum +
        ", surname=" + surname +
        ", name=" + name +
        ", isAlive=" + isAlive +
        ", gender=" + gender +
        ", zipai=" + zipai +
        ", fatherId=" + fatherId +
        ", motherId=" + motherId +
        ", brothersSistersId=" + brothersSistersId +
        ", daughterId=" + daughterId +
        ", sonId=" + sonId +
        ", spouseId=" + spouseId +
        ", selfDetails=" + selfDetails +
        ", spouseDetails=" + spouseDetails +
        ", elseName=" + elseName +
        ", hao=" + hao +
        ", posthumousTitle=" + posthumousTitle +
        ", hui=" + hui +
        ", linenumDescribe=" + linenumDescribe +
        ", positionTitle=" + positionTitle +
        ", birthDate=" + birthDate +
        ", awayDate=" + awayDate +
        ", orientation=" + orientation +
        ", obiit=" + obiit +
        ", ancientSite=" + ancientSite +
        ", endnote=" + endnote +
        ", isLive=" + isLive +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
