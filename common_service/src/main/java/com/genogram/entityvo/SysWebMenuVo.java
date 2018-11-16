package com.genogram.entityvo;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
/**
 * @Author: wang,wei
 * @Date: 2018/11/5
 * @Time: 21:17
 * @Param: 
 * @return: 
 * @Description:
 *
 */
public class SysWebMenuVo{
    private int showId;
    private int fanSysSiteId;
    private int fanSysWebMenuId;
    private List<SysWebMenuVo> child;

    /**
     * 页面显示栏目名称
     */
    private String menuShowName;
    /**
     * 权限ID
     */
    private Integer id;
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
     * 菜单类型:图片文章视频
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
     * 是否有子栏目;0:否;1是
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
