package com.genogram.unit;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @version 1.0
 * @Description: 敏感词过滤
 * @Project：test
 * @Author : chenming
 * @Date ： 2014年4月20日 下午4:17:15
 */
public class SensitivewordFilter {
    @SuppressWarnings("rawtypes")
    private Map sensitiveWordMap = null;

    /**
     * 最小匹配规则
     */
    public static int minMatchTYpe = 1;
    /**
     * 最大匹配规则
     */
    public static int maxMatchType = 2;

    /**
     * 构造函数，初始化敏感词库
     */
    public SensitivewordFilter() {
        sensitiveWordMap = new SensitiveWordInit().initKeyWord();
    }

    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt       文字
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * @return 若包含返回true，否则返回false
     * @author chenming
     * @date 2014年4月20日 下午4:28:30
     * @version 1.0
     */
    public boolean isContaintSensitiveWord(String txt, int matchType) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            //判断是否包含敏感字符
            int matchFlag = this.checkSensitiveWord(txt, i, matchType);

            //大于0存在，返回true
            if (matchFlag > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取文字中的敏感词
     *
     * @param txt       文字
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * @return
     * @author chenming
     * @date 2014年4月20日 下午5:10:52
     * @version 1.0
     */
    public Set<String> getSensitiveWord(String txt, int matchType) {
        Set<String> sensitiveWordList = new HashSet<String>();

        for (int i = 0; i < txt.length(); i++) {

            //判断是否包含敏感字符
            int length = checkSensitiveWord(txt, i, matchType);

            //存在,加入list中
            if (length > 0) {
                sensitiveWordList.add(txt.substring(i, i + length));

                //减1的原因，是因为for会自增
                i = i + length - 1;
            }
        }

        return sensitiveWordList;
    }

    /**
     * 替换敏感字字符
     *
     * @param txt
     * @param matchType
     * @param replaceChar 替换字符，默认*
     * @author chenming
     * @date 2014年4月20日 下午5:12:07
     * @version 1.0
     */
    public String replaceSensitiveWord(String txt, int matchType, String replaceChar) {
        String resultTxt = txt;

        //获取所有的敏感词
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    /**
     * 获取替换字符串
     *
     * @param replaceChar
     * @param length
     * @return
     * @author chenming
     * @date 2014年4月20日 下午5:21:19
     * @version 1.0
     */
    private String getReplaceChars(String replaceChar, int length) {
        String resultReplace = replaceChar;
        for (int i = 1; i < length; i++) {
            resultReplace += replaceChar;
        }

        return resultReplace;
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     *
     * @param txt
     * @param beginIndex
     * @param matchType
     * @author chenming
     * @date 2014年4月20日 下午4:31:03
     * @return，如果存在，则返回敏感词字符的长度，不存在返回0
     * @version 1.0
     */
    @SuppressWarnings({"rawtypes"})
    public int checkSensitiveWord(String txt, int beginIndex, int matchType) {

        //敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;

        //匹配标识数默认为0
        int matchFlag = 0;
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);

            //获取指定key
            nowMap = (Map) nowMap.get(word);
            //存在，则判断是否为最后一个
            if (nowMap != null) {
                //找到相应key，匹配标识+1
                matchFlag++;
                //如果为最后一个匹配规则,结束循环，返回匹配标识数
                if ("1".equals(nowMap.get("isEnd"))) {
                    //结束标志位为true
                    flag = true;
                    //最小规则，直接返回,最大规则还需继续查找
                    if (SensitivewordFilter.minMatchTYpe == matchType) {
                        break;
                    }
                }
            } else {     //不存在，直接返回
                break;
            }
        }
        Integer length = 2;
        //长度必须大于等于1，为词
        if (matchFlag < length || !flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }

    public static void main(String[] args) {
        SensitivewordFilter filter = new SensitivewordFilter();
        System.out.println("敏感词的数量：" + filter.sensitiveWordMap.size());
        String string = "狗杂种\n" +
                "狗日的\n" +
                "操你祖宗\n" +
                "操你全家\n" +
                "操你大爷\n" +
                "妈逼\n" +
                "你麻痹\n" +
                "麻痹的";
        System.out.println("待检测语句字数：" + string.length());
        long beginTime = System.currentTimeMillis();
        Set<String> set = filter.getSensitiveWord(string, 1);
        long endTime = System.currentTimeMillis();
        System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
        System.out.println("总共消耗时间为：" + (endTime - beginTime));
    }
}
