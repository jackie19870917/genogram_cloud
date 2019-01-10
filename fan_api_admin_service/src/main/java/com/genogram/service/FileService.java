package com.genogram.service;


import com.genogram.model.FileModel;

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
     * @return List<String>
     * @Description: 查询所有key
     */
    List<String> findAllKeys();


    List<String> getImageFilesOfPPT(String pathId);
}
