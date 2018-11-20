package com.genogram.entityvo;

import com.genogram.entity.ProNewsFamousAncestor;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *祖先分支封装类
 *@Author: yuzhou
 *@Date: 2018-11-20
 *@Time: 14:17
 *@Param:
 *@return:
 *@Description:
*/
@Data
public class AncestorsBranchVo {

    /**
     * 1 县级 2省级
     */
    private int source;
    /**
     * 编号
     */
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    private Integer showId;
    /**
     * 父id
     */
    private Integer parentId;
    /**
     * 祖先头衔
     */
    private String ancestorTitle;
    /**
     * 祖先名
     */
    private String ancestorName;
    /**
     * 人物简介
     */
    private String ancestorSummary;
    /**
     * 头像图片位置
     */
    private String picFileSrc;
    /**
     * 头像名
     */
    private String picFileName;
    /**
     * 状态(0:删除;1:发布;3:不显示)
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

    /**
     * 字派
     */
    private String zipai;

    /**
     * 郡望
     */
    private String junwang;

    /**
     * 堂号
     */
    private String tanghao;

    /**
     * 省级祖先分支
     */
    private List<ProNewsFamousAncestor> proNewsFamousAncestorList;
}
