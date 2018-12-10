package com.genogram.unit;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @Author: yuzhou
 * @Date: 2018-11-09
 * @Time: 16:08
 * @Param:
 * @return:
 * @Description:
 */
public class StringsUtils extends StringUtils {
    /**
     * 字符串为空
     *
     * @param param
     * @return
     */
    public static boolean isEmpty(String param) {
        if (param == null || "".equals(param.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符串不为空
     *
     * @param param
     * @return
     */
    public static boolean isNotEmpty(String param) {
        if (isEmpty(param)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 提取字符串中得数字
     *
     * @param param
     * @return
     */
    public static String getNumber(String param) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(param);
        return m.replaceAll("").trim();
    }

    public static String removeTag(String htmlStr) {

        // script
        String regExScript = "<script[^>]*?>[\\s\\S]*?<\\/script>";

        // style
        String regExStyle = "<style[^>]*?>[\\s\\S]*?<\\/style>";

        // HTML tag
        String regExHtml = "<[^>]+>";

        // other characters
        String regExSpace = "\\s+|\t|\r|\n";

        Pattern pScript = Pattern.compile(regExScript,
                Pattern.CASE_INSENSITIVE);
        Matcher mScript = pScript.matcher(htmlStr);
        htmlStr = mScript.replaceAll("");

        Pattern pStyle = Pattern
                .compile(regExStyle, Pattern.CASE_INSENSITIVE);
        Matcher mStyle = pStyle.matcher(htmlStr);
        htmlStr = mStyle.replaceAll("");

        Pattern pHtml = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
        Matcher mHtml = pHtml.matcher(htmlStr);
        htmlStr = mHtml.replaceAll("");

        Pattern pSpace = Pattern
                .compile(regExSpace, Pattern.CASE_INSENSITIVE);
        Matcher mSpace = pSpace.matcher(htmlStr);
        htmlStr = mSpace.replaceAll(" ");

        return htmlStr;
    }

}
