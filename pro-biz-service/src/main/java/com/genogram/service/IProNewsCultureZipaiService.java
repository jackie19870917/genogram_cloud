package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.ProNewsCultureNews;
import com.genogram.entity.ProNewsCultureZipai;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsCultureZipaiVo;

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

    /**
     * 省级家族字派模糊查询
     * @param entity  查询条件
     * @param pageNo  当前页
     * @param pageSize  每页显示条数
     * @return
     */
    Page<NewsCultureZipaiVo> getZipaiVaguePage(Wrapper<ProNewsCultureZipai> entity, Integer pageNo, Integer pageSize,Integer showId,String zipaiTxt);

    /**
     * 省级字派进入后台页面
     * @param id 主键
     * @return
     */
    ProNewsCultureZipai getZiPaiDetail(Integer id);

    /**
     * 省级家族字派后台新增修改
     * @param proNewsCultureZipai
     * @return
     */
    boolean addOrUpdateZiPai(ProNewsCultureZipai proNewsCultureZipai);

    /**
     * 省级家族字派后台删除
     * @param id
     * @param status
     * @return
     */
    Boolean deleteZipaiById(Integer id, int status);
}
