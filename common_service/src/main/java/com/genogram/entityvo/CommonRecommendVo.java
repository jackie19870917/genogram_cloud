package com.genogram.entityvo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

/**
 * 推荐表封装
 *
 * @Author: yuzhou
 * @Date: 2018-11-17
 * @Time: 18:50
 * @Param:
 * @return:
 * @Description:
 */
@Data
public class CommonRecommendVo {

    /**
     * 推荐表主键
     */
    private int recommendId;

    /**
     * 联谊会名称
     */
    private String sizeName;

    /**
     * 分类1代表家族文化 2代表记录家族 3代表家族产业 4代表家族名人
     */
    private int source;

    /*家族文化 家族动态*/
    /**
     * 编号
     */
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    private Integer showId;
    /**
     * 标题
     */
    private String newsTitle;
    /**
     * 内容
     */
    private String newsText;
    /**
     * 查看数
     */
    private Integer visitNum;
    /**
     * 状态(0:删除;1:已发布;2:草稿3:不显示)
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

    /*家族产业*/

    /**
     * 家族产业具体地址
     */
    private String industryLocation;


    /*家族名人*/
    /**
     * 人名
     */
    private String personName;
    /**
     * 人物简介
     */
    private String personSummary;
    /**
     * 头像图片位置
     */
    private String picFileSrc;
    /**
     * 头像名
     */
    private String picFileName;

}
