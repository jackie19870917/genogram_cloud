package com.genogram.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.genogram.entity.*;
import com.genogram.entityvo.CommentVo;
import com.genogram.mapper.AllUserCommentsMapper;
import com.genogram.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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

    @Autowired
    private IAllUserNewsInfoService allUserNewsInfoService;

    @Autowired
    private IAllUserPicsService allUserPicsService;

    @Autowired
    private IAllUserSaysService allUserSaysService;

    @Autowired
    private IAllUserVideosService allUserVideosService;

    @Autowired
    private CommonIFanNewsCharityOutService fanNewsCharityOutService;

    @Autowired
    private CommonIFanNewsCharityPayInService fanNewsCharityPayInService;

    @Autowired
    private CommonIFanNewsCultureNewsService fanNewsCultureNewsService;

    @Autowired
    private CommonIFanNewsCultureZipaiService fanNewsCultureZipaiService;
    @Autowired
    private CommonIFanNewsFamilyRecordService fanNewsFamilyRecordService;
    @Autowired
    private CommonIFanNewsFamilyRecordVedioService fanNewsFamilyRecordVedioService;

    @Autowired
    private CommonIFanNewsFamousAncestorService fanNewsFamousAncestorService;

    @Autowired
    private CommonIFanNewsFamousPersonService fanNewsFamousPersonService;

    @Autowired
    private CommonIFanNewsIndustryService fanNewsIndustryService;

    @Autowired
    private CommonIFanNewsUploadTreeFileService fanNewsUploadTreeFileService;

    @Autowired
    private CommonIFanSysCharitableDeclareService fanSysCharitableDeclareService;


    @Override
    public List<CommentVo> getAllUserComments(Integer topicId, String entityName) {
        List<CommentVo> commentVoList = null;
        Wrapper<AllUserComments> wrapper = new EntityWrapper<>();
        wrapper.eq("topic_id", topicId);
        wrapper.eq("entity_name", entityName);
        wrapper.eq("status", 1);
        //Wrapper.groupBy("create_time").
        List<AllUserComments> allUserCommentsList = this.selectList(wrapper);
        CommentVo commentVo;
        CommentVo commentToReplyVo;
        List<CommentVo> commentToReplyVoList = null;
        CommentVo replyToReplyVo;
        //构造评论结构体
        if (!StringUtils.isEmpty(allUserCommentsList)) {

            for (AllUserComments allUserComments : allUserCommentsList) {
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
                                    if (tempId.equals(parent.getId())) {
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
                                                    if (tempId.equals(cv.getId())) {
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

    /**
     * 各个表添加评论数
     *
     * @Author: yuzhou
     * @Date: 2018-12-20
     * @Time: 18:05
     * @Param:
     * @return:
     * @Description:
     */
    @Override
    public void addCommon(AllUserComments allUserComments) {
        //判断
        if ("AllUserNewsInfo".equals(allUserComments.getEntityName())) {
            //个人日志
            AllUserNewsInfo allUserNewsInfo = allUserNewsInfoService.selectById(allUserComments.getTopicId());
            allUserNewsInfo.setCommentCount(allUserNewsInfo.getCommentCount() + 1);
            allUserNewsInfoService.updateAllColumnById(allUserNewsInfo);
        } else if ("AllUserPics".equals(allUserComments.getEntityName())) {
            //个人视频
            AllUserPics allUserPics = allUserPicsService.selectById(allUserComments.getTopicId());
            allUserPics.setCommentCount(allUserPics.getCommentCount() + 1);
            allUserPicsService.updateAllColumnById(allUserPics);
        } else if ("AllUserSays".equals(allUserComments.getEntityName())) {
            //个人说说
            AllUserSays allUserSays = allUserSaysService.selectById(allUserComments.getTopicId());
            allUserSays.setCommentCount(allUserSays.getCommentCount() + 1);
            allUserSaysService.updateAllColumnById(allUserSays);
        } else if ("AllUserVideos".equals(allUserComments.getEntityName())) {
            //个人视频
            AllUserVideos allUserVideos = allUserVideosService.selectById(allUserComments.getTopicId());
            allUserVideos.setCommentCount(allUserVideos.getCommentCount() + 1);
            allUserVideosService.updateAllColumnById(allUserVideos);
        } else if ("FanNewsCharityOut".equals(allUserComments.getEntityName())) {
            //家族慈善财务收支
            FanNewsCharityOut fanNewsCharityOut = fanNewsCharityOutService.selectById(allUserComments.getTopicId());
            fanNewsCharityOut.setCommentCount(fanNewsCharityOut.getCommentCount() + 1);
            fanNewsCharityOutService.updateAllColumnById(fanNewsCharityOut);
        } else if ("FanNewsCharityPayIn".equals(allUserComments.getEntityName())) {
            //慈善公益捐款人
            FanNewsCharityPayIn fanNewsCharityPayIn = fanNewsCharityPayInService.selectById(allUserComments.getTopicId());
            fanNewsCharityPayIn.setCommentCount(fanNewsCharityPayIn.getCommentCount() + 1);
            fanNewsCharityPayInService.updateAllColumnById(fanNewsCharityPayIn);
        } else if ("FanNewsCultureNews".equals(allUserComments.getEntityName())) {
            //家族文化
            FanNewsCultureNews fanNewsCultureNews = fanNewsCultureNewsService.selectById(allUserComments.getTopicId());
            fanNewsCultureNews.setCommentCount(fanNewsCultureNews.getCommentCount() + 1);
            fanNewsCultureNewsService.updateAllColumnById(fanNewsCultureNews);
        } else if ("FanNewsCultureZipai".equals(allUserComments.getEntityName())) {
            //家族字派
            FanNewsCultureZipai fanNewsCultureZipai = fanNewsCultureZipaiService.selectById(allUserComments.getTopicId());
            fanNewsCultureZipai.setCommentCount(fanNewsCultureZipai.getCommentCount() + 1);
            fanNewsCultureZipaiService.updateAllColumnById(fanNewsCultureZipai);
        } else if ("FanNewsFamilyRecord".equals(allUserComments.getEntityName())) {
            //记录家族文章
            FanNewsFamilyRecord fanNewsFamilyRecord = fanNewsFamilyRecordService.selectById(allUserComments.getTopicId());
            fanNewsFamilyRecord.setCommentCount(fanNewsFamilyRecord.getCommentCount() + 1);
            fanNewsFamilyRecordService.updateAllColumnById(fanNewsFamilyRecord);
        } else if ("FanNewsFamilyRecordVedio".equals(allUserComments.getEntityName())) {
            //记录家族视频
            FanNewsFamilyRecordVedio fanNewsFamilyRecordVedio = fanNewsFamilyRecordVedioService.selectById(allUserComments.getTopicId());
            fanNewsFamilyRecordVedio.setCommentCount(fanNewsFamilyRecordVedio.getCommentCount() + 1);
            fanNewsFamilyRecordVedioService.updateAllColumnById(fanNewsFamilyRecordVedio);
        } else if ("FanNewsFamousAncestor".equals(allUserComments.getEntityName())) {
            //祖先分支
            FanNewsFamousAncestor fanNewsFamousAncestor = fanNewsFamousAncestorService.selectById(allUserComments.getTopicId());
            fanNewsFamousAncestor.setCommentCount(fanNewsFamousAncestor.getCommentCount() + 1);
            fanNewsFamousAncestorService.updateAllColumnById(fanNewsFamousAncestor);
        } else if ("FanNewsFamousPerson".equals(allUserComments.getEntityName())) {
            //家族名人
            FanNewsFamousPerson fanNewsFamousPerson = fanNewsFamousPersonService.selectById(allUserComments.getTopicId());
            fanNewsFamousPerson.setCommentCount(fanNewsFamousPerson.getCommentCount() + 1);
            fanNewsFamousPersonService.updateAllColumnById(fanNewsFamousPerson);
        } else if ("FanNewsIndustry".equals(allUserComments.getEntityName())) {
            //家族产业
            FanNewsIndustry fanNewsIndustry = fanNewsIndustryService.selectById(allUserComments.getTopicId());
            fanNewsIndustry.setCommentCount(fanNewsIndustry.getCommentCount() + 1);
            fanNewsIndustryService.updateAllColumnById(fanNewsIndustry);
        } else if ("FanNewsUploadTreeFile".equals(allUserComments.getEntityName())) {
            //电子家谱
            FanNewsUploadTreeFile fanNewsUploadTreeFile = fanNewsUploadTreeFileService.selectById(allUserComments.getTopicId());
            fanNewsUploadTreeFile.setCommentCount(fanNewsUploadTreeFile.getCommentCount() + 1);
            fanNewsUploadTreeFileService.updateAllColumnById(fanNewsUploadTreeFile);
        } else if ("FanSysCharitableDeclare".equals(allUserComments.getEntityName())) {
            //慈善帮扶
            FanSysCharitableDeclare fanSysCharitableDeclare = fanSysCharitableDeclareService.selectById(allUserComments.getTopicId());
            fanSysCharitableDeclare.setCommentCount(fanSysCharitableDeclare.getCommentCount() + 1);
            fanSysCharitableDeclareService.updateAllColumnById(fanSysCharitableDeclare);
        }
    }

}

