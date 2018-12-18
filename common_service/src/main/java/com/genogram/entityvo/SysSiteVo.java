package com.genogram.entityvo;

import lombok.Data;

import java.util.Date;

/**
 * @author: Toxicant
 * @date: 2018-11-23
 */
@Data
public class SysSiteVo {

    private Integer id;
    /**
     * 编号
     */
    private String fanUrlCode;
    /**
     * 姓氏编号
     */
    private Integer familyCode;
    /**
     * 姓氏
     */
    private String familyName;
    /**
     * 地区编号
     */
    private String regionCode;
    /**
     * 地区名称
     */
    private String regionName;
    /**
     * 图腾
     */
    private String url;
    /**
     * 网站名称
     */
    private String name;
    /**
     * 管理员id
     */
    private Integer admin;
    /**
     * 状态(0:删除1:已开通;2:未开通;3:待开通)
     */
    private Integer status;
    /**
     * 父网站id
     */
    private Integer parent;
    /**
     * 一级域名
     */
    private String oneUrl;
    /**
     * 2域名
     */
    private String twoUrl;
    /**
     * 管理员是否可以操作:1:可用;0禁用;
     */
    private String adminEnable;
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
}
