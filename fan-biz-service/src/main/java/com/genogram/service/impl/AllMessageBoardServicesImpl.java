package com.genogram.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.AllMessageBoard;
import com.genogram.mapper.AllMessageBoardMapper;
import com.genogram.service.IFanMessageBoardService;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
@Service
public class AllMessageBoardServicesImpl extends ServiceImpl<AllMessageBoardMapper, AllMessageBoard> implements IFanMessageBoardService {
//生成时间
    @Override
    public boolean addOrUpdateRecord(AllMessageBoard allMessageBoard) {
        //生成时间
        Timestamp format = DateUtil.getCurrentTimeStamp();
        //存入创建时间
        allMessageBoard.setCreateTime(format);
        allMessageBoard.setCreateUser(null);
        //插入修改时间
        allMessageBoard.setUpdateTime(format);
        allMessageBoard.setUpdateUser(null);
        //存入修改时间
        allMessageBoard.setUpdateTime(format);
        allMessageBoard.setUpdateUser(null);
        boolean result = this.insertOrUpdate(allMessageBoard);

        return result;
    }
}
