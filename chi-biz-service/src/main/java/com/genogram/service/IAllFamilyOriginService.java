package com.genogram.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
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

    /**
     * 删除姓氏起源信息
     *
     * @param id
     * @param status
     * @param userLogin
     * @return
     */
    Boolean deleteOrigin(Integer id, int status, AllUserLogin userLogin);

    /**
     * 全国姓氏起源查询
     *
     * @param pageNo
     * @param pageSize
     * @param entity
     * @return
     */
    Page<AllFamilyOrigin> getOriginPage(Integer pageNo, Integer pageSize, Wrapper<AllFamilyOrigin> entity);
}
