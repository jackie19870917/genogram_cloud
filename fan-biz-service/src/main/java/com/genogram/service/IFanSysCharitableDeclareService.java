package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.FanSysCharitableDeclare;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.FamilyRecordVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaohei
 * @since 2018-11-29
 */
public interface IFanSysCharitableDeclareService extends IService<FanSysCharitableDeclare> {
    /**
     * 联谊会慈善帮扶后台分页查询
     * @param showId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<FanSysCharitableDeclare> getCharitableDeclarePage(Integer showId, Integer pageNo, Integer pageSize);

    /**
     * 联谊会慈善帮扶后台新增修改
     * @param fanSysCharitableDeclare
     * @param fileName
     * @param filePath
     * @return
     */
    boolean addOrUpdateCharitableDeclare(FanSysCharitableDeclare fanSysCharitableDeclare,String fileName,String filePath);
    /**
     * 联谊会慈善帮扶删除
     * @param id
     * @param status
     * @return
     */
    Boolean deleteCharitableDeclareById(Integer id, int status);
}
