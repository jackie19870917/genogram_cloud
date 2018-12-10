package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllFamily;
import com.genogram.entityvo.TestVo;

import java.util.Map;

/**
 * @param null
 * @Author: wang, wei
 * @Date: 2018-11-05
 * @Time: 22:17
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
     * @Time: 22:17
     * @return:
     * @Description:
     */
    public TestVo test(String username, String pwd);

    /**
     * queryMyItems
     *
     * @param mapPage
     * @param requestParam
     * @Author: wang, wei
     * @Date: 2018-11-05
     * @Time: 22:17
     * @return:
     * @Description:
     */
    public Page<AllFamily> queryMyItems(Page<AllFamily> mapPage, Map requestParam);
}
