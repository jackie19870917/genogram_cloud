package com.genogram.service;

import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.FanSysSite;
import com.genogram.entity.ProSysSite;

import java.util.List;

/**
 * @author: Toxicant
 * @date: 2018-11-23
 */
public interface ISysSiteService extends IService<FanSysSite> {

    /**
     * 县级开通网站
     *
     * @param fanSysSite
     * @return
     */
    FanSysSite insertFanSysSite(FanSysSite fanSysSite);

    /**
     * 省级开通网站
     *
     * @param proSysSite
     * @return
     */
    ProSysSite insertProSysSite(ProSysSite proSysSite);

    /**
     * 根据姓氏和父编号   查询
     * @param familyCode
     * @param parent
     * @return
     */
    List<FanSysSite> getFanSysSite(Integer familyCode,String parent);

    /**
     * 查询
     *
     * @return
     */
    List<ProSysSite> getProSysSite();
}
