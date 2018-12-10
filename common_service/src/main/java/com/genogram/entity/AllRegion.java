package com.genogram.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 地区表
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
@TableName("all_region")
public class AllRegion extends Model<AllRegion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 地区编号
     */
    private Integer code;
    /**
     * 地区名
     */
    private String name;
    /**
     * 父code
     */
    @TableField("parent_code")
    private Integer parentCode;
    /**
     * 1.县;2.省
     */
    private Integer type;


    public Integer getId() {
        return id;
    }

    public AllRegion setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public AllRegion setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public AllRegion setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getParentCode() {
        return parentCode;
    }

    public AllRegion setParentCode(Integer parentCode) {
        this.parentCode = parentCode;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public AllRegion setType(Integer type) {
        this.type = type;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllRegion{" +
                ", id=" + id +
                ", code=" + code +
                ", name=" + name +
                ", parentCode=" + parentCode +
                ", type=" + type +
                "}";
    }
}
