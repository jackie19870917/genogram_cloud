package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.FanNewsCultureNews;
import com.genogram.entityvo.FamilyCultureVo;
import com.genogram.entityvo.NewsDetailVo;

/**
 * <p>
 * 联谊会-家族文化文章表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
public interface IFanNewsCultureNewsService extends IService<FanNewsCultureNews> {

    /**
     *联谊会家族文化详情查询
     * @param id  文章ID
     * @return
     */
    NewsDetailVo getFamilyCultureDetail(Integer id);

    /**
     * 联谊会家族文化增加查看数
     * @param id
     */
    void addVisitNum(Integer id);
}
