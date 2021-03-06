package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserComments;
import com.genogram.entity.AllUserReply;
import com.genogram.entityvo.CommentVo;
import com.genogram.service.IAllUserCommentsService;
import com.genogram.service.IAllUserReplyService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author yizx
 * @since 2018-12-07
 */
@Api(description = "评论-维护controller")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/allUserComments")
public class AllUserCommentsController {

    @Autowired
    private IAllUserCommentsService allUserCommentsService;

    @Autowired
    IAllUserReplyService allUserReplyService;

    @ApiOperation(value = "获取评论列表", notes = "entityName-实体名,topicId-主题id,pageNo-页数,pageSize-每页显示条数")
    @RequestMapping(value = "getAllUserComments", method = RequestMethod.POST)
    public Response<Page<CommentVo>> getAllUserComments(@ApiParam("topicId") @RequestParam(value = "topicId", required = false) Integer topicId,
                                                        @ApiParam("entityName") @RequestParam("entityName") String entityName, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {

        if (StringUtils.isEmpty(topicId)) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "topicId为空");
        }
        if (StringUtils.isEmpty(entityName)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "serviceName为空");
        }
        Page<CommentVo> commentVoList = allUserCommentsService.getAllUserComments(topicId, entityName, pageNo, pageSize);
        if (StringUtils.isEmpty(commentVoList) || StringUtils.isEmpty(commentVoList.getRecords())) {
            return ResponseUtils.error(Constants.IS_EMPTY, "评论为空");
        } else {
            return ResponseUtils.success(commentVoList);
        }

    }

    @ApiOperation(value = "添加评论", notes = "allUserComments-评论实体")
    @RequestMapping(value = "addAllUserComments", method = RequestMethod.POST)
    public Response<Boolean> addAllUserComments(@RequestBody AllUserComments allUserComments) {

        if (StringUtils.isEmpty(allUserComments)) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "allUserComments为空");
        }
        Boolean flag = allUserCommentsService.insertAllUserComments(allUserComments);
        //判断是否添加成功 各个表添加评论数
        if (flag) {
            allUserCommentsService.addCommon(allUserComments);
        }
        return ResponseUtils.success(flag);
    }


    @ApiOperation(value = "添加回复", notes = "AllUserReply-回复实体")
    @RequestMapping(value = "addAllUserReply", method = RequestMethod.POST)
    public Response<Boolean> addAllUserReply(@RequestBody AllUserReply allUserReply) {

        if (StringUtils.isEmpty(allUserReply)) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "allUserReply为空");
        }
        Boolean flag = allUserReplyService.insertAllUserReply(allUserReply);
        return ResponseUtils.success(flag);
    }

    @ApiOperation(value = "删除评论", notes = "id-实体id")
    @RequestMapping(value = "delAllUserComments", method = RequestMethod.POST)
    public Response<Boolean> delAllUserComments(@ApiParam("id") @RequestParam(value = "id", required = false) Integer id) {

        if (StringUtils.isEmpty(id)) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "allUserReply为空");
        }
        Boolean flag = allUserCommentsService.delAllUserComments(id);
        if (flag) {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtils.success(Constants.ERROR_LOG);
        }

    }

    @ApiOperation(value = "删除回复", notes = "id-实体id")
    @RequestMapping(value = "delAllUserReply", method = RequestMethod.POST)
    public Response<Boolean> delAllUserReply(@ApiParam("id") @RequestParam(value = "id", required = false) Integer id) {

        if (StringUtils.isEmpty(id)) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "allUserReply为空");
        }
        Boolean flag = allUserReplyService.delAllUserReply(id);
        if (flag) {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtils.success(Constants.ERROR_LOG);
        }
    }

    @ApiOperation(value = "删除评论列表", notes = "id-实体id")
    @RequestMapping(value = "delAllUserCommentsList", method = RequestMethod.POST)
    public Response<CommentVo> delAllUserCommentsList(@RequestBody List<Integer> idList) {

        if (StringUtils.isEmpty(idList)) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "idList为空");
        }
        Integer count = allUserCommentsService.delAllUserCommentslist(idList);
        if (count > 0) {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtils.success(Constants.ERROR_LOG);
        }
    }
}

