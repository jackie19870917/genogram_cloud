package com.genogram.controller;

import com.genogram.config.Constants;
import com.genogram.service.IUploadFastDfsService;
import com.genogram.service.IUploadFileService;
import com.genogram.unit.Response;
import com.genogram.unit.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
@RequestMapping("/fan")
@Api(description = "县级上传")
public class UploadFastDfsController {

    Logger log = LoggerFactory.getLogger(UploadFastDfsController.class);

    @Autowired
    private IUploadFastDfsService uploadFastDfsService;
    @Autowired
    private IUploadFileService uploadFileService;
    @Autowired
    private ConventerController conventerController;

    /**
     * 文件上传
     *
     * @param file
     * @param isGenealogy
     * @param response
     * @param request
     * @param model
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "上传文件", notes = "返回文件src")
    @RequestMapping(value = "/uploadFastdfs", method = RequestMethod.POST)
    public Response<Map> uploadFastdfs(MultipartFile file, @RequestParam(value = "isGenealogy", defaultValue = "0") Integer isGenealogy,
                                       HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        Map<String, Object> stringObjectMap = uploadFastDfsService.uploadFastDfs(file);
        log.info("isGenealogy==  " + isGenealogy);
        /*//if (isGenealogy != 100) {
        String file_path = (String) stringObjectMap.get("file_path");
        String[] split = file_path.split("\\.");
        int suffixIndex = split.length - 1;
        log.info("split[suffixIndex]==  " + split[suffixIndex]);
        if (split[suffixIndex].equals("doc") || split[suffixIndex].equals("docx") || split[suffixIndex].equals("pdf")) {
            //文件所在地址
            String filePath = Constants.ALIYUN_IP + file_path;
            //电子谱文件名称
            String treePreviewPath = conventerController.fileConventer(filePath, model, request, response);
            file_path = file_path + "@" + Constants.ELECTRONIC_SPECTRUM_PREVIEW_IP + treePreviewPath;
            stringObjectMap.put("file_path", file_path);
        }
        return ResponseUtlis.success(stringObjectMap);
    }*/
        if (isGenealogy != 0) {
            Object path = stringObjectMap.get("file_path");
            //文件所在地址
            String filePath = Constants.ALIYUN_IP + path;
            //电子谱文件名称
            String treePreviewPath = conventerController.fileConventer(filePath, model, request, response);
            path = path + "@" + Constants.ELECTRONIC_SPECTRUM_PREVIEW_IP + treePreviewPath;
            stringObjectMap.put("file_path", path);
        }
        return ResponseUtils.success(stringObjectMap);
    }
}
