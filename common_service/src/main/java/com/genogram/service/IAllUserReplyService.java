package com.genogram.service;

import com.genogram.entity.AllUserReply;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yizx
 * @since 2018-12-07
 */
public interface IAllUserReplyService extends IService<AllUserReply> {

    List<AllUserReply> getAllUserReply(Integer commentId);

    Boolean insertAllUserReply(AllUserReply allUserReply);

    Boolean delAllUserReply(Integer id);

}
