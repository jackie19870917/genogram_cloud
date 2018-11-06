package com.genogram.entityvo;

import com.genogram.entity.FanNewsCultureNews;
import com.genogram.entity.FanNewsUploadFile;
import lombok.Data;

import java.util.List;

//家族文化查询
@Data
public class FamilyCultureVo extends FanNewsCultureNews {

    //家族文化图片
    private List<FanNewsUploadFile> fanNewsUploadFileList;

}
