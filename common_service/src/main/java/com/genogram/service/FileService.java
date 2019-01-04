package com.genogram.service;

import com.genogram.entity.FileModel;

import java.util.List;

/**
 * Created by chicheng on 2017/12/27.
 */
public interface FileService {

    /**
     * @param hashCode
     * @return FileModel
     */
    FileModel findFileModelByHashCode(String hashCode);

    /**
     * @Description: 查询所有key
     * @return List<String>
     */
    List<String> findAllKeys();


    List<String> getImageFilesOfPPT(String pathId);
}
