package com.genogram.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.genogram.controller.FastdfsClient;
import com.genogram.entity.FanNewsUploadFile;
import com.genogram.mapper.FanNewsUploadFileMapper;
import com.genogram.service.UploadFastdfsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static com.genogram.config.Constants.IP_FAST_DFS;

@Service
public class UploadFastdfsServicesImpl extends ServiceImpl<FanNewsUploadFileMapper, FanNewsUploadFile> implements UploadFastdfsService {
    @Override
    public Map<String,Object> uploadFastdfs(MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        //FanNewsUploadFile fanNewsUploadFile = new FanNewsUploadFile();
        try {
            FastdfsClient client = new FastdfsClient();
            //获取到要上传文件对象的原始文件名(Original：原始的)
            String oldName = file.getOriginalFilename();
            //获取原始文件名中的扩展名(a.b.jpg)
            String extName = oldName.substring(oldName.lastIndexOf(".") + 1);
            //上传到fastDFS文件服务器
            String path = client.uploadFile(file.getBytes(), extName);
            //硬编码
            path = IP_FAST_DFS + path;
            String fastDFSpath = "http://192.168.2.132:8090/"+ path.substring(path.lastIndexOf("/")+1);
            //上传
            //boolean insert = this.insertOrUpdate(fanNewsUploadFile);
            System.out.println(path);
            map.put("error", 0);
            map.put("url", path);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", 1);
            map.put("message", "文件上传失败");
            return map;
        }
    }
}
