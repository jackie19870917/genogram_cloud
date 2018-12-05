package com.genogram.controller;

import com.genogram.service.IUploadFastDfsService;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtlis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author: wang, wei
 * @Date: 2018-11-11
 * @Time: 12:37
 * @return:
 * @Description:
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pro")
@Api(description = "省级上传")
public class UploadFastDfsController {
    @Autowired
    private IUploadFastDfsService uploadFastDfsService;
    @Autowired
    private IUploadFileService uploadFileService;

    /**
     * 文件上传
     *
     * @param file
     * @Author: wang, wei
     * @Date: 2018-11-11
     * @Time: 13:30
     * @return:
     * @Description:
     */
    @ApiOperation(value = "上传文件", notes = "返回文件src")
    @RequestMapping(value = "/uploadFastdfs", method = RequestMethod.POST)
    public Response<Map> uploadFastdfs(MultipartFile file) {
        return ResponseUtlis.success(uploadFastDfsService.uploadFastDfs(file));
    }
}
