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
 * 省级web菜单基础表,仅做初始化的时候用
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
@TableName("chi_sys_web_menu")
public class ChiSysWebMenu extends Model<ChiSysWebMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 菜单级数
     */
    @TableField("tree_num")
    private Integer treeNum;
    /**
     * 栏目名称
     */
    @TableField("menu_name")
    private String menuName;
    /**
     * 父ID
     */
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 前台2级栏目文章列表API
     */
    @TableField("api_url")
    private String apiUrl;
    /**
     * 后台2级栏目文章列表API
     */
    @TableField("api_admin_url")
    private String apiAdminUrl;
    /**
     * 排列顺序
     */
    @TableField("order_index")
    private Integer orderIndex;
    /**
     * 菜单类型:图片文章视频
     */
    @TableField("menu_code")
    private String menuCode;
    /**
     * 菜单种类
     */
    @TableField("menu_type")
    private String menuType;
    /**
     * 是否是admin显示菜单(0:否;1:是)
     */
    @TableField("is_admin")
    private Integer isAdmin;
    /**
     * 是否是admin显示菜单(0:否;1:是)
     */
    @TableField("is_web")
    private Integer isWeb;
    /**
     * 是否手动添加的子栏目;0:否;1是
     */
    private Integer istatic;
    /**
     * 备注
     */
    private String comments;
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

    public ChiSysWebMenu setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getTreeNum() {
        return treeNum;
    }

    public ChiSysWebMenu setTreeNum(Integer treeNum) {
        this.treeNum = treeNum;
        return this;
    }

    public String getMenuName() {
        return menuName;
    }

    public ChiSysWebMenu setMenuName(String menuName) {
        this.menuName = menuName;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public ChiSysWebMenu setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public ChiSysWebMenu setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        return this;
    }

    public String getApiAdminUrl() {
        return apiAdminUrl;
    }

    public ChiSysWebMenu setApiAdminUrl(String apiAdminUrl) {
        this.apiAdminUrl = apiAdminUrl;
        return this;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public ChiSysWebMenu setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
        return this;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public ChiSysWebMenu setMenuCode(String menuCode) {
        this.menuCode = menuCode;
        return this;
    }

    public String getMenuType() {
        return menuType;
    }

    public ChiSysWebMenu setMenuType(String menuType) {
        this.menuType = menuType;
        return this;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public ChiSysWebMenu setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    public Integer getIsWeb() {
        return isWeb;
    }

    public ChiSysWebMenu setIsWeb(Integer isWeb) {
        this.isWeb = isWeb;
        return this;
    }

    public Integer getIstatic() {
        return istatic;
    }

    public ChiSysWebMenu setIstatic(Integer istatic) {
        this.istatic = istatic;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public ChiSysWebMenu setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChiSysWebMenu setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ChiSysWebMenu setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ChiSysWebMenu setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ChiSysWebMenu setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ChiSysWebMenu{" +
                ", id=" + id +
                ", treeNum=" + treeNum +
                ", menuName=" + menuName +
                ", parentId=" + parentId +
                ", apiUrl=" + apiUrl +
                ", apiAdminUrl=" + apiAdminUrl +
                ", orderIndex=" + orderIndex +
                ", menuCode=" + menuCode +
                ", menuType=" + menuType +
                ", isAdmin=" + isAdmin +
                ", isWeb=" + isWeb +
                ", istatic=" + istatic +
                ", comments=" + comments +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "}";
    }
}
