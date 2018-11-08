package com.genogram.controller;

import com.genogram.entity.FanIndexInfo;
import com.genogram.service.IFanIndexInfoService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/fanIndex/")
public class FanIndexController {

    @Autowired
    private IFanIndexInfoService iFanIndexInfoService;

    //联谊会简介,宣言
    @RequestMapping(value = "getFanIndexInfo",method = RequestMethod.GET)
    public Response<FanIndexInfo> getFanIndexInfo(@RequestParam(value = "siteId",defaultValue = "1") Integer siteId) {

        FanIndexInfo fanIndexInfo = iFanIndexInfoService.getFanIndexInfo(siteId);

        return ResponseUtlis.success(fanIndexInfo);
    }

    //新增或修改    联谊会简介,宣言
    @RequestMapping(value = "insertOrUpdateFanIndexInfo", method = RequestMethod.POST)
    public Response<FanIndexInfo> insertOrUpdateFanIndexInfo(FanIndexInfo fanIndexInfo) {

        Integer integer = iFanIndexInfoService.insertOrUpdateFanIndexInfo(fanIndexInfo);

        return ResponseUtlis.success(200);
    }
}
