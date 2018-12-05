package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.AllUserReg;
import com.genogram.mapper.AllUserRegMapper;
import com.genogram.service.IAllUserRegService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户注册表 服务实现类
 * </p>
 *
 * @author yizx
 * @since 2018-12-05
 */
@Service
public class AllUserRegServiceImpl extends ServiceImpl<AllUserRegMapper, AllUserReg> implements IAllUserRegService {

    @Override
    public AllUserReg getAllUserRegById(Integer id) {
        Wrapper<AllUserReg> wrapper = new EntityWrapper<AllUserReg>();
        wrapper.eq("all_user_login_id", id);
        AllUserReg allUserReg=this.selectOne(wrapper);
        return allUserReg;
    }

    @Override
    public AllUserReg getAllUserReg(AllUserReg allUserReg) {
        return null;
    }

    @Override
    public Boolean updateUserReg(AllUserReg allUserReg) {
        return null;
    }

    @Override
    public Boolean insertAllUserReg(AllUserReg allUserReg) {
        return null;
    }
}
