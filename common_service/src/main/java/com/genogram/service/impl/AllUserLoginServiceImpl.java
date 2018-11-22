package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.AllUserLogin;
import com.genogram.mapper.AllUserLoginMapper;
import com.genogram.service.IAllUserLoginService;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * <p>
 * 用户登录表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class AllUserLoginServiceImpl extends ServiceImpl<AllUserLoginMapper, AllUserLogin> implements IAllUserLoginService {

    @Override
    public AllUserLogin getAllUserLogin(AllUserLogin allUserLogin) {

        Wrapper<AllUserLogin> wrapper = new EntityWrapper<AllUserLogin>();
       // wrapper.eq("user_name", allUserLogin.getUserName());
        wrapper.eq("mobile_phone", allUserLogin.getMobilePhone());
        return this.selectOne(wrapper);
    }

    @Override
    public Boolean insertAllUserLogin(AllUserLogin allUserLogin) {

        Wrapper<AllUserLogin> wrapper = new EntityWrapper<AllUserLogin>();

        wrapper.eq("mobile_phone", allUserLogin.getMobilePhone());
        AllUserLogin login = this.selectOne(wrapper);

        if (!StringUtils.isEmpty(login)) {
            return false;
        }

        String userId="user"+ DateUtil.getAllTime()+String.format("%02d", new Random().nextInt(100));
        allUserLogin.setUserId(userId);
        allUserLogin.setStatus(1);
        allUserLogin.setSiteId(1);
        allUserLogin.setUserName(allUserLogin.getMobilePhone());
        allUserLogin.setNickName(allUserLogin.getMobilePhone());
        allUserLogin.setPicSrc("http://47.105.177.1:6090/00/00/rB_QCFv0uzGAWsd6AABYCUDwssM470.jpg");
        allUserLogin.setRole(0);
        allUserLogin.setCreateUser(1);
        allUserLogin.setUpdateUser(1);
        allUserLogin.setCreateTime(DateUtil.getCurrentTimeStamp());
        allUserLogin.setUpdateTime(DateUtil.getCurrentTimeStamp());

        return this.insert(allUserLogin);
    }

    @Override
    public AllUserLogin getAllUserLoginById(Integer id) {

        return this.selectById(id);
    }

    @Override
    public Boolean updateAllUserLogin(AllUserLogin allUserLogin) {

        allUserLogin.setUpdateTime(DateUtil.getCurrentTimeStamp());
        return this.updateById(allUserLogin);
    }

    @Override
    public Boolean updateUserLogin(AllUserLogin allUserLogin) {

        allUserLogin.setUpdateTime(DateUtil.getCurrentTimeStamp());
        return  this.updateById(allUserLogin);
    }
}
