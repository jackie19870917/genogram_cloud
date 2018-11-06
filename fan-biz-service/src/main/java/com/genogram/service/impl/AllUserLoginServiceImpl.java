package com.genogram.service.impl;

import com.genogram.entity.AllUserLogin;
import com.genogram.mapper.AllUserLoginMapper;
import com.genogram.service.IAllUserLoginService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}