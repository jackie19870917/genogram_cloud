package com.genogram.unit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *字符串工具类
 *@Author: yuzhou
 *@Date: 2018-11-09
 *@Time: 16:08
 *@Param:
 *@return:
 *@Description:
*/
public class StringsUtils
{
    /**
     * 字符串为空
     * 
     * @param param
     * @return
     */
    public static boolean isEmpty(String param)
    {
        if (param == null || "".equals(param.trim()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

	/**
     * 字符串不为空
     * 
     * @param param
     * @return
     */
    public static boolean isNotEmpty(String param)
    {
        if (isEmpty(param))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 提取字符串中得数字
     * 
     * @param param
     * @return
     */
    public static String getNumber(String param)
    {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(param);
        return m.replaceAll("").trim();
    }

    public static String removeTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // script
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // style
        String regEx_html = "<[^>]+>"; // HTML tag
        String regEx_space = "\\s+|\t|\r|\n";// other characters

        Pattern p_script = Pattern.compile(regEx_script,
                Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");

        Pattern p_style = Pattern
                .compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");

        Pattern p_space = Pattern
                .compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(" ");

        return htmlStr;
    }

}
