package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.ProNewsCultureZipai;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 省级-家族文化-字派表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface IProNewsCultureZipaiService extends IService<ProNewsCultureZipai> {

    /**
     * 省级家族字派查询
     * @param entity  查询条件
     * @param pageNo  当前页
     * @param pageSize 每页显示条数
     * @return
     */
    Page<ProNewsCultureZipai> commonality(Wrapper<ProNewsCultureZipai> entity, Integer pageNo, Integer pageSize);
}
