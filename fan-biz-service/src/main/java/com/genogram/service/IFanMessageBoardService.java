package com.genogram.service;

import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.AllMessageBoard;

public interface IFanMessageBoardService extends IService<AllMessageBoard> {
    /**
     * 联谊会留言板后台新增
     * @param
     * @return
     */
    boolean addOrUpdateRecord(AllMessageBoard allMessageBoard);
}
