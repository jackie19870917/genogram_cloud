package com.genogram.service;

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
     * @param showId 显示位置Id
     * @param status 状态
     * @param pageNo 当前页
     * @param pageSize 每页显示条数
     * @return
     */
    Page<FanNewsCultureZipai> commonality(Integer showId, Integer status, Integer pageNo, Integer pageSize);

    /**
     *联谊会首页字派查询
     * @param showId 显示位置Id
     * @param status 状态
     * @return
     */
    StringBuffer CommonalityIndex(Integer showId, Integer status);

    /**
     *联谊会字派后台进入修改页面
     * @param id 状态
     */
    FanNewsCultureZipai getZiPaiDetail(Integer id);

    /**
     * 联谊会字派后台新增
     * @param fanNewsCultureZipai
     * @return
     */
    boolean addOrUpdateZiPai(FanNewsCultureZipai fanNewsCultureZipai);

}
