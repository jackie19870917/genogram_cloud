package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCultureZipai;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

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
     * @param entity 查询条件
     * @param pageNo 当前页
     * @param pageSize 每页显示条数
     * @return
     */
    Page<FanNewsCultureZipai> commonality(Wrapper<FanNewsCultureZipai> entity, Integer pageNo, Integer pageSize);

    /**
     *联谊会首页字派查询
     * @param entity 查询条件
     * @return
     */
    StringBuffer commonalityIndex(Wrapper<FanNewsCultureZipai> entity);

    /**
     *联谊会字派后台进入修改页面
     * @param id
     * @return
     */
    FanNewsCultureZipai getZiPaiDetail(Integer id);

    /**
     * 联谊会字派后台新增
     * @param fanNewsCultureZipai
     * @return
     */
    boolean addOrUpdateZiPai(FanNewsCultureZipai fanNewsCultureZipai);

    /**
     *联谊会家族字派后台删除
     * @param id
     * @param status
     */
    Boolean deleteByIdZipai(Integer id, int status);
}
