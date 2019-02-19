package com.genogram.controller;


import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;

import com.genogram.entity.PuPepoleInfo;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IPuPepoleInfoService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtils;
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
@Api(description = "家谱数据录入")
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

    @ApiOperation(value = "添加人物信息 新增修改", notes = "puBaseInfo-谱实体")
    @RequestMapping(value = "addPuPepoleInfo", method = RequestMethod.POST)
    public Response<Boolean> addPuPepoleInfo(@RequestBody PuPepoleInfo puPepoleInfo,
                                             @ApiParam("基础表ID") @RequestParam(value = "puBaseInfoId", required = false) Integer puBaseInfoId,
                                             @ApiParam("pepoleID 主键 根据这个主键增加pu_pepole_info数据") @RequestParam(value = "pepoleId", required = false) Integer pepoleId,
                                             @ApiParam("是否是根人物 isPepId 0=根人物 1=兄弟姐妹 2=配偶 3=女儿 4=儿子") @RequestParam(value = "isPepId", defaultValue = "0") Integer isPepId,
                                             @ApiParam("token") @RequestParam(value = "token", required = false) String token) {
        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        if (StringUtils.isEmpty(puPepoleInfo)) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "puPepoleInfo为空");
        }
        //判断id是否为空
        if (puBaseInfoId == null) {
            return ResponseUtils.error(Constants.IS_EMPTY, null);
        }
        //状态(0:删除;1:显示;)
        int isLive = 1;
        puPepoleInfo.setIsLive(isLive);
        Boolean aBoolean = puPepoleInfoService.addPuPepoleInfo(puPepoleInfo, userLogin, puBaseInfoId, pepoleId, isPepId);
        if (!aBoolean) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "失败");
        }

        return ResponseUtils.error(Constants.SUCCESSFUL_CODE, "成功");
    }

    @ApiOperation(value = "删除人物信息", notes = "")
    @RequestMapping(value = "deletePuPepoleInfo", method = RequestMethod.POST)
    public Response<Boolean> deletePuPepoleInfo(@ApiParam("pepoleID 主键 根据这个主键删除pu_pepole_info数据") @RequestParam(value = "id") Integer id,
                                             @ApiParam("token") @RequestParam(value = "token", required = false) String token){
        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }
        //判断ID是否为空
        if(id==null){
            return ResponseUtils.error(Constants.ERRO_CODE,"pepoleID为空");
        }

        Boolean aBoolean =puPepoleInfoService.deletePuPepoleInfo(id);
        if (!aBoolean) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "删除失败");
        }

        return ResponseUtils.error(Constants.SUCCESSFUL_CODE, "删除成功");
    }

}

