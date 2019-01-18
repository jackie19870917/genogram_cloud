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
     *查询谱基本信息
     *@Author: yuzhou
     *@Date: 2019-01-18
     *@Time: 16:53
     *@Param: 
     *@return:
     *@Description:
    */
    Page<PuBaseInfo> getPuBaseInfoPage(List statusList, Integer pageNo, Integer pageSize,AllUserLogin userLogin);

    /**
     * 删除谱书
     *
     * @param id
     * @param status
     * @return
     */
    Boolean deletePuBaseInfoById(Integer id, int status, AllUserLogin userLogin);

    
}
