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
 *  前端控制器
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
    @ApiOperation(value = "添加人物信息", notes = "puBaseInfo-谱实体")
    @RequestMapping(value = "addPuPepoleInfo", method = RequestMethod.POST)
    public Response<Boolean> addPuPepoleInfo(@RequestBody PuPepoleInfo puPepoleInfo,
                                           @ApiParam("token") @RequestParam(value = "token", required = false) String token) {
        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

       // AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());


        if (StringUtils.isEmpty(puPepoleInfo)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "puPepoleInfo为空");
        }


        return ResponseUtlis.success(puPepoleInfoService.insertOrUpdate(puPepoleInfo));
    }

}

