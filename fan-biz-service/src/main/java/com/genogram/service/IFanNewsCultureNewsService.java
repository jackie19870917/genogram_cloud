package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanNewsCultureNews;
import com.baomidou.mybatisplus.service.IService;
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
     *联谊会家族文化查询
     * @param showId 显示位置Id
     * @param status 状态
     * @param pageNo 当前页
     * @param pageSize 每页显示条数
     * @return
     */
    Page<FamilyCultureVo> getFamilyCulturePage(Integer showId, Integer status, Integer pageNo, Integer pageSize);

    /**
     *联谊会家族文化详情查询
     * @param showId   显示位置Id
     * @param id  文章ID
     * @return
     */
    NewsDetailVo getFamilyCultureDetail(Integer showId, Integer id);
}
