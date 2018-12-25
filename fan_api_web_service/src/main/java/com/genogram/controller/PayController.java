package com.genogram.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.genogram.config.AlipayConfig;
import com.genogram.config.Constants;
import com.genogram.config.PayConfig;
import com.genogram.config.WeChatConfig;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanIndexFund;
import com.genogram.entity.FanNewsCharityPayIn;
import com.genogram.service.*;
import com.genogram.unit.*;
import com.github.wxpay.sdk.WXPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import static com.genogram.unit.WeChatConstant.TOKEN;

/**
 * 支付
 *
 * @author Toxicant
 * @date 2016/10/31
 */
@Api(description = "支付")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/pay")
public class PayController {

    Logger log = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private IFanNewsCharityPayInService fanNewsCharityPayInService;

    @Autowired
    private IFanSysWebNewsShowService fanSysWebNewsShowService;

    @Autowired
    private IFanIndexFundService fanIndexFundService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    private String baseUrl;

    @Value("${pay_notify_url}")
    public String notifyUrl;

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     * http://192.168.2.179:8090/genogram/pay/return_url
     */
    @Value("${pay_return_url}")
    public String returnUrl;

    @ApiOperation(value = "支付宝支付", notes = "id:主键,showId:显示位置,payUsrId:捐款人,payAmount:捐款金额")
    @RequestMapping(value = "aLiPay", method = RequestMethod.POST)
    public Response<FanNewsCharityPayIn> aLiPay(FanNewsCharityPayIn fanNewsCharityPayIn,
                                                @ApiParam("网站ID") @RequestParam Integer siteId,
                                                @ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                @ApiParam("是否匿名(1-匿名,0-不匿名)") @RequestParam("anonymous") Integer anonymous,
                                                @ApiParam("回调地址") @RequestParam(value = "url") String url) throws IOException {


        this.baseUrl = url;
        // 获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type);

        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);

        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String outTradeNo = DateUtil.getAllTime() + String.format("%02d", new Random().nextInt(100));

        // 付款金额，必填
        String totalAmount = fanNewsCharityPayIn.getPayAmount().toString();

        // 订单名称，必填
        String subject = "炎黄网在线支付宝支付";

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeoutExpress = "2h";

        Integer showId = fanSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(siteId, "index_architecture_pay_in_person").getShowId();

        String payChannel = "支付宝支付";

        AllUserLogin userLogin = new AllUserLogin();

