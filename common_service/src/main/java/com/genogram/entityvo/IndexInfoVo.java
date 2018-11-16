package com.genogram.entityvo;

import lombok.Data;

import java.util.Date;

/**
 * 联谊会信息(网站名称,图腾,宣言,简介)
 *
 * @Author: Toxicant
 * @Date: 2018-11-09
 * @Time: 10:58
 * @Param:
 * @return:
 * @Description:
 */
@Data
public class IndexInfoVo {

    private Integer id;
    /**
     * 网站id(fan_sys_site_id)
     */
    private Integer siteId;
    /**
     * 图腾图片地址
     */
    private String totemPicSrc;
    /**
     * 宣言
     */
    private String title;
    /**
     * 简介
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 修改人
     */
    private Integer updateUser;
    /**
     * 网站名称
     */
    private String name;
}
