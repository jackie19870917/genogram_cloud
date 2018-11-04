package com.genogram.unit;

import com.genogram.entityvo.TestVo;

public class  TestUnit {
    public static TestVo test(String str,String pwd){
        TestVo vo = new TestVo();
        vo.setName(str);
        vo.setPwd(pwd);
        return vo;
    }
}
