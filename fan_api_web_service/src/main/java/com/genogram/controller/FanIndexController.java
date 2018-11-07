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
    @RequestMapping(value = "/index/getChatRecordPage",method = RequestMethod.GET)
    public Response<FanIndexMessage> getChatRecord(
            @RequestParam(value = "siteId") Integer siteId,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
            ) {
        try {
            //状态
            int status =1;
            Page<FanIndexMessage> fanIndexMessage= iFanIndexMessageService.getChatRecord(siteId, status,pageNo,pageSize);
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

