package com.genogram.controller;

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
import com.genogram.unit.DateUtil;
import com.genogram.unit.PayUtils;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
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
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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
    public Response weChatPay(Model model, HttpServletRequest request,
                              FanNewsCharityPayIn fanNewsCharityPayIn,
                              @ApiParam("网站ID") @RequestParam Integer siteId,
                              @ApiParam("token") @RequestParam(value = "token", required = false) String token,
                              @ApiParam("是否匿名(1-匿名,0-不匿名)") @RequestParam("anonymous") Integer anonymous,
                              @ApiParam("回调地址") @RequestParam(value = "url") String url) {

        this.baseUrl = url;

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

        String totalAmount = rootElt.elementText("total_fee");

        FanNewsCharityPayIn fanNewsCharityPayIn = new FanNewsCharityPayIn();

        fanNewsCharityPayIn.setOrderId(outTradeNo);
        fanNewsCharityPayIn = fanNewsCharityPayInService.selectOne(fanNewsCharityPayIn);

        fanNewsCharityPayIn.setPayTime(DateUtil.getCurrentTimeStamp());
        fanNewsCharityPayIn.setStatus(1);

        fanNewsCharityPayInService.insertFanNewsCharityPayIn(fanNewsCharityPayIn);

        //修改基金金额
        Integer siteId = fanSysWebNewsShowService.getSiteIdByShowId(fanNewsCharityPayIn.getShowId()).getSiteId();

        FanIndexFund fanIndexFund = fanIndexFundService.getFanIndexFund(siteId);

        System.out.println(fanIndexFund.getRemain());
        fanIndexFund.setRemain(fanIndexFund.getRemain().add(new BigDecimal( (Double.parseDouble(totalAmount) * 0.01) + "")));
        fanIndexFund.setPayOnline(fanIndexFund.getPayOnline().add(new BigDecimal( (Double.parseDouble(totalAmount) * 0.01) + "")));

        fanIndexFundService.insertOrUpdateFanIndexFund(fanIndexFund);

        //给微信返回支付成功结果
        String responseStr = "<xml>";
        responseStr += "<return_code><![CDATA[SUCCESS]]></return_code>";
        responseStr += "</xml>";

        response.getWriter().write(responseStr);
        System.out.println("responseStr2:" + responseStr);


        System.out.println("支付完成");

        response.sendRedirect(this.baseUrl + "result=success&out_trade_no=" + outTradeNo + "&total_amount=" + totalAmount);
    }
}
