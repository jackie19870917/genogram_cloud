package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.AllUserReg;
import com.genogram.entityvo.PersonVo;
import com.genogram.mapper.AllUserRegMapper;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IAllUserRegService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户注册表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
@Service
public class AllUserRegServiceImpl extends ServiceImpl<AllUserRegMapper, AllUserReg> implements IAllUserRegService {

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Override
    public PersonVo getAllUserRegByUserId(Integer userId) {

        Wrapper<AllUserReg> wrapper = new EntityWrapper<>();
        wrapper.eq("all_user_login_id", userId);

        AllUserReg allUserReg = this.selectOne(wrapper);

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userId);

        PersonVo personVo = new PersonVo();

        BeanUtils.copyProperties(allUserReg,personVo);

        personVo.setNickName(allUserLogin.getNickName());
        personVo.setUrl(allUserLogin.getPicSrc());

        return personVo;
    }
}
