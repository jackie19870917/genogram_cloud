package com.genogram.entityvo;

import com.genogram.entity.FanNewsFamousPerson;
import com.genogram.entity.FanNewsUploadFile;
import lombok.Data;

import java.util.List;

@Data
public class FamilyPersonVo extends FanNewsFamousPerson {
    //记录家族图片
    private List<FanNewsUploadFile> fanNewsUploadFileList;
}
