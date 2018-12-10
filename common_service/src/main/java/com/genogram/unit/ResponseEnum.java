package com.genogram.unit;

/**
 * @param null
 * @Author: wang, wei
 * @Date: 2018-11-05
 * @Time: 22:23
 * @return:
 * @Description:
 */
public enum ResponseEnum {
    /**
     * agree
     */
    UNKNOW_ERROR(-1, "未知错误"),
    /**
     * controller 返回成功
     */
    SUCCESS(200, "请求成功"),
    /**
     * agree
     */
    ERROR(0, "请求失败"),
    /**
     * agree
     */
    ROLENAME_IS_NULL(201, "角色名为空"),
    /**
     * agree
     */
    KEY_IS_NULL(202, "主键为空"),
    /**
     * agree
     */
    MARK_IMG_ERROR(203, "添加水印失败"),
    /**
     * agree
     */
    FILE_IS_NULL(204, "文件为空"),
    /**
     * agree
     */
    FILE_SIZE_FULL(205, "文件大小超出");
    private int code;
    private String msg;

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
