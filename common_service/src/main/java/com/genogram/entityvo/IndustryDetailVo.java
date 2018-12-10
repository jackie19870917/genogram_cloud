package com.genogram.entityvo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.genogram.entity.FanNewsUploadFile;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 产业详情
 *
 * @Author: yuzhou
 * @Date: 2018-11-10
 * @Time: 17:51
 * @Param:
 * @return:
 * @Description:
 */
@Data
public class IndustryDetailVo {

    /**
     * 推荐表主键
     */
    private int recommendId;

    /**
     * 联谊会名称
     */
    private String sizeName;


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

    /**
     * 分类1代表家族文化 2代表记录家族 3代表家族产业
     */
    private int source;

    /**
     * 用户名
     */
    private String createUserName;
    private String updateUserName;
    private long updateTimeLong;
    private long createTimeLong;
    /**
     * 附件图片
     */
    private List<FanNewsUploadFile> fanNewsUploadFileList;

}