        if (1 == anonymous) {
            userLogin.setId(1);
        } else {
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
            } else {
                userLogin = userService.getUserLoginInfoByToken(token);
            }
        }

        fanNewsCharityPayIn.setOrderId(outTradeNo);
        fanNewsCharityPayIn.setShowId(showId);
        fanNewsCharityPayIn.setPayUsrId(userLogin.getId());
        fanNewsCharityPayIn.setType(1);
        fanNewsCharityPayIn.setStatus(2);
        fanNewsCharityPayIn.setPayChannel(payChannel);

        fanNewsCharityPayInService.insertFanNewsCharityPayIn(fanNewsCharityPayIn);

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\"," + "\"total_amount\":\"" + totalAmount
                + "\"," + "\"subject\":\"" + subject + "\"," + "\"timeout_express\":\"" + timeoutExpress + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        // 请求
        try {
            String result = alipayClient.pageExecute(alipayRequest).getBody();

            return ResponseUtlis.success(result);
        } catch (AlipayApiException e) {
            e.printStackTrace();

            return null;
        }
    }

    @RequestMapping(value = "/return_url")
    public void aLiPayReturnNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info("支付成功, 进入同步通知接口...");

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>(16);
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        // 调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset,
                AlipayConfig.sign_type);

        if (signVerified) {

            // 商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String totalAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            FanNewsCharityPayIn fanNewsCharityPayIn = new FanNewsCharityPayIn();

            fanNewsCharityPayIn.setOrderId(outTradeNo);
            fanNewsCharityPayIn = fanNewsCharityPayInService.selectOne(fanNewsCharityPayIn);

            fanNewsCharityPayIn.setPayTime(DateUtil.getCurrentTimeStamp());
            fanNewsCharityPayIn.setStatus(1);

            fanNewsCharityPayInService.insertFanNewsCharityPayIn(fanNewsCharityPayIn);

            //修改基金金额
            Integer siteId = fanSysWebNewsShowService.getSiteIdByShowId(fanNewsCharityPayIn.getShowId()).getSiteId();

            FanIndexFund fanIndexFund = fanIndexFundService.getFanIndexFund(siteId);

            fanIndexFund.setRemain(fanIndexFund.getRemain().add(new BigDecimal(totalAmount)));
            fanIndexFund.setPayOnline(fanIndexFund.getPayOnline().add(new BigDecimal(totalAmount)));

            fanIndexFundService.insertOrUpdateFanIndexFund(fanIndexFund);

            log.info("********************** 支付成功(支付宝同步通知) **********************");
            log.info("* 订单号: {}", outTradeNo);
            log.info("* 支付宝交易号: {}", tradeNo);
            log.info("* 实付金额: {}", totalAmount);
            log.info("* 购买产品: {}", "炎黄统谱网在线支付宝扫码支付");
            log.info("***************************************************************");

            response.sendRedirect(this.baseUrl + "result=success&out_trade_no=" + outTradeNo + "&total_amount=" + totalAmount);

        } else {
            log.info("支付, 验签失败...");
            response.sendRedirect(this.baseUrl + "result=error");
        }

    }

    @RequestMapping("/notify_url")
    public String aLiPayNotifyNotice(HttpServletRequest request) throws Exception {

        log.info("支付成功, 进入异步通知接口...");

        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>(16);
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            params.put(name, valueStr);
        }

        // 调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset,
                AlipayConfig.sign_type);

        // ——请在这里编写您的程序（以下代码仅作参考）——

        /*
         * 实际验证过程建议商户务必添加以下校验：
         * 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
         * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
         * 3、校验通知中的seller_id（或者seller_email)是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
         * 4、验证app_id是否为该商户本身。
         */
        // 验证成功
        if (signVerified) {
            // 商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String totalAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            String tradeFinshed = "TRADE_FINISHED";
            String tradeSuccess = "TRADE_SUCCESS";

            if (tradeFinshed.equals(tradeStatus)) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序

                // 注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                // 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (tradeSuccess.equals(tradeStatus)) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序

                // 注意：
                // 付款完成后，支付宝系统发送该交易状态通知

                // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水

                log.info("********************** 支付成功(支付宝异步通知) **********************");
                log.info("* 订单号: {}", outTradeNo);
                log.info("* 支付宝交易号: {}", tradeNo);
                log.info("* 实付金额: {}", totalAmount);
                log.info("* 购买产品: {}", "炎黄统谱网在线支付宝扫码支付");
                log.info("***************************************************************");
            }
            log.info("支付成功...");

        } else {// 验证失败
            log.info("支付, 验签失败...");
        }

        return "success";
    }

    @ApiOperation("微信支付")
    @RequestMapping(value = "weChatPay", method = RequestMethod.POST)
    public Response weChatPay(HttpServletRequest request,
                              FanNewsCharityPayIn fanNewsCharityPayIn,
                              @ApiParam("网站ID") @RequestParam Integer siteId,
                              @ApiParam("token") @RequestParam(value = "token", required = false) String token,
                              @ApiParam("是否匿名(1-匿名,0-不匿名)") @RequestParam("anonymous") Integer anonymous) {

        Integer showId = fanSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(siteId, "index_architecture_pay_in_person").getShowId();

        String payChannel = "微信支付";

        AllUserLogin userLogin = new AllUserLogin();

        if (1 == anonymous) {
            userLogin.setId(1);
        } else {
            if (StringUtils.isEmpty(token)) {
                return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
            } else {
                userLogin = userService.getUserLoginInfoByToken(token);
            }
        }

        // 订单编号
        String payId = DateUtil.getAllTime() + String.format("%02d", new Random().nextInt(100));

        // 支付用户的id
        String userIp = PayUtils.getRemoteAddr(request);

        // 支付金额
        String totalFee = fanNewsCharityPayIn.getPayAmount() + "";

        // 商品描述
        String body = "炎黄統譜网在线微信扫码支付";

        fanNewsCharityPayIn.setOrderId(payId);
        fanNewsCharityPayIn.setShowId(showId);
        fanNewsCharityPayIn.setPayUsrId(userLogin.getId());
        fanNewsCharityPayIn.setType(1);
        fanNewsCharityPayIn.setStatus(2);
        fanNewsCharityPayIn.setPayChannel(payChannel);

        fanNewsCharityPayInService.insertFanNewsCharityPayIn(fanNewsCharityPayIn);

        // 回调地址
        String callback = new PayConfig().getNotifyUrl();

        // 生成一个code_url
        String codeUrl = PayUtils.pay(payId, userIp, totalFee, body, callback);

        if (StringUtils.isEmpty(codeUrl)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, "服务器正忙");
        } else {

            Map map = new HashMap(16);

            map.put("code_url", codeUrl);
            map.put("out_trade_no", payId);
            map.put("total_fee", totalFee);

            return ResponseUtlis.success(map);
        }
    }

    @ApiOperation("查询订单请求")
    @RequestMapping(value = "getFanNewsCharityPayIn", method = RequestMethod.POST)
    public Response<FanNewsCharityPayIn> getFanNewsCharityPayIn(@ApiParam("订单号") @RequestParam("outTradeNo") String outTradeNo) {

        FanNewsCharityPayIn fanNewsCharityPayIn = new FanNewsCharityPayIn();

        fanNewsCharityPayIn.setOrderId(outTradeNo);

        fanNewsCharityPayIn = fanNewsCharityPayInService.selectOne(fanNewsCharityPayIn);

        if (StringUtils.isEmpty(fanNewsCharityPayIn)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, "订单号不存在");
        } else {
            if (fanNewsCharityPayIn.getStatus() == 1) {

                return ResponseUtlis.success(fanNewsCharityPayIn);
            } else {
                return ResponseUtlis.error(201, null);
            }
        }
    }

    @ApiOperation("微信支付完成后的回调")
    @RequestMapping("callBack")
    public void callBack(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {

        InputStream is = request.getInputStream();
        // 取HTTP请求流长度
        int size = request.getContentLength();
        // 用于缓存每次读取的数据
        byte[] buffer = new byte[size];
        // 用于存放结果的数组
        byte[] xmldataByte = new byte[size];
        int count = 0;
        int rbyte = 0;
        // 循环读取
        while (count < size) {
            // 每次实际读取长度存于rbyte中
            rbyte = is.read(buffer);
            for (int i = 0; i < rbyte; i++) {
                xmldataByte[count + i] = buffer[i];
            }
            count += rbyte;
        }
        is.close();
        String requestStr = new String(xmldataByte, "UTF-8");
        System.out.println(requestStr);
        Document doc = DocumentHelper
                .parseText(requestStr);
        Element rootElt = doc.getRootElement();

        String outTradeNo = rootElt.elementText("out_trade_no");

        FanNewsCharityPayIn fanNewsCharityPayIn = new FanNewsCharityPayIn();

        fanNewsCharityPayIn.setOrderId(outTradeNo);
        fanNewsCharityPayIn = fanNewsCharityPayInService.selectOne(fanNewsCharityPayIn);

        Integer status = 2;
        if (fanNewsCharityPayIn.getStatus().equals(status)) {

            //修改基金金额
            Integer siteId = fanSysWebNewsShowService.getSiteIdByShowId(fanNewsCharityPayIn.getShowId()).getSiteId();

            FanIndexFund fanIndexFund = fanIndexFundService.getFanIndexFund(siteId);

            System.out.println(fanIndexFund.getRemain());
            fanIndexFund.setRemain(fanIndexFund.getRemain().add(fanNewsCharityPayIn.getPayAmount()));
            fanIndexFund.setPayOnline(fanIndexFund.getPayOnline().add(fanNewsCharityPayIn.getPayAmount()));

            fanIndexFundService.insertOrUpdateFanIndexFund(fanIndexFund);
        }

        fanNewsCharityPayIn.setPayTime(DateUtil.getCurrentTimeStamp());
        fanNewsCharityPayIn.setStatus(1);

        fanNewsCharityPayInService.insertFanNewsCharityPayIn(fanNewsCharityPayIn);

        //给微信返回支付成功结果
        String responseStr = "<xml>";
        responseStr += "<return_code><![CDATA[SUCCESS]]></return_code>";
        responseStr += "</xml>";

        response.getWriter().write(responseStr);
        System.out.println("responseStr2:" + responseStr);
    }

    /***
     * 微信浏览器内微信支付/公众号支付(JSAPI)
     * @param request
     * @param fanNewsCharityPayIn
     * @param siteId
     * @param token
     * @param anonymous
     * @return
     */
    @ApiOperation("微信公众号支付")
    @RequestMapping(value = "orders", method = RequestMethod.POST)
    public Response orders(HttpServletRequest request,
                           FanNewsCharityPayIn fanNewsCharityPayIn,
                           @ApiParam("网站ID") @RequestParam Integer siteId,
                           @ApiParam("token") @RequestParam(value = "token", required = false, defaultValue = "") String token,
                           @ApiParam("是否匿名(1-匿名,0-不匿名)") @RequestParam("anonymous") Integer anonymous) {

        try {

            Integer showId = fanSysWebNewsShowService.getSysWebNewsShowBySiteIdAndMenuCode(siteId, "index_architecture_pay_in_person").getShowId();

            String payChannel = "微信公众号支付";
            fanNewsCharityPayIn.getPayAmount().multiply(new BigDecimal(100));
            String totalFee = (int) Double.parseDouble(fanNewsCharityPayIn.getPayAmount().multiply(new BigDecimal(100)) + "") + "";

            // 订单编号
            String payId = DateUtil.getAllTime() + String.format("%02d", new Random().nextInt(100));

            HttpSession session = request.getSession();

            //获取openId
            String openId = (String) session.getAttribute("openId");

            //用户Id
            AllUserLogin userLogin = new AllUserLogin();

            if (1 == anonymous) {
                userLogin.setId(1);
            } else {

                String undefined = "undefined";
                if (token.equals(undefined)) {
                    token = "";
                }

                if (StringUtils.isEmpty(token)) {
                    return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
                } else {
                    userLogin = userService.getUserLoginInfoByToken(token);

                }
            }

            // 商品描述
            String body = "炎黄統譜网在线微信扫码支付";

            fanNewsCharityPayIn.setOrderId(payId);
            fanNewsCharityPayIn.setShowId(showId);
            fanNewsCharityPayIn.setPayUsrId(userLogin.getId());
            fanNewsCharityPayIn.setType(1);
            fanNewsCharityPayIn.setStatus(2);
            fanNewsCharityPayIn.setPayChannel(payChannel);

            fanNewsCharityPayInService.insertFanNewsCharityPayIn(fanNewsCharityPayIn);

            //拼接统一下单地址参数
            Map<String, String> paraMap = new HashMap<String, String>(16);
            //获取请求ip地址
            String ip = PayUtils.getRemoteAddr(request);

            paraMap.put("appid", WeChatConfig.APP_ID);
            paraMap.put("body", body);
            paraMap.put("mch_id", WeChatConfig.MCH_ID);
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
            paraMap.put("openid", openId);
            //订单号
            paraMap.put("out_trade_no", payId);
            paraMap.put("spbill_create_ip", ip);
            paraMap.put("total_fee", totalFee);
            paraMap.put("trade_type", WeChatConfig.TRADE_TYPE_JSAPI);
            // 此路径是微信服务器调用支付结果通知路径随意写
            paraMap.put("notify_url", WeChatConfig.NOTIFY_URL);
            String sign = WXPayUtil.generateSignature(paraMap, WeChatConfig.KEY);
            paraMap.put("sign", sign);
            //将所有参数(map)转xml格式
            String xml = WXPayUtil.mapToXml(paraMap);

            // 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
            String unifiedorderUrl = WeChatConfig.UNIFIED_ORDER_URL;

            //发送post请求"统一下单接口"返回预支付id:prepay_id
            String xmlStr = HttpRequest.sendPost(unifiedorderUrl, xml);

            System.out.println(xmlStr);

            //以下内容是返回前端页面的json数据
            //预支付id
            String prepayId = "";
            String success = "SUCCESS";
            if (xmlStr.indexOf(success) != -1) {
                Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);
                prepayId = (String) map.get("prepay_id");
            }
            Map<String, String> payMap = new HashMap<String, String>(16);
            payMap.put("appId", WeChatConfig.APP_ID);
            payMap.put("timeStamp", System.currentTimeMillis() + "");
            payMap.put("nonceStr", WXPayUtil.generateNonceStr());
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id=" + prepayId);
            String paySign = WXPayUtil.generateSignature(payMap, WeChatConfig.KEY);
            payMap.put("paySign", paySign);

            return ResponseUtlis.success(payMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("入口")
    @RequestMapping(value = "go", method = RequestMethod.GET)
    public void go(@ApiParam("访问编号") @RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Cookie cookie = new Cookie("codeNo", code);

        cookie.setMaxAge(60*60);   //存活期为一个月 30*24*60*60
        cookie.setPath("/");
        response.addCookie(cookie);

        HttpSession session = request.getSession();
        log.info("code:  " + code);
        session.setAttribute("codeNo", code);
        request.setAttribute("codeNo", code);
        // request.getRequestDispatcher("https://www.baidu.com").forward(request, response);
        // request.getRequestDispatcher("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb192063260e82181&redirect_uri=http://yhtpw.com/fanApiWebService/genogram/pay/oauth2WeChat?showwxpaytitle=1&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect").forward(request, response);
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb192063260e82181&redirect_uri=http://yhtpw.com/fanApiWebService/genogram/pay/oauth2WeChat?showwxpaytitle=1&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
    }

    @ApiOperation("默认请求")
    @RequestMapping(value = "GO", method = RequestMethod.GET)
    public Response Go()  {

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb192063260e82181&redirect_uri=http://yhtpw.com/fanApiWebService/genogram/pay/oauth2WeChat?showwxpaytitle=1&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
        return ResponseUtlis.success(url);
    }

    @ApiOperation("回调")
    @RequestMapping("oauth2WeChat")
    public Response oauth2WeChat(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

            System.out.println(openId);
            // 获取用户信息
            //SnsUserInfo snsUserInfo = getSNSUserInfo(accessToken, openId);
            // System.out.println("***********************************用户信息unionId：" + snsUserInfo.getUnionid() + "***:" + snsUserInfo.getNickname());
            // 设置要传递的参数

            // 具体业务start

            // 具体业务end

            byte[] bytes = Base64.encodeBase64(openId.getBytes(), true);
            openId = new String(bytes);

            // String url = "http://yhtpw.com/mobile/#/base?code=" + codeNo01 + "&openId=" + openId;
            //String url = "http://yhtpw.com/mobile";
            //log.info(url);
            // response.sendRedirect(url);
            return ResponseUtlis.success(openId);
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

    /**
     * 通过网页授权获取用户信息
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openId      用户标识
     * @return SNSUserInfo
     */
    public SnsUserInfo getSNSUserInfo(String accessToken, String openId) {
        SnsUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(NetUtil.get(requestUrl));

        if (null != jsonObject) {
            try {
                snsUserInfo = new SnsUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getInteger("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 用户特权信息
                List<String> list = JSON.parseArray(jsonObject.getString("privilege"), String.class);
                snsUserInfo.setPrivilegeList(list);
                //与开放平台共用的唯一标识，只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
                snsUserInfo.setUnionid(jsonObject.getString("unionid"));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInteger("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return snsUserInfo;
    }

    @ApiOperation("微信验证")
    @RequestMapping("contactWeChat")
    public void contactWeChat(HttpServletRequest request,
                              HttpServletResponse response) {
        String method = request.getMethod();
        String remoteHost = request.getRemoteHost();
        String remoteAddr = request.getRemoteAddr();
        System.out.println("method :" + method);
        System.out.println("remoteHost :" + remoteHost + " remoteAddr :"
                + remoteAddr);
        try {
            String get = "GET";
            if (get.equals(method)) {
                // 微信加密签名
                // ceb87cf6583bdd37bc49fb7b10fc42f4c3ae4bf2
                String signature = request.getParameter("signature");
                System.out.println("微信加密签名" + signature);
                // 随机字符串
                String echostr = request.getParameter("echostr");
                System.out.println("随机字符串" + echostr);
                // 时间戳
                String timestamp = request.getParameter("timestamp");
                System.out.println("时间戳" + timestamp);
                // 随机数
                String nonce = request.getParameter("nonce");
                System.out.println("随机数" + nonce);

                if (null != signature) {
                    String[] str = {TOKEN, timestamp, nonce};
                    // 字典序排序
                    Arrays.sort(str);
                    String bigStr = str[0] + str[1] + str[2];
                    // SHA1加密
                    String digest = new Sha1()
                            .getDigestOfString(bigStr.getBytes()).toLowerCase();
                    System.out.println("digest " + digest);
                    // 确认请求来至微信
                    if (digest.equals(signature)) {
                        response.getWriter().print(echostr);
                        System.out.println("echostr: " + echostr);

                    } else {
                        System.out.println("echostr: err");
                        response.getWriter().write("It 's not Wxpt!");
                    }
                }
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}