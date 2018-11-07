package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.FanIndexFamilySummarys;
import com.genogram.entity.FanIndexInfo;
import com.genogram.entity.FanIndexMessage;
import com.genogram.entityvo.CharityFundVo;
import com.genogram.service.IFanIndexFamilySummarysService;
import com.genogram.service.IFanIndexInfoService;
import com.genogram.service.IFanIndexMessageService;
import com.genogram.service.IFanNewsCharityService;
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

    @Autowired
    private IFanIndexFamilySummarysService iFanIndexFamilySummarysService;

    @Autowired
    private IFanNewsCharityService iFanNewsCharityService;

    @Autowired
    private IFanIndexInfoService iFanIndexInfoService;

    Integer status=1;

    //联谊堂
    @RequestMapping(value = "index/getFanIndexFamilySummarysPage", method = RequestMethod.GET)
    public Response<FanIndexFamilySummarys> getFanIndexFamilySummarysPage( @RequestParam(value = "siteId") Integer siteId,
                                                                           @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                                           @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        Page<FanIndexFamilySummarys> fanIndexFamilySummarysPage = iFanIndexFamilySummarysService.getFanIndexFamilySummarysPage(siteId, status, pageNo, pageSize);

        return ResponseUtlis.success(fanIndexFamilySummarysPage);
    }

    //联谊会简介,宣言
    @RequestMapping(value = "index/getFanIndexInfo",method = RequestMethod.GET)
    public Response<FanIndexInfo> getFanIndexInfo(@RequestParam(value = "siteId",defaultValue = "1") Integer siteId) {

        FanIndexInfo fanIndexInfo = iFanIndexInfoService.getFanIndexInfo(siteId);

        return ResponseUtlis.success(fanIndexInfo);
    }

    //联谊会首页聊天记录
    @RequestMapping(value = "/index/getChatRecordList",method = RequestMethod.GET)
    public Response<FanIndexMessage> getChatRecordList(
            @RequestParam(value = "siteId") Integer siteId
    ) {
        try {
            //判断showId是否有值
            if(siteId==null){
                return ResponseUtlis.error(Constants.IS_EMPTY,null);
            }
            //状态
            //状态
            int status =1;
            int pageNo=1;
            int pageSize=5;
            Page<FanIndexMessage> fanIndexMessage= iFanIndexMessageService.getChatRecordList(siteId, status,pageNo,pageSize);
            if (fanIndexMessage == null) {
                //没有取到参数,返回空参
                Page<FanIndexMessage> emptfamilyCultureVo = new Page<FanIndexMessage>();
                return ResponseUtlis.error(Constants.ERRO_CODE,emptfamilyCultureVo);
            }
            return ResponseUtlis.success(fanIndexMessage);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtlis.error(Constants.FAILURE_CODE,null);
        }
    }

}
