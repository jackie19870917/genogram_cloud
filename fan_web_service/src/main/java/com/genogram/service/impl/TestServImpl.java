package com.genogram.service.impl;

import com.genogram.entityvo.TestVo;
import com.genogram.service.TestServ;
import com.genogram.unit.TestUnit;
import org.springframework.stereotype.Service;

@Service
public class TestServImpl implements TestServ {
    @Override
    public TestVo test(String str,String pwd) {
        TestVo vo = TestUnit.test(str,pwd);
        return vo;
    }
}
