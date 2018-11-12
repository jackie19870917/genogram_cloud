package com.genogram.service.impl;

import com.genogram.client.FastdfsClient;
import com.genogram.service.IUploadFastDfsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class UploadFastDfsImpl implements IUploadFastDfsService {
    @Value("${fan_api_admin_service.fastDfs_ip}")
    private String fastDfsIp;
    @Override
    public Map<String, Object> uploadFastDfs(MultipartFile file) {
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
            path = path.replaceAll("group1/M00/","");
            String fastDfsPath = fastDfsIp+ path;
            System.out.println(fastDfsPath);
            map.put("ok", "upload_success");
            map.put("url", path);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "upload_error");
            map.put("message", "文件上传失败");
            return map;
        }
    }
}
