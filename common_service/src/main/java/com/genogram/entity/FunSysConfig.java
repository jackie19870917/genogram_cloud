package com.genogram.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 联谊会基础配置表
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@TableName("fun_sys_config")
public class FunSysConfig extends Model<FunSysConfig> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 常量组
     */
    @TableField("constant_group")
    private String constantGroup;
    /**
     * 常量名称
     */
    @TableField("constant_name")
    private String constantName;
    /**
     * 常量编码
     */
    @TableField("constant_code")
    private String constantCode;
    /**
     * 备注
     */
    private String comment;


    public String getId() {
        return id;
    }

    public FunSysConfig setId(String id) {
        this.id = id;
        return this;
    }

    public String getConstantGroup() {
        return constantGroup;
    }

    public FunSysConfig setConstantGroup(String constantGroup) {
        this.constantGroup = constantGroup;
        return this;
    }

    public String getConstantName() {
        return constantName;
    }

    public FunSysConfig setConstantName(String constantName) {
        this.constantName = constantName;
        return this;
    }

    public String getConstantCode() {
        return constantCode;
    }

    public FunSysConfig setConstantCode(String constantCode) {
        this.constantCode = constantCode;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public FunSysConfig setComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FunSysConfig{" +
        ", id=" + id +
        ", constantGroup=" + constantGroup +
        ", constantName=" + constantName +
        ", constantCode=" + constantCode +
        ", comment=" + comment +
        "}";
    }
}
