package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.ConstantClassField;
import com.genogram.entity.FanIndexMessage;
import com.genogram.service.IFanIndexMessageService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    // 返回状态码 成功 200
    private Integer SUCCESSFUL_CODE = ConstantClassField.SUCCESSFUL_CODE;
    // 返回状态码 失败 500
    private Integer FAILURE_CODE = ConstantClassField.FAILURE_CODE;
    // 返回状态码 失败 400
    private Integer ERRO_CODE = ConstantClassField.ERRO_CODE;

    //联谊会首页聊天记录
    @RequestMapping(value = "/getChatRecord",method = RequestMethod.GET)
    public Response<FanIndexMessage> getChatRecord(
            @RequestParam(value = "siteId") Integer siteId, // 家族文化显示位置
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
            @RequestParam(value = "status", defaultValue = "1") Integer status) {
        try {
            Page<FanIndexMessage> fanIndexMessage= iFanIndexMessageService.getChatRecord(siteId, status,pageNo,pageSize);
            if (fanIndexMessage == null) {
                return ResponseUtlis.error(ERRO_CODE);
            }
            return ResponseUtlis.success(fanIndexMessage);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(FAILURE_CODE);
        }
    }

}

