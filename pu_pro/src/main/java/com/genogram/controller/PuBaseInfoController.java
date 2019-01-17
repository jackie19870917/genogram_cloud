package com.genogram.controller;


import com.genogram.config.Constants;
import com.genogram.entity.PuBaseInfo;
import com.genogram.service.IPuBaseInfoService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yizx
 * @since 2019-01-16
 */

@Api(description = "修谱controller")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/puBaseInfo")
public class PuBaseInfoController {

    @Autowired
    IPuBaseInfoService puBaseInfoService;


    @ApiOperation(value = "创建谱基本信息", notes = "puBaseInfo-谱实体")
    @RequestMapping(value = "addPuBaseInfo", method = RequestMethod.POST)
    public Response<Boolean> addPuBaseInfo(@RequestBody PuBaseInfo puBaseInfo) {

        if (StringUtils.isEmpty(puBaseInfo)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "puBaseInfo为空");
        }
        return ResponseUtlis.success( puBaseInfoService.insert(puBaseInfo));
    }


}

