package com.genogram.config;

/**
 * 常量定义类  final
 *
 * @Author:
 * @Date: 2018-11-09
 * @Time: 15:55
 * @Param:
 * @return:
 * @Description:
 */
public final class Constants {
    public static final String SUNDAY = "SUNDAY";
    public static final String MONDAY = "MONDAY";
    public static final String TUESDAY = "TUESDAY";
    public static final String WEDNESDAY = "WEDNESDAY";
    public static final String THURSDAY = "THURSDAY";
    public static final String FRIDAY = "FRIDAY";
    public static final String SATURDAY = "SATURDAY";
    public static final String LOGIN_LOG = "login";
    public static final String OPERATION_LOG = "operation";
    public static final String ERROR_LOG = "error";
    public static final String OTHER_LOG = "other";
    public static final String DATABASE_TABLE_NON_EXIST = "表不存在";
    public static final Integer DATABASE_TABLE_CODE = 101;

    /**
     * 返回状态码 成功
     */
    public static final Integer SUCCESSFUL_CODE = 200;

    /**
     * 返回状态码 失败
     */
    public static final Integer FAILURE_CODE = 500;

    /**
     * 返回状态码 错误 没有查到参数
     */
    public static final Integer ERRO_CODE = 400;
    /**
     * 返回状态码 敏感词汇
     */
    public static final Integer SENSITIVE_WORD = 205;
    /**
     * 返回状态码 数据为空
     */
    public static final Integer IS_EMPTY = 204;

    /**
     * 返回状态码 没有登陆
     */
    public static final Integer NOTLOGIN = 400;

    /**
     * 返回状态码 未被授权
     */
    public static final Integer UNAUTHORIZED = 401;

    /**
     * visitNum  省级查看数超过400推荐
     */
    public static final Integer PRO_VISIT_NUM = 400;

    /**
     * visitNum  县级查看数超过200推荐
     */
    public static final Integer FAN_VISIT_NUM = 200;

    /**
     * 电子谱预览地址
     */
    //http://192.168.2.122:8083/fileConventer?filePath=http://47.105.177.1:6090/00/01/rB_QCFwXNg-AdtcNAABhJxiBuv8808.doc
    /**
     * 本地电子谱预览地址
     */
    public static final String ELECTRONIC_SPECTRUM_PREVIEW_IP = "http://192.168.2.122:8083/fileConventer?filePath=http://192.168.2.179:6090/";
    //阿里云 47.105.177.1:6090/
    //public static final String a="http://47.105.177.1:8083/fileConventer?filePath=http://192.168.2.179:6090/";
}
