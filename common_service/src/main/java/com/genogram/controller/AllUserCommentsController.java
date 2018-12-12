package com.genogram.controller;
import com.genogram.config.Constants;
import com.genogram.entityvo.CommentVo;
import com.genogram.service.IAllUserCommentsService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
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
 * @author wangwei
 * @since 2018-12-07
 */
@Api(description = "获取评论")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/allUserComments")
public class AllUserCommentsController {

    @Autowired
    private IAllUserCommentsService AllUserCommentsService;
    @ApiOperation(value = "评论列表", notes = "entityName-实体名,topicId-主题id")
    @RequestMapping(value = "getAllUserComments", method = RequestMethod.GET)
    public Response<List<CommentVo>> getAllUserComments(@ApiParam("topicId") @RequestParam(value = "topicId", required = false) Integer topicId,
                                                        @ApiParam("entityName") @RequestParam("entityName")  String entityName) {

        if (StringUtils.isEmpty(topicId)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "topicId为空");
        }
        if (StringUtils.isEmpty(entityName)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "serviceName为空");
        }
        List<CommentVo> commentVoList=AllUserCommentsService.getAllUserComments(topicId,entityName);
        return ResponseUtlis.success(commentVoList);
    }


}

