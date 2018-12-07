package com.genogram.entityvo;

import com.genogram.entity.FanNewsUploadFile;
import com.genogram.entity.ProNewsUploadFile;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 慈善公益收支文章
 *
 * @Author: Toxicant
 * @Date: 2018-11-09
 * @Time: 10:58
 * @Param:
 * @return:
 * @Description:
 */
@Data
public class NewsCharityOutVo {

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
     * 标题(财务支出文章)
     */
    private String newsTitle;
    /**
     * 内容
     */
    private String newsText;
    /**
     * 文章类别(1.财政支出;2.财政收入)
     */
    private Integer newsType;
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
     * 文章的图片(联谊会)
     */
    List<FanNewsUploadFile> fanNewsUploadFileList;

    /**
     * 文章的图片(省级)
     */
    List<ProNewsUploadFile> proNewsUploadFileList;

    /**
     * 图片
     */
    List newsUploadFileList;
}
