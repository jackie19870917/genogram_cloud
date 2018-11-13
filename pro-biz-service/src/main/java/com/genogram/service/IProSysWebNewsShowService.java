package com.genogram.service;

import com.genogram.entity.ProSysWebNewsShow;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.SysWebMenuVo;

import java.util.List;

/**
 * <p>
 * 省级网站文章挂靠位置表 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-11-13
 */
public interface IProSysWebNewsShowService extends IService<ProSysWebNewsShow> {
    public List<SysWebMenuVo> getTitlesByMenuId(String hostIp, int siteId, int menuId);

}
