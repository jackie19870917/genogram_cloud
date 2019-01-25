package com.genogram.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.genogram.config.WeChatConfig;
import com.genogram.unit.HttpUtil;
import com.genogram.unit.WxUnitl;
import com.genogram.unit.WeiXinUnitl;
import com.genogram.unit.WinXinEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Toxicant
 */
@Api(description = "分享")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/weixin")
public class WeiXinController {

    Logger log = LoggerFactory.getLogger(WeiXinController.class);

    @ApiOperation("自定义分享")
    @RequestMapping("/sgture")
    public Map<String, Object> sgture(HttpServletRequest request) {
        String strUrl = request.getParameter("url");
        WinXinEntity wx = WeiXinUnitl.getWinXinEntity(strUrl);
        // 将wx的信息到给页面
        Map<String, Object> map = new HashMap<String, Object>(16);
        String sgture = WxUnitl.getSignature(wx.getTicket(), wx.getNoncestr(), wx.getTimestamp(), strUrl);
        //签名
        map.put("sgture", sgture.trim());
        //时间戳
        map.put("timestamp", wx.getTimestamp().trim());
        //随即串
        map.put("noncestr", wx.getNoncestr().trim());
        //appID
        map.put("appid", WeChatConfig.APP_ID);
        return map;
    }

    private JSONObject getAccessToken() {
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        String requestUrl = accessTokenUrl.replace("APPID", WeChatConfig.APP_ID).replace("APPSECRET", WeChatConfig.APP_SECRET);
        log.info("getAccessToken.requestUrl====>" + requestUrl);
        JSONObject result = HttpUtil.doGet(requestUrl);
        return result;
    }

    /**
     * 获取ticket
     *
     * @param request
     * @return
     */
    private JSONObject getJsApiTicket(HttpServletRequest request) {
        String apiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
        String requestUrl = apiTicketUrl.replace("ACCESS_TOKEN", (String) request.getSession().getAttribute("accessToken"));
        log.info("getJsApiTicket.requestUrl====>" + requestUrl);
        JSONObject result = HttpUtil.doGet(requestUrl);
        return result;
    }

    /**
     * 生成微信权限验证的参数
     *
     * @param jsApiTicket
     * @param url
     * @return
     */
    public Map<String, String> makeWXTicket(String jsApiTicket, String url) {
        Map<String, String> ret = new HashMap<String, String>(16);
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsApiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        log.info("String1=====>" + string1);
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            log.info("signature=====>" + signature);
        } catch (NoSuchAlgorithmException e) {
            log.error("WeChatController.makeWXTicket=====Start");
            log.error(e.getMessage(), e);
            log.error("WeChatController.makeWXTicket=====End");
        } catch (UnsupportedEncodingException e) {
            log.error("WeChatController.makeWXTicket=====Start");
            log.error(e.getMessage(), e);
            log.error("WeChatController.makeWXTicket=====End");
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsApiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appid", WeChatConfig.APP_ID);

        return ret;
    }

    /**
     * 字节数组转换为十六进制字符串
     *
     * @param hash
     * @return
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 生成随机字符串
     *
     * @return
     */
    private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成时间戳
     *
     * @return
     */
    private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
