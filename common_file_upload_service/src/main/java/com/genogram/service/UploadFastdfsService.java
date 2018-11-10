package com.genogram.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadFastdfsService {
    Map<String,Object> uploadFastdfs(MultipartFile file);
}
