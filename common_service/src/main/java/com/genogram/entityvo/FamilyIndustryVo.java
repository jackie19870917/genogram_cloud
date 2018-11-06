package com.genogram.entityvo;

import com.genogram.entity.FanNewsIndustry;
import com.genogram.entity.FanNewsUploadFile;
import lombok.Data;
import java.util.List;

//家族产业查询
@Data
public class FamilyIndustryVo extends FanNewsIndustry {

    //家族产业图片
    private List<FanNewsUploadFile> fanNewsUploadFileList;
}
