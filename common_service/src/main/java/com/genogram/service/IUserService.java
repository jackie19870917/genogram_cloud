package com.genogram.service;

import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.AllUserLogin;

/**
 * @author: Toxicant
 * @date: 2018-11-23
 */
public interface IUserService extends IService<AllUserLogin> {

    /**
     * 用户对象
     *
     * @param str
     * @return
     */
    AllUserLogin getUserLoginInfoByToken(String str);
}
