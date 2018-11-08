package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.entity.FanIndexFamilySummarys;
import com.genogram.entity.FanIndexInfo;
import com.genogram.entityvo.FanIndexInfoVo;
import com.genogram.service.IFanIndexFamilySummarysService;
import com.genogram.service.IFanIndexInfoService;
import com.genogram.service.IFanNewsUploadFileService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/fanIndex")
public class FanIndexController {

    @Autowired
    private IFanIndexInfoService iFanIndexInfoService;

    @Autowired
    private IFanNewsUploadFileService iFanNewsUploadFileService;

    @Autowired
    private IFanIndexFamilySummarysService iFanIndexFamilySummarysService;

    //联谊会简介,宣言
    @RequestMapping(value = "getFanIndexInfo",method = RequestMethod.GET)
    public Response<FanIndexInfoVo> getFanIndexInfo(@RequestParam("siteId") Integer siteId) {

        FanIndexInfoVo fanIndexInfoVo = iFanIndexInfoService.getFanIndexInfoVo(siteId);

        return ResponseUtlis.success(fanIndexInfoVo);
    }

    //新增或修改    联谊会简介,宣言
    @RequestMapping(value = "insertOrUpdateFanIndexInfo", method = RequestMethod.POST)
    public Response<FanIndexInfo> insertOrUpdateFanIndexInfo(FanIndexInfo fanIndexInfo) {

        Boolean aBoolean = iFanIndexInfoService.insertOrUpdateFanIndexInfo(fanIndexInfo);

        if (aBoolean) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }

    //联谊堂
    @RequestMapping(value = "getFanIndexFamilySummarysPage", method = RequestMethod.GET)
    public Response<FanIndexFamilySummarys> getFanIndexFamilySummarysPage(@RequestParam(value = "siteId") Integer siteId,
                                                                          @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Integer status = 1;
        Page<FanIndexFamilySummarys> fanIndexFamilySummarysPage = iFanIndexFamilySummarysService.getFanIndexFamilySummarysPage(siteId, status, pageNo, pageSize);

        return ResponseUtlis.success(fanIndexFamilySummarysPage);
    }

    //联谊堂详情
    @RequestMapping(value = "getFanIndexFamilySummarys", method = RequestMethod.GET)
    public Response<FanIndexFamilySummarys> getFanIndexFamilySummarys(@RequestParam(value = "id") Integer id) {

        FanIndexFamilySummarys fanIndexFamilySummarys = iFanIndexFamilySummarysService.getFanIndexFamilySummarys(id);

        return ResponseUtlis.success(fanIndexFamilySummarys);
    }

    //新增或修改    联谊堂
    @RequestMapping(value = "insertOrUpdateFanIndexFamilySummarys", method = RequestMethod.POST)
    public Response<FanIndexFamilySummarys> insertOrUpdateFanIndexFamilySummarys(FanIndexFamilySummarys fanIndexFamilySummarys) {

        Boolean aBoolean = iFanIndexFamilySummarysService.insertOrUpdateFanIndexFamilySummarys(fanIndexFamilySummarys);

        if (aBoolean) {
            return ResponseUtlis.success(200);
        } else {
            return ResponseUtlis.success(400);
        }
    }
}
