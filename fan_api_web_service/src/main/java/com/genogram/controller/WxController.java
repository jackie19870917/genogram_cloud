package com.genogram.controller;

import com.alibaba.fastjson.JSON;
import com.genogram.config.WeChatConfig;
import com.genogram.unit.NetUtil;
import com.genogram.unit.Oauth2Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 支付
 *
 * @author Toxicant
 * @date 2016/10/31
 */
@Api(description = "跳转")
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/wx")
public class WxController {

    Logger log = LoggerFactory.getLogger(WxController.class);


    @ApiOperation("回调")
    @RequestMapping("oauth2WeChat")
    public String oauth2WeChat(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");

        /**
         *  用户同意授权
         */
        String authdeny = "authdeny";
        if (!authdeny.equals(code)) {
            // 获取网页授权access_token
            Oauth2Token oauth2Token = getOauth2AccessToken(WeChatConfig.APP_ID, WeChatConfig.APP_SECRET, code);
            System.out.println("oauth2Token信息：" + oauth2Token.toString());

            // 网页授权接口访问凭证
            String accessToken = oauth2Token.getAccessToken();

            // 用户标识
            String openId = oauth2Token.getOpenId();

            HttpSession session = request.getSession();
            session.setAttribute("accessToken", accessToken);
            session.setAttribute("openId", openId);

            System.out.println("首次的openId=" + openId);
            // 获取用户信息
            //SnsUserInfo snsUserInfo = getSNSUserInfo(accessToken, openId);
            // System.out.println("***********************************用户信息unionId：" + snsUserInfo.getUnionid() + "***:" + snsUserInfo.getNickname());
            // 设置要传递的参数

            // 具体业务start


            return "index";
        } else {
            return null;
        }
    }

    /**
     * 获取网页授权凭证
     *
     * @param appId     公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @param code
     * @return WeixinAouth2Token
     */
    public Oauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        Oauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(NetUtil.get(requestUrl));
        if (null != jsonObject) {
            try {
                wat = new Oauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInteger("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInteger("errcode");
                String errorMsg = jsonObject.getString("errmsg");

                log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }
}
