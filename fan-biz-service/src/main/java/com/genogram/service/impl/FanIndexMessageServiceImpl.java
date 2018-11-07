package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexMessage;
import com.genogram.mapper.FanIndexMessageMapper;
import com.genogram.service.IFanIndexMessageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 联谊会在线聊天 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@Service
public class FanIndexMessageServiceImpl extends ServiceImpl<FanIndexMessageMapper, FanIndexMessage> implements IFanIndexMessageService {

    //联谊会首页聊天记录
    @Override
    public Page<FanIndexMessage> getChatRecordList(Integer siteId, Integer status,Integer pageNo,Integer pageSize) {
        Wrapper<FanIndexMessage> entity = new EntityWrapper<FanIndexMessage>();
        entity.eq("site_id", siteId);
        entity.eq("status", status);
        entity.orderBy("create_time", false);
        Page<FanIndexMessage> fanNewsCultureZipais = this.selectPage(new Page<FanIndexMessage>(pageNo, pageSize), entity);
        return fanNewsCultureZipais;
    }
}
