package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.AllMessageBoard;
import com.genogram.service.IFanMessageBoardService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@Api(description = "联谊会留言板查询")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/allMessageBoard")
public class FanMessageBoardController {
    @Autowired
    private IFanMessageBoardService iFanMessageBoardService;

    @ApiOperation(value = "留言板查询")
    @RequestMapping(value = "/selectMessage", method = RequestMethod.GET)
    public Response<AllMessageBoard> getMessageBoardDetail(
            @RequestParam(value = "site_id") Integer siteId,
            @RequestParam(value = "source_type") Integer sourceType,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize
    ) {
        Page<AllMessageBoard> allMessageBoardPage = iFanMessageBoardService.getMessageBoard(siteId,sourceType,pageNo, pageSize);
       return ResponseUtlis.success(allMessageBoardPage);
    }
}
