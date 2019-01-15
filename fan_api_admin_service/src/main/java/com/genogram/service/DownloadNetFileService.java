package com.genogram.service;


import com.genogram.model.FileModel;

/**
 * @author Toxicant
 */
public interface DownloadNetFileService {

    /**
     * 下载文件, 状态改变, 下载文件路径, 会回写到fileModel中
     *
     * @param fileModel
     */
    public void download(FileModel fileModel);

}
