package com.genogram.service;

import java.util.Set;

/**
 * @author Administrator
 */
public interface IAllCheckOutService {

    /**
     * 获取文字中的敏感词
     * @param string
     * @return
     */
    Set getSensitiveWord(String string);
}
