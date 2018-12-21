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
import com.genogram.unit.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

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

        BeanUtils.copyProperties(allUserReg, personVo);

        personVo.setNickName(allUserLogin.getNickName());
        personVo.setUserName(allUserLogin.getUserName());
        personVo.setRealName(allUserLogin.getRealName());
        personVo.setPicSrc(allUserLogin.getPicSrc());

        return personVo;
    }

    @Override
    public Boolean insertAllUserReg(AllUserReg allUserReg) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();
        allUserReg.setCreateTime(timeStamp);
        allUserReg.setUpdateTime(timeStamp);
        allUserReg.setStatus(1);
        allUserReg.setUpdateUser(allUserReg.getCreateUser());

        return this.insert(allUserReg);
    }

    @Override
    public Boolean updateAllUserReg(PersonVo personVo) {

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        PersonVo allUserRegByUserId = this.getAllUserRegByUserId(personVo.getAllUserLoginId());

        personVo.setUpdateTime(timeStamp);

        AllUserReg allUserReg = new AllUserReg();

        BeanUtils.copyProperties(personVo, allUserReg);
        allUserReg.setId(allUserRegByUserId.getId());

        AllUserLogin allUserLogin = new AllUserLogin();
        BeanUtils.copyProperties(personVo, allUserLogin);
        allUserLogin.setId(personVo.getAllUserLoginId());

        return this.updateById(allUserReg) && allUserLoginService.updateById(allUserLogin);
    }

}
