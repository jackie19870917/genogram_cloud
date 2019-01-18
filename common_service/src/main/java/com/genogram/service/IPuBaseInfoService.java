package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
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
     *查询谱基本信息
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<PuBaseInfo> getPuBaseInfoPage(Wrapper<PuBaseInfo> entity, Integer pageNo, Integer pageSize);

    /**
     * 删除谱书
     *
     * @param id
     * @param status
     * @return
     */
    Boolean deletePuBaseInfoById(Integer id, int status, AllUserLogin userLogin);

}
