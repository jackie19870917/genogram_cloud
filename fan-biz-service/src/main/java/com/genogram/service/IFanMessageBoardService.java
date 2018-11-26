package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.AllMessageBoard;
import com.genogram.entityvo.FamilyRecordVo;

import java.util.List;

public interface IFanMessageBoardService extends IService<AllMessageBoard> {
    /**
     * 联谊会留言板后台新增
     * @param
     * @return
     */
    boolean addOrUpdateRecord(AllMessageBoard allMessageBoard);

    /**
     * 后台查询
     * @return
     */
    Page<AllMessageBoard> getMessageBoard(Integer siteId,Integer sourceType,Integer pageNo, Integer pageSize);
}
