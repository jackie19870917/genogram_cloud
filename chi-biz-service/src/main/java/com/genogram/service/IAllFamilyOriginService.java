package com.genogram.service;

import com.genogram.entity.AllFamilyOrigin;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entity.AllUserLogin;

/**
 * <p>
 * 姓氏起源 服务类
 * </p>
 *
 * @author wangwei
 * @since 2019-02-18
 */
public interface IAllFamilyOriginService extends IService<AllFamilyOrigin> {

    /**
     * 全国姓氏起源新增 修改
     *
     * @param allFamilyOrigin
     * @param userLogin
     * @return
     */
    Boolean addOrUpdateOrigin(AllFamilyOrigin allFamilyOrigin, AllUserLogin userLogin);
}
