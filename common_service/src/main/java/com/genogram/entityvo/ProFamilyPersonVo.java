package com.genogram.entityvo;

import com.genogram.entity.ProNewsUploadFile;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Data
public class ProFamilyPersonVo {
    /**
     * 家族名人
     */
    private List<ProNewsUploadFile> proNewsUploadFilesList;
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
     * 1代表家族文化 2 代表记录家族 3代表家族名人
     */
    private int source;
}
