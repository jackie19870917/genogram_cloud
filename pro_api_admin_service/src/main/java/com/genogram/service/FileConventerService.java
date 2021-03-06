package com.genogram.service;

import com.genogram.model.FileModel;

/**
 * @author chicheng
 * @date 2017/12/28
 */
public interface FileConventerService {
    /**
     * 文件转换并存储
     *
     * @param fileModel
     */
    void conventer(FileModel fileModel);

}
