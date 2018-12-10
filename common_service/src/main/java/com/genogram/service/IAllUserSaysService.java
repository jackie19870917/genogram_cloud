package com.genogram.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllUserSays;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 个人说说 服务类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-05
 */
public interface IAllUserSaysService extends IService<AllUserSays> {

    /**
     * 根据用户查询说说
     *
     * @param userId
     * @param list
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<AllUserSays> getAllUserSaysPage(Integer userId, List list, Integer pageNo, Integer pageSize);

    /**
     * 新增   说说
     *
     * @param allUserSays
     * @return
     */
    AllUserSays insertAllUserSays(AllUserSays allUserSays);

    /**
     * 删除 说说
     *
     * @param id
     * @param userId
     * @return
     */
    Boolean deleteAllUserSays(Integer id, Integer userId);

    /**
     * 说说详情
     *
     * @param id
     * @return
     */
    AllUserSays getAllUserSaysById(Integer id);
}
