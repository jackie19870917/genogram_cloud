package com.genogram.unit;

public enum ResponseEnum
{
    UNKNOW_ERROR(-1, "未知错误"), 
    SUCCESS(200, "请求成功"), 
    ERROR(0, "请求失败"),
    ROLENAME_IS_NULL(201, "角色名为空"),
    KEY_IS_NULL(202, "主键为空"),
    MARK_IMG_ERROR(203, "添加水印失败"),
    FILE_IS_NULL(204, "文件为空"),
    FILE_SIZE_FULL(205, "文件大小超出")
    ;
    private int code;
    private String msg;

    private ResponseEnum(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public int getCode()
    {
        return code;
    }

    public String getMsg()
    {
        return msg;
    }

}
