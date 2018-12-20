package com.genogram.service;

import com.genogram.entity.AllUserReply;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yizx
 * @since 2018-12-07
 */
public interface IAllUserReplyService extends IService<AllUserReply> {

    /**
     * 获取回复
     *
     * @param commentId
     * @return
     */
    List<AllUserReply> getAllUserReply(Integer commentId);

    /**
     * 新增回复
     *
     * @param allUserReply
     * @return
     */
    Boolean insertAllUserReply(AllUserReply allUserReply);

    /**
     * 删除回复
     *
     * @param id
     * @return
     */
    Boolean delAllUserReply(Integer id);

}
