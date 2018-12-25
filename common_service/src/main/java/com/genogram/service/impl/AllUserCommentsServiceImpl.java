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
    private CommonFanNewsCharityOutService fanNewsCharityOutService;

    @Autowired
    private CommonFanNewsCharityPayInService fanNewsCharityPayInService;

    @Autowired
    private CommonFanNewsCultureNewsService fanNewsCultureNewsService;

    @Autowired
    private CommonFanNewsCultureZipaiService fanNewsCultureZipaiService;
    @Autowired
    private CommonFanNewsFamilyRecordService fanNewsFamilyRecordService;
    @Autowired
    private CommonFanNewsFamilyRecordVedioService fanNewsFamilyRecordVedioService;

    @Autowired
    private CommonFanNewsFamousAncestorService fanNewsFamousAncestorService;

    @Autowired
    private CommonFanNewsFamousPersonService fanNewsFamousPersonService;

    @Autowired
    private CommonFanNewsIndustryService fanNewsIndustryService;

    @Autowired
    private CommonFanNewsUploadTreeFileService fanNewsUploadTreeFileService;

    @Autowired
    private CommonFanSysCharitableDeclareService fanSysCharitableDeclareService;

    @Autowired
    private CommonProNewsCharityOutService proNewsCharityOutService;

    @Autowired
    private CommonProNewsCharityPayInService proNewsCharityPayInService;

    @Autowired
    private CommonProNewsCultureNewsService proNewsCultureNewsService;

    @Autowired
    private CommonProNewsCultureZipaiService proNewsCultureZipaiService;

    @Autowired
    private CommonProNewsFamilyRecordService proNewsFamilyRecordService;

    @Autowired
    private CommonProNewsFamilyRecordVedioService proNewsFamilyRecordVedioService;

    @Autowired
    private CommonProNewsFamousAncestorService proNewsFamousAncestorService;

    @Autowired
    private CommonProNewsFamousPersonService proNewsFamousPersonService;

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
                AllUserLogin UserLogin = allUserLoginService.getAllUserLoginById(allUserComments.getCreateUser());
                commentVo.setId(allUserComments.getId());
                commentVo.setUserName(UserLogin.getUserName());
                commentVo.setNickName(UserLogin.getNickName());
                commentVo.setPicSrc(UserLogin.getPicSrc());
                commentVo.setContent(allUserComments.getContent());
                commentVo.setCreateTime(allUserComments.getCreateTime());
                List<AllUserReply> allUserReplylist = allUserReplyService.getAllUserReply(allUserComments.getId());
                //CommentVo.setAllUserReplyList(allUserReplylist);
                if (!StringUtils.isEmpty(allUserReplylist)) {
                    commentToReplyVoList = new ArrayList<CommentVo>();
                    for (AllUserReply allUserReply : allUserReplylist) {
                        UserLogin = allUserLoginService.getAllUserLoginById(allUserComments.getCreateUser());
                        commentToReplyVo = new CommentVo();
                        commentToReplyVo.setId(allUserReply.getId());
                        commentToReplyVo.setUserName(UserLogin.getUserName());
                        commentToReplyVo.setNickName(UserLogin.getNickName());
                        commentToReplyVo.setPicSrc(UserLogin.getPicSrc());
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
                                        UserLogin = allUserLoginService.getAllUserLoginById(allUserComments.getCreateUser());
                                        replyToReplyVo = new CommentVo();
                                        replyToReplyVo.setId(allUserReply.getId());
                                        replyToReplyVo.setUserName(UserLogin.getUserName());
                                        replyToReplyVo.setNickName(UserLogin.getNickName());
                                        replyToReplyVo.setPicSrc(UserLogin.getPicSrc());
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
                                                        UserLogin = allUserLoginService.getAllUserLoginById(allUserComments.getCreateUser());
                                                        replyToReplyVo = new CommentVo();
                                                        replyToReplyVo.setId(allUserReply.getId());
                                                        replyToReplyVo.setUserName(UserLogin.getUserName());
                                                        replyToReplyVo.setNickName(UserLogin.getNickName());
                                                        replyToReplyVo.setPicSrc(UserLogin.getPicSrc());
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
        String userNewsInfo = "AllUserNewsInfo";
        String userPics = "AllUserPics";
        String userSays = "AllUserSays";
        String userVideos = "AllUserVideos";
        String newsCharityOut = "FanNewsCharityOut";
        String newsCharityIn = "FanNewsCharityPayIn";
        String newsCultureNews = "FanNewsCultureNews";
        String newsCultureZipai = "FanNewsCultureZipai";
        String newsFamilyRecord = "FanNewsFamilyRecord";
        String newsFamilyRecordVedio = "FanNewsFamilyRecordVedio";
        String newsFamousAncestor = "FanNewsFamousAncestor";
        String newsFamousPerson = "FanNewsFamousPerson";
        String newsIndustry = "FanNewsIndustry";
        String newsUploadTreeFile = "FanNewsUploadTreeFile";
        String sysCharitableDeclare = "FanSysCharitableDeclare";
        String charityOut = "ProNewsCharityOut";
        String charityPayIn = "ProNewsCharityPayIn";
        String cultureNews = "ProNewsCultureNews";
        String cultureZiPai = "ProNewsCultureZipai";
        String familyRecord = "ProNewsFamilyRecord";
        String familyRecordVideo = "ProNewsFamilyRecordVedio";
        String famousAncestor = "ProNewsFamousAncestor";
        String famousPerson = "ProNewsFamousPerson";
        //判断
        if (userNewsInfo.equals(allUserComments.getEntityName())) {
            //个人日志
            AllUserNewsInfo allUserNewsInfo = allUserNewsInfoService.selectById(allUserComments.getTopicId());
            allUserNewsInfo.setCommentCount(allUserNewsInfo.getCommentCount() + 1);
            allUserNewsInfoService.updateAllColumnById(allUserNewsInfo);
        } else if (userPics.equals(allUserComments.getEntityName())) {
            //个人视频
            AllUserPics allUserPics = allUserPicsService.selectById(allUserComments.getTopicId());
            allUserPics.setCommentCount(allUserPics.getCommentCount() + 1);
            allUserPicsService.updateAllColumnById(allUserPics);
        } else if (userSays.equals(allUserComments.getEntityName())) {
            //个人说说
            AllUserSays allUserSays = allUserSaysService.selectById(allUserComments.getTopicId());
            allUserSays.setCommentCount(allUserSays.getCommentCount() + 1);
            allUserSaysService.updateAllColumnById(allUserSays);
        } else if (userVideos.equals(allUserComments.getEntityName())) {
            //个人视频
            AllUserVideos allUserVideos = allUserVideosService.selectById(allUserComments.getTopicId());
            allUserVideos.setCommentCount(allUserVideos.getCommentCount() + 1);
            allUserVideosService.updateAllColumnById(allUserVideos);
        } else if (newsCharityOut.equals(allUserComments.getEntityName())) {
            //家族慈善财务收支
            FanNewsCharityOut fanNewsCharityOut = fanNewsCharityOutService.selectById(allUserComments.getTopicId());
            fanNewsCharityOut.setCommentCount(fanNewsCharityOut.getCommentCount() + 1);
            fanNewsCharityOutService.updateAllColumnById(fanNewsCharityOut);
        } else if (newsCharityIn.equals(allUserComments.getEntityName())) {
            //慈善公益捐款人
            FanNewsCharityPayIn fanNewsCharityPayIn = fanNewsCharityPayInService.selectById(allUserComments.getTopicId());
            fanNewsCharityPayIn.setCommentCount(fanNewsCharityPayIn.getCommentCount() + 1);
            fanNewsCharityPayInService.updateAllColumnById(fanNewsCharityPayIn);
        } else if (newsCultureNews.equals(allUserComments.getEntityName())) {
            //家族文化
            FanNewsCultureNews fanNewsCultureNews = fanNewsCultureNewsService.selectById(allUserComments.getTopicId());
            fanNewsCultureNews.setCommentCount(fanNewsCultureNews.getCommentCount() + 1);
            fanNewsCultureNewsService.updateAllColumnById(fanNewsCultureNews);
        } else if (newsCultureZipai.equals(allUserComments.getEntityName())) {
            //家族字派
            FanNewsCultureZipai fanNewsCultureZipai = fanNewsCultureZipaiService.selectById(allUserComments.getTopicId());
            fanNewsCultureZipai.setCommentCount(fanNewsCultureZipai.getCommentCount() + 1);
            fanNewsCultureZipaiService.updateAllColumnById(fanNewsCultureZipai);
        } else if (newsFamilyRecord.equals(allUserComments.getEntityName())) {
            //记录家族文章
            FanNewsFamilyRecord fanNewsFamilyRecord = fanNewsFamilyRecordService.selectById(allUserComments.getTopicId());
            fanNewsFamilyRecord.setCommentCount(fanNewsFamilyRecord.getCommentCount() + 1);
            fanNewsFamilyRecordService.updateAllColumnById(fanNewsFamilyRecord);
        } else if (newsFamilyRecordVedio.equals(allUserComments.getEntityName())) {
            //记录家族视频
            FanNewsFamilyRecordVedio fanNewsFamilyRecordVedio = fanNewsFamilyRecordVedioService.selectById(allUserComments.getTopicId());
            fanNewsFamilyRecordVedio.setCommentCount(fanNewsFamilyRecordVedio.getCommentCount() + 1);
            fanNewsFamilyRecordVedioService.updateAllColumnById(fanNewsFamilyRecordVedio);
        } else if (newsFamousAncestor.equals(allUserComments.getEntityName())) {
            //祖先分支
            FanNewsFamousAncestor fanNewsFamousAncestor = fanNewsFamousAncestorService.selectById(allUserComments.getTopicId());
            fanNewsFamousAncestor.setCommentCount(fanNewsFamousAncestor.getCommentCount() + 1);
            fanNewsFamousAncestorService.updateAllColumnById(fanNewsFamousAncestor);
        } else if (newsFamousPerson.equals(allUserComments.getEntityName())) {
            //家族名人
            FanNewsFamousPerson fanNewsFamousPerson = fanNewsFamousPersonService.selectById(allUserComments.getTopicId());
            fanNewsFamousPerson.setCommentCount(fanNewsFamousPerson.getCommentCount() + 1);
            fanNewsFamousPersonService.updateAllColumnById(fanNewsFamousPerson);
        } else if (newsIndustry.equals(allUserComments.getEntityName())) {
            //家族产业
            FanNewsIndustry fanNewsIndustry = fanNewsIndustryService.selectById(allUserComments.getTopicId());
            fanNewsIndustry.setCommentCount(fanNewsIndustry.getCommentCount() + 1);
            fanNewsIndustryService.updateAllColumnById(fanNewsIndustry);
        } else if (newsUploadTreeFile.equals(allUserComments.getEntityName())) {
            //电子家谱
            FanNewsUploadTreeFile fanNewsUploadTreeFile = fanNewsUploadTreeFileService.selectById(allUserComments.getTopicId());
            fanNewsUploadTreeFile.setCommentCount(fanNewsUploadTreeFile.getCommentCount() + 1);
            fanNewsUploadTreeFileService.updateAllColumnById(fanNewsUploadTreeFile);
        } else if (sysCharitableDeclare.equals(allUserComments.getEntityName())) {
            //慈善帮扶
            FanSysCharitableDeclare fanSysCharitableDeclare = fanSysCharitableDeclareService.selectById(allUserComments.getTopicId());
            fanSysCharitableDeclare.setCommentCount(fanSysCharitableDeclare.getCommentCount() + 1);
            fanSysCharitableDeclareService.updateAllColumnById(fanSysCharitableDeclare);
        } else if (charityOut.equals(allUserComments.getEntityName())) {
            //省级家族慈善财务收支
            ProNewsCharityOut proNewsCharityOut = proNewsCharityOutService.selectById(allUserComments.getTopicId());
            proNewsCharityOut.setCommentCount(proNewsCharityOut.getCommentCount() + 1);
            proNewsCharityOutService.updateAllColumnById(proNewsCharityOut);
        } else if (charityPayIn.equals(allUserComments.getEntityName())) {
            //省级慈善公益捐款人
            ProNewsCharityPayIn proNewsCharityPayIn = proNewsCharityPayInService.selectById(allUserComments.getTopicId());
            proNewsCharityPayIn.setCommentCount(proNewsCharityPayIn.getCommentCount() + 1);
            proNewsCharityPayInService.updateAllColumnById(proNewsCharityPayIn);
        } else if (cultureNews.equals(allUserComments.getEntityName())) {
            //省级家族文化
            ProNewsCultureNews proNewsCultureNews = proNewsCultureNewsService.selectById(allUserComments.getTopicId());
            proNewsCultureNews.setCommentCount(proNewsCultureNews.getCommentCount() + 1);
            proNewsCultureNewsService.updateAllColumnById(proNewsCultureNews);
        } else if (cultureZiPai.equals(allUserComments.getEntityName())) {
            //省级家族文化字派
            ProNewsCultureZipai proNewsCultureZipai = proNewsCultureZipaiService.selectById(allUserComments.getTopicId());
            proNewsCultureZipai.setCommentCount(proNewsCultureZipai.getCommentCount() + 1);
            proNewsCultureZipaiService.updateAllColumnById(proNewsCultureZipai);
        } else if (familyRecord.equals(allUserComments.getEntityName())) {
            //省级记录家族_文章
            ProNewsFamilyRecord proNewsFamilyRecord = proNewsFamilyRecordService.selectById(allUserComments.getTopicId());
            proNewsFamilyRecord.setCommentCount(proNewsFamilyRecord.getCommentCount() + 1);
            proNewsFamilyRecordService.updateAllColumnById(proNewsFamilyRecord);
        } else if (familyRecordVideo.equals(allUserComments.getEntityName())) {
            //省级记录家族_视频
            ProNewsFamilyRecordVedio proNewsFamilyRecordVedio = proNewsFamilyRecordVedioService.selectById(allUserComments.getTopicId());
            proNewsFamilyRecordVedio.setCommentCount(proNewsFamilyRecordVedio.getCommentCount() + 1);
            proNewsFamilyRecordVedioService.updateAllColumnById(proNewsFamilyRecordVedio);
        } else if (famousAncestor.equals(allUserComments.getEntityName())) {
            //省级祖先分支
            ProNewsFamousAncestor proNewsFamousAncestor = proNewsFamousAncestorService.selectById(allUserComments.getTopicId());
            proNewsFamousAncestor.setCommentCount(proNewsFamousAncestor.getCommentCount() + 1);
            proNewsFamousAncestorService.updateAllColumnById(proNewsFamousAncestor);
        } else if (famousPerson.equals(allUserComments.getEntityName())) {
            //省级家族名人
            ProNewsFamousPerson proNewsFamousPerson = proNewsFamousPersonService.selectById(allUserComments.getTopicId());
            proNewsFamousPerson.setCommentCount(proNewsFamousPerson.getCommentCount() + 1);
            proNewsFamousPersonService.updateAllColumnById(proNewsFamousPerson);
        }

    }

}

