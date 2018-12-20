package com.genogram.entityvo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.genogram.entity.FanNewsIndustry;
import com.genogram.entity.FanNewsUploadFile;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 家族产业查询
 *
 * @Author: yuzhou
 * @Date: 2018-11-09
 * @Time: 15:59
 * @Param:
 * @return:
 * @Description:
 */
@Data
public class FamilyIndustryVo {


    /**
     * 主键
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
     * 家族产业具体地址
     */
    private String industryLocation;
    /**
     * 种类(1:家族产业;2:个人产业)
     */
    private String type;
    /**
     * 查看数
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
     * 创建时间LONG
     */
    private Long updateTimeLong;

    /**
     * 修改时间LONG
     */
    private Long createTimeLong;


    /**
     * 家族产业图片
     */
    private List newsUploadFileList;

}
