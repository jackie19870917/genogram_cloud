package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.ChiNewsFamilyRecord;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 省级-记录家族-家族动态,家族通告文章表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-15
 */
public interface IChiNewsFamilyRecordService extends IService<ChiNewsFamilyRecord> {

    /**
     * 全国记录家族文章新增 修改
     *
     * @param chiNewsFamilyRecord
     * @param userLogin
     * @return
     */
    Boolean addOrUpdateRecord(ChiNewsFamilyRecord chiNewsFamilyRecord, AllUserLogin userLogin);

    /**
     * 全国记录家族文章删除
     *
     * @param id
     * @param userLogin
     * @param status
     * @return
     */
    Boolean deleteRecord(Integer id, AllUserLogin userLogin, int status);

    /**
     * 全国记录家族文章查询
     *
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<ChiNewsFamilyRecord> getRecordPage(Wrapper<ChiNewsFamilyRecord> entity, Integer pageNo, Integer pageSize);
}
