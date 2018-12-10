package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserPics;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 个人照片 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
public interface IAllUserPicsService extends IService<AllUserPics> {

    /**
     * 根据用户查询照片
     *
     * @param userId
     * @param list
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<AllUserPics> getAllUserPicsPage(Integer userId, List list, Integer pageNo, Integer pageSize);

    /**
     * 新增/修改   照片
     *
     * @param allUserPics
     * @return
     */
    AllUserPics insertOrUpdateAllUserPics(AllUserPics allUserPics);

    /**
     * 删除 照片
     *
     * @param id
     * @param userId
     * @return
     */
    Boolean deleteAllUserPics(Integer id, Integer userId);

    /**
     * 照片详情
     *
     * @param id
     * @return
     */
    AllUserPics getAllUserPicsById(Integer id);
}
