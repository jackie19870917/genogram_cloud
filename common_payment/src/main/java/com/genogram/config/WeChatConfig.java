package com.genogram.config;

/**
 * @author Administrator
 */
public class WeChatConfig {

    /**
     * 公众号AppId
     */
    public static final String APP_ID = "wxb192063260e82181";

    /**
     * 公众号AppSecret
     */
    public static final String APP_SECRET = "b4c1d0d15c5545e4ff26caed1b1551af";

    /**
     * 微信支付商户号
     */
    public static final String MCH_ID = "1516336491";

    /**
     * 微信支付API秘钥
     */
    public static final String KEY = "JpjwyhWoD247nBnyVL4RTadu3dhCbstI";

    /**
     * 微信支付api证书路径
     */
    public static final String CERT_PATH = "***/apiclient_cert.p12";

    /**
     * 微信统一下单url
     */
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 微信申请退款url
     */
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 微信支付通知url
     */
    public static final String NOTIFY_URL = "http://www.yhtpw.com/fanApiWebService/genogram/pay/callBack";

    /**
     * 微信交易类型:公众号支付
     */
    public static final String TRADE_TYPE_JSAPI = "JSAPI";

}
