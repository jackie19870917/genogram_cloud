package com.genogram.entityvo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.genogram.entity.FanNewsUploadFile;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *
 *@Author: zhouyu
 *@Date: 2018-11-09
 *@Time: 10:41
 *@Param:
 *@return:
 *@Description:
*/
@Data
public class NewsDetailVo {

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    @TableField("show_id")
    private Integer showId;
    /**
     * 标题
     */
    @TableField("news_title")
    private String newsTitle;
    /**
     * 内容
     */
    @TableField("news_text")
    private String newsText;
    /**
     * 查看数
     */
    @TableField("visit_num")
    private Integer visitNum;
    /**
     * 状态(0:删除;1:已发布;2:草稿3:不显示)
     */
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人
     */
    @TableField("create_user")
    private Integer createUser;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    @TableField("update_user")
    private Integer updateUser;
    /**
     * 修改人
     */

    //用户名
    private String userName;

    //附件图片
    private List<FanNewsUploadFile> fanNewsUploadFileList;


}
