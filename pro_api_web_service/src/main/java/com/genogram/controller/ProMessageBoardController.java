package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entity.AllMessageBoard;
import com.genogram.service.IAllCheckOutService;
import com.genogram.service.IProMessageBoardServices;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Administrator
 */
@Api(description = "省级留言板")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/allMessageBoard")
public class ProMessageBoardController {
    @Autowired
    private IProMessageBoardServices iProMessageBoardServices;

    @Autowired
    private IAllCheckOutService allCheckOutService;

    /**
     * 省级留言板添加
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
            @RequestParam(value = "siteId") Integer siteId,
            @ApiParam("token") @RequestParam(value = "token", defaultValue = "") String token) {
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }
        int sourceType = 2;
        allMessageBoard.setSourceType(sourceType);
        try {

            Set set = allCheckOutService.getSensitiveWord(allMessageBoard.getContent());

            if (set.size() >= 1) {
                return ResponseUtlis.error(Constants.SENSITIVE_WORD, "您输入的含有敏感词汇  ----    " + set);
            }

            // 插入数据
            boolean b = iProMessageBoardServices.addOrUpdateRecord(allMessageBoard);
            return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }
}
