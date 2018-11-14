package com.genogram.entityvo;

import com.genogram.entity.FanNewsFamousPerson;
import com.genogram.entity.FanNewsUploadFile;
import lombok.Data;

import java.util.List;

@Data
public class FamilyPersonVo extends FanNewsFamousPerson {
    /**
     * 家族名人
     */
    private List<FanNewsUploadFile> fanNewsUploadFileList;
    /**
     * 用户名
     */
    private String createUserName;
    private String updateUserName;
    private long updateTimeLong;
    private long createTimeLong;
}
