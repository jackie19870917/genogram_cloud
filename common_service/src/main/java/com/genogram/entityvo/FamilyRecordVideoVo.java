package com.genogram.entityvo;

import com.genogram.entity.ProNewsUploadFile;
import com.genogram.entity.ProNewsUploadVedio;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Data
public class FamilyRecordVideoVo {
    //记录家族视频
    /**
     * 视频概要
     */
    private List<ProNewsUploadVedio> fanUploadVedioList;

    /**
     * 封面
     */
    private List<ProNewsUploadFile> fanNewsUploadFile;


    /**
     * 文件上传ID
     */
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    private Integer showId;
    /**
     * 视频种类1.个人;2官方
     */
    private Integer vedioType;
    /**
     * 视频标题
     */
    private String title;
    /**
     * 视频作者
     */
    private String auth;
    /**
     * 状态(0:删除;1:显示;2:不显示)
     */
    private Integer status;
    private Date createTime;
    private Integer createUserName;
    private Date updateTime;
    private Integer updateUserName;
    /**
     * 创建时间LONG
     */
    private Long updateTimeLong;

    /**
     * 修改时间LONG
     */
    private Long createTimeLong;

    /**
     * 评论数
     */
    private Integer commentCount;
}
