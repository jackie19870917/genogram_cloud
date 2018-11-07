package com.genogram.entityvo;

import com.genogram.entity.FanNewsFamilyRecord;
import com.genogram.entity.FanNewsUploadFile;
import lombok.Data;

import java.util.List;
@Data
public class FamilyRecordVo extends FanNewsFamilyRecord {
    //记录家族图片
    private List<FanNewsUploadFile> fanNewsUploadFileList;
}
