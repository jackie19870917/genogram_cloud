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

    /**
     *  获取评论
     * @param topicId
     * @param serviceName
     * @return
     */
    List<CommentVo> getAllUserComments(Integer topicId, String serviceName);

    /**
     * 新增评论
     * @param allUserComments
     * @return
     */
    Boolean insertAllUserComments(AllUserComments allUserComments);

    /**
     * 删除评论
     * @param id
     * @return
     */
    Boolean delAllUserComments(Integer id);

    /**
     * 删除评论列表
     * @param idList
     * @return
     */
    Integer delAllUserCommentslist(List<Integer> idList);

}
