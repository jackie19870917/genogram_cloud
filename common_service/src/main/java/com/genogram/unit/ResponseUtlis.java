package com.genogram.unit;


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

    public static Response error(Integer code, String msg)
    {
        Response response = new Response();
        response.setCode(code);
        response.setMsg(msg);
        response.setData(null);
        return response;
    }
}
