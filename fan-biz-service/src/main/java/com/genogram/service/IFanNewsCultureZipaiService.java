package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCultureZipai;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 联谊会-家族文化-字派表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsCultureZipaiService extends IService<FanNewsCultureZipai> {

    /**
     * 联谊会字派查询
     * @param siteId 显示位置Id
     * @param status 状态
     * @param pageNo 当前页
     * @param pageSize 每页显示条数
     * @return
     */
    Page<FanNewsCultureZipai> commonality(Integer siteId, Integer status, Integer pageNo, Integer pageSize);
}