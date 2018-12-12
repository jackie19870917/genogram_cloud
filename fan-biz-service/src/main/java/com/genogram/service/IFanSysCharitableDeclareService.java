package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.FanSysCharitableDeclare;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyRecordVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xiaohei
 * @since 2018-11-29
 */
public interface IFanSysCharitableDeclareService extends IService<FanSysCharitableDeclare> {
    /**
     * 联谊会慈善帮扶后台分页查询
     *
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FanSysCharitableDeclare> getCharitableDeclarePage(Wrapper<FanSysCharitableDeclare> entity, Integer pageNo, Integer pageSize);

    /**
     * 联谊会慈善帮扶删除
     *
     * @param id
     * @return
     */
    Boolean deleteCharitableDeclareById(Integer id);

    /**
     * 联谊会慈善帮扶申报添加
     * @param fanSysCharitableDeclare  慈善帮扶申报表
     * @return
     */
    Boolean addCharityAssist(FanSysCharitableDeclare fanSysCharitableDeclare);
}
