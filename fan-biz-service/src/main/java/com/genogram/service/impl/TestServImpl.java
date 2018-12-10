package com.genogram.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllFamily;
import com.genogram.entityvo.TestVo;
import com.genogram.mapper.AllFamilyMapper;
import com.genogram.service.TestServ;
import com.genogram.unit.TestUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 * @Author: wang,wei
 * @Date: 2018-11-05
 * @Time: 22:22
 * @return:
 * @Description:
 *
 */
@Service
public class TestServImpl implements TestServ {
    @Autowired
    private AllFamilyMapper allFamilyMapper;
    @Override
    public TestVo test(String str,String pwd) {
        TestVo vo = TestUnit.test(str,pwd);

        AllFamily a = allFamilyMapper.selectById("8");
        //update
        a.setValue(str);
        a.setFirstLetter(pwd);
        allFamilyMapper.updateById(a);

        //分页
        return vo;
    }

    @Override
    public Page<AllFamily> queryMyItems(Page<AllFamily> mapPage, Map requestParam) {
        return null;
    }
}
