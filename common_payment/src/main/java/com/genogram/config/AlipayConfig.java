package com.genogram.config;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 支付宝支付
 *
 * @author Toxicant
 * @date 2016/10/31
 */
public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    public static String app_id = "2018100861619554";

    /**
     *  商户私钥，您的PKCS8格式RSA2私钥
     */
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDo34v8ssie2xlwifQCzlbujF5SGggh+iFWFODsvPg1Wg780LfLzjbWy6S3Zy02ZD5S6PzRvaQxxVTwbPPuCwm3FaviayKlPpQpyGr1MTUtPPF/paFWM4pX50yKjrZJzCib2sfZ4/24OU9V6lheGnN3wyXg+MTqQqIc2MQCtSlDIDI9Z+ArS4xu8EKNNcGf7Gl50jejYSjvL6PZMgNbvXd9nx+I62CjSKlrhonhdEuih+3K0a5tttB71ov6lt57JNYb6r33sHfDrkt3wF3bNMrPs4AbyFWhRipYlWrgzcboDKkpTDz2BCwbD9jzRkqG+AG0aQU/DB7ObAWonV4yVW/xAgMBAAECggEBAMY8TmF+HBRGtSA8/1BIMG5/ZWeEq3mpASnTOimtL6nTEUEbt+oxkDNHRd+uYCmWKgvQNC+jvRt1PToxtXDlSo7lTt2+j86VN/Fdn9c73WW2JGqiJ3ZHB7SOvHSSyhCGT+dk1Y3hbEGf365mgHUTFUpVAHDDtHUq5e+igzE+4HI3XVDl0bzhFALj6A3/aNx+1zebnGTt5UoTHgzr9itv+wiwI9+JiaBhBeh0zM4YuKT5SpT91p3qUIrAJ655QYkeam2EHT77gjp3pyPSQHoO3tsI73YdmJ/Qd7xHIzqCS63oGXOVCCDG5gMdWD52PSznxAvttOHf6YBp78XlU2Ih6AECgYEA+xPLQMcMVPDCe1CEbLGtCiD2GC9YNF4kWecmhYdQT9tMwMaiAuR9NIwhlwfNKsWaZ95WqPRaTTuTJrt/sjWJmAXHEFPe83Z4XRukN3t6R2BWIe+gZdB2IRGoXH5SHmkFDgCMRLgiqyNWXJ3xFRWjXEpaWT3tvIgUexFsjP//BHECgYEA7XBiC3K3TKf8rLXLyCKszjaL1Fe3d8bKNgjslzZ2snskec5q0mCLSV1jWusQiqXrJx6Z576xUJBRxkkokGtAosI5FfNBPfA1ArQ5rngkv0pq1V5pUD25ttUtReHdCy9R8j7OPe+hHHveayr2ht0/lrfQdE71Qz1h/TCkPZIk44ECgYB8pd8KUcd1g/GJdvfHXo4hsSt75kVL+lDhsTgWoo7klmMMcZCNDxJ5u/gRGvZofaGiX2d2iZkrLqBnYj6LIrkkl/mzyW+uPuqSjt6ADPUv6kvYnTZsVYnP8Qw8nxn1rv2rbeo1U5UikXCWmnLNnOh1MRSZqX0VVBmW35eciJr6gQKBgQDc8nw5/Pj3G0c0jENiLGozxmxeH3X5voGCwNEwcR0JPzAVLPEYbYCPt5hyDVWqP3VQRNig30FvH60I4CEgKr3N9wBaHa+zbGhX6M/YO7KuUxiRDrbfVWEQ8tSbN++SrekKjEktN2Tr9ZRmk2cu3omCve/7m1H9zQv3KKFdLjangQKBgFSQI+OWgjm0O12osNA/sBwZpSWH1vT8pQmREObxSx4SmzBJ6URKpzZ6LZQKq5W/C6XTq6gcnsjwEGiMG0Sp1qJVbixbN9HxvLlkcIk31y2LntLmQ2cRrPcDItI2YYe6+t5OFfQD0uLnha7TM5mE43kV2NE5mQWWgnKsd9blV6jB";

    /**
     * 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
     */
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgR4qMfqEcSQ4V1PO4cVl50mzEx0Tr9F/EMQT1Uagr3fhIddHfvSzJfuYDnmBRNUSkwLTMTvqi7Zct4rLtG8FUqv4VZh6wKS79+lppmllj6pwZkEQR4GtiqqBLw5USeTl0H+nkUOdOeq1cGqy41xUEiDygJSNuAhCFAGqhwepOCdysChX9jDFcWADTyN2TmuRCx2PMjuMueTKOYg2z6pv2gKXbrDDaZkp0C9+gTqPybhgyX1Tl53yhEd+oD2goUqgzfCQ1kdOAKzwmZDZkBuwVPLxPGb1+m7WT7Vm/CuvR60BBjd43q5/vXt3emrsikF/TqRi50LynNkyE7ROF+uaWwIDAQAB";

    /**
     * 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static String notify_url = "http://192.168.2.152:8090/genogram/pay/notify_url";

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static String return_url = "http://192.168.2.152:8090/genogram/pay/return_url";

    /**
     *  签名方式
     */
    public static String sign_type = "RSA2";

    /**
     * 字符编码格式
     */
    public static String charset = "utf-8";

    /**
     * 支付宝网关
     */
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    /**
     * 支付宝网关
     */
    public static String log_path = "C:\\";


    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
