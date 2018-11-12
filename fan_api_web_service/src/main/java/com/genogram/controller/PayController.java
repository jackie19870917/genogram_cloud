package com.genogram.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.genogram.entity.FanNewsCharityPayIn;
import com.genogram.service.IFanNewsCharityPayInService;
import com.genogram.unit.AlipayConfig;
import com.genogram.unit.DateUtil;
import com.genogram.unit.PayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/pay")
public class PayController {

    Logger log = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private IFanNewsCharityPayInService iFanNewsCharityPayInService;

    @RequestMapping(value = "aLiPay", method = RequestMethod.POST)
    public String aLiPay(String money) {

        // 获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type);

        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = null;

        // 付款金额，必填
        String total_amount = money;

        // 订单名称，必填
        String subject = "炎黄网在线支付宝扫码支付";

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "2h";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\"" + total_amount
                + "\"," + "\"subject\":\"" + subject + "\"," + "\"timeout_express\":\"" + timeout_express + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        // 请求
        try {
            String result = alipayClient.pageExecute(alipayRequest).getBody();

            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();

            return null;
        }
    }

    @RequestMapping(value = "/return_url")
    public String alipayReturnNotice(HttpServletRequest request, HttpServletResponse response,
                                     FanNewsCharityPayIn fanNewsCharityPayIn, String url) throws Exception {

        // 跨域解决
        response.setHeader("Access-Control-Allow-Origin", "*");

        log.info("支付成功, 进入同步通知接口...");

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
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

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset,
                AlipayConfig.sign_type); // 调用SDK验证签名

        if (signVerified) {

            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            url = new String(request.getParameter("url").getBytes("ISO-8859-1"), "UTF-8");
            // 支付方式
            String payChannel = "支付宝支付";
            BigDecimal payAmount = new BigDecimal(total_amount);
            Timestamp format = DateUtil.getCurrentTimeStamp();

            fanNewsCharityPayIn.setShowId(Integer.parseInt(out_trade_no));
            fanNewsCharityPayIn.setPayAmount(payAmount);
            fanNewsCharityPayIn.setType(1);
            fanNewsCharityPayIn.setCreateTime(format);
            fanNewsCharityPayIn.setUpdateTime(format);
            fanNewsCharityPayIn.setCreateUser(1);
            fanNewsCharityPayIn.setPayChannel(payChannel);

            iFanNewsCharityPayInService.insertFanNewsCharityPayIn(fanNewsCharityPayIn);

            log.info("********************** 支付成功(支付宝同步通知) **********************");
            log.info("* 订单号: {}", out_trade_no);
            log.info("* 支付宝交易号: {}", trade_no);
            log.info("* 实付金额: {}", total_amount);
            log.info("* 购买产品: {}", "炎黄统谱网在线支付宝扫码支付");
            log.info("***************************************************************");

            System.out.println(url);
            return "'" + url + "'" + "?out_trade_no=" + "'" + out_trade_no + "'" + "&total_amount=" + "'" + total_amount
                    + "'" + "&productName='炎黄统谱网在线支付宝扫码支付'";
        } else {
            log.info("支付, 验签失败...");
            return null;
        }
        /*
         * ModelAndView mv = new ModelAndView("success"); // ——请在这里编写您的程序（以下代码仅作参考）—— if
         * (signVerified) { // 商户订单号 String out_trade_no = new
         * String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
         *
         * // 支付宝交易号 String trade_no = new
         * String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
         *
         * // 付款金额 String total_amount = new
         * String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
         *
         * // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水 //
         * orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);
         *
         * // 支付方式 String payChannel = "支付宝支付"; BigDecimal payAmount = new
         * BigDecimal(total_amount); String creator = "user2018093014268912"; Timestamp
         * format = DateUtil.format(new Date());
         * sysDonationInfoService.insert(out_trade_no, payAmount, creator, format,
         * payChannel);
         *
         * log.info("********************** 支付成功(支付宝同步通知) **********************");
         * log.info("* 订单号: {}", out_trade_no); log.info("* 支付宝交易号: {}", trade_no);
         * log.info("* 实付金额: {}", total_amount); log.info("* 购买产品: {}",
         * "炎黄统谱网在线支付宝扫码支付");
         * log.info("***************************************************************");
         *
         * mv.addObject("out_trade_no", out_trade_no); mv.addObject("trade_no",
         * trade_no); mv.addObject("total_amount", total_amount);
         * mv.addObject("productName", "炎黄统谱网在线支付宝扫码支付");
         *
         * } else { log.info("支付, 验签失败..."); }
         *
         * return mv;
         */
    }

    @RequestMapping("/notify_url")
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 跨域解决
        response.setHeader("Access-Control-Allow-Origin", "*");

        log.info("支付成功, 进入异步通知接口...");

        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset,
                AlipayConfig.sign_type); // 调用SDK验证签名

        // ——请在这里编写您的程序（以下代码仅作参考）——

        /*
         * 实际验证过程建议商户务必添加以下校验： 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
         * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）， 3、校验通知中的seller_id（或者seller_email)
         * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
         * 4、验证app_id是否为该商户本身。
         */
        if (signVerified) {// 验证成功
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            if ("TRADE_FINISHED".equals(trade_status)) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序

                // 注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                // 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if ("TRADE_SUCCESS".equals(trade_status)) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序

                // 注意：
                // 付款完成后，支付宝系统发送该交易状态通知

                // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
                // orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);

                log.info("********************** 支付成功(支付宝异步通知) **********************");
                log.info("* 订单号: {}", out_trade_no);
                log.info("* 支付宝交易号: {}", trade_no);
                log.info("* 实付金额: {}", total_amount);
                log.info("* 购买产品: {}", "炎黄统谱网在线支付宝扫码支付");
                log.info("***************************************************************");
            }
            log.info("支付成功...");

        } else {// 验证失败
            log.info("支付, 验签失败...");
        }

        return "success";
    }

    @RequestMapping(value = "weChatPay", method = RequestMethod.POST)
    public void weChatPay(Model model,HttpServletRequest request,FanNewsCharityPayIn fanNewsCharityPayIn) {

        // 订单编号
        String payId = null;

        // 支付用户的id
        String userIp = PayUtils.getRemoteAddr(request);

        // 支付金额
        String total_fee = fanNewsCharityPayIn.getPayAmount()+"";

        // 商品描述
        String body = "炎黄統譜网在线微信扫码支付";

        // 回调地址
        String callback = "localhost:8080/success";

        // 生成一个code_url
        String code_url = PayUtils.pay(payId, userIp, total_fee, body, callback);
        // String code_url = PayUtils.pay(payId, userId, total_fee, body, callback,
        // request);
        System.out.println(code_url);

        model.addAttribute("code_url", code_url);
        model.addAttribute("oid", payId);
        model.addAttribute("totalprice", total_fee);

        // 进入到二维码生成页面
        //return "saoma";

    }
}
