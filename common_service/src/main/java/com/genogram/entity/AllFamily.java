package com.genogram.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 姓氏表
 * </p>
 *
 * @author wangwei
 * @since 2018-12-03
 */
@TableName("all_family")
public class AllFamily extends Model<AllFamily> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 姓氏
     */
    private String value;
    /**
     * 拼音
     */
    private String spell;
    /**
     * 手写字母大写
     */
    @TableField("first_letter")
    private String firstLetter;
    /**
     * 排序
     */
    private Integer sort;


    public Integer getId() {
        return id;
    }

    public AllFamily setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return value;
    }

    public AllFamily setValue(String value) {
        this.value = value;
        return this;
    }

    public String getSpell() {
        return spell;
    }

    public AllFamily setSpell(String spell) {
        this.spell = spell;
        return this;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public AllFamily setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public AllFamily setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AllFamily{" +
        ", id=" + id +
        ", value=" + value +
        ", spell=" + spell +
        ", firstLetter=" + firstLetter +
        ", sort=" + sort +
        "}";
    }
}
