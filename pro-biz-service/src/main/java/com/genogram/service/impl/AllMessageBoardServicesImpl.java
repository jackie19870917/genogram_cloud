package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.entity.AllMessageBoard;
import com.genogram.mapper.AllMessageBoardMapper;
import com.genogram.service.IProMessageBoardServices;
import com.genogram.unit.DateUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * 省级留言板
 *
 * @author Administrator
 */
@Service
public class AllMessageBoardServicesImpl extends ServiceImpl<AllMessageBoardMapper, AllMessageBoard> implements IProMessageBoardServices {
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
        boolean result = this.insertOrUpdate(allMessageBoard);
        return result;
    }
    /**
     * 留言板查询
     * @param
     * @return
     */
    @Override
    public Page<AllMessageBoard> getMessageBoard(Integer siteId, Integer sourceType, Integer pageNo, Integer pageSize) {
        Wrapper<AllMessageBoard> entity = new EntityWrapper<AllMessageBoard>();
        entity.orderBy("id",false);
        entity.eq("site_id", siteId);
        entity.eq("source_type",sourceType);
        Page<AllMessageBoard> fanNewsFamilyRecord =this.selectPage(new Page<AllMessageBoard>(pageNo, pageSize), entity);
        return fanNewsFamilyRecord;
    }
}
