package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserNewsInfo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 个人日志 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
public interface IAllUserNewsInfoService extends IService<AllUserNewsInfo> {

    /**
     * 根据用户查询日志
     * @param userId
     * @param list
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<AllUserNewsInfo> getAllUserNewsInfoPage(Integer userId, List list,Integer pageNo,Integer pageSize);

    /**
     * 新增   日志
     * @param allUserNewsInfo
     * @return
     */
    AllUserNewsInfo insertAllUserNewsInfo(AllUserNewsInfo allUserNewsInfo);

    /**
     * 修改 日志
     * @param allUserNewsInfo
     * @return
     */
    AllUserNewsInfo updateAllUserNewsInfo(AllUserNewsInfo allUserNewsInfo);

    /**
     * 删除 日志
     * @param id
     * @param userId
     * @return
     */
    Boolean deleteAllUserNewsInfo(Integer id, Integer userId);

    /**
     * 日志 详情
     * @param id
     * @return
     */
    AllUserNewsInfo getAllUserNewsInfoById(Integer id);
}
