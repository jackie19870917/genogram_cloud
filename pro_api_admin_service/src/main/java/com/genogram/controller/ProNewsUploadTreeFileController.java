package com.genogram.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.genogram.config.Constants;
import com.genogram.entity.AllUserLogin;
import com.genogram.entity.FanNewsUploadTreeFile;
import com.genogram.entity.ProNewsUploadTreeFile;
import com.genogram.service.IAllUserLoginService;
import com.genogram.service.IProNewsUploadTreeFileService;
import com.genogram.service.IUserService;
import com.genogram.unit.DateUtil;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
@Api(description = "电子谱(后台)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/genogram/admin/proNewsUploadTreeFile")
public class ProNewsUploadTreeFileController {

    Logger log = LoggerFactory.getLogger(ProNewsUploadTreeFileController.class);

    @Autowired
    private IProNewsUploadTreeFileService proNewsUploadTreeFileService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAllUserLoginService allUserLoginService;

    private List getList() {

        List list = new ArrayList();
        /**
         * 角色权限 (0.不是管理员,1.县级管理员,2省级管理员,3.全国管理员,4县级副管理员,5省级副管理员,6全国副管理员,9.超级管理员)
         */
        list.add(2);
        list.add(5);
        list.add(9);

        return list;
    }

    @ApiOperation(value = "电子谱查询", notes = "id-主键,familyCode-姓氏,regionCode-地区,filePath-文件路径,fileName-文件名称,contactUser-联系人,status-状态(1-公开,2-密码访问,3-私密,0-删除),password-密码,preThirty-前三十页(1-显示,2-不显示)")
    @RequestMapping(value = "getProNewsUploadTreeFileList", method = RequestMethod.POST)
    public Response<ProNewsUploadTreeFile> getProNewsUploadTreeFileList(@ApiParam("网站ID") @RequestParam("siteId") Integer siteId,
                                                                        @ApiParam("token") @RequestParam(value = "token", required = false) String token,
                                                                        @ApiParam("地区") @RequestParam(value = "filename", required = false) String filename,
                                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        List list = new ArrayList<>();

        // 状态   1-公开  2-密码访问  3-私密  0-删除
        list.add(1);
        list.add(2);
        list.add(3);

        Page<ProNewsUploadTreeFile> proNewsUploadTreeFilePage = proNewsUploadTreeFileService.getProNewsUploadTreeFilePage(siteId, filename, list, pageNo, pageSize);

        return ResponseUtils.success(proNewsUploadTreeFilePage);
    }

    @ApiOperation(value = "电子谱详情", notes = "id-主键,familyCode-姓氏,regionCode-地区,isFrom-来源(1-县级,2-省级),filePath-文件路径,fileName-文件名称,contactUser-联系人,status-状态(1-公开,2-密码访问,3-私密,0-删除),password-密码,preThirty-前三十页(1-显示,2-不显示)")
    @RequestMapping(value = "getProNewsUploadTreeFile", method = RequestMethod.POST)
    public Response<ProNewsUploadTreeFile> getProNewsUploadTreeFile(@ApiParam("主键") @RequestParam("id") Integer id,
                                                                    // @ApiParam("密码") @RequestParam(value = "password", required = false) String password,
                                                                    @ApiParam("来源") @RequestParam(value = "isFrom", defaultValue = "2") Integer isFrom,
                                                                    @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        Integer a = 2;
        if (isFrom.equals(a)) {
            ProNewsUploadTreeFile proNewsUploadTreeFile = proNewsUploadTreeFileService.getProNewsUploadTreeFile(id);

            return ResponseUtils.success(proNewsUploadTreeFile);

        } else {

            FanNewsUploadTreeFile fanNewsUploadTreeFile = proNewsUploadTreeFileService.getFanNewsUploadTreeFile(id);

            return ResponseUtils.success(fanNewsUploadTreeFile);
        }

    }

    @ApiOperation("删除电子谱")
    @RequestMapping(value = "deleteProNewsUploadTreeFile", method = RequestMethod.POST)
    public Response<ProNewsUploadTreeFile> deleteProNewsUploadTreeFile(Integer id,
                                                                       @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        Boolean result = proNewsUploadTreeFileService.deleteProNewsUploadTreeFile(id, userLogin.getId());

        if (result) {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }

    @ApiOperation(value = "电子谱 新增/修改", notes = "id-主键,siteId-网站ID,familyCode-姓氏,regionCode-地区,isFrom-来源(1-县级,2-省级),filePath-文件路径,fileName-文件名称,contactUser-联系人,status-状态(1-公开,2-密码访问,3-私密,0-删除),password-密码,preThirty-前三十页(1-显示,2-不显示)")
    @RequestMapping(value = "updateProNewsUploadTreeFile", method = RequestMethod.POST)
    public Response<FanNewsUploadTreeFile> insertOrUpdateProNewsUploadTreeFile(ProNewsUploadTreeFile proNewsUploadTreeFile,
                                                                               @ApiParam("token") @RequestParam(value = "token", required = false) String token) {

        //  判断是否登陆
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.error(Constants.NOTLOGIN, "您还没有登陆");
        }

        AllUserLogin userLogin = userService.getUserLoginInfoByToken(token);

        if (StringUtils.isEmpty(userLogin)) {
            return ResponseUtils.error(Constants.FAILURE_CODE, "token错误");
        }

        AllUserLogin allUserLogin = allUserLoginService.getAllUserLoginById(userLogin.getId());

        //  判断是否有权限访问
        if (!this.getList().contains(allUserLogin.getRole())) {
            return ResponseUtils.error(Constants.UNAUTHORIZED, "您没有权限访问");
        }

        Timestamp timeStamp = DateUtil.getCurrentTimeStamp();

        if (proNewsUploadTreeFile.getId() == null) {
            proNewsUploadTreeFile.setCreateUser(userLogin.getId());
            proNewsUploadTreeFile.setIsFrom(2);
            proNewsUploadTreeFile.setCreateTime(timeStamp);
        }

        proNewsUploadTreeFile.setUpdateUser(userLogin.getId());
        proNewsUploadTreeFile.setUpdateTime(timeStamp);

        if (proNewsUploadTreeFile.getId() == null) {
            //文件上传就转换成jpg图片
            String[] split = proNewsUploadTreeFile.getFilePath().split("@");
            proNewsUploadTreeFile.setFilePath(split[0]);
            proNewsUploadTreeFile.setTreePreviewPath(split[1]);
        }

        //存储电子谱预览地址
        //http://192.168.2.122:8083/fileConventer?filePath=http://47.105.17 7.1:6090/00/01/rB_QCFwXNg-AdtcNAABhJxiBuv8808.doc
        //fanNewsUploadTreeFile.setTreePreviewPath(Constants.ELECTRONIC_SPECTRUM_PREVIEW_IP + fanNewsUploadTreeFile.getFilePath());
        /*String filePath = Constants.ALIYUN_IP + fanNewsUploadTreeFile.getFilePath();
        //电子谱文件名称
        String treePreviewPath = conventerController.fileConventer(filePath, model, request, response);
        fanNewsUploadTreeFile.setTreePreviewPath(Constants.ELECTRONIC_SPECTRUM_PREVIEW_IP + treePreviewPath);*/
        Boolean result = proNewsUploadTreeFileService.insertOrUpdateProNewsUploadTreeFile(proNewsUploadTreeFile);

        if (result) {
            return ResponseUtils.success(Constants.SUCCESSFUL_CODE);
        } else {
            return ResponseUtils.error(Constants.FAILURE_CODE, null);
        }
    }
}

