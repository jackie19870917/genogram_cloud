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
 * 姓氏起源
 * </p>
 *
 * @author wangwei
 * @since 2019-02-18
 */
@TableName("all_family_origin")
public class AllFamilyOrigin extends Model<AllFamilyOrigin> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 姓氏表ID all_family_origin
     */
    @TableField("family_id")
    private Integer familyId;
    /**
     * 姓氏繁体
     */
    @TableField("ancient_surnames")
    private String ancientSurnames;
    /**
     * 始祖
     */
    @TableField("first_ancestor")
    private String firstAncestor;
    /**
     * 祖源地
     */
    @TableField("progenitor_source")
    private String progenitorSource;
    /**
     * 统谱派
     */
    @TableField("pu_clique")
    private String puClique;
    /**
     * 宋版排序
     */
    @TableField("ancient_rank")
    private String ancientRank;
    /**
     * 当今排序
     */
    @TableField("nowadays_rank")
    private String nowadaysRank;
    /**
     * 人口概况
     */
    @TableField("population_profile")
    private String populationProfile;
    /**
     * 姓氏图腾地址
     */
    @TableField("family_totem_pic")
    private String familyTotemPic;
    /**
     * 姓氏释义
     */
    @TableField("family_paraphrase")
    private String familyParaphrase;
    /**
     * 由来
     */
    private String derive;
    /**
     * 郡望
     */
    @TableField("jun_wang")
    private String junWang;
    /**
     * 迁徙路线
     */
    @TableField("migrate_line")
    private String migrateLine;
    /**
     * 人口分布
     */
    @TableField("population_distribution")
    private String populationDistribution;
    /**
     * 姓氏人物
     */
    @TableField("family_figure")
    private String familyFigure;
    /**
     * 状态(0:删除;1:已发布;2:草稿3:不显示)
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

    public AllFamilyOrigin setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public AllFamilyOrigin setFamilyId(Integer familyId) {
        this.familyId = familyId;
        return this;
    }

    public String getAncientSurnames() {
        return ancientSurnames;
    }

    public AllFamilyOrigin setAncientSurnames(String ancientSurnames) {
        this.ancientSurnames = ancientSurnames;
        return this;
    }

    public String getFirstAncestor() {
        return firstAncestor;
    }

    public AllFamilyOrigin setFirstAncestor(String firstAncestor) {
        this.firstAncestor = firstAncestor;
        return this;
    }

    public String getProgenitorSource() {
        return progenitorSource;
    }

    public AllFamilyOrigin setProgenitorSource(String progenitorSource) {
        this.progenitorSource = progenitorSource;
        return this;
    }

    public String getPuClique() {
        return puClique;
    }

    public AllFamilyOrigin setPuClique(String puClique) {
        this.puClique = puClique;
        return this;
    }

    public String getAncientRank() {
        return ancientRank;
    }

    public AllFamilyOrigin setAncientRank(String ancientRank) {
        this.ancientRank = ancientRank;
        return this;
    }

    public String getNowadaysRank() {
        return nowadaysRank;
    }

    public AllFamilyOrigin setNowadaysRank(String nowadaysRank) {
        this.nowadaysRank = nowadaysRank;
        return this;
    }

    public String getPopulationProfile() {
        return populationProfile;
    }

    public AllFamilyOrigin setPopulationProfile(String populationProfile) {
        this.populationProfile = populationProfile;
        return this;
    }

    public String getFamilyTotemPic() {
        return familyTotemPic;
    }

    public AllFamilyOrigin setFamilyTotemPic(String familyTotemPic) {
        this.familyTotemPic = familyTotemPic;
        return this;
    }

    public String getFamilyParaphrase() {
        return familyParaphrase;
    }

    public AllFamilyOrigin setFamilyParaphrase(String familyParaphrase) {
        this.familyParaphrase = familyParaphrase;
        return this;
    }

    public String getDerive() {
        return derive;
    }

    public AllFamilyOrigin setDerive(String derive) {
        this.derive = derive;
        return this;
    }

    public String getJunWang() {
        return junWang;
    }

    public AllFamilyOrigin setJunWang(String junWang) {
        this.junWang = junWang;
        return this;
    }

    public String getMigrateLine() {
        return migrateLine;
    }

    public AllFamilyOrigin setMigrateLine(String migrateLine) {
        this.migrateLine = migrateLine;
        return this;
    }

    public String getPopulationDistribution() {
        return populationDistribution;
    }

    public AllFamilyOrigin setPopulationDistribution(String populationDistribution) {
        this.populationDistribution = populationDistribution;
        return this;
    }

    public String getFamilyFigure() {
        return familyFigure;
    }

    public AllFamilyOrigin setFamilyFigure(String familyFigure) {
        this.familyFigure = familyFigure;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AllFamilyOrigin setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AllFamilyOrigin setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public AllFamilyOrigin setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AllFamilyOrigin setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public AllFamilyOrigin setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllFamilyOrigin{" +
                ", id=" + id +
                ", familyId=" + familyId +
                ", ancientSurnames=" + ancientSurnames +
                ", firstAncestor=" + firstAncestor +
                ", progenitorSource=" + progenitorSource +
                ", puClique=" + puClique +
                ", ancientRank=" + ancientRank +
                ", nowadaysRank=" + nowadaysRank +
                ", populationProfile=" + populationProfile +
                ", familyTotemPic=" + familyTotemPic +
                ", familyParaphrase=" + familyParaphrase +
                ", derive=" + derive +
                ", junWang=" + junWang +
                ", migrateLine=" + migrateLine +
                ", populationDistribution=" + populationDistribution +
                ", familyFigure=" + familyFigure +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
