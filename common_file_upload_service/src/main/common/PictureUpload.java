package org.java.controller;

import java.util.HashMap;
import java.util.Map;

import org.java.common.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PictureUpload {
	//将常量注入到controlle中
	@Value("${IMAGE_SERVER_PATH}")
	private String IMAGE_SERVER_PATH;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public Map<String,Object> upload(MultipartFile uploadFile) throws Exception{
		Map<String, Object> map = new HashMap<>();
		try {
			FastDFSClient client = new FastDFSClient("classpath:resource/fdfs_client.conf");
			//获取到要上传文件对象的原始文件名(Original：原始的)
			String oldName = uploadFile.getOriginalFilename();
			//获取原始文件名中的扩展名(a.b.jpg)
			String extName = oldName.substring(oldName.lastIndexOf(".") + 1);
			//上传到fastDFS文件服务器
			String path = client.uploadFile(uploadFile.getBytes(), extName);
			//真实服务器访问文件的地址：http://192.168.25.133/group1/M00/00/02/wKgZhVtmYzGARW8-AAD64mz9OmY836.jpg
			//硬编码
			path = IMAGE_SERVER_PATH + path;
			map.put("error", 0);
			map.put("url", path);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", 1);
			map.put("message", "图片上传失败");
			return map;
		}
	}
}
