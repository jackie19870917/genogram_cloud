package com.genogram.service;


import com.genogram.model.FileModel;

import java.util.List;

/**
 * @author chicheng
 * @date 2017/12/27
 */
public interface FileService {

    /**
     * 文件
     *
     * @param hashCode
     * @return
     */
    FileModel findFileModelByHashCode(String hashCode);

    /**
     * 查询所有key
     *
     * @return
     */
    List<String> findAllKeys();

    /**
     * 集合
     *
     * @param pathId
     * @return
     */
    List<String> getImageFilesOfPPT(String pathId);
}
