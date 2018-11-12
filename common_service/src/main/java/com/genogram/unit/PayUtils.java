package com.genogram.unit;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;

public class PayUtils extends WXPayUtil {
	/**
	 * @param payid:付款ID<br/>
	 * @param userip:用户ip<br/>
	 * @param total_fee:总金额<br/>
	 * @param body:商品描述<br/>
	 * @param callback:支付成功后调用的servlet地址<br/>
	 * @return
	 */
	public static String pay(String payid, String userip, String total_fee, String body, String callback) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("body", body);
		data.put("out_trade_no", payid);
		data.put("device_info", "");
		// 货币类型
		data.put("fee_type", "CNY");
		data.put("total_fee", Integer.valueOf(total_fee) * 100 + "");
		data.put("spbill_create_ip", "127.0.0.1");
		data.put("notify_url", callback);
		// 支付方式
		data.put("trade_type", "NATIVE");

		PayConfig config = new PayConfig();
		WXPay wxpay = new WXPay(config);
		try {
			return wxpay.unifiedOrder(data).get("code_url");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("微信支付服务器异常");
		}
	}

	public static String getOrdersid(String xml) {
		int index = xml.indexOf("<out_trade_no>");
		String ordersid = xml.substring(index, index + 55);
		ordersid = ordersid.substring(23);
		return ordersid;
	}

	public static Map<String, String> getPayResult(HttpServletRequest request) {
		try {
			ServletInputStream is = request.getInputStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream(is.available());
			BufferedInputStream in = new BufferedInputStream(is);
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while ((len = in.read(buffer, 0, buf_size)) != -1) {
				bos.write(buffer, 0, len);
			}
			String xmlResult = new String(bos.toByteArray(), "UTF-8");
			in.close();
			bos.close();
			return PayUtils.xmlToMap(xmlResult);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if (ip.indexOf(",") != -1) {
				ip = ip.split(",")[0];
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
			// 根据网卡获取本机配置的IP地址
			InetAddress inetAddress = null;
			try {
				inetAddress = InetAddress.getLocalHost();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ip = inetAddress.getHostAddress();
		}
		return ip;
	}
}