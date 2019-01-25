package com.genogram.controller;


import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;

import com.genogram.entity.PuPepoleInfo;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IPuPepoleInfoService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yizx
 * @since 2019-01-18
 */
@Api(description = "添加人物controller")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/puPepoleInfo")
public class PuPepoleInfoController {

    @Autowired
    private IPuPepoleInfoService puPepoleInfoService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    @ApiOperation(value = "添加人物信息 修改", notes = "puBaseInfo-谱实体")
    @RequestMapping(value = "addPuPepoleInfo", method = RequestMethod.POST)
    public Response<Boolean> addPuPepoleInfo(@RequestBody PuPepoleInfo puPepoleInfo,
                                             @ApiParam("基础表ID") @RequestParam(value = "id", required = false) Integer id,
                                             @ApiParam("token") @RequestParam(value = "token", required = false) String token) {
        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        if (StringUtils.isEmpty(puPepoleInfo)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "puPepoleInfo为空");
        }
        //判断id是否为空
        if (id == null) {
            return ResponseUtlis.error(Constants.IS_EMPTY, null);
        }
        //状态(0:删除;1:显示;)
        int isLive = 1;
        puPepoleInfo.setIsLive(isLive);
        Boolean aBoolean = puPepoleInfoService.addPuPepoleInfo(puPepoleInfo, userLogin, id);
        if (!aBoolean) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "失败");
        }
        return ResponseUtlis.error(Constants.SUCCESSFUL_CODE, "成功");
    }

}

