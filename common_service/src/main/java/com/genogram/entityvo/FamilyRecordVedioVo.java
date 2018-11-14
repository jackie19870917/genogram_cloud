package com.genogram.entityvo;


import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entity.FanNewsUploadVedio;
import lombok.Data;
import java.util.Date;
import java.util.List;
@Data
public class FamilyRecordVedioVo{
    //记录家族视频
    /**
     * 视频概要
     */
    private List<FanNewsUploadVedio> fanUploadVedioList;

    /**
     * 封面
     */
    private List<FanNewsUploadFile> fanNewsUploadFileList;


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
    private Integer createUser;
    private Date updateTime;
    private Integer updateUser;
}
