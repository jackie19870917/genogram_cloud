package com.genogram.entityvo;


import lombok.Data;

import java.util.Date;

/**
 * 省级字派Vo表
 *
 * @Author: yuzhou
 * @Date: 2018-11-26
 * @Time: 11:12
 * @Param:
 * @return:
 * @Description:
 */
@Data
public class ProNewsCultureZipaiVo {

    /**
     * 主键字派ID
     */
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    private Integer showId;
    /**
     * 字派具体地域
     */
    private String ziapiLocation;
    /**
     * 祖先名
     */
    private String ancestorsName;
    /**
     * 字派数组:数字和字的组合
     */
    private String zipaiTxt;
    /**
     * 访问数
     */
    private Integer visitNum;
    /**
     * 状态(0:删除;1:已发布;2:草稿3:不显示)
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
     * 1 代表省级  2 代表县级
     */
    private int source;

}
