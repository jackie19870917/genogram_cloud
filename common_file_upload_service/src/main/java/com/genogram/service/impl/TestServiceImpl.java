package com.genogram.service.impl;

import com.genogram.entity.AllUserLogin;
import com.genogram.mapper.AllUserLoginMapper;
import com.genogram.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class TestServiceImpl implements ITestService{
    @Autowired
    AllUserLoginMapper allUserLoginMapper;
    @Override
    public AllUserLogin getAllUserLogin() {
        AllUserLogin allUserLogin = allUserLoginMapper.selectById(1);
        return allUserLogin;
    }
}
