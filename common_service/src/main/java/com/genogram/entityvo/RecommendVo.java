package com.genogram.entityvo;

import lombok.Data;

import java.util.Date;

@Data
public class RecommendVo {

    /**
     * 推荐主键
     */
    private Integer id;
    /**
     * 显示位置id
     */
    private Integer showId;
    /**
     * 文章id
     */
    private Integer newsId;
    /**
     * 是否省显示(0:否;1是)
     */
    private Integer isProvince;
    /**
     * 是否全国显示(0:否;1是)
     */
    private Integer isCountry;
    /**
     * 状态(0:删除;2:通过正常显示;1:审核中3:不通过不显示)
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
     * 创建时间
     */
    private Long createTimeLong;
    /**
     * 修改时间
     */
    private Long updateTimeLong;
}
