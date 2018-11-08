package com.genogram.entityvo;

import com.genogram.entity.FanNewsFamilyRecordVedio;
import com.genogram.entity.FanNewsUploadVedio;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Data
public class FamilyRecordVedioVo extends FanNewsFamilyRecordVedio {
    //记录家族视频
    /**
     * 视频概要
     */
    private List<FanNewsUploadVedio> fanUploadVedioList;
}
