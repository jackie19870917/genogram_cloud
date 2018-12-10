package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.AllFamily;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanSysSite;
import com.genogram.entity.ProSysSite;
import com.genogram.mapper.AllFamilyMapper;
import com.genogram.mapper.AllUserLoginMapper;
import com.genogram.mapper.FanSysSiteMapper;
import com.genogram.mapper.ProSysSiteMapper;
import com.genogram.service.IAllUserLoginService;
import com.genogram.unit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;

import static com.baomidou.mybatisplus.enums.SqlLike.LEFT;

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

    @Autowired
    private FanSysSiteMapper fanSysSiteMapper;

    @Autowired
    private ProSysSiteMapper proSysSiteMapper;

    @Autowired
    private AllFamilyMapper allFamilyMapper;

    @Override
    public AllUserLogin getAllUserLogin(AllUserLogin allUserLogin) {

        Wrapper<AllUserLogin> wrapper = new EntityWrapper<AllUserLogin>();
        wrapper.eq("mobile_phone", allUserLogin.getMobilePhone());
        return this.selectOne(wrapper);
    }

    @Override
    public AllUserLogin insertAllUserLogin(AllUserLogin allUserLogin) {

        Wrapper<AllUserLogin> wrapper = new EntityWrapper<AllUserLogin>();

        wrapper.eq("mobile_phone", allUserLogin.getMobilePhone());
        AllUserLogin login = this.selectOne(wrapper);

        if (!StringUtils.isEmpty(login)) {
            return null;
        }

        String userId = "user" + DateUtil.getAllTime() + String.format("%02d", new Random().nextInt(100));
        allUserLogin.setUserId(userId);
        allUserLogin.setStatus(1);
        allUserLogin.setUserName(allUserLogin.getMobilePhone());
        allUserLogin.setNickName(allUserLogin.getMobilePhone());
        allUserLogin.setPicSrc("00/05/default_person.jpg");
        allUserLogin.setRole(0);
        allUserLogin.setCreateUser(1);
        allUserLogin.setUpdateUser(1);
        allUserLogin.setCreateTime(DateUtil.getCurrentTimeStamp());
        allUserLogin.setUpdateTime(DateUtil.getCurrentTimeStamp());

        this.insert(allUserLogin);

        return allUserLogin;
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
        return this.updateById(allUserLogin);
    }

    @Override
    public Page<AllUserLogin> getAllUserLoginPage(Wrapper<AllUserLogin> wrapper, Integer pageNo, Integer pageSize) {

        return this.selectPage(new Page<>(pageNo, pageSize), wrapper);
    }

    @Override
    public List<FanSysSite> getFanSysSite(Wrapper<FanSysSite> wrapper) {

        if (StringUtils.isEmpty(wrapper)) {
            return fanSysSiteMapper.selectList(null);
        } else {
            return fanSysSiteMapper.selectList(wrapper);
        }
    }

    @Override
    public List<ProSysSite> getProSysSite(Wrapper<ProSysSite> wrapper) {

        if (StringUtils.isEmpty(wrapper)) {
            return proSysSiteMapper.selectList(null);
        } else {
            return proSysSiteMapper.selectList(wrapper);
        }
    }

    @Override
    public List<AllFamily> getAllFamily(Wrapper<AllFamily> wrapper) {

        if (StringUtils.isEmpty(wrapper)) {
            return allFamilyMapper.selectList(null);
        } else {
            return allFamilyMapper.selectList(wrapper);
        }
    }

    @Override
    public List<FanSysSite> getFanSysSitePage(Wrapper<FanSysSite> wrapper, Integer pageNo, Integer pageSize) {
        if (StringUtils.isEmpty(wrapper)) {
            return fanSysSiteMapper.selectPage(new Page<>(pageNo, pageSize), null);
        } else {
            return fanSysSiteMapper.selectPage(new Page<>(pageNo, pageSize), wrapper);
        }
    }

    @Override
    public List<ProSysSite> getProSysSitePage(Wrapper<ProSysSite> wrapper, Integer pageNo, Integer pageSize) {
        if (StringUtils.isEmpty(wrapper)) {
            return proSysSiteMapper.selectPage(new Page<>(pageNo, pageSize), null);
        } else {
            return proSysSiteMapper.selectPage(new Page<>(pageNo, pageSize), wrapper);
        }
    }

}
