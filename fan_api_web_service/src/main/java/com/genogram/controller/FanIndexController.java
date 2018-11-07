package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanIndexMessage;
import com.genogram.service.IFanIndexMessageService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 联谊会首页-设置 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-11-05
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/fanIndex")
public class FanIndexController {

    @Autowired
    private IFanIndexMessageService iFanIndexMessageService;

    //联谊会首页聊天记录
    @RequestMapping(value = "/index/getChatRecordList",method = RequestMethod.GET)
    public Response<FanIndexMessage> getChatRecordList(
            @RequestParam(value = "siteId") Integer siteId
            ) {
        try {
            //状态
            int status =1;
            int pageNo=1;
            int pageSize=5;
            Page<FanIndexMessage> fanIndexMessage= iFanIndexMessageService.getChatRecordList(siteId, status,pageNo,pageSize);
            if (fanIndexMessage == null) {
                return ResponseUtlis.error(Constants.ERRO_CODE);
            }
            return ResponseUtlis.success(fanIndexMessage);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE);
        }
    }

}

