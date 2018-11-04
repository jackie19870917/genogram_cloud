package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllFamily;
import com.genogram.entityvo.TestVo;

import java.util.Map;

public interface TestServ {
    public TestVo test(String username,String pwd);
    public Page<AllFamily> queryMyItems(Page<AllFamily> mapPage, Map requestParam);
}
