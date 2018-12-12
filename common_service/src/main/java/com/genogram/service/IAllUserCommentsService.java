package com.genogram.service;

import com.genogram.entity.AllUserComments;
import com.baomidou.mybatisplus.service.IService;
import com.genogram.entityvo.CommentVo;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author yizx
 * @since 2018-12-07
 */
public interface IAllUserCommentsService extends IService<AllUserComments> {

    List<CommentVo> getAllUserComments(Integer topicId, String serviceName);

}
