package com.genogram.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.genogram.config.AlipayConfig;
import com.genogram.config.Constants;
import com.genogram.config.PayConfig;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanIndexFund;
import com.genogram.entity.FanNewsCharityPayIn;
import com.genogram.service.IFanIndexFundService;
import com.genogram.service.IFanNewsCharityPayInService;
import com.genogram.service.IFanSysWebNewsShowService;
import com.genogram.service.IUserService;
import com.genogram.unit.*;
import com.github.wxpay.sdk.WXPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
         * 实际验证过程建议商户务必添加以下校验： 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
         * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）， 3、校验通知中的seller_id（或者seller_email)
         * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
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

                //修改基金金额
                Integer siteId = fanSysWebNewsShowService.getSiteIdByShowId(fanNewsCharityPayIn.getShowId()).getSiteId();

                FanIndexFund fanIndexFund = fanIndexFundService.getFanIndexFund(siteId);

                System.out.println(fanIndexFund.getRemain());
                fanIndexFund.setRemain(fanIndexFund.getRemain().add(fanNewsCharityPayIn.getPayAmount()));
                fanIndexFund.setPayOnline(fanIndexFund.getPayOnline().add(fanNewsCharityPayIn.getPayAmount()));

                fanIndexFundService.insertOrUpdateFanIndexFund(fanIndexFund);
                return ResponseUtlis.success(fanNewsCharityPayIn);
            } else {
                return ResponseUtlis.error(201, null);
            }
        }
    }

    @RequestMapping("callBack")
    public void callBack(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {

        java.io.InputStream is = request.getInputStream();
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

    /**
     * @param request
     * @param code
     * @return Map
     * @Description 微信浏览器内微信支付/公众号支付(JSAPI)
     */
    @ApiOperation("微信公众号支付")
    @RequestMapping(value = "orders", method = RequestMethod.GET)
    public Map orders(HttpServletRequest request, String code) {

        PayConfig payConfig = new PayConfig();
        try {
            //页面获取openId接口
            String getopenid_url = "https://api.weixin.qq.com/sns/oauth2/access_token";
            String param =
                    "appid=" + payConfig.getAppID() + "&secret=" + payConfig.getKey() + "&code=" + code + "&grant_type=authorization_code";
            //向微信服务器发送get请求获取openIdStr
            String openIdStr = HttpRequest.sendGet(getopenid_url, param);
            JSONObject json = JSONObject.parseObject(openIdStr);//转成Json格式
            String openId = json.getString("openid");//获取openId

            //拼接统一下单地址参数
            Map<String, String> paraMap = new HashMap<String, String>();
            //获取请求ip地址
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (ip.indexOf(",") != -1) {
                String[] ips = ip.split(",");
                ip = ips[0].trim();
            }

            paraMap.put("appid", payConfig.getAppID());
            paraMap.put("body", "炎黄统谱网在线微信支付");
            paraMap.put("mch_id", payConfig.getMchID());
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
            paraMap.put("openid", openId);
            paraMap.put("out_trade_no", DateUtil.getAllTime() + String.format("%02d", new Random().nextInt(100)));//订单号
            paraMap.put("spbill_create_ip", ip);
            paraMap.put("total_fee", "1");
            paraMap.put("trade_type", "JSAPI");
            paraMap.put("notify_url", payConfig.getNotifyUrl());// 此路径是微信服务器调用支付结果通知路径随意写
            String sign = WXPayUtil.generateSignature(paraMap, payConfig.getKey());
            paraMap.put("sign", sign);
            String xml = WXPayUtil.mapToXml(paraMap);//将所有参数(map)转xml格式

            // 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
            String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

            String xmlStr = HttpRequest.sendPost(unifiedorder_url, xml);//发送post请求"统一下单接口"返回预支付id:prepay_id

            //以下内容是返回前端页面的json数据
            String prepay_id = "";//预支付id
            if (xmlStr.indexOf("SUCCESS") != -1) {
                Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);
                prepay_id = (String) map.get("prepay_id");
            }
            Map<String, String> payMap = new HashMap<String, String>();
            payMap.put("appId", payConfig.getAppID());
            payMap.put("timeStamp", System.currentTimeMillis() + "");
            payMap.put("nonceStr", WXPayUtil.generateNonceStr());
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id=" + prepay_id);
            String paySign = WXPayUtil.generateSignature(payMap, payConfig.getKey());
            payMap.put("paySign", paySign);
            return payMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @RequestMapping("/oauth2WeChat")
    public void oauth2WeChat(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 用户同意授权后，能获取到code
        Map<String, String[]> params = request.getParameterMap();//针对get获取get参数
        String[] codes = params.get("code");//拿到code的值
        String code = codes[0];//code
        //String[] states = params.get("state");
        //String state = states[0];//state

        System.out.println("****************code:" + code);
        // 用户同意授权
        /*if (!"authdeny".equals(code)) {
            // 获取网页授权access_token
            Oauth2Token oauth2Token = getOauth2AccessToken("wxb0000000000e", "4c22222233333335555a9", code);
            System.out.println("***********************************oauth2Token信息：" + oauth2Token.toString());
            // 网页授权接口访问凭证
            String accessToken = oauth2Token.getAccessToken();
            // 用户标识
            String openId = oauth2Token.getOpenId();
            // 获取用户信息
            SNSUserInfo snsUserInfo = getSNSUserInfo(accessToken, openId);
            System.out.println("***********************************用户信息unionId：" + snsUserInfo.getUnionid() + "***:" + snsUserInfo.getNickname());
            // 设置要传递的参数

            //具体业务start

            //具体业务end

            String url = "http://wftest.zzff.net/#/biddd?from=login&tokenId=" + snsUserInfo.getUnionid();

            response.sendRedirect(url);
            return;
        }*/
    }

    public void contactWeChat(HttpServletRequest request,
                              HttpServletResponse response) {
        String method = request.getMethod();
        String remoteHost = request.getRemoteHost();
        String remoteAddr = request.getRemoteAddr();
        System.out.println("method :" + method);
        System.out.println("remoteHost :" + remoteHost + " remoteAddr :"
                + remoteAddr);
        try {
            if ("GET".equals(method)) {
                // 微信加密签名
                String signature = request.getParameter("signature"); // ceb87cf6583bdd37bc49fb7b10fc42f4c3ae4bf2
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
                    Arrays.sort(str); // 字典序排序
                    String bigStr = str[0] + str[1] + str[2];
                    // SHA1加密
                    String digest = new SHA1()
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