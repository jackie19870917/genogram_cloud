package com.genogram.service;

import com.genogram.entity.AllUserReg;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.PersonVo;

/**
 * <p>
 * 用户注册表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
public interface IAllUserRegService extends IService<AllUserReg> {

    /**
     * 根据用户Id查询用户
     * @param userId
     * @return
     */
    PersonVo getAllUserRegByUserId(Integer userId);
}
