package com.genogram.entityvo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCultureZipai;
import lombok.Data;

import java.util.Date;
/**
 *字派查询
 *@Author: yuzhou
 *@Date: 2018-11-12
 *@Time: 16:46
 *@Param:
 *@return:
 *@Description:
*/
@Data
public class FanNewsCultureZipaiVo {

    /**
     * 主键字派ID
     */
    private Integer id;
    /**
     * 显示位置id(fan_sys_web_news_show_id)
     */
    private Integer showId;
    /**
     * 字派具体地域
     */
    private String ziapiLocation;
    /**
     * 祖先名
     */
    private String ancestorsName;
    /**
     * 字派数组:数字和字的组合
     */
    private String zipaiTxt;
    /**
     * 访问数
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
     * 创建时间LONG
     */
    private Long updateTimeLong;

    /**
     * 修改时间LONG
     */
    private Long createTimeLong;

}
