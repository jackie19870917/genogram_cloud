package com.genogram.controller;

import com.genogram.entity.AllUserLogin;
import com.genogram.mapper.AllFamilyMapper;
import com.genogram.service.ITestService;;
import com.genogram.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static com.genogram.config.Constants.IP_FAST_DFS;

@RequestMapping("/test")
@Controller
public class TestController {

    @Autowired
    ITestService iTestService;
    //将常量注入到controlle中
    @Autowired
    private UploadService uploadService;
    //联谊会家族字派查询
    @ResponseBody
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/test1",method = RequestMethod.POST)
    public AllUserLogin getAllUserLogin(){
        AllUserLogin allUserLogin = iTestService.getAllUserLogin();
        return allUserLogin;
    }
    //文件上传
//    @ResponseBody
//    @RequestMapping(value ="/upload",method = RequestMethod.POST)
//    public Map<String,Object> upload(MultipartFile file,Integer showid ,Integer newsid) throws Exception{
//        Map<String, Object> map = new HashMap<>();
//        try {
//            FastdfsClient client = new FastdfsClient("E:\\work\\genogram_cloud\\common_file_upload_service\\src\\main\\resources\\fastDFS.properties");
//            //获取到要上传文件对象的原始文件名(Original：原始的)
//            String oldName = file.getOriginalFilename();
//            //获取原始文件名中的扩展名(a.b.jpg)
//            String extName = oldName.substring(oldName.lastIndexOf(".") + 1);
//            //上传到fastDFS文件服务器
//            String path = client.uploadFile(file.getBytes(), extName);
//            //硬编码
//            path = IP_FAST_DFS + path;
//            String fastDFSpath = "http://192.168.2.132:8090/"+ path.substring(path.lastIndexOf("/")+1);
//            //fastDFSpath="ssss.xml;ssss.xml;rrr.pdf";
//            uploadService.addNews(showid , newsid, fastDFSpath);
//            System.out.println(path);
//            map.put("error", 0);
//            map.put("url", path);
//            return map;
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("error", 1);
//            map.put("message", "文件上传失败");
//            return map;
//        }
//    }
}
