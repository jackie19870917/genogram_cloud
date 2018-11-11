package com.genogram.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

public interface IUploadFastDfsService {
    Map<String,Object> uploadFastDfs(MultipartFile file);
}
