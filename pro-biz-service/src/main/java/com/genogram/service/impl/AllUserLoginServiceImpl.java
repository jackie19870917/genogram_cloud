package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.AllUserLogin;
import com.genogram.mapper.AllUserLoginMapper;
import com.genogram.service.IAllUserLoginService;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

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

        String userId="user"+ DateUtil.getAllTime()+String.format("%02d", new Random().nextInt(100));
        allUserLogin.setUserId(userId);
        allUserLogin.setCreateTime(DateUtil.getCurrentTimeStamp());
       // allUserLogin.s

        return this.insert(allUserLogin);
    }
}