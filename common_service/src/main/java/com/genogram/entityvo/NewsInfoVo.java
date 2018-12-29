package com.genogram.entityvo;

import lombok.Data;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class NewsInfoVo {

    private Integer id;
    /**
     * 个人id
     */
    private Integer userId;
    /**
     * 作者
     */
    private String nickName;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章封面url
     */
    private String newsFaceUrl;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 省级编号
     */
    private Integer proCode;
    /**
     * 地区编号
     */
    private Integer regionId;
    /**
     * 系统管理员操作状态(1-展示,2-不展示)
     */
    private Integer sysStatus;
    /**
     * 阅读量
     */
    private Integer readNum;
    /**
     * 状态（0 删除 1 正常 2 草稿）
     */
    private Integer status;
    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人id
     */
    private Integer createUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private Integer updateUser;

}
