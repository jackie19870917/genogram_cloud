package com.genogram.entityvo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entity.ProNewsUploadFile;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 文章详情
 *
 * @Author: zhouyu
 * @Date: 2018-11-09
 * @Time: 10:41
 * @Param:
 * @return:
 * @Description:
 */
@Data
public class NewsDetailVo {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    private Integer showId;
    /**
     * 财务支出金额
     */
    private BigDecimal amount;
    /**
     * (财务支出)用途
     */
    private String useFor;
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
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 分类1代表家族文化 2代表记录家族 3代表家族产业
     */
    private int source;

    /**
     * 创建时间
     */
    private Long updateTimeLong;

    /**
     * 修改时间
     */
    private Long createTimeLong;

    /**
     * 附件图片
     */
    private List newsUploadFileList;
}
