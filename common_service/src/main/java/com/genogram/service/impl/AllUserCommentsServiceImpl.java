package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.AllUserComments;
import com.genogram.entity.AllUserReply;
import com.genogram.entityvo.CommentVo;
import com.genogram.mapper.AllUserCommentsMapper;
import com.genogram.service.IAllUserCommentsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IAllUserReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author wangwei
 * @since 2018-12-07
 */
@Service
public class AllUserCommentsServiceImpl extends ServiceImpl<AllUserCommentsMapper, AllUserComments> implements IAllUserCommentsService {

    @Autowired
    private IAllUserReplyService allUserReplyService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @Override
    public List<CommentVo> getAllUserComments(Integer topicId, String entityName) {
        List<CommentVo> commentVoList = null;
        Wrapper<AllUserComments> Wrapper = new EntityWrapper<>();
        Wrapper.eq("topic_id", topicId);
        Wrapper.eq("entity_name", entityName);
        Wrapper.eq("status", 1);
        //Wrapper.groupBy("create_time").
        List<AllUserComments> AllUserCommentsList = this.selectList(Wrapper);
        CommentVo commentVo;
        CommentVo commentToReplyVo;
        List<CommentVo> commentToReplyVoList = null;
        CommentVo replyToReplyVo;
        //构造评论结构体
        if (!StringUtils.isEmpty(AllUserCommentsList)) {

            for (AllUserComments allUserComments : AllUserCommentsList) {
                //创建一条评论实体
                commentVo = new CommentVo();

                if (null == commentVoList || commentVoList.size() == 0) {
                    commentVoList = new ArrayList<CommentVo>();
                }
                commentVo.setId(allUserComments.getId());
                commentVo.setUserName(allUserLoginService.getAllUserLoginById(allUserComments.getCreateUser()).getUserName());
                commentVo.setContent(allUserComments.getContent());
                commentVo.setCreateTime(allUserComments.getCreateTime());
                List<AllUserReply> allUserReplylist = allUserReplyService.getAllUserReply(allUserComments.getId());
                //CommentVo.setAllUserReplyList(allUserReplylist);
                if (!StringUtils.isEmpty(allUserReplylist)) {
                    commentToReplyVoList = new ArrayList<CommentVo>();
                    for (AllUserReply allUserReply : allUserReplylist) {
                        commentToReplyVo = new CommentVo();
                        commentToReplyVo.setId(allUserReply.getId());
                        commentToReplyVo.setUserName(allUserLoginService.getAllUserLoginById(allUserReply.getCreateUser()).getUserName());
                        commentToReplyVo.setContent(allUserReply.getContent());
                        commentToReplyVo.setCreateTime(allUserReply.getCreateTime());

                        if (allUserReply.getReplyType() == 1) {
                            //评论的回复
                            commentToReplyVoList.add(commentToReplyVo);
                        } else if (allUserReply.getReplyType() == 2) {
                            //回复的回复
                            if (commentToReplyVoList != null && commentToReplyVoList.size() > 0) {
                                for (CommentVo parent : commentToReplyVoList) {
                                    Integer tempId = allUserReply.getReplyId();
                                    if (tempId == parent.getId()) {
                                        //回复的回复
                                        replyToReplyVo = new CommentVo();
                                        replyToReplyVo.setId(allUserReply.getId());
                                        replyToReplyVo.setUserName(allUserLoginService.getAllUserLoginById(allUserReply.getCreateUser()).getUserName());
                                        replyToReplyVo.setContent(allUserReply.getContent());
                                        replyToReplyVo.setCreateTime(allUserReply.getCreateTime());
                                        if (null == parent.getNext() || parent.getNext().size() == 0) {
                                            parent.setNext(new ArrayList<CommentVo>());
                                        }
                                        parent.getNext().add(replyToReplyVo);
                                        break;
                                    } else {
                                        //与回复表的评论进行匹配

                                        while ((null != parent) || (parent.getNext() != null) || (parent.getNext().size() > 0)) {
                                            boolean flag = false;
                                            List<CommentVo> commentVoListParent = parent.getNext();
                                            if (null != commentVoListParent) {
                                                for (CommentVo cv : commentVoListParent) {
                                                    if (tempId == cv.getId()) {
                                                        //回复的回复
                                                        replyToReplyVo = new CommentVo();
                                                        replyToReplyVo.setId(allUserReply.getId());
                                                        replyToReplyVo.setUserName(allUserLoginService.getAllUserLoginById(allUserReply.getCreateUser()).getUserName());
                                                        replyToReplyVo.setContent(allUserReply.getContent());
                                                        replyToReplyVo.setCreateTime(allUserReply.getCreateTime());
                                                        if (null == parent.getNext() || parent.getNext().size() == 0) {
                                                            cv.setNext(new ArrayList<CommentVo>());
                                                        }
                                                        if (null == cv.getNext() || cv.getNext().size() == 0) {
                                                            cv.setNext(new ArrayList<CommentVo>());
                                                        }
                                                        cv.getNext().add(replyToReplyVo);
                                                        flag = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (flag) {
                                                flag = false;
                                                break;
                                            }
                                            if (null != parent.getNext() && parent.getNext().size() > 0) {
                                                //
                                                parent = parent.getNext().get(0);
                                            } else {
                                                break;
                                            }
                                        }
                                    }


                                }

                            }
                        }
                    }

                } else {
                    continue;
                }

                commentVo.setNext(commentToReplyVoList);

                commentVoList.add(commentVo);
            }

        }
/*
        public List<ReplyDTO> getReplyListByRid(Long rid) {
            List<ReplyDO> replyDOList = replyDAO.queryReplyByCid(rid);
            if (replyDOList == null || replyDOList.size() == 0) {
                return new ArrayList<>();
            }

            List<ReplyDTO> replyDTOList = new ArrayList<>();
            List<ReplyDTO> parentList = new ArrayList<>();
            for (ReplyDO replyDO : replyDOList) {
                ReplyDTO replyDTO = convertReplyToDTO(replyDO);
                if (replyDTO.getReplyType() == ReplyType.COMMENT) {


                    replyDTOList.add(replyDTO);
                    parentList.add(replyDTO);
                } else {
                    boolean foundParent = false;
                    if (replyDTOList.size() > 0) {
                        for (ReplyDTO parent : parentList) {
                            if (parent.getId().equals(replyDTO.getReplyId())) {
                                if (parent.getNext() == null) {
                                    parent.setNext(new ArrayList<ReplyDTO>());
                                }
                                parent.getNext().add(replyDTO);
                                parentList.add(replyDTO);
                                foundParent = true;
                                break;
                            }
                        }
                    }
                    if (!foundParent) {
                        throw new RuntimeException("sort reply error,should not go here.");
                    }
                }
            }
            return replyDTOList;
        }*/
        return commentVoList;
    }

    @Override
    public Boolean insertAllUserComments(AllUserComments allUserComments) {
        return this.insert(allUserComments);
    }

    @Override
    public Boolean delAllUserComments(Integer id) {
        return this.deleteById(id);
    }

    @Override
    public Integer delAllUserCommentslist(List<Integer> idList) {
        return this.delAllUserCommentslist(idList);
    }


}

