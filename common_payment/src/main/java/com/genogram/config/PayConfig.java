package com.genogram.config;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

/**
 * 微信支付配置对象
 *
 * @author Toxicant
 * @date 2016/10/31
 */
public class PayConfig implements WXPayConfig {

    /**
     * 获取 App ID（企业方公众号Id）
     *
     * @return
     */
    @Override
    public String getAppID() {
        return "wxb192063260e82181";
    }

    /**
     * 获取 Mch ID（商户账号）
     *
     * @return
     */
    @Override
    public String getMchID() {
        return "1516336491";
    }

    /**
     * 获取 API 密钥
     *
     * @return
     */
    @Override
    public String getKey() {
        return "JpjwyhWoD247nBnyVL4RTadu3dhCbstI";
    }

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    /**
     * 回调URL(该回调的地址一定要是通过微信官方认证的)
     *
     * @return
     */
    public String getNotifyUrl() {
        return "http://www.yhtpw.com:8090/genogram/pay/callback";
    }

    @Override
    /**
     * 获取商户证书内容（我们这里不需要证书）
     */
    public InputStream getCertStream() {
        return null;
    }
}