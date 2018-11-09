package com.genogram.entityvo;

import com.genogram.entity.FanNewsCharityOut;
import com.genogram.entity.FanNewsUploadFile;
import lombok.Data;

import java.util.List;

/**
 *         慈善公益收支文章
 *@Author: Toxicant
 *@Date: 2018-11-09
 *@Time: 10:58
 *@Param:
 *@return:
 *@Description:
*/
@Data
public class FanNewsCharityOutVo extends FanNewsCharityOut {

    List<FanNewsUploadFile> fanNewsUploadFileList;
}
