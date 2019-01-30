package com.genogram.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsUploadTreeFile;
import com.genogram.entity.ProNewsUploadTreeFile;
import com.genogram.service.IProNewsUploadTreeFileService;
import com.genogram.service.IUserService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 后端控制器
 * </p>
 *
 * @author wangwei
 * @since 2018-12-03
 */
@Api(description = "电子谱(前台)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/proNewsUploadTreeFile")
public class ProNewsUploadTreeFileController {

    @Autowired
    private IProNewsUploadTreeFileService proNewsUploadTreeFileService;

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "电子谱查询", notes = "id-主键,familyCode-姓氏,regionCode-地区,isFrom-来源(1-县级,2-省级),filePath-文件路径,fileName-文件名称,contactUser-联系人,status-状态(1-公开,2-密码访问,3-私密,0-删除),password-密码,preThirty-前三十页(1-显示,2-不显示)")
    @RequestMapping(value = "getProNewsUploadTreeFileList", method = RequestMethod.GET)
    public Response<ProNewsUploadTreeFile> getProNewsUploadTreeFileList(@ApiParam("网站ID") @RequestParam("siteId") Integer siteId,
                                                                        @ApiParam("地区") @RequestParam(value = "regionCode", required = false) String regionCode,
                                                                        @ApiParam("名称") @RequestParam(value = "fileName", required = false) String fileName,
                                                                        @ApiParam("状态") @RequestParam(value = "status", defaultValue = "0") Integer status,
                                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        List list = new ArrayList<>();

        if (status != 0) {
            list.add(status);
        } else {

            // 状态   1-公开  2-密码访问  3-私密  0-删除
            list.add(1);
            list.add(2);
        }

        Page newsUploadTreeFile = proNewsUploadTreeFileService.getProNewsUploadTreeFile(regionCode, fileName,siteId, list, pageNo, pageSize);

        if (StringUtils.isEmpty(newsUploadTreeFile)) {
            return ResponseUtlis.error(Constants.ERRO_CODE, null);
        }

        return ResponseUtlis.success(newsUploadTreeFile);
    }

    @ApiOperation(value = "电子谱详情", notes = "id-主键,familyCode-姓氏,regionCode-地区,isFrom-来源(1-县级,2-省级),filePath-文件路径,fileName-文件名称,contactUser-联系人,status-状态(1-公开,2-密码访问,3-私密,0-删除),password-密码,preThirty-前三十页(1-显示,2-不显示)")
    @RequestMapping(value = "getProNewsUploadTreeFile", method = RequestMethod.POST)
    public Response<ProNewsUploadTreeFile> getProNewsUploadTreeFile(@ApiParam("主键") @RequestParam("id") Integer id,
                                                                    @ApiParam("密码") @RequestParam(value = "password", required = false) String password,
                                                                    @ApiParam("来源") @RequestParam("isFrom") Integer isFrom,
                                                                    @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return ResponseUtlis.error(Constants.UNAUTHORIZED, "亲,您还没有登陆哟!");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtlis.error(Constants.FAILURE_CODE, "token错误");
        }

        Integer a = 2;
        if (isFrom.equals(a)) {
            ProNewsUploadTreeFile proNewsUploadTreeFile = proNewsUploadTreeFileService.getProNewsUploadTreeFile(id);

            Integer status = 2;
            if (proNewsUploadTreeFile.getStatus().equals(status)) {
                if (!proNewsUploadTreeFile.getPassword().equals(password)) {
                    return ResponseUtlis.error(Constants.ERRO_CODE, "密码错误");
                }
            }

            return ResponseUtlis.success(proNewsUploadTreeFile);

        } else {

            FanNewsUploadTreeFile fanNewsUploadTreeFile = proNewsUploadTreeFileService.getFanNewsUploadTreeFile(id);

            Integer status = 2;
            if (fanNewsUploadTreeFile.getStatus().equals(status)) {
                if (!fanNewsUploadTreeFile.getPassword().equals(password)) {
                    return ResponseUtlis.error(Constants.ERRO_CODE, "密码错误");
                }
            }

            return ResponseUtlis.success(fanNewsUploadTreeFile);
        }

    }
}

