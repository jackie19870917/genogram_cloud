package com.genogram.controller;


import com.genogram.config.Constants;
import com.genogram.entity.AllMessageBoard;
import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.service.IAllCheckOutService;
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

import java.util.Set;

/**
 * 联谊会留言板
 *
 * @author wangwei
 * @since 2018-11-23
 */
@Api(description = "联谊会留言板")
@RestController
@CrossOrigin
@RequestMapping("/genogram/allMessageBoard")
public class FanMessageBoardController {
    @Autowired
    private IFanMessageBoardService iFanMessageBoardService;

    @Autowired
    private IAllCheckOutService allCheckOutService;

    /**
     * 联谊会留言板添加
     *
     * @Author: yuzhou
     * @Date: 2018-11-10
     * @Time: 12:10
     * @Param:
     * @return:
     * @Description:
     */
    @ApiOperation(value = "留言板添加")
    @RequestMapping(value = "/addMessage", method = RequestMethod.POST)
    public Response<AllMessageBoard> addMessage(
            AllMessageBoard allMessageBoard,
            @RequestParam(value = "siteId") String siteId) {
        int sourceType = 1;
        String createUser = null;
        String updateUser = null;
        allMessageBoard.setUpdateUser(updateUser);
        allMessageBoard.setCreateUser(createUser);
        allMessageBoard.setSourceType(sourceType);
        try {

            // 插入数据
            Set set = allCheckOutService.getSensitiveWord(allMessageBoard.getContent());

            if (set.size() >= 1) {
                return ResponseUtlis.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set);
            }

            boolean b = iFanMessageBoardService.addOrUpdateRecord(allMessageBoard);
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}

