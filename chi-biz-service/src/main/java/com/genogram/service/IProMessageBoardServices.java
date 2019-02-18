package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.AllMessageBoard;

/**
 * @author Administrator
 */
public interface IProMessageBoardServices extends IService<AllMessageBoard> {
    /**
     * 省级留言板后台新增
     *
     * @param allMessageBoard
     * @return
     */
    boolean addOrUpdateRecord(AllMessageBoard allMessageBoard);

    /**
     * 后台查询
     *
     * @param siteId
     * @param sourceType
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<AllMessageBoard> getMessageBoard(Integer siteId, Integer sourceType, Integer pageNo, Integer pageSize);
}
