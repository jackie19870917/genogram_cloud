package com.genogram.service;

import com.genogram.entityvo.TestVo;

/**
 *
 * @Author: wang,wei
 * @Date: 2018-11-05
 * @Time: 22:18
 * @param null
 * @return:
 * @Description:
 *
 */
public interface TestServ {
    /**
     * test
     * @Author: wang,wei
     * @Date: 2018-11-05
     * @Time: 22:18
     * @param username
     * @param pwd
     * @return:
     * @Description:
     *
     */
    public TestVo test(String username, String pwd);
}
