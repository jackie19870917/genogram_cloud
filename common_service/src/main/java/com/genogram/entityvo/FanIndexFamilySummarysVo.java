package com.genogram.entityvo;

import lombok.Data;

import java.util.Date;

/**
 *      联谊堂信息
 *@Author: Toxicant
 *@Date: 2018-11-09
 *@Time: 16:45
 *@Param:
 *@return:
 *@Description:
*/
@Data
public class FanIndexFamilySummarysVo {

    private Integer id;
    /**
     * 网站id
     */
    private Integer siteId;
    /**
     * 堂号
     */
    private String rootGroup;
    /**
     * 始迁祖
     */
    private String rootPerson;
    /**
     * 负责人
     */
    private Integer leader;
    /**
     * 负责人姓名
     */
    private String leaderName;
    /**
     * 负责人电话
     */
    private String leaderPhone;
    /**
     * 膜拜数
     */
    private Integer worshipNum;
    /**
     * 点赞数
     */
    private Integer praiseNum;
    /**
     * 状态(0:删除;1:显示;2:不显示)
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
     * 修改时间
     */
    private Date updateTime;
    /**
     * 修改人
     */
    private Integer updateUser;

}
