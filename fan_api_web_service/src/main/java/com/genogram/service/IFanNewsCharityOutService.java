package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.FanNewsCharityOut;

import java.util.List;

/**
 * <p>
 * 联谊会-家族慈善财务支出表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsCharityOutService extends IService<FanNewsCharityOut> {

    Page<FanNewsCharityOut> selectPage(Integer siteId, Integer status, Integer pageNo, Integer pageSize);

    List<FanNewsCharityOut> selectPageList(Integer siteId, Integer status, Integer pageNo, Integer pageSize);

}
