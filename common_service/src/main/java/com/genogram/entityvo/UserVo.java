package com.genogram.entityvo;

import lombok.Data;

import java.util.Date;

/**
 *@author: Toxicant
 *@date: 2018-11-23
*/
@Data
public class UserVo {

    private Integer id;
    /**
     * 用户主键，user开头，日期年月日小时分秒+随机数组成
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 别名
     */
    private String nickName;
    /**
     * 手机号
     */
    private String mobilePhone;
    /**
     * 头像url
     */
    private String picSrc;
    /**
     * 状态(0:删除;1:正常;2:异常)
     */
    private Integer status;
    /**
     * 网站id(省或者县的网站id)
     */
    private Integer siteId;
    /**
     * 角色1.(县级管理员,省级管理员)
     */
    private Integer role;
    /**
     * sys_family:id
     */
    private Integer familyCode;
    private Integer regionCode;
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
     * token
     */
    private String token;
}
