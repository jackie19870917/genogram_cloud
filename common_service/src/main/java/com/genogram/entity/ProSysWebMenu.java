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
 * 省级web,admin菜单基础表,非文章位置表
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@TableName("pro_sys_web_menu")
public class ProSysWebMenu extends Model<ProSysWebMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
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
     * 是否有子栏目;0:否;1是
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

    public ProSysWebMenu setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getTreeNum() {
        return treeNum;
    }

    public ProSysWebMenu setTreeNum(Integer treeNum) {
        this.treeNum = treeNum;
        return this;
    }

    public String getMenuName() {
        return menuName;
    }

    public ProSysWebMenu setMenuName(String menuName) {
        this.menuName = menuName;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public ProSysWebMenu setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public ProSysWebMenu setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        return this;
    }

    public String getApiAdminUrl() {
        return apiAdminUrl;
    }

    public ProSysWebMenu setApiAdminUrl(String apiAdminUrl) {
        this.apiAdminUrl = apiAdminUrl;
        return this;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public ProSysWebMenu setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
        return this;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public ProSysWebMenu setMenuCode(String menuCode) {
        this.menuCode = menuCode;
        return this;
    }

    public String getMenuType() {
        return menuType;
    }

    public ProSysWebMenu setMenuType(String menuType) {
        this.menuType = menuType;
        return this;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public ProSysWebMenu setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    public Integer getIsWeb() {
        return isWeb;
    }

    public ProSysWebMenu setIsWeb(Integer isWeb) {
        this.isWeb = isWeb;
        return this;
    }

    public Integer getIstatic() {
        return istatic;
    }

    public ProSysWebMenu setIstatic(Integer istatic) {
        this.istatic = istatic;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public ProSysWebMenu setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProSysWebMenu setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public ProSysWebMenu setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ProSysWebMenu setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public ProSysWebMenu setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProSysWebMenu{" +
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
