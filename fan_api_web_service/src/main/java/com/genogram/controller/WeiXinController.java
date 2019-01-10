package com.genogram.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.genogram.config.WeChatConfig;
import com.genogram.unit.WXUnitl;
import com.genogram.unit.WeiXinUnitl;
import com.genogram.unit.WinXinEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "分享")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/weixin")
public class WeiXinController {

    @ApiOperation("自定义分享")
    @RequestMapping("/sgture.do")
    public Map<String, Object> sgture(HttpServletRequest request) {
        String strUrl = request.getParameter("url");
        WinXinEntity wx = WeiXinUnitl.getWinXinEntity(strUrl);
        // 将wx的信息到给页面
        Map<String, Object> map = new HashMap<String, Object>();
        String sgture = WXUnitl.getSignature(wx.getTicket(), wx.getNoncestr(), wx.getTimestamp(), strUrl);
        map.put("sgture", sgture.trim());//签名
        map.put("timestamp", wx.getTimestamp().trim());//时间戳
        map.put("noncestr", wx.getNoncestr().trim());//随即串
        map.put("appid", WeChatConfig.APP_ID);//appID
        return map;
    }

}
