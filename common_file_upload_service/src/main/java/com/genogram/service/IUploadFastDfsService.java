package com.genogram.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

/**
 * @author Administrator
 */
public interface IUploadFastDfsService {
    /**
     * 文件上传
     * @param file
     * @return
     */
    Map<String,Object> uploadFastDfs(MultipartFile file);
}
