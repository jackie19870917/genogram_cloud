package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.PuBaseInfo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

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
     * 查询谱基本信息
     *
     * @param statusList
     * @param pageNo
     * @param pageSize
     * @param userLogin
     * @return
     */
    Page<PuBaseInfo> getPuBaseInfoPage(List statusList, Integer pageNo, Integer pageSize, AllUserLogin userLogin);

    /**
     * 删除谱书
     *
     * @param id
     * @param status
     * @param userLogin
     * @return
     */
    Boolean deletePuBaseInfoById(Integer id, int status, AllUserLogin userLogin);

    /**
     * 创建谱基本信息 修改
     *
     * @param puBaseInfo
     * @param userLogin
     * @return
     */
    Boolean addPuBaseInfo(PuBaseInfo puBaseInfo, AllUserLogin userLogin);
}
