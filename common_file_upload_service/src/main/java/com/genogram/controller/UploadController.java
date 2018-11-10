package com.genogram.controller;

import com.genogram.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/uploadname")
@Controller
public class UploadController {
    @Autowired
    private UploadService uploadService;

    //文件上传
    @ResponseBody
    @RequestMapping(value ="/uploadname",method = RequestMethod.POST)
    public void upLoadToDbFile(String fileNames, Integer showid , Integer newsid) throws Exception {
        uploadService.upLoadFile(fileNames ,showid, newsid);
    }
    //文件删除
    @ResponseBody
    @RequestMapping(value ="/deletename",method = RequestMethod.POST)
    public void upLoadToDbFile(String ids) throws Exception {
        uploadService.deleteFile(ids
        );
    }
}
