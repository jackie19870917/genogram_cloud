package com.genogram.config;

/**  
 * 常量定义类  final   
 * Created by dongxinyou on 2018/4/13.
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
    
	// fastDFS配置文件地址
	public static final String SITE_FAST_DFS = "E:\\yu\\genealogy\\swocean\\src\\main\\resources\\fastDFS.properties";
	// fastDFS服务器ip地址
	public static final String IP_FAST_DFS = "http://192.168.2.132:8090/";
	//返回状态码 成功
	public static final Integer SUCCESSFUL_CODE = 200;
	//返回状态码 失败
	public static final Integer FAILURE_CODE = 500;
	//返回状态码 错误
	public static final Integer ERRO_CODE = 400;
	//返回状态码 为空
    public static final Integer IS_EMPTY = 204;
}
