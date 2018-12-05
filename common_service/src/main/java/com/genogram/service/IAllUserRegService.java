package com.genogram.service;

import com.genogram.entity.AllUserReg;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户注册表 服务类
 * </p>
 *
 * @author yizx
 * @since 2018-12-05
 */
public interface IAllUserRegService extends IService<AllUserReg> {
    /**
     * 登陆
     *
     * @param allUserReg
     * @return
     */
    AllUserReg getAllUserReg(AllUserReg allUserReg);

    /**
     * 注册
     *
     * @param allUserReg
     * @return
     */
    Boolean insertAllUserReg(AllUserReg allUserReg);

    /**
     * 查询
     *
     * @param id 主键
     * @return
     */
    AllUserReg getAllUserRegById(Integer id);

    /**
     * 修改个人资料
     *
     * @param allUserLReg
     * @return
     */
    Boolean updateUserReg(AllUserReg allUserLReg);
}
