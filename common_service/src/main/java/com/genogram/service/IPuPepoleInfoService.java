package com.genogram.service;

import com.genogram.entity.AllUserLogin;
import com.genogram.entity.PuPepoleInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yizx
 * @since 2019-01-18
 */
public interface IPuPepoleInfoService extends IService<PuPepoleInfo> {

    /**
     * 添加人物信息 修改
     * @param puPepoleInfo
     * @param userLogin
     * @param puBaseInfoId
     * @return
     */
    Boolean addPuPepoleInfo(PuPepoleInfo puPepoleInfo, AllUserLogin userLogin, Integer puBaseInfoId, Integer pepoleId, Integer isPepId);
}
