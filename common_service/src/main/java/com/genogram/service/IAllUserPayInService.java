package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.FanNewsCharityPayIn;
import com.genogram.entity.ProNewsCharityPayIn;

import java.util.List;

/**
 * @author Administrator
 */
public interface IAllUserPayInService extends IService<FanNewsCharityPayIn> {

    /**
     * 查询联谊会个人捐款记录
     *
     * @param userId
     * @return
     */
    List<FanNewsCharityPayIn> getFanNewsCharityPayInListByUserId(Integer userId);

    /**
     * 查询省级个人捐款记录
     *
     * @param userId
     * @return
     */
    List<ProNewsCharityPayIn> getProNewsCharityPayInListByUserId(Integer userId);

    /**
     * 个人捐款记录
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page getPayInPageByUserId(Integer userId, Integer pageNo, Integer pageSize);
}
