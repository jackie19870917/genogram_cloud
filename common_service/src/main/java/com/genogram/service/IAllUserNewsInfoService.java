package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserNewsInfo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 个人日志 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
public interface IAllUserNewsInfoService extends IService<AllUserNewsInfo> {

    /**
     * 根据用户查询日志
     * @param userId
     * @param list
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<AllUserNewsInfo> getAllUserNewsInfoPage(Integer userId, List list,Integer pageNo,Integer pageSize);
}
