package com.genogram.unit;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.genogram.config.WeChatConfig;
import net.sf.json.JSONObject;

/**
 * @author Toxicant
 */
public class WeiXinUnitl {
    public static WinXinEntity getWinXinEntity(String url) {
        WinXinEntity wx = new WinXinEntity();
        String access_token = getAccessToken();
        String ticket = getTicket(access_token);
        Map<String, String> ret = Sign.sign(ticket, url);
        wx.setTicket(ret.get("jsapi_ticket"));
        wx.setSignature(ret.get("signature"));
        wx.setNoncestr(ret.get("nonceStr"));
        wx.setTimestamp(ret.get("timestamp"));
        return wx;
    }

    /**
     * 获取token
     *
     * @return
     */
    private static String getAccessToken() {
        String access_token = "";
        //获取access_token填写client_credential
        String grant_type = "client_credential";
        //第三方用户唯一凭证
        String AppId = WeChatConfig.APP_ID;
        //第三方用户唯一凭证密钥，即appsecret
        String secret = WeChatConfig.APP_SECRET;
        //这个url链接地址和参数皆不能变  

        //访问链接
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + grant_type + "&appid=" + AppId + "&secret=" + secret;

        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            // 必须是get方式请求
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes);
            JSONObject demoJson = JSONObject.fromObject(message);
            access_token = demoJson.getString("access_token");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return access_token;
    }

    /**
     * 获取ticket
     *
     * @param access_token
     * @return
     */
    private static String getTicket(String access_token) {
        String ticket = null;
        // 这个url链接和参数不能变
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            // 必须是get方式请求
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            // 连接超时30秒
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            // 读取超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            ticket = demoJson.getString("ticket");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;
    }
}
