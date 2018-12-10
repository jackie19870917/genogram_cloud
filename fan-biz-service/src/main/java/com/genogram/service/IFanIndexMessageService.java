package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexMessage;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 联谊会在线聊天 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanIndexMessageService extends IService<FanIndexMessage> {

    /**
     * 联谊会首页聊天记录
     *
     * @param siteId   网站ID
     * @param status   状态
     * @param pageNo   当前页
     * @param pageSize 每页显示条数
     * @return
     */
    Page<FanIndexMessage> getChatRecordList(Integer siteId, Integer status, Integer pageNo, Integer pageSize);
}
