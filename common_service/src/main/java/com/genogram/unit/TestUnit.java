package com.genogram.unit;

import com.genogram.entityvo.TestVo;

/**
 *
 * @Author: wang,wei
 * @Date: 2018-11-05
 * @Time: 22:22
 * @return:
 * @Description:
 *
 */
public class  TestUnit {
    public static TestVo test(String str,String pwd){
        TestVo vo = new TestVo();
        vo.setName(str);
        vo.setPwd(pwd);
        return vo;
    }
}
