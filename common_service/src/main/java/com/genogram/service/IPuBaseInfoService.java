package com.genogram.service;

import com.genogram.entity.AllUserLogin;
import com.genogram.entity.PuBaseInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yizx
 * @since 2019-01-16
 */
public interface IPuBaseInfoService extends IService<PuBaseInfo> {


    /**
     * 删除谱书
     *
     * @param id
     * @param status
     * @return
     */
    Boolean deletePuBaseInfoById(Integer id, int status, AllUserLogin userLogin);
}
