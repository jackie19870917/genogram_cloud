package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCharityPayIn;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 联谊网-慈善公益-捐款人 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsCharityPayInService extends IService<FanNewsCharityPayIn> {

    Page<FanNewsCharityPayIn> selectPage(Integer siteId, Integer status, Integer pageNo, Integer pageSize);
}
