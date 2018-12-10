package com.genogram.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsUploadTreeFile;
import com.genogram.service.IFanNewsUploadTreeFileService;
import com.genogram.service.IUserService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-12-04
 */
@Api(description = "电子谱(后台)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/fanNewsUploadTreeFile")
public class FanNewsUploadTreeFileController {

    @Autowired
    private IFanNewsUploadTreeFileService fanNewsUploadTreeFileService;

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "电子谱查询", notes = "id-主键,siteId-网站ID,regionCode-地区,isFrom-来源(1-省级,2-县级),filePath-文件路径,fileName-文件名称,contactUser-联系人,status-状态(1-公开,2-密码访问,3-私密,0-删除),password-密码,preThirty-前三十页(1-显示,2-不显示)")
    @RequestMapping(value = "getFanNewsUploadTreeFileList", method = RequestMethod.POST)
    public Response<FanNewsUploadTreeFile> getFanNewsUploadTreeFileList(@ApiParam("网站ID") @RequestParam("siteId") Integer siteId,
                                                                        @ApiParam("名称") @RequestParam(value = "fileName", required = false) String fileName,
                                                                        @ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "亲,您还没有登陆哟!");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        List list = new ArrayList<>();

        // 状态   1-公开  2-密码访问  3-私密  0-删除
        list.add(1);
        list.add(2);
        list.add(3);

        Page<FanNewsUploadTreeFile> fanNewsUploadTreeFilePage = fanNewsUploadTreeFileService.getFanNewsUploadTreeFile(siteId, fileName, list, pageNo, pageSize);

        if (StringUtils.isEmpty(fanNewsUploadTreeFilePage)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(fanNewsUploadTreeFilePage);
    }

    @ApiOperation(value = "电子谱详情", notes = "id-主键,siteId-网站ID,regionCode-地区,isFrom-来源(1-省级,2-县级),filePath-文件路径,fileName-文件名称,contactUser-联系人,status-状态(1-公开,2-密码访问,3-私密,0-删除),password-密码,preThirty-前三十页(1-显示,2-不显示)")
    @RequestMapping(value = "getFanNewsUploadTreeFile", method = RequestMethod.POST)
    public Response<FanNewsUploadTreeFile> getFanNewsUploadTreeFile(@ApiParam("主键") @RequestParam("id") Integer id,
                                                                    @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "亲,您还没有登陆哟!");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        FanNewsUploadTreeFile fanNewsUploadTreeFile = fanNewsUploadTreeFileService.getFanNewsUploadTreeFile(id);

        if (StringUtils.isEmpty(fanNewsUploadTreeFile)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }

        return ResponseUtlis.success(fanNewsUploadTreeFile);
    }

    @ApiOperation(value = "电子谱 新增/修改", notes = "id-主键,siteId-网站ID,regionCode-地区,isFrom-来源(1-省级,2-县级),filePath-文件路径,fileName-文件名称,contactUser-联系人,status-状态(1-公开,2-密码访问,3-私密,0-删除),password-密码,preThirty-前三十页(1-显示,2-不显示)")
    @RequestMapping(value = "uploadFanNewsUploadTreeFile", method = RequestMethod.POST)
    public Response<FanNewsUploadTreeFile> uploadFanNewsUploadTreeFile(FanNewsUploadTreeFile fanNewsUploadTreeFile,
                                                                       @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "亲,您还没有登陆哟!");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        if (fanNewsUploadTreeFile.getId() == null) {
            fanNewsUploadTreeFile.setCreateUser(userLogin.getId());
            fanNewsUploadTreeFile.setIsFrom(2);
            fanNewsUploadTreeFile.setCreateTime(timeStamp);
        }

        fanNewsUploadTreeFile.setUpdateUser(userLogin.getId());
        fanNewsUploadTreeFile.setUpdateTime(timeStamp);

        Boolean result = fanNewsUploadTreeFileService.insertOrUpdateFanNewsUploadTreeFile(fanNewsUploadTreeFile);

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

    @ApiOperation("电子谱删除")
    @RequestMapping(value = "deleteFanNewsUploadTreeFile", method = RequestMethod.POST)
    public Response<FanNewsUploadTreeFile> deleteFanNewsUploadTreeFile(@ApiParam("主键") @RequestParam("id") Integer id,
                                                                       @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "亲,您还没有登陆哟!");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        Boolean result = fanNewsUploadTreeFileService.deleteFanNewsUploadTreeFile(id, userLogin.getId());

        if (result) {
            return ResponseUtlis.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtlis.error(Constants.FAILURE_CODE, null);
        }
    }

}

