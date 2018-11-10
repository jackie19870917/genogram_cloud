package com.genogram.controller;

import com.genogram.service.UploadFastdfsService;
import com.genogram.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static com.genogram.config.Constants.IP_FAST_DFS;

@RequestMapping("/uploadFastDFS")
@Controller
public class UploadFastDFSController {
    @Autowired
    private UploadFastdfsService uploadFastdfsService;
    //文件上传
    @ResponseBody
    @RequestMapping(value ="/upload",method = RequestMethod.POST)
    public void upload(MultipartFile file) throws Exception {
        uploadFastdfsService.uploadFastdfs(file);
    }
}
