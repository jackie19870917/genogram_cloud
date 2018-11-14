package com.genogram.service;

import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.AllUserLogin;

/**
 * <p>
 * 用户登录表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IAllUserLoginService extends IService<AllUserLogin> {

    /**
     * 登陆
     * @param allUserLogin
     * @return
     */
    AllUserLogin getAllUserLogin(AllUserLogin allUserLogin);

    /**
     * 注册
     * @param allUserLogin
     * @return
     */
    Boolean insertAllUserLogin(AllUserLogin allUserLogin);
}
