package com.genogram.entityvo;

import com.genogram.entity.FanNewsCharityOut;
import com.genogram.entity.FanNewsUploadFile;
import lombok.Data;

import java.util.List;

//慈善公益支出
@Data
public class FanNewsCharityOutVo extends FanNewsCharityOut {

    List<FanNewsUploadFile> fanNewsUploadFileList;
}
