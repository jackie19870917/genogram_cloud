package com.genogram.service;

import com.genogram.entity.AllUserLogin;
import com.baomidou.mybatisplus.service.IService;

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

    /**
     * 查询
     */
    AllUserLogin getAllUserLoginById(Integer id);

    /**
     * 修改密码
     */
    Boolean updateAllUserLogin(AllUserLogin allUserLogin);
}
