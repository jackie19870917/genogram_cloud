package com.genogram.service;

public interface UploadService {
    boolean upLoadFile(String fileNames,Integer showid ,Integer newsid);
    boolean deleteFile(String ids);
}
