package com.genogram.entityvo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
/**
 * @Author: wang, wei
 * @Date: 2018/11/5
 * @Time: 21:17
 * @Param:
 * @return:
 * @Description:
 *
 */
public class SysWebMenuVo {
    private Integer id;
    /**
     * site_id和menuid组合
     */
    private Integer showId;
    /**
     * 网站id
     */
    private Integer siteId;
    private Integer menuId;
    /**
     * 菜单级数
     */
    private Integer treeNum;
    /**
     * 栏目名称
     */
    private String menuName;
    /**
     * 父ID
     */
    private Integer parentId;
    /**
     * 前台2级栏目文章列表API
     */
    private String apiUrl;
    /**
     * 后台2级栏目文章列表API
     */
    private String apiAdminUrl;
    /**
     * 排列顺序
     */
    private Integer orderIndex;
    /**
     * 菜单类型
     */
    private String menuCode;
    /**
     * 菜单种类
     */
    private String menuType;
    /**
     * 是否是admin显示菜单(0:否;1:是)
     */
    private Integer isAdmin;
    /**
     * 是否是admin显示菜单(0:否;1:是)
     */
    private Integer isWeb;
    /**
     * 是否手动添加的子栏目;0:否;1是
     */
    private Integer istatic;
    /**
     * 备注
     */
    private String comments;
    private Date createTime;
    private Integer createUser;
    private Date updateTime;
    private Integer updateUser;
}
