package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.entity.FanSysSite;
import com.genogram.service.IFanSysSiteService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 *
 *@author: Toxicant
 *@date: 2018-11-26
 *@time: 14:19
 *@param:
 *@return:
 *@Description:
*/
@Api(description = "开通网站")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("genogram/admin/sysSite")
public class SysSiteController {

    @Autowired
    private IFanSysSiteService fanSysSiteService;

    @ApiOperation("开通网站")
    @RequestMapping(value = "insertSysSite", method = RequestMethod.POST)
    public Response<FanSysSite> insertSysSite(@ApiParam("token")@RequestParam(value = "token",required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "token不能为空");
        }


        return null;
    }
}
