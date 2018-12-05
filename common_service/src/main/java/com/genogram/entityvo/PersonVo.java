package com.genogram.entityvo;

import lombok.Data;

import java.util.Date;

/**
 * 个人信息
 *
 * @author keriezhang
 * @date 2016/10/31
 */
@Data
public class PersonVo {

    private static final long serialVersionUID = 1L;

    /**
     * 注册ID
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Integer allUserLoginId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 图像
     */
    private String url;
    /**
     * 外文名
     */
    private String englishName;
    /**
     * 国籍
     */
    private String nation;
    /**
     * 出生地
     */
    private String birthplace;
    /**
     * 职业
     */
    private String job;
    /**
     * 历代
     */
    private Integer lidai;
    /**
     * 近世
     */
    private Integer jinshi;
    /**
     * 老派
     */
    private String laopai;
    /**
     * 新派
     */
    private String xinpai;
    /**
     * 统派
     */
    private String tongpai;
    /**
     * 现居
     */
    private String presentAddress;
    /**
     * 故居
     */
    private String oldAddress;
    /**
     * 现居别称
     */
    private String alias;
    /**
     * 简介
     */
    private String summary;
    /**
     * 粉丝
     */
    private Integer fans;
    /**
     * 诚信值
     */
    private Integer honesty;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 状态(0:删除;1:正常,2:非正常)
     */
    private Integer status;
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
