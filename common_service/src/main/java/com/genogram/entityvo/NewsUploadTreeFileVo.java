package com.genogram.entityvo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangwei
 * @since 2018-12-14
 */
@Data
public class NewsUploadTreeFileVo {


    private Integer id;
    /**
     * 网站id
     */
    private Integer siteId;
    /**
     * 地区
     */
    private String regionCode;
    /**
     * 来源(0-省级,1-县级)
     */
    private Integer isFrom;
    /**
     * 姓氏
     */
    private Integer familyCode;
    /**
     * 姓氏
     */
    private String familyName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 联系人
     */
    private String contactUser;
    /**
     * 状态(1-公开,2-密码访问,3-私密,0-删除)
     */
    private Integer status;
    /**
     * 访问密码(公开状态,密码为null)
     */
    private String password;
    /**
     * 前三十页(1-显示,2-不显示)
     */
    private Integer preThirty;
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

}
