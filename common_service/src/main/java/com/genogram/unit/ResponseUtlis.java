package com.genogram.unit;

/**
 *
 * @Author: wang,wei
 * @Date: 2018-11-05
 * @Time: 22:21
 * @return:
 * @Description:
 *
 */
public class ResponseUtlis
{
    public static Response success(Object object)
    {
        Response response = new Response();
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMsg(ResponseEnum.SUCCESS.getMsg());
        response.setData(object);
        return response;
    }

    public static Response success()
    {
        return success(null);
    }

    public static Response error(Integer code,Object object)
    {
        Response response = new Response();
        response.setCode(code);
        response.setMsg(code.toString());
        response.setData(object);
        return response;
    }
}
