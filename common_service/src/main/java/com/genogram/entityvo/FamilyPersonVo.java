package com.genogram.entityvo;

import com.genogram.entity.FanNewsFamousPerson;
import com.genogram.entity.FanNewsUploadFile;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 推荐表Vo表
 *
 * @Author: yuzhou
 * @Date: 2018-11-26
 * @Time: 11:10
 * @Param:
 * @return:
 * @Description:
 */
@Data
public class FamilyPersonVo {


    /**
     * 推荐表主键
     */
    private int recommendId;

    /**
     * 联谊会名称
     */
    private String sizeName;


    /**
     * 家族名人
     */
    private List<FanNewsUploadFile> fanNewsUploadFileList;
    /**
     * 用户名
     */
    private String createUserName;
    private String updateUserName;
    private long updateTimeLong;
    private long createTimeLong;

    /**
     * 编号
     */
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    private Integer showId;
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
    /**
     * 查看数
     */
    private Integer visitNum;
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
     * 1代表家族文化 2 代表家族产业 3代表记录家族 4代表家族名人
     */
    private int source;
}
