package com.genogram.controller;


import com.genogram.config.Constants;
import com.genogram.entity.AllMessageBoard;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.service.IFanMessageBoardService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-23
 */
@Api(description = "联谊会留言板")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/allMessageBoard")
public class FanMessageBoardController {
    @Autowired
    private IFanMessageBoardService iFanMessageBoardService;
    /**
     *联谊会留言板添加
     *@Author: yuzhou
     *@Date: 2018-11-10
     *@Time: 12:10
     *@Param:
     *@return:
     *@Description:
     */
    @ApiOperation(value = "留言板添加")
    @RequestMapping(value = "/addMessage", method = RequestMethod.POST)
    public Response<AllMessageBoard> addMessage(
            AllMessageBoard allMessageBoard,
            @RequestParam(value = "siteId")Integer siteId,
            @ApiParam("token")@RequestParam(value = "token",required = false)String token) {
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }
        int sourceType = 1;
        allMessageBoard.setSourceType(sourceType);
        try {
            // 插入数据
            boolean b = iFanMessageBoardService.addOrUpdateRecord(allMessageBoard);
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}

