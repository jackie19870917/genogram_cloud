package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllFamily;
import com.genogram.entityvo.TestVo;

import java.util.Map;

/**
 *
 * @Author: wang,wei
 * @Date: 2018-11-05
 * @Time: 22:17
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
     * @Time: 22:17
     * @param username
     * @param pwd
     * @return:
     * @Description:
     *
     */
    public TestVo test(String username,String pwd);
    /**
     * queryMyItems
     * @Author: wang,wei
     * @Date: 2018-11-05
     * @Time: 22:17
     * @param mapPage
     * @param requestParam
     * @return:
     * @Description:
     *
     */
    public Page<AllFamily> queryMyItems(Page<AllFamily> mapPage, Map requestParam);
}
