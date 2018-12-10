package com.genogram.service;

import com.genogram.entityvo.TestVo;

/**
 * @param null
 * @Author: wang, wei
 * @Date: 2018-11-05
 * @Time: 22:18
 * @return:
 * @Description:
 */
public interface TestServ {
    /**
     * test
     *
     * @param username
     * @param pwd
     * @Author: wang, wei
     * @Date: 2018-11-05
     * @Time: 22:18
     * @return:
     * @Description:
     */
    public TestVo test(String username, String pwd);
}
